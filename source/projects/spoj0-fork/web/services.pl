#!/usr/bin/perl

use lib "/home/spoj0";
use spoj0;
use Dancer;
use Dancer::Serializer::JSON;
use MIME::Base64 qw( decode_base64 );
use Digest::MD5 qw(md5_hex);

set serializer   => 'JSON';
set logger       => 'file';
set behind_proxy => 1;        # useful since Dancer runs behind apache proxy

my $dbh = &SqlConnect;

# ==================
# common subroutines
# ==================

# reads the contents of a file
sub readContents {
	my $file = shift @_;
	open my $HANDLE, "<", $file;
	my $result = "";
	while (<$HANDLE>) {
		$result .= $_;
	}
	close $file;
	return $result;
}

# writes data to a file
sub writeContents {
	my $file = shift @_;
	my $data = shift @_;

	open my $HANDLE, ">", $file;
	print $HANDLE $data;
	close $file;
}

# formats the response pair appropriately
sub resp($$$) {
	my $code     = shift @_;
	my $response = shift @_;
	my $error    = shift @_;
	if ($error) {
		{ response_code => 99, response => "Error: " . $@ };
	}
	else {
		{ response_code => $code, response => $response };
	}
}

# writes the contest data appropriately
sub writeContestConf {
	my $file        = shift @_;
	my $data        = shift @_;
	my $format_mask = "%s=%s\n";

	open my $handle, ">", $file;
	printf $handle $format_mask, "name",         $data->{name};
	printf $handle $format_mask, "start_time",   $data->{start_time};
	printf $handle $format_mask, "duration",     $data->{duration};
	printf $handle $format_mask, "show_sources", $data->{show_sources};
	printf $handle $format_mask, "about",        $data->{about};
	close $handle;
}

# writes problem configuration
sub writeProblemConf {
	my $file        = shift @_;
	my $data        = shift @_;
	my $format_mask = "%s=%s\n";

	open my $handle, ">", $file;
	printf $handle $format_mask, "name",       $data->{name};
	printf $handle $format_mask, "time_limit", $data->{time_limit};
	printf $handle $format_mask, "about",      $data->{about};
	close $handle;
}

# writes problem description
sub writeProblemDescr {
	my $dir  = shift @_;
	my $data = shift @_;

	if ( exists( $data->{description} ) ) {
		my $description = decode_base64( $data->{description} );
		my $type        = $DEFAULT_DESCRIPTION_TYPE;
		if ( exists( $data->{description_type} ) ) {
			$type = lc( $data->{description_type} );
		}

		open my $DESCR_FILE, ">", $dir . "description" . "." . $type;
		print $DESCR_FILE $description;
		close $DESCR_FILE;
	}
}

# writes problem checker
sub writeProblemChecker {
	my $dir  = shift @_;
	my $data = shift @_;

	if ( exists( $data->{checker} ) ) {
		my $checker = decode_base64( $data->{checker} );
		writeContents( "$dir/checker", $checker );
	}
}

# writes problem solution
sub writeProblemSolution {
	my $dir  = shift @_;
	my $data = shift @_;

	if ( exists( $data->{solution} ) && exists( $data->{solution_type} ) ) {
		my $solution      = $data->{solution};
		my $solution_type = $data->{solution_type};
		writeContents( "$dir/solution.${solution_type}", $solution );
	}
}

# checks URL input parameters for a contest problem
sub checkContestProblem {

	my $set_code = shift @_;
	my $letter   = shift @_;
	my $auth     = shift @_;

	my ( $response_code, $response );
	if ( !( $$set_code =~ /$NAME_PATTERN/ ) ) {
		( $response_code, $response ) = (
			1,
			"Set code must contain only letters, "
			  . "numbers, whitespaces, dashes and underscores."
		);
		return ( $response_code, $response );
	}

	if ( !( $$letter =~ /$NAME_PATTERN/ ) ) {
		( $response_code, $response ) = (
			2,
			"Problem id (letter) must contain only letters, "
			  . "numbers, whitespaces, dashes and underscores."
		);
		return ( $response_code, $response );
	}

	my $cont;
	if ( $$auth->{admin} ) {
		$cont = ( GetContestsEx $dbh, { 'set_code' => $$set_code } );
	}
	else {
		$cont =
		  ( GetContestsEx $dbh, { 'set_code' => $$set_code, 'active' => 1 } );
	}

	if ( scalar keys $cont == 0 ) {
		( $response_code, $response ) = (
			3, "Failed to retrieve contest with id (set code): '$${set_code}'."
		);
		return ( $response_code, $response );
	}

	my $contest = $cont->[0];
	my $probs   = GetProblemsEx $dbh,
	  {
		'contest_id' => $contest->{contest_id},
		'letter'     => $$letter
	  };

	if ( !scalar @$probs ) {
		( $response_code, $response ) = (
			4,
			"Failed to retrieve problem with id (letter): '$${letter}'"
			  . " in contest with id (set code): '$${set_code}'."
		);
		return ( $response_code, $response );
	}

	if ( !-e "$SETS_DIR/$$set_code" ) {
		( $response_code, $response ) =
		  ( 5, "Contest directory $$set_code does not exist on file system." );
		return ( $response_code, $response );
	}

	if ( !-e "$SETS_DIR/$$set_code/$$letter" ) {
		( $response_code, $response ) = (
			6,
			"Problem directory '$${letter}' does not"
			  . " exist on file system in contest '$${set_code}'. "
		);
		return ( $response_code, $response );
	}

	return ( $response_code, $response );
}

# removes obsolete problem data
sub removeProblemData {

	my $prob = shift @_;
	delete $prob->{c_ustart};
	delete $prob->{contest_id};
	delete $prob->{p_dir};
	delete $prob->{c_show_sources};
	delete $prob->{c_duration};
	delete $prob->{c_dir};
	delete $prob->{c_code};
	delete $prob->{c_active};
	delete $prob->{c_name};
	delete $prob->{c_online};
	delete $prob->{c_start};
}

# reads the number of tests
sub getTestCount {

	my $path = shift @_;
	my $i    = 1;

	while ( $i < 100 ) {
		my $infix = '.' . sprintf( "%02d", $i );
		last if ( !( -f "$path/test$infix.in" ) );
		$i++;
	}

	return $i - 1;
}

# check if a user is able to login using basic authentication
sub restLogin {
	my $headers = shift @_;
	my $result;
	if ( exists( $headers->{user} ) && exists( $headers->{pass} ) ) {
		my $user = decode_base64( $headers->{user} );
		my $pass = decode_base64( $headers->{pass} );
		$result = GetUserByCredentials( $dbh, $user, $pass );
	}
	return $result;
}

sub restAdminLogin {
	my $headers = shift @_;
	my $result;
	if ( exists( $headers->{user} ) && exists( $headers->{pass} ) ) {
		my $user        = decode_base64( $headers->{user} );
		my $pass        = decode_base64( $headers->{pass} );
		my $credentials = GetUserByCredentials( $dbh, $user, $pass );
		if ( $credentials && $credentials->{admin} ) {
			$result = $credentials;
		}
	}
	return $result;
}

# ===========================
# managing the spoj0 instance
# ===========================

# starting the instance
put '/start/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response ) = ( 0, "Spoj0 daemon started." );

	#	eval {
	if ( !restAdminLogin( request->headers ) ) {
		( $response_code, $response ) = ( 20, "Unauthorized access." );
		return;
	}

	my $result =
	  system(
		"cd $HOME_DIR; ./spoj0-control.pl start 2> /home/spoj0run/services.log"
	  );

	if ( $result != 0 ) {
		( $response_code, $response ) =
		  ( 1, "Failed to start daemon (status: $result). Reason: $!" );
	}

	#	};
	resp $response_code, $response, $@;
};

# stopping the instance
put '/stop/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response ) = ( 0, "Spoj0 daemon stopped." );
	eval {

		if ( !restAdminLogin( request->headers ) ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $result =
		  system(
"cd $HOME_DIR; ./spoj0-control.pl stop 2> /home/spoj0run/services.log"
		  );
		if ( $result != 0 ) {
			( $response_code, $response ) = (
				1,
				"Failed to stop daemon (status: $result). Please check "
				  . "/home/spoj0run/services.log."
			);
		}
	};
	resp $response_code, $response, $@;
};

# killing the instance
put '/kill/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response ) = ( 0, "Spoj0 daemon killed." );
	eval {

		if ( !restAdminLogin( request->headers ) ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $result =
		  system(
"cd $HOME_DIR; ./spoj0-control.pl kill 2> /home/spoj0run/services.log"
		  );
		if ( $result != 0 ) {
			( $response_code, $response ) = (
				1,
				"Failed to kill daemon (status: $result). Please check "
				  . "/home/spoj0run/services.log."
			);
		}
	};
	resp $response_code, $response, $@;
};

# returning the status of the instance
put '/status/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {

		if ( !restAdminLogin( request->headers ) ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $count = `pgrep spoj0-daemon | wc -l 2> /home/spoj0run/services.log`;
		chomp($count);
		$response = "There are ${count} instances running.";

		( $response_code, $response ) = ( 0, $response );
	};

	resp $response_code, $response, $@;
};

# ===================
# contests & problems
# ===================

# retrieves all contests
# query-parameter includeInvisible includes invisible sets as well
# 	(only for admin users)
get '/contests/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $user = restLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $conts;
		if ( $user->{admin} ) {
			$conts = GetContestsEx $dbh, { 'reverse' => 1 };
		}
		else {
			$conts = GetContestsEx $dbh, { 'active' => 1, 'reverse' => 1, };
		}
		( $response_code, $response ) = ( 0, $conts );
	};

	resp $response_code, $response, $@;
};

# retrieves a particular contest
get '/contests/:id/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {

		my $user = restLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $id = params->{id};

		my $cont;
		if ( $user->{admin} ) {
			$cont =
			  ( GetContestsEx $dbh, { 'set_code' => $id, 'reverse' => 1 } );
		}
		else {
			$cont = (
				GetContestsEx $dbh,
				{ 'set_code' => $id, 'active' => 1, 'reverse' => 1 }
			);
		}

		if ( scalar keys $cont == 0 ) {
			( $response_code, $response ) =
			  ( 1, "Failed to retrieve contest with id (set code): '${id}'." );

			return;
		}
		else {
			( $response_code, $response ) = ( 0, $cont->[0] );
		}
	};

	resp $response_code, $response, $@;
};

# creates a contest
put '/contests/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );

	eval {
		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $cont = from_json( request->body );

		if ( !exists( $cont->{name} ) ) {
			( $response_code, $response ) =
			  ( 1, "No name specified for contest. " );
			return;
		}

		if ( !( $cont->{name} =~ /$NAME_PATTERN/ ) ) {
			( $response_code, $response ) = (
				2,
				"Contest name must contain only letters, "
				  . "numbers, whitespaces, dashes and underscores."
			);
			return;
		}

		my $set_code = $cont->{name};
		$set_code =~ s/\s/_/g;

		if ( -e "$SETS_DIR/$set_code" ) {
			( $response_code, $response ) =
			  ( 3, "Contest directory '${set_code}' already exists. " );
			return;
		}

		my $existing = ( GetContestsEx $dbh, { 'set_code' => $set_code } );
		if (@$existing) {
			( $response_code, $response ) =
			  ( 4, "Contest '$set_code' exist in system. " );
			return;
		}

		my %set_data = (
			'set_code'   => $set_code,
			'name'       => $cont->{name},
			'start_time' => exists( $cont->{start_time} ) ? $cont->{start_time}
			: SqlNow,
			'duration' => exists( $cont->{duration} ) ? $cont->{duration}
			: 300,
			'show_sources' => exists( $cont->{show_sources} )
			? $cont->{show_sources}
			: '1',
			'about' => exists( $cont->{about} ) ? $cont->{about}
			: ""
		);
		mkdir("$SETS_DIR/$set_code");
		writeContestConf( "$SETS_DIR/$set_code/setinfo.conf", \%set_data );
		SqlInsert( \$dbh, 'contests', \%set_data );
		my $contResult =
		  ( GetContestsEx $dbh, { 'set_code' => $set_code } )->[0];

		( $response_code, $response ) = ( 0, $contResult );
	};

	resp $response_code, $response, $@;
};

# updates an existing contest
post '/contests/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $cont = from_json( request->body );

		if ( !exists( $cont->{set_code} ) ) {
			( $response_code, $response ) =
			  ( 1, "No set code specified for contest. " );
			return;
		}
		my $set_code = $cont->{set_code};

		if ( !( $set_code =~ /$NAME_PATTERN/ ) ) {
			( $response_code, $response ) = (
				2,
				"Set code must contain only letters, "
				  . "numbers, whitespaces, dashes and underscores."
			);
			return;
		}

		if ( !-e "$SETS_DIR/$set_code" ) {
			( $response_code, $response ) = (
				3,
				"Contest directory '$set_code' does not exist on file system."
			);
			return;
		}

		my $contResult = ( GetContestsEx $dbh, { 'set_code' => $set_code } );

		if ( !@$contResult ) {
			( $response_code, $response ) = (
				4,
				"Contest '$set_code' exists on "
				  . "file system but is not imported."
			);
			return;
		}
		my $dbCont   = $contResult->[0];
		my %set_data = (
			'name' => exists( $cont->{name} ) ? $cont->{name}
			: $dbCont->{name},
			'start_time' => exists( $cont->{start_time} ) ? $cont->{start_time}
			: $dbCont->{start_time},
			'duration' => exists( $cont->{duration} ) ? $cont->{duration}
			: $dbCont->{duration},
			'show_sources' => exists( $cont->{show_sources} )
			? $cont->{show_sources}
			: $dbCont->{show_sources},
			'about' => exists( $cont->{about} ) ? $cont->{about}
			: $dbCont->{about}
		);

		writeContestConf( "$SETS_DIR/$set_code/setinfo.conf", \%set_data );
		SqlUpdate( \$dbh, 'contests', { "set_code" => $set_code }, \%set_data );
		$contResult = ( GetContestsEx $dbh, { 'set_code' => $set_code } )->[0];

		( $response_code, $response ) = ( 0, $contResult );
	};
	resp $response_code, $response, $@;
};

# deletes a contest
del '/contests/:id/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );

	eval {
		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $set_code = params->{id};

		if ( !( $set_code =~ /$NAME_PATTERN/ ) ) {
			( $response_code, $response ) = (
				1,
				"Set code must contain only letters, "
				  . "numbers, whitespaces, dashes and underscores."
			);
		}
		else {
			if ( -e "$SETS_DIR/$set_code" ) {
				system("rm -R $SETS_DIR/$set_code");
			}

			my $cont = ( GetContestsEx $dbh, { 'set_code' => $set_code } );
			if ( scalar @$cont ) {
				SqlDelete( \$dbh, 'problems',
					{ "contest_id" => $cont->[0]->{contest_id}, } );
				SqlDelete( \$dbh, 'contests', { "set_code" => $set_code } );
			}

			( $response_code, $response ) =
			  ( 0, "Contest with set code: '${set_code}' deleted." );
		}
	};    # eval

	resp $response_code, $response, $@;
};

# retrieves all problems in the specified contest
get '/contests/:id/problems/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $user = restLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $id = params->{id};

		if ( !( $id =~ /$NAME_PATTERN/ ) ) {
			( $response_code, $response ) = (
				1,
				"Set code must contain only letters, "
				  . "numbers, whitespaces, dashes and underscores."
			);
			return ( $response_code, $response );
		}

		my $cont;
		if ( $user->{admin} ) {
			$cont = ( GetContestsEx $dbh, { 'set_code' => $id } );
		}
		else {
			$cont =
			  ( GetContestsEx $dbh, { 'set_code' => $id, 'active' => 1 } );
		}

		if ( scalar keys $cont == 0 ) {
			( $response_code, $response ) =
			  ( 2, "Failed to retrieve contest with id (set code): '${id}'." );
		}
		else {
			my $contest = $cont->[0];
			my $probs   = GetProblemsEx $dbh,
			  { 'contest_id' => $contest->{contest_id} };

			foreach my $prob (@$probs) {
				removeProblemData($prob);
			}

			( $response_code, $response ) = ( 0, $probs );
		}
	};    #eval

	resp $response_code, $response, $@;
};

# retrieves a problem from a contest
get '/contests/:id/problems/:problem_id/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {

		my $user = restLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $id         = params->{id};
		my $problem_id = params->{problem_id};
		( $response_code, $response ) =
		  checkContestProblem( \$id, \$problem_id, \$user );

		if ($response_code) {
			return;
		}

		my $contest = ( GetContestsEx $dbh, { 'set_code' => $id } )->[0];
		my $probs = GetProblemsEx $dbh,
		  {
			'contest_id' => $contest->{contest_id},
			'letter'     => $problem_id
		  };

		removeProblemData( $probs->[0] );
		( $response_code, $response ) = ( 0, $probs->[0] );
	};    # eval
	resp $response_code, $response, $@;
};

# adds a new problem to the contest
put '/contests/:id/problems/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {

		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $id         = params->{id};
		my $db_contest = ( GetContestsEx $dbh, { 'set_code' => $id } );

		if ( scalar keys $db_contest == 0 ) {
			( $response_code, $response ) =
			  ( 1, "Failed to retrieve contest with id (set code): '${id}'." );
			return;
		}
		my $contest = $db_contest->[0];
		my $cont    = from_json( request->body );
		if ( !exists( $cont->{letter} ) ) {
			( $response_code, $response ) =
			  ( 2, "No problem id (letter) specified. " );
			return;
		}

		if ( !( $cont->{letter} =~ /$NAME_PATTERN/ ) ) {
			( $response_code, $response ) = (
				3,
				"Problem id (letter) must contain only letters, "
				  . "numbers, whitespaces, dashes and underscores."
			);
			return;
		}

		if ( !-e "$SETS_DIR/$id" ) {

			( $response_code, $response ) =
			  ( 4, "Contest '${id}' does not exist on file system." );
			return;
		}

		my $letter = $cont->{letter};
		$letter =~ s/\s/_/g;
		if ( -e "$SETS_DIR/$id/$letter" ) {
			( $response_code, $response ) = (
				5,
				"Problem directory '${letter}' already"
				  . " exists in contest '${id}'. "
			);
			return;
		}

		my $probs = GetProblemsEx $dbh,
		  {
			'contest_id' => $contest->{contest_id},
			'letter'     => $letter
		  };

		if ( scalar @$probs ) {
			( $response_code, $response ) = (
				6,
				"Problem '${letter}' already" . " exists in contest '${id}'. "
			);
			return;
		}

		my %problem_data = (
			'contest_id' => $contest->{contest_id},
			'letter'     => $letter,
			'name'       => exists( $cont->{name} ) ? $cont->{name}
			: "Unnamed $letter",
			'time_limit' => exists( $cont->{time_limit} ) ? $cont->{time_limit}
			: 1,
			'about' => exists( $cont->{about} ) ? $cont->{about}
			: ""
		);

		if ( exists( $cont->{solution} )
			&& !exists( $cont->{solution_type} ) )
		{
			( $response_code, $response ) = ( 7, "No solution type provided." );
			return;

		}

		if ( exists( $cont->{solution_type} )
			&& !( $cont->{solution_type} =~ /^[\w]+$/ ) )
		{
			( $response_code, $response ) =
			  ( 8, "Solution type must contain only letters and numbers." );
			return;
		}

		if ( exists( $cont->{description_type} )
			&& !( $cont->{description_type} =~ /^[\w]+$/ ) )
		{
			( $response_code, $response ) =
			  ( 9, "Description type must contain only letters and numbers." );
			return;
		}

		mkdir("$SETS_DIR/$id/$letter");
		writeProblemConf( "$SETS_DIR/$id/$letter/problem-info.conf",
			\%problem_data );
		writeProblemDescr( "$SETS_DIR/$id/$letter/", $cont );
		writeProblemSolution( "$SETS_DIR/$id/$letter/", $cont );
		writeProblemChecker( "$SETS_DIR/$id/$letter/", $cont );

		SqlInsert( \$dbh, 'problems', \%problem_data );
		my $probResult = (
			GetProblemsEx $dbh,
			{
				'contest_id' => $problem_data{contest_id},
				'letter'     => $letter
			}
		)->[0];
		removeProblemData($probResult);

		( $response_code, $response ) = ( 0, $probResult );
	};

	resp $response_code, $response, $@;
};

# updates a problem in the contest
post '/contests/:id/problems/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {

		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $id         = params->{id};
		my $db_contest = ( GetContestsEx $dbh, { 'set_code' => $id } );

		if ( scalar keys $db_contest == 0 ) {
			( $response_code, $response ) =
			  ( 1, "Failed to retrieve contest with id (set code): '${id}'." );
			return;
		}

		my $contest = $db_contest->[0];
		my $cont    = from_json( request->body );
		if ( !exists( $cont->{letter} ) ) {
			( $response_code, $response ) =
			  ( 2, "No problem id (letter) specified. " );
			return;
		}

		if ( !( $cont->{letter} =~ /$NAME_PATTERN/ ) ) {
			( $response_code, $response ) = (
				3,
				"Problem id (letter) must contain only letters, "
				  . "numbers, whitespaces, dashes and underscores."
			);
			return;
		}

		if ( !-e "$SETS_DIR/$id" ) {

			( $response_code, $response ) =
			  ( 4, "Contest '${id}' does not exist on file system." );
			return;
		}

		my $letter = $cont->{letter};
		if ( !-e "$SETS_DIR/$id/$letter" ) {
			( $response_code, $response ) = (
				5,
				"Problem directory '${letter}' does not"
				  . " exist in contest '${id}'. "
			);
			return;
		}

		my $probs = GetProblemsEx $dbh,
		  {
			'contest_id' => $contest->{contest_id},
			'letter'     => $letter
		  };

		if ( !scalar @$probs ) {

			( $response_code, $response ) = (
				6,
				"Problem '${letter}' exists on "
				  . "file system but is not imported."
			);
			return;
		}

		my $existing     = $probs->[0];
		my %problem_data = (
			'contest_id' => $contest->{contest_id},
			'letter'     => $existing->{letter},
			'name'       => exists( $cont->{name} ) ? $cont->{name}
			: $existing->{name},
			'time_limit' => exists( $cont->{time_limit} ) ? $cont->{time_limit}
			: $existing->{time_limit},
			'about' => exists( $cont->{about} ) ? $cont->{about}
			: $existing->{about}
		);

		if ( exists( $cont->{solution} )
			&& !exists( $cont->{solution_type} ) )
		{
			( $response_code, $response ) = ( 7, "No solution type provided." );
			return;

		}

		if ( exists( $cont->{solution_type} )
			&& !( $cont->{solution_type} =~ /^[\w]+$/ ) )
		{
			( $response_code, $response ) =
			  ( 8, "Solution type must contain only letters and numbers." );
			return;
		}

		if ( exists( $cont->{description_type} )
			&& !( $cont->{description_type} =~ /^[\w]+$/ ) )
		{
			( $response_code, $response ) =
			  ( 9, "Description type must contain only letters and numbers." );
			return;
		}

		writeProblemConf( "$SETS_DIR/$id/$letter/problem-info.conf",
			\%problem_data );
		system("rm $SETS_DIR/$id/$letter/description.*");
		writeProblemDescr( "$SETS_DIR/$id/$letter/", $cont );
		writeProblemSolution( "$SETS_DIR/$id/$letter/", $cont );
		writeProblemChecker( "$SETS_DIR/$id/$letter/", $cont );

		SqlUpdate(
			\$dbh,
			'problems',
			{
				'contest_id' => $contest->{contest_id},
				'letter'     => $letter
			},
			\%problem_data
		);
		my $probResult = (
			GetProblemsEx $dbh,
			{
				'contest_id' => $contest->{contest_id},
				'letter'     => $letter
			}
		)->[0];
		removeProblemData($probResult);

		( $response_code, $response ) = ( 0, $probResult );
	};    # eval

	resp $response_code, $response, $@;
};

# deletes a problem from the contest
del '/contests/:id/problems/:problem_id/?' => sub {
	content_type 'application/json';

	my ( $response_code, $response );
	eval {

		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $set_code   = params->{id};
		my $letter     = params->{problem_id};
		my $db_contest = ( GetContestsEx $dbh, { 'set_code' => $set_code } );

		if ( !scalar keys $db_contest ) {
			( $response_code, $response ) = (
				1,
				"Failed to retrieve contest with id (set code): '${set_code}'."
			);
			return;
		}

		if ( !( $set_code =~ /$NAME_PATTERN/ ) ) {
			( $response_code, $response ) = (
				2,
				"Set code must contain only letters, "
				  . "numbers, whitespaces, dashes and underscores."
			);
			return;
		}

		if ( !( $letter =~ /$NAME_PATTERN/ ) ) {
			( $response_code, $response ) = (
				3,
				"Priblem id (letter) must contain only letters, "
				  . "numbers, whitespaces, dashes and underscores."
			);
			return;
		}

		if ( -e "$SETS_DIR/$set_code/$letter" ) {
			system("rm -R $SETS_DIR/$set_code/$letter");
		}

		SqlDelete(
			\$dbh,
			'problems',
			{
				"contest_id" => $db_contest->[0]->{contest_id},
				'letter'     => $letter
			}
		);

		( $response_code, $response ) =
		  ( 0, "Problem: '${letter}' deleted from contest: ${set_code}." );
	};    # eval

	resp $response_code, $response, $@;
};

# =====
# TESTS
# =====

# gets all tests for a problem
get '/contests/:id/problems/:problem_id/tests/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $set_code = params->{id};
		my $letter   = params->{problem_id};
		( $response_code, $response ) =
		  checkContestProblem( \$set_code, \$letter, \$user );
		if ($response_code) {
			return;
		}

		my $prob_dir = "$SETS_DIR/$set_code/$letter";

		my $tests = [];
		if ( -f "$prob_dir/test.in" ) {
			if ( !( -f "$prob_dir/test.ans" ) ) {
				( $response_code, $response ) =
				  ( 7, "Missing answer for multiline test." );
				return;
			}

			push(
				$tests,
				{
					'test' => readContents("$prob_dir/test.in"),
					'ans'  => readContents("$prob_dir/test.ans")
				}
			);
		}
		else {
			my $i = 1;
			while ( $i < 100 ) {
				my $infix = '.' . sprintf( "%02d", $i );
				if ( !( -f "$prob_dir/test$infix.in" ) ) {
					last;
				}
				if ( !( -f "$prob_dir/test$infix.ans" ) ) {
					( $response_code, $response ) =
					  ( 7, "Missing answer for test '${i}'." );
					return;
				}

				push(
					$tests,
					{
						'seq'  => $i,
						'test' => readContents("$prob_dir/test$infix.in"),
						'ans'  => readContents("$prob_dir/test$infix.ans")
					}
				);
				$i++;
			}
		}
		( $response_code, $response ) = ( 0, $tests );
	};
	resp $response_code, $response, $@;
};

# gets a particular test for a problem
get '/contests/:id/problems/:problem_id/tests/:test_id/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $set_code = params->{id};
		my $letter   = params->{problem_id};
		my $test_id  = params->{test_id};

		( $response_code, $response ) =
		  checkContestProblem( \$set_code, \$letter, \$user );
		if ($response_code) {
			return;
		}

		if ( !( $test_id =~ /^\d+$/ ) || $test_id > 99 ) {
			( $response_code, $response ) =
			  ( 7, "Test id must be a number less than 100." );

			return;
		}

		my $prob_dir = "$SETS_DIR/$set_code/$letter";
		if ( -f "$prob_dir/test.in" ) {
			if ( $test_id > 1 ) {
				( $response_code, $response ) = (
					8,
					"There is only one multiline test. "
					  . "Test id must not be greater than 1."
				);
			}
			else {
				if ( !( -f "$prob_dir/test.ans" ) ) {
					( $response_code, $response ) =
					  ( 9, "Answer for test does not exist." );
					return;
				}

				( $response_code, $response ) = (
					0,
					{
						'test' => readContents("$prob_dir/test.in"),
						'ans'  => readContents("$prob_dir/test.ans")
					}
				);
			}
			return;
		}

		my $numTests = getTestCount("$prob_dir/");

		my $infix = '.' . sprintf( "%02d", $test_id );
		if ( $numTests >= $test_id && -f "$prob_dir/test$infix.in" ) {

			if ( !( -f "$prob_dir/test$infix.ans" ) ) {
				( $response_code, $response ) =
				  ( 9, "Answer for test ${test_id} does not exist." );
				return;
			}

			( $response_code, $response ) = (
				0,
				{
					'seq'  => $test_id,
					'test' => readContents("$prob_dir/test$infix.in"),
					'ans'  => readContents("$prob_dir/test$infix.ans")
				}
			);
		}
		else {
			( $response_code, $response ) =
			  ( 10, "No test with index ${test_id}." );
		}
	};
	resp $response_code, $response, $@;
};

# adds a particular test for a problem
put '/contests/:id/problems/:problem_id/tests/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $set_code = params->{id};
		my $letter   = params->{problem_id};
		my $body     = from_json( request->body );

		( $response_code, $response ) =
		  checkContestProblem( \$set_code, \$letter, \$user );
		if ($response_code) {
			return;
		}

		if ( !exists( $body->{test} ) ) {
			( $response_code, $response ) = ( 7, "No test provided in body." );
			return;
		}

		if ( !exists( $body->{ans} ) ) {
			( $response_code, $response ) =
			  ( 8, "No test answer provided in body." );
			return;
		}

		my $test     = $body->{test};
		my $ans      = $body->{ans};
		my $prob_dir = "$SETS_DIR/$set_code/$letter";

		if ( exists( $body->{multiline} )
			&& $body->{multiline} eq ( lc("true") ) )
		{
			if ( -f "$prob_dir/test.in" ) {
				( $response_code, $response ) =
				  ( 10, "Multiline test already exists." );
			}
			else {
				writeContents( "$prob_dir/test.in",  $test );
				writeContents( "$prob_dir/test.ans", $ans );
				( $response_code, $response ) = ( 0, "Multiline test added." );
			}
		}
		else {
			my $numTests = getTestCount("$prob_dir/");

			if ( $numTests >= 99 ) {
				( $response_code, $response ) =
				  ( 9, "Maximum number of tests reached (99)." );
			}
			else {
				my $test_id = $numTests + 1;
				my $infix = '.' . sprintf( "%02d", $test_id );
				writeContents( "$prob_dir/test$infix.in",  $test );
				writeContents( "$prob_dir/test$infix.ans", $ans );
				( $response_code, $response ) = ( 0, "Added test ${test_id}." );
			}
		}
	};
	resp $response_code, $response, $@;
};

# updates a particular test for a problem
post '/contests/:id/problems/:problem_id/tests/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $set_code = params->{id};
		my $letter   = params->{problem_id};
		my $body     = from_json( request->body );

		( $response_code, $response ) =
		  checkContestProblem( \$set_code, \$letter, \$user );
		if ($response_code) {
			return;
		}

		my $test;
		if ( exists( $body->{test} ) ) {
			$test = $body->{test};
		}

		my $ans;
		if ( exists( $body->{ans} ) ) {
			$ans = $body->{ans};
		}

		my $prob_dir = "$SETS_DIR/$set_code/$letter";

		if ( exists( $body->{multiline} )
			&& ( $body->{multiline} ) eq ( lc("true") ) )
		{
			writeContents( "$prob_dir/test.in",  $test ) if $test;
			writeContents( "$prob_dir/test.ans", $ans )  if $ans;
			( $response_code, $response ) = ( 0, "Multiline test updated." );
		}
		else {
			if ( !exists( $body->{seq} ) ) {
				( $response_code, $response ) =
				  ( 7, "No test id (seq) provided." );
				return;
			}

			my $test_id  = $body->{seq};
			my $numTests = getTestCount("$prob_dir/");

			if ( !( $test_id =~ /^\d+$/ ) || $test_id > 99 ) {
				( $response_code, $response ) =
				  ( 8, "Test id must be a number less than 100." );

				return;
			}

			if ( $numTests < $test_id ) {
				( $response_code, $response ) = (
					9,
					"Test id: ${test_id} is larger "
					  . "that the number of tests: ${numTests}."
				);
				return;
			}

			my $infix = '.' . sprintf( "%02d", $test_id );
			writeContents( "$prob_dir/test$infix.in",  $test ) if $test;
			writeContents( "$prob_dir/test$infix.ans", $ans )  if $ans;
			( $response_code, $response ) = ( 0, "Test ${test_id} updated." );
		}
	};

	resp $response_code, $response, $@;
};

# deletes all tests for a particular problem
del '/contests/:id/problems/:problem_id/tests/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $set_code = params->{id};
		my $letter   = params->{problem_id};
		( $response_code, $response ) =
		  checkContestProblem( \$set_code, \$letter, \$user );
		if ($response_code) {
			return;
		}

		my $prob_dir = "$SETS_DIR/$set_code/$letter";
		system("rm -R $prob_dir/test*");
		( $response_code, $response ) = ( 0, "All tests deleted." );
	};
	resp $response_code, $response, $@;
};

# deletes a test for the problem
del '/contests/:id/problems/:problem_id/tests/:test_id/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $set_code = params->{id};
		my $letter   = params->{problem_id};
		( $response_code, $response ) =
		  checkContestProblem( \$set_code, \$letter, \$user );
		if ($response_code) {
			return;
		}

		my $test_id = params->{test_id};

		if ( !( $test_id =~ /^\d+$/ ) || $test_id > 99 ) {
			( $response_code, $response ) =
			  ( 8, "Test id must be a number less than 100." );
			return;
		}

		my $prob_dir = "$SETS_DIR/$set_code/$letter";

		if ( -f "$prob_dir/test.in" ) {

			if ( $test_id > 1 ) {
				( $response_code, $response ) = (
					9,
					"There is only one multiline test. "
					  . "Test id must not be greater than 1."
				);
				return;
			}

			system("rm $prob_dir/test.in");
			system("rm $prob_dir/test.ans");
			( $response_code, $response ) = ( 0, "Multiline test deleted." );
		}
		else {
			my $numTests = getTestCount("$prob_dir/");

			if ( $numTests < $test_id ) {
				( $response_code, $response ) = (
					10,
					"Test id: ${test_id} is larger "
					  . "that the number of tests: ${numTests}."
				);
				return;
			}

			my $infix      = '.' . sprintf( "%02d", $test_id );
			my $last_infix = '.' . sprintf( "%02d", $numTests );

			system("rm $prob_dir/test$infix.in");
			system("rm $prob_dir/test$infix.ans");
			system("mv $prob_dir/test$last_infix.in $prob_dir/test$infix.in");
			system("mv $prob_dir/test$last_infix.ans $prob_dir/test$infix.ans");

			( $response_code, $response ) = ( 0, "Test ${test_id} deleted." );
		}

	};
	resp $response_code, $response, $@;
};

# ===========
# submissions
# ===========

# submits a solution for a problem
put '/contests/:id/problems/:problem_id/submissions/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $user = restLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $set_code = params->{id};
		my $letter   = params->{problem_id};
		my $body     = from_json( request->body );

		( $response_code, $response ) =
		  checkContestProblem( \$set_code, \$letter, \$user );
		if ($response_code) {
			return;
		}

		if ( !exists( $body->{source} ) ) {
			( $response_code, $response ) = ( 8, "No source code provided." );
			return;
		}

		my $source_code = $body->{source};
		my $language = exists( $body->{lang} ) ? $body->{lang} : $DEFAULT_LANG;
		my $source_name = $DEFAULT_SOURCE_FILE{$language};

		if ( !$source_name ) {
			( $response_code, $response ) =
			  ( 7, "Unsupported language: '$language'" );
			return;
		}

		if ( length($source_code) > 1024 * 1024 ) {
			( $response_code, $response ) =
			  ( 9, "Can not submit files larger than 1MB!" );
			return;
		}

		my $cont = ( GetContestsEx $dbh, { 'set_code' => $set_code } )->[0];
		my $prob = (
			GetProblemsEx $dbh,
			{
				'contest_id' => $cont->{contest_id},
				'letter'     => $letter
			}
		)->[0];

		my %run_data = (
			'problem_id'  => $prob->{problem_id},
			'user_id'     => $user->{user_id},
			'submit_time' => SqlNow(),
			'language'    => $language,
			'source_code' => $source_code,
			'source_name' => $source_name,
			'about'       => exists( $body->{about} ) ? $body->{about} : "",
			'status'      => 'waiting',
			'log'         => ''
		);

		SqlInsert \$dbh, 'runs', \%run_data;
		my $run_id = $dbh->last_insert_id( undef, 'spoj', 'runs', 'run_id' );
		( $response_code, $response ) = ( 0, { 'submission_id' => $run_id } );
	};
	resp $response_code, $response, $@;
};

# gets all submissions for a problem
get '/contests/:id/problems/:problem_id/submissions/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $user = restLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $set_code = params->{id};
		my $letter   = params->{problem_id};
		( $response_code, $response ) =
		  checkContestProblem( \$set_code, \$letter, \$user );
		if ($response_code) {
			return;
		}

		my $cont = ( GetContestsEx $dbh, { 'set_code' => $set_code } )->[0];
		my $prob = (
			GetProblemsEx $dbh,
			{
				'contest_id' => $cont->{contest_id},
				'letter'     => $letter
			}
		)->[0];

		my $run_infos = GetProblemRunInfos( \$dbh, $prob->{problem_id} );
		foreach my $run_info (@$run_infos) {
			delete $run_info->{problem_id};
			delete $run_info->{source_name};

			my $finished =
			  ( $cont->{'unow'} - $cont->{'c_start'} ) / 60 >
			  $cont->{'duration'};

			if (   !$user->{admin}
				&& !( $cont->{'show_sources'} == 1 && $finished )
				&& $run_info->{'status'} ne 'ce' )
			{
				delete $run_info->{source_code};
				delete $run_info->{language};
				delete $run_info->{log};
			}
			elsif ( !$user->{admin} && $run_info->{'status'} eq 'ce' ) {
				delete $run_info->{source_code};
				delete $run_info->{language};
			}
		}
		( $response_code, $response ) = ( 0, $run_infos );
	};
	resp $response_code, $response, $@;
};

# gets a particular submission for a problem
get '/contests/:id/problems/:problem_id/submissions/:submission_id/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $user = restLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $set_code = params->{id};
		my $letter   = params->{problem_id};
		( $response_code, $response ) =
		  checkContestProblem( \$set_code, \$letter, \$user );
		if ($response_code) {
			return;
		}

		my $submission_id = params->{submission_id};
		if ( !( $submission_id =~ /^\d+$/ ) ) {
			( $response_code, $response ) =
			  ( 7, "Submission id must be a number." );
			return;
		}

		my $cont = ( GetContestsEx $dbh, { 'set_code' => $set_code } )->[0];
		my $prob = (
			GetProblemsEx $dbh,
			{
				'contest_id' => $cont->{contest_id},
				'letter'     => $letter
			}
		)->[0];

		my $run_info = GetRunInfo( \$dbh, $submission_id );
		if ($run_info) {
			if ( $prob->{problem_id} == $run_info->{problem_id} ) {

				delete $run_info->{problem_id};
				delete $run_info->{source_name};

				my $finished =
				  ( $cont->{'unow'} - $cont->{'c_start'} ) / 60 >
				  $cont->{'duration'};
				if (   !$user->{admin}
					&& !( $cont->{'show_sources'} == 1 && $finished )
					&& $run_info->{'status'} ne 'ce' )
				{
					delete $run_info->{source_code};
					delete $run_info->{language};
					delete $run_info->{log};
				}
				elsif ( !$user->{admin} && $run_info->{'status'} eq 'ce' ) {
					delete $run_info->{source_code};
					delete $run_info->{language};
				}

				( $response_code, $response ) = ( 0, $run_info );
				return;
			}
		}

		( $response_code, $response ) = (
			8,
			"No submission with id: ${submission_id} in the specified problem."
		);
	};
	resp $response_code, $response, $@;
};

# rejudges all submissions for a problem
# @query-parameter failedOnly If 'true' then only failed submissions are rejudged
put '/contests/:id/problems/:problem_id/submissions/rejudge/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $set_code = params->{id};
		my $letter   = params->{problem_id};
		( $response_code, $response ) =
		  checkContestProblem( \$set_code, \$letter, \$user );
		if ($response_code) {
			return;
		}

		my $cont = ( GetContestsEx $dbh, { 'set_code' => $set_code } )->[0];
		my $prob = (
			GetProblemsEx $dbh,
			{
				'contest_id' => $cont->{contest_id},
				'letter'     => $letter
			}
		)->[0];
		my $problem_id = $prob->{problem_id};

		if ( exists( request->params->{failedOnly} )
			&& request->params->{failedOnly} eq lc("true") )
		{
			system(
"cd $HOME_DIR; ./spoj0-control.pl rejudge-problem $problem_id 2> "
				  . "/home/spoj0run/services.log" );
			( $response_code, $response ) = (
				0,
				"Failed submissions for problem"
				  . " with id '${problem_id}' rejudged."
			);
		}
		else {
			system(
"cd $HOME_DIR; ./spoj0-control.pl rejudge-problem-all $problem_id 2> "
				  . "/home/spoj0run/services.log" );
			( $response_code, $response ) = (
				0,
				"All submissions for problem with id '${problem_id}' rejudged."
			);
		}
	};

	resp $response_code, $response, $@;
};

# rejudges a submission
put '/submissions/:submission_id/rejudge/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $submission_id = params->{submission_id};
		if ( !( $submission_id =~ /^\d+$/ ) ) {
			( $response_code, $response ) =
			  ( 1, "Submission id must be a number." );
			return;
		}

		my $run_info = GetRunInfo( \$dbh, $submission_id );
		if ($run_info) {
			system(
"cd $HOME_DIR; ./spoj0-control.pl rejudge-run $submission_id 2> "
				  . "/home/spoj0run/services.log" );
			( $response_code, $response ) =
			  ( 0, "Submissions with id '$submission_id' rejudged." );
			return;
		}

		( $response_code, $response ) = (
			2,
			"No submission with id: ${submission_id} in the specified problem."
		);
	};
	resp $response_code, $response, $@;
};

# =====
# users
# =====

# retrieves all the users of the system
get '/users/?' => sub {
	my ( $response_code, $response );
	eval {
		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $users = GetUsers($dbh);
		( $response_code, $response ) = ( 0, $users );
	};
	resp $response_code, $response, $@;
};

# retrieves a particular user of the system
get '/users/:user_id/?' => sub {
	my ( $response_code, $response );
	eval {
		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $user_id = params->{user_id};
		if ( !( $user_id =~ /^\d+$/ ) ) {
			( $response_code, $response ) = ( 1, "User id must be a number." );
			return;
		}
		my $get_user = GetUserById( $dbh, $user_id );
		if ($get_user) {
			( $response_code, $response ) = ( 0, $get_user );
		}
		else {
			( $response_code, $response ) =
			  ( 2, "No user with id: ${user_id}." );
		}
	};
	resp $response_code, $response, $@;
};

# adds a user
put '/users/?' => sub {
	my ( $response_code, $response );
	eval {
		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $user_data = from_json( request->{body} );
		if ( !exists( $user_data->{username} ) ) {
			( $response_code, $response ) = ( 1, "No username provided." );
			return;
		}
		if ( !exists( $user_data->{password} ) ) {
			( $response_code, $response ) = ( 2, "No password provided." );
			return;
		}

		my $username = $user_data->{username};
		my $password = $user_data->{password};

		if ( Login( $dbh, $username, $password ) ) {
			( $response_code, $response ) =
			  ( 3, "User with provided credentials already exists." );
			return;
		}

		if ( !exists( $user_data->{display_name} )
			|| length( $user_data->{display_name} ) < 5 )
		{
			( $response_code, $response ) =
			  ( 4, "Display name must be at least 5 characters long." );
			return;
		}

		my %data = (
			'name'         => $username,
			'pass_md5'     => md5_hex($password),
			'display_name' => $user_data->{display_name},
			'hidden'       => exists( $user_data->{hidden} )
			? $user_data->{hidden}
			: 0,
			'admin' => exists( $user_data->{admin} ) ? $user_data->{admin} : 0,
			'about' => ""
		);

		$data{'about'} .= " city:" . ( $user_data->{city} )
		  if ( exists( $user_data->{city} ) );
		$data{'about'} .= " inst:" . $user_data->{institution}
		  if ( exists( $user_data->{institution} ) );
		$data{'about'} .= " fn:" . $user_data->{fn}
		  if ( exists( $user_data->{fn} ) );
		$data{'about'} .= " email:" . $user_data->{email}
		  if ( exists( $user_data->{email} ) );
		$data{'about'} .= " icq:" . $user_data->{icq}
		  if ( exists( $user_data->{icq} ) );
		$data{'about'} .= " skype:" . $user_data->{skype}
		  if ( exists( $user_data->{skype} ) );
		$data{'about'} .= " other:" . $user_data->{other}
		  if ( exists( $user_data->{other} ) );

		SqlInsert( \$dbh, 'users', \%data );
		my $added_user = GetUserByCredentials( $dbh, $username, $password );
		( $response_code, $response ) = ( 0, $added_user );
	};

	resp $response_code, $response, $@;
};

# deletes a particular user from the system
del '/users/:user_id/?' => sub {
	my ( $response_code, $response );
	eval {

		my $user = restAdminLogin( request->headers );
		if ( !$user ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $user_id = params->{user_id};
		if ( !( $user_id =~ /^\d+$/ ) ) {
			( $response_code, $response ) = ( 1, "User id must be a number." );
			return;
		}
		my $get_user = GetUserById( $dbh, $user_id );
		if ($get_user) {
			SqlDelete( \$dbh, 'users', { "user_id" => $user_id } );
		}

		( $response_code, $response ) =
		  ( 0, "User with id '$user_id' deleted." );
	};
	resp $response_code, $response, $@;
};

# gets all the submissions for the specified user
get '/users/:user_id/submissions/?' => sub {
	my ( $response_code, $response );
	eval {

		my $user_id = params->{user_id};
		if ( !( $user_id =~ /^\d+$/ ) ) {
			( $response_code, $response ) = ( 1, "User id must be a number." );
			return;
		}

		my $auth = restLogin( request->headers );
		if ( !$auth
			|| ( !( $auth->{admin} ) && $auth->{user_id} != $user_id ) )
		{
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $user = GetUserById( $dbh, $user_id );
		if ( !$user ) {
			( $response_code, $response ) =
			  ( 8, "No user with id: ${user_id}." );
			return;
		}

		my $run_infos = GetUserRunInfos( \$dbh, $user->{user_id} );
		foreach my $run_info (@$run_infos) {
			delete $run_info->{problem_id};
			delete $run_info->{source_name};
		}

		( $response_code, $response ) = ( 0, $run_infos );
	};
	resp $response_code, $response, $@;
};

# get a particular submission for the specified user
get '/users/:user_id/submissions/:submission_id' => sub {
	my ( $response_code, $response );
	eval {

		my $user_id = params->{user_id};
		if ( !( $user_id =~ /^\d+$/ ) ) {
			( $response_code, $response ) = ( 1, "User id must be a number." );
			return;
		}

		my $auth = restLogin( request->headers );
		if ( !$auth
			|| ( !( $auth->{admin} ) && $auth->{user_id} != $user_id ) )
		{
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $submission_id = params->{submission_id};
		if ( !( $submission_id =~ /^\d+$/ ) ) {
			( $response_code, $response ) =
			  ( 2, "Submission id must be a number." );
			return;
		}

		my $run_info = GetRunInfo( \$dbh, $submission_id );
		if ($run_info) {
			delete $run_info->{problem_id};
			delete $run_info->{source_name};

			if ( $user_id == $run_info->{user_id} ) {
				( $response_code, $response ) = ( 0, $run_info );
				return;
			}
		}

		( $response_code, $response ) = (
			3, "No submission with id: ${submission_id} for the specified user."
		);
	};
	resp $response_code, $response, $@;
};

# ====
# news
# ====

# retrieves the news
get '/news/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $auth = restLogin( request->headers );
		if ( !$auth ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $news = GetNews($dbh);
		( $response_code, $response ) = ( 0, $news );
	};
	resp $response_code, $response, $@;
};

# retrieves from the newsrequest
get '/news/:id?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $auth = restLogin( request->headers );
		if ( !$auth ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $id = params->{id};
		if ( !( $id =~ /^\d+$/ ) ) {
			( $response_code, $response ) =
			  ( 1, "News item id must be an integer." );
			return;
		}

		my $news = GetNews( $dbh, { 'new_id' => $id } );
		if ( !scalar @$news ) {
			( $response_code, $response ) =
			  ( 2, "No news item with id ${id} found." );
			return;
		}

		( $response_code, $response ) = ( 0, $news->[0] );
	};
	resp $response_code, $response, $@;
};

# adds to the news
put '/news/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $auth = restAdminLogin( request->headers );
		if ( !$auth ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $news = from_json( request->{body} );

		if ( !exists( $news->{filename} ) ) {
			( $response_code, $response ) = ( 1, "No filename provided." );
			return;
		}

		if ( !exists( $news->{topic} ) ) {
			( $response_code, $response ) = ( 2, "No topic provided." );
			return;
		}

		if ( !exists( $news->{content} ) ) {
			( $response_code, $response ) = ( 3, "No content provided." );
			return;
		}

		my $file    = $news->{filename};
		my $topic   = $news->{topic};
		my $content = $news->{content};

		if ( -f "$NEWS_DIR/${file}.new" ) {
			( $response_code, $response ) = (
				4,
"News with filename '${file}.new' already exists on file system."
			);
			return;
		}

		my $existing_news = GetNews( $dbh, { 'file' => $file . ".new" } );
		if ( scalar @$existing_news ) {
			( $response_code, $response ) = (
				5, "News with filename '${file}.new' already exists in system."
			);
			return;
		}

		my %news_data = (
			'file'     => $file . ".new",
			'new_time' => SqlNow,
			'topic'    => $topic,
			'content'  => $content
		);

		writeContents( "$NEWS_DIR/${file}.new", $topic . "\n" . $content );
		SqlInsert( \$dbh, 'news', \%news_data );
		my $added_news = GetNews( $dbh, { 'file' => $file . ".new" } )->[0];
		( $response_code, $response ) = ( 0, $added_news );
	};
	resp $response_code, $response, $@;
};

# edits the news
post '/news/?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {

		my $auth = restAdminLogin( request->headers );
		if ( !$auth ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $news = from_json( request->{body} );
		if ( !exists( $news->{new_id} ) ) {
			( $response_code, $response ) = ( 1, "No news item id provided." );
			return;
		}

		my $id = $news->{new_id};
		my $existing_news = GetNews( $dbh, { 'new_id' => $id } );
		if ( !scalar @$existing_news ) {
			( $response_code, $response ) =
			  ( 2, "News item with id '${id}' does not exist in system." );
			return;
		}
		my $existing = $existing_news->[0];

		my %news_data = (
			'file' => exists( $news->{filename} ) ? $news->{filename} . ".new"
			: $existing->{file},
			'new_time' => $existing->{new_time},
			'topic'    => exists( $news->{topic} ) ? $news->{topic}
			: $existing->{topic},
			'content' => exists( $news->{content} ) ? $news->{content}
			: $existing->{content}
		);

		my $existing_file = $existing->{file};
		my $file          = $news_data{'file'};
		system("rm $NEWS_DIR/$existing_file");
		writeContents( "$NEWS_DIR/$file",
			$news_data{'topic'} . "\n" . $news_data{'content'} );
		SqlUpdate( \$dbh, 'news', { 'new_id' => $id }, \%news_data );
		my $added_news = GetNews( $dbh, { 'new_id' => $id } )->[0];
		( $response_code, $response ) = ( 0, $added_news );
	};
	resp $response_code, $response, $@;
};

# deletes from the news
del '/news/:id?' => sub {
	content_type 'application/json';
	my ( $response_code, $response );
	eval {
		my $auth = restAdminLogin( request->headers );
		if ( !$auth ) {
			( $response_code, $response ) = ( 20, "Unauthorized access." );
			return;
		}

		my $id = params->{id};
		if ( !( $id =~ /^\d+$/ ) ) {
			( $response_code, $response ) =
			  ( 1, "News item id must be an integer." );
			return;
		}

		my $existing_news = GetNews( $dbh, { 'new_id' => $id } );
		if ( scalar @$existing_news ) {
			my $existing = $existing_news->[0];
			my $file     = $existing->{file};
			system("rm $NEWS_DIR/$file");
			SqlDelete( \$dbh, 'news', { 'new_id' => $id } );
		}
		( $response_code, $response ) = ( 0, "News with id: '${id}' deleted." );
	};
	resp $response_code, $response, $@;
};

dance;
