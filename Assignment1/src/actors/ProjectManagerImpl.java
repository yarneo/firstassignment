/**
 * 
 */
package actors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import utils.DependencyResolver;
import utils.DependencyResolverImpl;
import utils.ManagerPropertyParser;

/**
 * @author yarneo
 *
 */
public class ProjectManagerImpl implements Runnable {
	
	//members
	private String name;
	private List<Project> projects;
	private String type;
	private List<String> projectIds;
	private DependencyResolver myProjects;
	private BlockingQueue<String> mailBox;

	/**
	 * 
	 */
	public ProjectManagerImpl(String _name) {
		this.name = _name;
		ManagerPropertyParser parseIt = new ManagerPropertyParser(name + ".txt");
		type = parseIt.getType();
		projects = parseIt.getProjects();
		projectIds = parseIt.getProjectIds();
		myProjects = new DependencyResolverImpl(projects);
	}
	public void run() {
		publish();
		
	}
	
	
	
	private synchronized String getFromMailBox() {
		while(mailBox.isEmpty()) {
		try {
		    this.wait();
			 } catch (InterruptedException ignored) {}	 
		}
		return mailBox.remove();
		this.notifyAll();
	}
	public String publishProjectsTemp() {
		String rdyProj = "";
		for(int i=0;i<projects.size();i++) {
			if(projects.get(i).getPrequesiteProjects().isEmpty()) {
				rdyProj = rdyProj + "," + projects.get(i).getId();
			}
		}
		return rdyProj;
	}
	public List<Project> publishProjects() {
		return myProjects.getReadyProjects();
	}
	public String updateProjsTemp(String finishedProject) {
		String preqRemoved = "";
		for(int i=0;i<projects.size();i++) {
			if(projects.get(i).getPrequesiteProjects().contains(finishedProject)) {
				projects.get(i).getPrequesiteProjects().remove(finishedProject);
				preqRemoved = preqRemoved + projects.get(i).getId() + ",";
			}
		}
		return preqRemoved;
	}
	public void updateProjs(String preqId) {
		myProjects.updateCompProj(preqId);
	}
	public void publish() {
		//board will be used although it hasn't been implemented yet
		b.add(this.publishProjects());
		
	}
	

}
