#!/bin/bash

# Should be run as root!

apt-get install unzip mysql-server mysql-client build-essential libxslt-dev libxml2-dev libmysqlclient-dev libreadline6 libreadline6-dev git-core zlib1g zlib1g-dev libssl-dev libyaml-dev libsqlite3-dev sqlite3 autoconf libc6-dev ncurses-dev automake libtool bison subversion ruby-b
undler

# note - rvm 2.0 should be already prior to running the script
# Install RVM
# curl -L get.rvm.io | bash -s stable
# source ~/.rvm/scripts/rvm
# rvm install 1.8.7
# rvm use 1.8.7 --default

echo "Please fill in the information about the maycamp user (as you like)"
adduser maycamp
usermod -a -G root maycamp

MAYCAMP_HOME=/home/maycamp

cd $MAYCAMP_HOME
mkdir -p maycamp
mkdir -p sandbox

cd maycamp
cp -R /home/system/dev/grading_ecosystem/source/projects/maycamp_arena-fork/* .

cd $MAYCAMP_HOME
chown -R maycamp:maycamp .
chmod -R 755 .


echo "\
`hostname`:
  host: localhost
  root: $MAYCAMP_HOME/sandbox
  user: `whoami`
  rsync: $MAYCAMP_HOME/maycamp/sets
" > config/grader.yml

# Setup the app

bundle install --deployment

bundle exec rake db:create
bundle exec rake db:migrate
bundle exec unicorn_rails -c config/unicorn.conf.rb -E production -D

# Start the grader
nohup bundle exec rake RAILS_ENV=production grader:start &

echo "The web server is started with"
echo "cd ~/maycamp; bundle exec unicorn_rails -c config/unicorn.conf.rb -E production -D"
echo ""
echo "The grader is started with"
echo "cd ~/maycamp; nohup bundle exec rake RAILS_ENV=production grader:start &"
echo ""
echo "You can add these to your /etc/rc.local"
echo ""
echo "It is also recommended to put a web server in front of the app, to handle keepalives and slow clients. Nginx is a good choice, because it scales better."
