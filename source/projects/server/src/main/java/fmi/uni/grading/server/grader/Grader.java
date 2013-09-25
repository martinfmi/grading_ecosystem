package fmi.uni.grading.server.grader;

import java.util.List;

import fmi.uni.grading.shared.beans.Problem;

public abstract class Grader {

		// Problem submission
		public abstract void submitProblem(Problem problem);
		
		// Contest handling
		public abstract void scheduleContest(); 
		
		public abstract void getActiveContests();
		
		// Problem Handling
		public abstract List<Problem> getProblems();
		
		public abstract void createProblem();
}
