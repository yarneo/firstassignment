/**
 *
 */
package actors;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;
import utils.ManagersInfo;
/**
 * @author Yarneo
 *
 */
public class BoardImpl implements Board {
/**
 * fdfs
 */
private List<Project> projectsBoard;
/**
 *
 */
public ConcurrentHashMap<String,Collection<BlockingQueue<Project>>> myProgrammersLink;
/**
 *
 */
public List<ManagersInfo> myManagersLink;
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
public void addAnnouncement(List<Project> projects) {
for(Iterator<Project> i = projects.iterator(); i.hasNext();){
	Project p = i.next();
	this.projectsBoard.add(p);
	}
	this.doYourMagic();
}
	/*
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
/**
 * 
 */
	public void doYourMagic(){
		for(Iterator<Project> j = this.projectsBoard.iterator(); j.hasNext();) {
			
			Collection<BlockingQueue<Project>> c;
			Project p = j.next();
			String temp = p.getType();
				if(this.myProgrammersLink.containsKey(temp)) {
					c = this.myProgrammersLink.get(temp);
					for(Iterator<BlockingQueue<Project>> i = c.iterator(); i.hasNext();) {
						BlockingQueue<Project> tempQueue = i.next();
						try {
							tempQueue.put(p);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
				}
	//	for(Iterator<BlockingQueue> i = myProjectsStorage.iterator(); i.hasNext(); ) {
		//	ProgrammersInfo temp=i.next();
			//if(temp.types.contains(projectsBoard.peek().getType())) {
				//sendProject(temp.id);
		//	}
	//	}
		}	
	}
	/*
	 * the method doneWithProject gets a completed project from the programmers
	 * and now checks in a special object that contains references to the managers' 
	 * project list and inbox, the project list to see if the id of the completed
	 * project appears in the list or if it appears in the prerequisite list of 
	 * each one of the projects in the list. If it does, then it adds the project
	 * into that certain managers inbox, which then wakes him up
	 * this method is so that not all managers are woken up instantly 
	 * by a finished project, but only the managers who are supposed to wake up
	 * because waking up a thread is expensive cpu wise.
	 */
	/**
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
	 * @see actors.Board#editAnouncement()
	 */
	@Override
	public void editAnouncement() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see actors.Board#removeAnouncement()
	 */
	@Override
	public void removeAnouncement(Project p1) {
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
}
