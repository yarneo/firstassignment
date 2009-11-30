/**
 * 
 */
package actors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import utils.DependencyResolver;
import utils.DependencyResolverImpl;
import utils.ManagerPropertyParser;
import utils.ManagersInfo;
import utils.LogHelper;

/**
 * @author yarneo
 *
 */
public class ProjectManager implements Runnable {
	
	//members
	private String name;
	private List<Project> projects;
	private String type;
	private List<String> projectIds;
	private DependencyResolver myProjects;
	private BlockingQueue<Project> mailBox;
	private boolean _shouldStop;
	private Board board;
	private LogHelper logger;
	
	
	
	/**
	 * 
	 * @param _name gets the name of the manager
	 * @param _board gets the message board
	 */
	public ProjectManager(String _name, Board _board) {
		this._shouldStop = false;
		this.board = _board;
		this.name = _name;
		ManagerPropertyParser parseIt = new ManagerPropertyParser(this.name + ".txt");
		this.type = parseIt.getType();
		this.projects = parseIt.getProjects();
		this.projectIds = parseIt.getProjectIds();
		this.myProjects = new DependencyResolverImpl(this.projects);
		this.mailBox = new ArrayBlockingQueue<Project>(100);
		
		ManagersInfo tempInfo = new ManagersInfo(this.projects, this.mailBox);
		List<ManagersInfo> tempList;
		tempList = this.board.getMyManagersLink();
		tempList.add(tempInfo);
		this.board.setMyManagersLink(tempList);
		logger = new LogHelper(LogHelper.LOG_FILE_NAME);
	}
	
	/**
	 * 
	 */
	public void run() {
		this.logger.log(this.name + " started working");
		while(!this.myProjects.getAllProjects().isEmpty()) {
		Project temp;
		//System.out.print(this.myProjects.areThereReadyProjects());
		if(this.myProjects.areThereReadyProjects())
			this.publish();
		//add all the pending projects of this manager to the observer info gatherer
			List<Project> tempList;
			tempList = this.board.getMyObserver().getPendingProjects();
			for(Iterator<Project> i = this.myProjects.getAllProjects().iterator(); i.hasNext();) {
				tempList.add(i.next());
			}
			this.board.getMyObserver().setPendingProjects(tempList);
		try {
			temp = this.mailBox.take();
			this.updateProjs(temp.getId()); 
		} catch(InterruptedException e) {}
		
		//what about deleting from manager the done project?
		synchronized(this){
			if (this._shouldStop)
			break;
			 }
		}
		
	}
	/**
	 * 
	 */
	public synchronized void stop() { this._shouldStop = true; }
	
	
	
	
	
	private List<Project> publishProjects() {
		if(this.myProjects.areThereReadyProjects()) {
			List<Project> ls = this.myProjects.getReadyProjects();
				for(Iterator<Project> i = ls.iterator(); i.hasNext(); ) {
					Project p =i.next();
					this.logger.log(this.name+" publishes project "+p.getId());
				}
			return ls;
		}
		return new ArrayList<Project>();
	}
	
	private void updateProjs(String preqId) {
		this.myProjects.updateCompProj(preqId);
	}
	private void publish() {
		//board will be used although it hasn't been implemented yet
		this.board.addAnnouncement(this.publishProjects());
		
	}
	

}
