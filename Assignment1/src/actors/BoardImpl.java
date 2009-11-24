/**
 * 
 */
package actors;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import actors.Project;
import java.util.Iterator;

/**
 * @author Yarneo
 *
 */
public class BoardImpl implements Board {

	
	private List<Project> projectsBoard;
	public ConcurrentHashMap<String, Collection<BlockingQueue<Project>>> myProgrammersLink;
	public ConcurrentHashMap<String, BlockingQueue<Project>> myManagersLink;
	/* (non-Javadoc)
	 * @see actors.Board#addAnnouncement()
	 * Have received a list of projects from the Program Manager which are the projects
	 * that have no prequesite dependencies and can be worked on effective immediately.
	 * the projects are then added to the BlockingQueue of the board to be handled by the board
	 * and then sent to the programmers.
	 */
	@Override
	public void addAnnouncement(List<Project> projects) {
		for(Iterator<Project> i = projects.iterator(); i.hasNext();) {
			Project p = i.next();
		    projectsBoard.add(p);
		}
		doYourMagic();
	}
	/*
	 * the method doYourMagic checks the projects on the projectboard of the board object,
	 * and looks at each one separately at this type, if the type exists in the 
	 * current keys in the ConcurrentHashMap then it looks at the key that is connected to it
	 * which is a collection of BlockingQueues which are referenced to the programmers
	 * who can work on such a type and adds the project to their current BlockingQueue
	 * after that, it notifies the BlockingQueue of the programmer so the thread can wake up
	 * and check the Project to see if he can work on it and/or what to do with it.
	 */
	public void doYourMagic(){
		for(Iterator<Project> j = projectsBoard.iterator(); j.hasNext();) {
			
			Collection<BlockingQueue<Project>> c;
			Project p = j.next();
			String temp = p.getType();
		if(myProgrammersLink.containsKey(temp)) {
			c = myProgrammersLink.get(temp);
			for(Iterator<BlockingQueue<Project>> i = c.iterator(); i.hasNext();) {
				BlockingQueue<Project> tempQueue = i.next();
				tempQueue.add(p);
				tempQueue.notifyAll();	
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
	public void doneWithProject() {
		
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
	public void removeAnouncement() {
		

	}

	/* (non-Javadoc)
	 * @see actors.Board#showBoardAnouncements()
	 */
	@Override
	public void showBoardAnouncements() {
		// TODO Auto-generated method stub

	}
}
