/**
 *
 */
package utils;

import java.util.Iterator;
import java.util.List;
import actors.Observer;
import actors.Programmer;
import actors.ProjectManager;

/**
 * @author Alon Segal
 *
 */
public class InitialThreadHandler {
	
	
	private MainParser mp3;
	/**
	 * @param mp Main parser object
	 */
	public InitialThreadHandler(MainParser mp) {
		this.mp3 = mp;
	}

	//private functions
	
	/**
	 * Running all the threads in the system
	 */
	public void runThreads() {
		this.runProgrammers(this.mp3.getProgrammers());
		this.runManagers(this.mp3.getProjectManagers());
		this.runObserver(this.mp3);
	}

	private void runProgrammers(List<Programmer> lp) {
		////////////////////
	/*	List<String> res = new ArrayList<String>();
		res.add("regular_computer");
		res.add("database1_connection");
		
		Project p1 = new ProjectImpl("1", "Project1", "gui", 1, new ArrayList<String>(),
				res);
		Project p2 = new ProjectImpl("2", "Project2", "qa", 10, new ArrayList<String>(),
				new ArrayList<String>());
		/////////////////////
		*/
		for(Iterator<Programmer> i = lp.iterator(); i.hasNext(); ) {
			Programmer p = i.next();
			Thread t = new Thread((Runnable)p);
			List<Thread> tempList;
			tempList = this.mp3.getObserverGatherer().getMyThreads();
			tempList.add(t);
			this.mp3.getObserverGatherer().setMyThreads(tempList);
			t.start();
		}
		
		//lp.get(1).sendNewProject(p1);
		//lp.get(0).sendNewProject(p1);
	}
	
	private void runManagers(List<ProjectManager> lm) {
		for(Iterator<ProjectManager> i = lm.iterator(); i.hasNext(); ) {
			ProjectManager p = i.next();
			Thread t = new Thread((Runnable)p);
			List<Thread> tempList;
			tempList = this.mp3.getObserverGatherer().getMyThreads();
			tempList.add(t);
			this.mp3.getObserverGatherer().setMyThreads(tempList);
			t.start();
		}
	}
	
	private void runObserver(MainParser _mp) {
		
		Observer observ = new Observer(_mp);
		Thread t = new Thread((Runnable)observ);
		t.start();
	}

}
