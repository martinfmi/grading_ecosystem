require "shell_utils"
require "sets_sync"

class Grader
  include ShellUtils
  attr_reader :root, :user, :runner, :tests_updated_at, :grader_app
  
  LANG_TO_COMPILER = {}

  class << self
    def with_stdout_and_stderr(new_stdout, new_stderr, &block)
      old_stdout, old_stderr = $stdout.dup, $stderr.dup
      STDOUT.reopen(new_stdout)
      STDERR.reopen(new_stderr)
      
      yield
    ensure
      STDOUT.reopen(old_stdout)
      STDERR.reopen(old_stderr)
    end
  end

  def initialize(root, user)
    @root = root
    @user = user
    
    sync_tests(Time.now)
  end
  
  def run
    running = true
    puts "Ready to grade"
    
    while running do
      ["INT", "TERM"].each do |signal|
        Signal.trap(signal) do
          puts "Stopping..."
          running = false
        end
      end
      
      check_durty_tests
      
      if run = Run.where(:status => Run::WAITING).order("runs.created_at ASC").first
        grade(run)
      elsif run = Run.where(:status => Run::CHECKING).order("runs.created_at ASC").first
        grade(run, 1)
      else
        sleep 1
      end
    end
  end

  def benchmark(samples = 100)
    puts "Benchmarking machine"
    check_durty_tests
    
    total_runs = 0
    total_factors = 0
    
    total_factor = Run.where(:total_points => 100).where("max_time > 0").order("RAND()").limit(samples).each do |run|
      original_time = run.max_time
      original_points = run.total_points

      grade(run, nil, true)

      run.update_time_and_mem
      run.update_total_points
      
      next if original_points != run.total_points
      
      total_factors += original_time / run.max_time
      total_runs += 1
    end
    
    puts "The bech factor of the machine is #{total_factors / total_runs}"
  end

  def grade(run, tests = nil, dry_run = false)
    tests ||= run.problem.number_of_tests
    update_attributes(run, dry_run, :status => Run::JUDGING)

    puts "Judging run with id #{run.id}"
    @runner = Pathname.new(File.join(File.dirname(__FILE__), "../ext/runner_#{run.problem.contest.runner_type}.rb")).realpath.to_s
    
    Dir.chdir @root do
      File.open("grader.log", "w") do |f|
        f.sync = true
        self.class.with_stdout_and_stderr(f, f) do
          # Compile
          compile(run)

          if $?.exitstatus != 0
            update_attributes(run, dry_run, :status => (["ce"] * tests).join(" "), :log => File.read("grader.log"))
            next
          end

          status = run_tests(run, tests)
          puts "final result: #{status.inspect}"
          
          update_attributes(run, dry_run, :status => status, :log => File.read("grader.log"))
        end
      end
    end
  end
  
  private
    
    def compile(run)
      File.open("program.cpp", "w") do |f|
        f.write(run.source_code)
      end
      
      puts "Compiling..."
      verbose_system "g++ program.cpp -o program -O2 -static -lm -x c++"
    end
    
    def run_tests(run, tests)
      # for each test, run the program
      run.problem.input_files[0...tests].zip(run.problem.output_files).map { |input_file, answer_file|
        verbose_system "#{@runner} --user #{@user} --time #{run.problem.time_limit.to_f} --mem #{run.problem.memory_limit} --procs 1 -i #{input_file} -o output -- ./program"
        
        case $?.exitstatus
          when 9
            "tl"
          when 127
            "ml"
          when 0
            check_output(run, answer_file, input_file)
          else
            "re"
        end
      }.join(" ")
    end

    def sync_tests(update_time)
      SetsSync.sync_sets(get_config)
      @tests_updated_at = update_time
      puts "Tests synced for time #{@tests_updated_at} on #{Time.now}"
    end

    def check_durty_tests
      if (last_update = Configuration.get(Configuration::TESTS_UPDATED_AT)) and last_update > @tests_updated_at
        # Download the tests again
        puts "Tests changed at #{last_update}, while the current version is from #{@tests_updated_at}. Syncing..."
        sync_tests(last_update)
      end
    end
    
    def check_output(run, answer_file, input_file)
      if checker = run.problem.checker
        verbose_system "#{checker} #{input_file} #{answer_file} output"
      else
        checker = File.join(RAILS_ROOT, "ext/diff.rb")
        verbose_system "#{checker} #{run.problem.diff_parameters} #{answer_file} output"
      end
      
      if $?.exitstatus != 0
        "wa"
      else
        "ok"
      end
      
    end

    def update_attributes(run, dry_run, attrs)
      run.attributes = attrs
      
      run.save unless dry_run
    end
end
