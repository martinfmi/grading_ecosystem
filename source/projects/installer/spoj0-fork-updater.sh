#!/bin/bash -verbose

spoj0dir=/home/system/dev/grading_ecosystem/source/projects/spoj0-fork
homedir=/home/spoj0
cd $spoj0dir

cp -R ./news/* /home/spoj0/news
cp -R ./sets/* /home/spoj0/sets
cp -R ./test/* /home/spoj0/test
cp -R ./web/* /home/spoj0/web
cp ./spoj0-control.pl /home/spoj0/
cp ./spoj0-daemon.pl /home/spoj0/
cp ./spoj0-grade.pl /home/spoj0/
cp ./spoj0.pm /home/spoj0/
cp ./test.pl /home/spoj0/

cd $homedir
perl ./spoj0-control.pl stop
perl ./spoj0-control.pl stop-web-services
/etc/init.d/apache2 force-reload
sleep 2;
perl ./spoj0-control.pl start
perl ./spoj0-control.pl start-web-services
cd $spoj0dir
