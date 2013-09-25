package fmi.uni.grading.shared.beans;

import java.util.List;

/**
 * Represents a series (a course that encapsulates contests and may contain subcourses). 
 * 
 * @author Martin Toshev
 */
public class Series extends CourseContainer {

	private String type;
	
	private String title;
	
	private String about;
	
	private String notes;
	
	private List<String> contestOrder;
	
	private String parent;
	
	private List<CourseContainer> children;
	
	private List<Series> subseries;
	
	private List<Contest> contests;
	
	
}
