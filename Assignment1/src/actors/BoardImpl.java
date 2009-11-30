/**
 *
 */
package actors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;
import utils.ManagersInfo;
import utils.ObserverInfoGatherer;
/**
 * @author Yarneo
 *
 */
public class BoardImpl implements Board {
	
	//members
	
	private boolean _isToTerminate;
	private List<Project> projectsBoard;
	private ConcurrentHashMap<String,Collection<BlockingQueue<ProgrammerMessage>>> myProgrammersLink;
	private List<ManagersInfo> myManagersLink;
	private ObserverInfoGatherer myObserver;
	
	/**
	 * Default constructor
	 */
	public BoardImpl(ObserverInfoGatherer obs) {
		this._isToTerminate = false;
		this.myProgrammersLink = new ConcurrentHashMap<String, Collection<BlockingQueue<ProgrammerMessage>>>();
		this.myManagersLink = new ArrayList<ManagersInfo>();
		this.projectsBoard = new ArrayList<Project>();
		this.myObserver = obs;
	}
	
	/* (non-Javadoc)
	* @see actors.Board#addAnnouncement()
	* Have received a list of projects from the Program Manager
	* which are the projects
	* that have no prequesite dependencies and can
	* be worked on effective immediately.
	* the projects are then added to the
	* BlockingQueue of the board to be handled by the board
	* and then sent to the programmers.
	*/
	@Override
	public synchronized void addAnnouncement(List<Project> projects) {
	for(Iterator<Project> i = projects.iterator(); i.hasNext();){
		Project p = i.next();
		this.projectsBoard.add(p);
		}
		this.doYourMagic();
	}
	
	/**
	 * the method doYourMagic checks the projects
	 * on the projectboard of the board object,
	 * and looks at each one separately at this type,
	 * if the type exists in the
	 * current keys in the ConcurrentHashMap
	 * then it looks at the key that is connected to it
	 * which is a collection of BlockingQueues which
	 * are referenced to the programmers
	 * who can work on such a type and adds the
	 * project to their current BlockingQueue
	 * after that, it notifies the BlockingQueue
	 * of the programmer so the thread can wake up
	 * and check the Project to see if he can work
	 * on it and/or what to do with it.
	 */
	public synchronized void doYourMagic(){
		for(Iterator<Project> j = this.projectsBoard.iterator(); j.hasNext();) {
			
			Collection<BlockingQueue<ProgrammerMessage>> c;
			Project p = j.next();
			String temp = p.getType();
				if(this.myProgrammersLink.containsKey(temp)) {
					c = this.myProgrammersLink.get(temp);
					for(Iterator<BlockingQueue<ProgrammerMessage>> i = c.iterator(); i.hasNext();) {
						BlockingQueue<ProgrammerMessage> tempQueue = i.next();
						try {
							tempQueue.put(new ProgrammerMessage(ProgrammerMessage.PROGRAMMER_PROJECT, p));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
		}	
	}
	/**
	 * the method doneWithProject gets a completed project from the programmers
	 * and now checks in a special object that contains references to the managers' 
	 * project list and inbox, the project list to see if the id of the completed
	 * project appears in the list or if it appears in the prerequisite list of 
	 * each one of the projects in the list. If it does, then it adds the project
	 * into that certain managers inbox, which then wakes him up
	 * this method is so that not all managers are woken up instantly 
	 * by a finished project, but only the managers who are supposed to wake up
	 * because waking up a thread is expensive cpu wise.
	 *
	*
	 * @param p1 project that is finished
	 */
	public void doneWithProject(Project p1) {
		for(Iterator<ManagersInfo> i = this.myManagersLink.iterator(); i.hasNext();) {
			ManagersInfo tempInfo =   i.next();
			for(Iterator<Project> j = tempInfo.projectList.iterator(); j.hasNext();) {
				Project tempProj = j.next();
				if(tempProj.getId() == p1.getId()) {
					tempInfo.managerInbox.add(p1);
				}
				else if(tempProj.getPrequesiteProjects().contains(p1.getId())) {
					tempInfo.managerInbox.add(p1);
				}
			}
			
		}
	}

	/* (non-Javadoc)
	 * @see actors.Board#removeAnouncement()
	 */
	@Override
	public synchronized void removeAnouncement(Project p1) {
		this.projectsBoard.remove(p1);

	}

	/* (non-Javadoc)
	 * @see actors.Board#showBoardAnouncements()
	 */
	@Override
	public void showBoardAnouncements() {
		for(Iterator<Project> i = this.projectsBoard.iterator(); i.hasNext();) {
			System.out.print(i.next().getId());
		}

	}
	
	public void updateCompletedPhase() {
		this.doYourMagic();
	}
/**
 * @return my programmers link
 */
	public ConcurrentHashMap<String, Collection<BlockingQueue<ProgrammerMessage>>> getMyProgrammersLink() {
		return this.myProgrammersLink;
	}
	/**
	 * @param _myProgrammersLink my programmers link
	 */
	public void setMyProgrammersLink(
			ConcurrentHashMap<String, Collection<BlockingQueue<ProgrammerMessage>>> _myProgrammersLink) {
		this.myProgrammersLink = _myProgrammersLink;
	}
	/**
	 * @return my managers link
	 */
	public List<ManagersInfo> getMyManagersLink() {
		return this.myManagersLink;
	}
	/**
	 * @param _myManagersLink my managers link
	 */
	public void setMyManagersLink(List<ManagersInfo> _myManagersLink) {
		this.myManagersLink = _myManagersLink;
	}
	
	/**
	 * Indicate if the progress is ready to be shut down.
	 * @return True if the process is to be terminated, false otherwise.
	 */
	public boolean isToTerminate() {
		return this._isToTerminate;
	}
	
	/**
	 * Shuts down all the system as soon as possible.
	 */
	public void shutdown() {
		this._isToTerminate = true;
	}

	/**
	 * @param myObserver the myObserver to set
	 */
	public void setMyObserver(ObserverInfoGatherer myObserver) {
		this.myObserver = myObserver;
	}

	/**
	 * @return the myObserver
	 */
	public ObserverInfoGatherer getMyObserver() {
		return myObserver;
	}
}
