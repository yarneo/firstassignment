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
	private BlockingQueue<Project> mailBox;
	private boolean shouldStop_ = false;
	private BoardImpl b;
	/**
	 * 
	 */
	public ProjectManagerImpl(String _name, BoardImpl board) {
		b = board;
		this.name = _name;
		ManagerPropertyParser parseIt = new ManagerPropertyParser(name + ".txt");
		type = parseIt.getType();
		projects = parseIt.getProjects();
		projectIds = parseIt.getProjectIds();
		myProjects = new DependencyResolverImpl(projects);
		b.myManagersLink.put(this.name, this.mailBox);
	}
	public void run() {
		while(!myProjects.getAllProjects().isEmpty()) {
		Project temp;
		publish();
		temp = this.getFromMailBox();
		updateProjs(temp.getId()); 
		synchronized(this){
			if (shouldStop_)
			break;
			 }
		}
		
	}
	public synchronized void stop() { shouldStop_ = true; }
	
	
	private synchronized Project getFromMailBox() {
		while(mailBox.isEmpty()) {
		try {
		    this.wait();
			 } catch (InterruptedException ignored) {}	 
		}
		return mailBox.remove();
	}
	private String publishProjectsTemp() {
		String rdyProj = "";
		for(int i=0;i<projects.size();i++) {
			if(projects.get(i).getPrequesiteProjects().isEmpty()) {
				rdyProj = rdyProj + "," + projects.get(i).getId();
			}
		}
		return rdyProj;
	}
	private List<Project> publishProjects() {
		return myProjects.getReadyProjects();
	}
	private String updateProjsTemp(String finishedProject) {
		String preqRemoved = "";
		for(int i=0;i<projects.size();i++) {
			if(projects.get(i).getPrequesiteProjects().contains(finishedProject)) {
				projects.get(i).getPrequesiteProjects().remove(finishedProject);
				preqRemoved = preqRemoved + projects.get(i).getId() + ",";
			}
		}
		return preqRemoved;
	}
	private void updateProjs(String preqId) {
		myProjects.updateCompProj(preqId);
	}
	private void publish() {
		//board will be used although it hasn't been implemented yet
		b.addAnnouncement(this.publishProjects());
		
	}
	

}
