package utils;

import java.util.List;
import java.util.PriorityQueue;

import actors.Project;

/**
 * DependencyResolver is an object which meant to hold and manage one ProjectManager's
 * Projects, and to be able to update the projects when a prequesite project is completed.
 */
public interface DependencyResolver {
	
	/**
	 * Given an ID number, goes through all the projects which are held, checks and
	 * updates if the current ID is a prequesite to one of them.
	 * 
	 * @param id ID of the finished project.
	 * 
	 * @pre: none.
	 * @post: none
	 */
	public void updateCompProj(String id);
	
	/**
	 * Returns all the projects which are ready for work.
	 * 
	 * @return List of ready projects.
	 * 
	 * @pre: none
	 * @post: none
	 */
	public List<Project> getReadyProjects();
	
	/**
	 * Gives the queue in which all the projects are held.
	 * 
	 * @return All the projects in the object
	 * 
	 * @pre: none
	 * @post: none
	 */
	public PriorityQueue<Project> getAllProjects();

	/**
	 * Checks if there are ready projects with no Prequesite.
	 * @return true if there are projects ready and false otherwise.
	 */
	public boolean areThereReadyProjects();
}
