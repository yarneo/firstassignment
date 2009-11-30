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
	private final int hundred=100;
	
	
	
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
		this.mailBox = new ArrayBlockingQueue<Project>(this.hundred);
		
		ManagersInfo tempInfo = new ManagersInfo(this.projects, this.mailBox);
		List<ManagersInfo> tempList;
		tempList = this.board.getMyManagersLink();
		tempList.add(tempInfo);
		this.board.setMyManagersLink(tempList);
		this.logger = new LogHelper(LogHelper.LOG_FILE_NAME);
		
		//add all the pending projects of this manager to the observer info gatherer
		List<Project> tempList2;
		synchronized (this.board.getMyObserver()) {

		tempList2 = this.board.getMyObserver().getPendingProjects();
		for(Iterator<Project> i = this.myProjects.getAllProjects().iterator(); i.hasNext();) {
			tempList2.add(i.next());
		}
		this.board.getMyObserver().setPendingProjects(tempList2);
		
		}
	}
	
	/**
	 * 
	 */
	public void run() {
		this.logger.log(this.name + " started working");
		while(!this.myProjects.getAllProjects().isEmpty() &!this._shouldStop) {
			try {
		Project temp;
		//System.out.print(this.myProjects.areThereReadyProjects());
		synchronized (this.board.getMyObserver().getPendingProjects()) {
		if(this.myProjects.areThereReadyProjects()) {
			List<Project> tempyListy;
			tempyListy = this.board.getMyObserver().getPendingProjects();
			for(Iterator<Project> i = this.myProjects.getAllProjects().iterator(); i.hasNext(); ) {
				Project p = i.next();
				if(p.getPrequesiteProjects().size()==0) {
					if(tempyListy.contains(p)) {
						tempyListy.remove(p);
					}
				}
			}
			this.board.getMyObserver().setPendingProjects(tempyListy);
		}
			this.publish();
			
		}
		
		
		
			temp = this.mailBox.take();
			this.updateProjs(temp.getId()); 
		} catch(InterruptedException e) {
			this._shouldStop=true;
		}
		}
		
	}
	/**
	 * 
	 */
	
	
	
	
	
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
	private void publish() throws InterruptedException{
		//board will be used although it hasn't been implemented yet
		this.board.addAnnouncement(this.publishProjects());
		
	}
	

}
