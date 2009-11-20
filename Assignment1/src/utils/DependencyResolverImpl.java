/**
 *
 */
package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import comparators.NumOfPreqComparator;

import exceptions.NoProjectsException;

import actors.Project;
import actors.ProjectImpl;

/**
 * @author Alon Segal
 *
 */
public class DependencyResolverImpl implements DependencyResolver {

	//members
	private PriorityQueue<Project> projects;

	/**
	 * constructor
	 * @param _projects a list of projects
	 */
	public DependencyResolverImpl(List<Project> _projects) {
		this.projects = new PriorityQueue<Project>(1 ,new NumOfPreqComparator());

		for(Iterator<Project> i = _projects.iterator(); i.hasNext(); ) {
			Project p = i.next();
			this.projects.add(p);
		}
	}

	/* (non-Javadoc)
	 * @see utils.DependencyResolver#getReadyProjects()
	 */
	/**
	 * @return the ready projects with no prequesite.
	 * @throws NoProjectsException No projects to be returned.
	 */
	@Override
	public List<Project> getReadyProjects() throws NoProjectsException {
		if(this.areThereReadyProjects()) {
			List<Project> lTemp = new ArrayList<Project>();
			for(Iterator<Project> i = this.projects.iterator(); i.hasNext(); ) {
				Project p = i.next();
				if(p.getPrequesiteProjects().size()==0) {
					lTemp.add(p);
					i.remove();
				}
			}
		return lTemp;
		}
		else throw new NoProjectsException("No projects to be returned");
	}

	/* (non-Javadoc)
	 * @see utils.DependencyResolver#updateCompProj(int)
	 */
	/**
	 * @param id ID of the project to update as completed.
	 */
	@Override
	public void updateCompProj(String id) {
		for(Iterator<Project> i = this.projects.iterator(); i.hasNext(); ) {
			Project p = i.next();
			this.removePrequesite(p, id);
		}
		//Add a fiction project to update the queue
		Project a = new ProjectImpl("", "", "", 0, new ArrayList<String>(),
				new ArrayList<String>(), new ArrayList<String>());
		this.projects.add(a);
		this.projects.remove(a);
	}

	/* (non-Javadoc)
	 * @see utils.DependencyResolver#getAllProjects()
	 */
	/**
	 * @return All the projects (completed and none-completed
	 */
	@Override
	public PriorityQueue<Project> getAllProjects() {

		return new PriorityQueue<Project>(this.projects);
	}

	/**
	 * Checks if there are ready projects with no Prequesite.
	 * @return true if there are projects ready and false otherwise.
	 */
	public boolean areThereReadyProjects() {
		for(Iterator<Project> i = this.projects.iterator(); i.hasNext(); ) {
			Project p = i.next();
			if(p.getPrequesiteProjects()!=null && p.getPrequesiteProjects().size()==0) {
				return true;
			}
		}
		return false;
	}

	/******************/
	//Private functions
	/******************/

	/**
	 * Get a project and remove a Prequesite from the list by ID (if exists)
	 * @param p A project
	 * @param id ID to remove
	 */
	private void removePrequesite(Project p, String id) {
		List<String> l = p.getPrequesiteProjects();
		for(Iterator<String> i = l.iterator(); i.hasNext(); ) {
			String pTemp = i.next();
			if (id.equals(pTemp)) {
				i.remove();
			}
		}
	}

}
