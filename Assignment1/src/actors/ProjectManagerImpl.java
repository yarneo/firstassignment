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
import utils.ManagersInfo;

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
	 * @param _name gets the name of the manager
	 * @param board gets the message board
	 */
	public ProjectManagerImpl(String _name, BoardImpl board) {
		this.b = board;
		this.name = _name;
		ManagerPropertyParser parseIt = new ManagerPropertyParser(this.name + ".txt");
		this.type = parseIt.getType();
		this.projects = parseIt.getProjects();
		this.projectIds = parseIt.getProjectIds();
		this.myProjects = new DependencyResolverImpl(this.projects);
		ManagersInfo tempInfo = new ManagersInfo(this.publishProjects(),this.mailBox);
		this.b.myManagersLink.add(tempInfo);
	}
	/**
	 * 
	 */
	public void run() {
		while(!this.myProjects.getAllProjects().isEmpty()) {
		Project temp;
		this.publish();
		temp = this.mailBox.remove();
		this.updateProjs(temp.getId()); 
		//what about deleting from manager the done project?
		synchronized(this){
			if (this.shouldStop_)
			break;
			 }
		}
		
	}
	/**
	 * 
	 */
	public synchronized void stop() { this.shouldStop_ = true; }
	
	
	private synchronized Project getFromMailBox() {
		while(this.mailBox.isEmpty()) {
		try {
		    this.wait();
			 } catch (InterruptedException ignored) {}	 
		}
		return this.mailBox.remove();
	}
	private String publishProjectsTemp() {
		String rdyProj = "";
		for(int i=0;i<this.projects.size();i++) {
			if(this.projects.get(i).getPrequesiteProjects().isEmpty()) {
				rdyProj = rdyProj + "," + this.projects.get(i).getId();
			}
		}
		return rdyProj;
	}
	private List<Project> publishProjects() {
		return this.myProjects.getReadyProjects();
	}
	private String updateProjsTemp(String finishedProject) {
		String preqRemoved = "";
		for(int i=0;i<this.projects.size();i++) {
			if(this.projects.get(i).getPrequesiteProjects().contains(finishedProject)) {
				this.projects.get(i).getPrequesiteProjects().remove(finishedProject);
				preqRemoved = preqRemoved + this.projects.get(i).getId() + ",";
			}
		}
		return preqRemoved;
	}
	private void updateProjs(String preqId) {
		this.myProjects.updateCompProj(preqId);
	}
	private void publish() {
		//board will be used although it hasn't been implemented yet
		this.b.addAnnouncement(this.publishProjects());
		
	}
	

}
