/**
 * 
 */
package utils;

import java.util.Iterator;
import java.util.List;

import resources.ProgrammerResourceHandler;

import actors.Observer;
import actors.ObserverImpl;
import actors.Programmer;

/**
 * @author Alon Segal
 *
 */
public class InitialThreadHandler {

	/**
	 * 
	 * @param mp mainParser object
	 * @param prh ProgrammerResourceHandler object
	 */
	public InitialThreadHandler(MainParser mp) {
		this.runObserver();
		this.runProgrammers(mp.getProgrammers());
		//TODO InitialThreadHandler - Complete thread running.
	}
	
	//private functions
	
	private void runObserver() {
		Observer observ = new ObserverImpl();
		Thread t = new Thread((Runnable)observ);
		t.start();
	}
	
	private void runProgrammers(List<Programmer> lp) {
		for(Iterator<Programmer> i = lp.iterator(); i.hasNext(); ) {
			Programmer p = i.next();
			Thread t = new Thread((Runnable)p);
			t.start();
		}
	}

}
