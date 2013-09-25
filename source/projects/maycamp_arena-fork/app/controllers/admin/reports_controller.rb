class Admin::ReportsController < Admin::BaseController
  def show
    @daily_submits_report = Run.count(:id, 
                                      :conditions => ["created_at > ?", 3.weeks.ago.to_s(:db)], 
                                      :select => "id",
                                      :group => "DATE_FORMAT(created_at, '%m/%d')")

    @total_submits_report = Run.count(:id, 
                                      :select => "id",
                                      :group => "DATE_FORMAT(created_at, '%Y/%m/%d')")

    @contest_submit_report = Run.connection.select_all("SELECT count(*) AS count_all, contests.name AS contests_name 
                                      FROM `runs`
                                      INNER JOIN `problems` ON `problems`.id = `runs`.problem_id
                                      INNER JOIN `contests` ON `contests`.id = `problems`.contest_id
                                      GROUP BY contests.id
                                      ORDER BY contests.created_at").inject([]) { |sum, a| sum << [a["contests_name"], a["count_all"]] }
  end
end
