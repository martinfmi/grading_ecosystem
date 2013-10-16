#!/bin/bash -verbose

pause() {
	echo "If everything is ok, press enter, otherwise cancel it with ctlr+c..."
	#read
}

#Should be run as root!
#there is no uninstaller so read it before running!

apt-get install launchtool coreutils libapache2-mod-perl2 apache2 mysql-server mysql-client subversion
# enable mod_proxy, mod_proxy_http and mod_headers
a2enmod headers
a2enmod proxy
a2enmod proxy_http

cpan Dancer Plack::Handler::Apache2;

echo "Please fill in the information about the spoj0 user (as you like)"
adduser spoj0
echo "Please fill in the information about the spoj0-exec user (as you like)"
adduser spoj0run

usermod -a -G spoj0,spoj0run,root www-data

pause

cd /home/spoj0

echo "stopping if old deamon is running"
./spoj0-control.pl stop

echo "note that this creates working svn version of the system, so you can submit changes back"
cp -R /home/system/dev/grading_ecosystem/source/projects/spoj0-fork/* .
#mkdir public_html
#ln -s /home/spoj0/web public_html

pause

chown -R spoj0:spoj0 .
chmod 755 *.pl
chmod 755 *.sh

cd /home/spoj0/web
chmod 755 *.pl

cd /home/spoj0
chmod -R 775 .

cd /home/spoj0run
chmod -R 775 .

cd /home/spoj0cp -R ./services/* /home/spoj0/services
pause

echo "Please enter the administrative password for mysql."
mysql -p < spoj0.sql

pause

cd /home/spoj0

echo "Trying to add site to apache..."

cat > /etc/apache2/sites-available/spoj0 <<EOT
Alias /spoj0 /home/spoj0/web/
<Directory /home/spoj0/web/>
  allow from all
  AddHandler perl-script .pl
  PerlHandler ModPerl::PerlRun
  Options Indexes FollowSymlinks MultiViews ExecCGI
</Directory>
ProxyPass /spoj0/api/ http://localhost:3000/
ProxyPassReverse /spoj0/api/ http://localhost:3000/
<Proxy *>
  Order allow,deny
  Allow from all
</Proxy>
<Location /spoj0/api/>
  RequestHeader set Request-Base /spoj0/api
</Location>
EOT

chmod 777 /etc/apache2/envvars
cat >> /etc/apache2/envvars <<EOT
export PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"
EOT
chmod 644 /etc/apache2/envvars

ln -s /etc/apache2/sites-available/spoj0 /etc/apache2/sites-enabled/555-spoj0

pause

echo "add the two sets"

cd /home/spoj0
./spoj0-control.pl import-set test1-fmi-2007-03-04
./spoj0-control.pl import-set test2-fmi-2007-03-04
./spoj0-control.pl import-set fmi-2007-03-18
./spoj0-control.pl import-set trivial

echo "NOTE: if you have old problem sets, you have to copy them manually"
pause

echo "if you want to add users or something else now, write a file named users.sql or custom.sql and put it in /home/spoj0/ and press enter"
read

mysql spoj0 < users.sql

#mysqldump -c --default-character-set=latin1 --set-charset spoj -t runs > custom.sql

mysql spoj0 < custom.sql




pause 
echo "make some test submits"

cd /home/spoj0
#spoj0-submit $problem_id $user_id $source_fn [$about]

echo "Sumbiting test sources!"

./spoj0-control.pl submit 1 1 ./test/ce.cpp cpp ce
./spoj0-control.pl submit 1 2 ./test/p1_pe.cpp cpp p1_pe
./spoj0-control.pl submit 1 3 ./test/re.cpp cpp re
./spoj0-control.pl submit 1 4 ./test/tl.cpp cpp tl
./spoj0-control.pl submit 1 5 ./test/tl_flood.cpp cpp tl_flood
./spoj0-control.pl submit 1 6 ./test/tl_sleep.cpp cpp tl_sleep
./spoj0-control.pl submit 1 7 ./test/wa.cpp cpp wa
./spoj0-control.pl submit 1 8 ./test/wa_noop.cpp cpp wa_noop
./spoj0-control.pl submit 1 9 ./sets/test1-fmi-2007-03-04/a/solution.cpp cpp p1_sol

./spoj0-control.pl submit 2 1 ./test/ce.cpp cpp ce
./spoj0-control.pl submit 3 2 ./test/p1_pe.cpp cpp p1_pe
./spoj0-control.pl submit 4 3 ./test/re.cpp cpp re
./spoj0-control.pl submit 5 4 ./test/tl.cpp cpp tl
./spoj0-control.pl submit 6 5 ./test/tl_flood.cpp cpp tl_flood
./spoj0-control.pl submit 7 6 ./test/tl_sleep.cpp cpp tl_sleep
./spoj0-control.pl submit 8 7 ./test/wa.cpp cpp wa
./spoj0-control.pl submit 9 8 ./test/wa_noop.cpp cpp wa_noop
./spoj0-control.pl submit 10 9 ./sets/test1-fmi-2007-03-04/a/solution.cpp cpp p1_sol

./spoj0-control.pl submit 1 9 ./sets/test1-fmi-2007-03-04/a/solution.cpp cpp p1_sol
./spoj0-control.pl submit 2 2 ./sets/test1-fmi-2007-03-04/b/solution.cpp cpp p2_sol
./spoj0-control.pl submit 3 9 ./sets/test1-fmi-2007-03-04/c/solution.cpp cpp p3_sol
./spoj0-control.pl submit 4 4 ./sets/test1-fmi-2007-03-04/d/solution.cpp cpp p4_sol
./spoj0-control.pl submit 5 9 ./sets/test1-fmi-2007-03-04/e/solution.cpp cpp p5_sol
./spoj0-control.pl submit 6 9 ./sets/test1-fmi-2007-03-04/f/solution.cpp cpp p6_sol
./spoj0-control.pl submit 7 7 ./sets/test1-fmi-2007-03-04/g/solution.cpp cpp p7_sol
./spoj0-control.pl submit 8 9 ./sets/test1-fmi-2007-03-04/h/solution.cpp cpp p8_sol


./spoj0-control.pl submit 2 1 ./test/ce.cpp cpp ce
./spoj0-control.pl submit 3 2 ./test/p1_pe.cpp cpp p1_pe
./spoj0-control.pl submit 4 3 ./test/re.cpp cpp re
./spoj0-control.pl submit 5 4 ./test/tl.cpp cpp tl
./spoj0-control.pl submit 6 5 ./test/tl_flood.cpp cpp tl_flood
./spoj0-control.pl submit 7 6 ./test/tl_sleep.cpp cpp tl_sleep
./spoj0-control.pl submit 8 7 ./test/wa.cpp cpp wa
./spoj0-control.pl submit 9 8 ./test/wa_noop.cpp cpp wa_noop
./spoj0-control.pl submit 10 9 ./sets/test1-fmi-2007-03-04/a/solution.cpp cpp p1_sol


./spoj0-control.pl submit 1 1 ./sets/test1-fmi-2007-03-04/a/solution.cpp cpp p1_sol
./spoj0-control.pl submit 2 1 ./sets/test1-fmi-2007-03-04/b/solution.cpp cpp p2_sol
./spoj0-control.pl submit 3 2 ./sets/test1-fmi-2007-03-04/c/solution.cpp cpp p3_sol
./spoj0-control.pl submit 4 3 ./sets/test1-fmi-2007-03-04/d/solution.cpp cpp p4_sol
./spoj0-control.pl submit 5 1 ./sets/test1-fmi-2007-03-04/e/solution.cpp cpp p5_sol
./spoj0-control.pl submit 6 2 ./sets/test1-fmi-2007-03-04/f/solution.cpp cpp p6_sol
./spoj0-control.pl submit 7 5 ./sets/test1-fmi-2007-03-04/g/solution.cpp cpp p7_sol
./spoj0-control.pl submit 8 3 ./sets/test1-fmi-2007-03-04/h/solution.cpp cpp p8_sol

#27 - hello
#28 - a+b
./spoj0-control.pl submit 27 5 ./test/hello_ok.java java hello_ok.java
./spoj0-control.pl submit 27 5 ./test/hello_pe.java java hello_pe.java
./spoj0-control.pl submit 27 5 ./test/hello_re.java java hello_re.java
./spoj0-control.pl submit 27 5 ./test/hello_tl.java java hello_tl.java
./spoj0-control.pl submit 27 5 ./test/hello_wa.java java hello_wa.java
./spoj0-control.pl submit 28 5 ./test/ab_ok.java java ab_ok.java
./spoj0-control.pl submit 27 5 ./test/hello_ok.cpp cpp hello_ok.cpp
./spoj0-control.pl submit 27 5 ./test/hello_pe.cpp cpp hello_pe.cpp
./spoj0-control.pl submit 28 5 ./test/ab_ok.cpp cpp ab_ok.cpp
./spoj0-control.pl submit 28 5 ./test/ab_pe.cpp cpp ab_pe.cpp
./spoj0-control.pl submit 28 5 ./test/ab_wa.cpp cpp ab_wa.cpp

pause

/etc/init.d/apache2 force-reload

pause

./spoj0-control.pl sync-news

echo "Running deamon!"
./spoj0-control.pl start

echo "Running Dancer web services"
./spoj0-control.pl start-web-services
