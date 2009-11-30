package actors;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import utils.ManagersInfo;
import utils.ObserverInfoGatherer;

public interface Board {
	/**
	 * 
	 */
	public void showBoardAnouncements();
	/**
	 * adds anouncements to the board from the program manager
	 * @param projects list of projects
	 * @throws InterruptedException throws the exception to the projectmanager
	 */
	public void addAnnouncement(List<Project> projects) throws InterruptedException;
	/**
	 * remove anouncements from the board
	 * @param p1 project to remove
	 */
	public void removeAnouncement(Project p1);
	/**
	 * @param myProgrammersLink the concurrenthashmap which links the programmers to the board
	 */
	public void setMyProgrammersLink(ConcurrentHashMap<String, Collection<BlockingQueue<ProgrammerMessage>>> myProgrammersLink);
	/**
	 * 
	 * @return return the concurrenthashmap which links the programmers to the board
	 */
	public ConcurrentHashMap<String, Collection<BlockingQueue<ProgrammerMessage>>> getMyProgrammersLink();
	/**
	 * 
	 * @return the list which links the managers to the board
	 */
	public List<ManagersInfo> getMyManagersLink();
	/**
	 * 
	 * @param myManagersLink the list which links the managers to the board
	 */
	public void setMyManagersLink(List<ManagersInfo> myManagersLink);
	
	/**
	 * the method doneWithProject gets a completed project from the programmers
	 * and now checks in a special object that contains references to the managers' 
	 * project list and inbox, the project list to see if the id of the completed
	 * project appears in the list or if it appears in the prerequisite list of 
	 * each one of the projects in the list. If it does, then it adds the project
	 * into that certain managers inbox, which then wakes him up
	 * this method is so that not all of the managers are woken up instantly 
	 * by a finished project, but only the managers who are supposed to wake up
	 * because waking up a thread is expensive cpu wise.
	 *
	*
	 * @param p1 project that is finished
	 */
	public void doneWithProject(Project p1);
	
	/**
	 * Indicate if the progress is ready to be shut down.
	 * @return True if the process is to be terminated, false otherwise.
	 */
	public boolean isToTerminate();
	
	/**
	 * Shuts down all the system as soon as possible.
	 */
	public void shutdown();
	/**
	 * 
	 * @param myObserver the link of the observer to the rest of the threads
	 */
	public void setMyObserver(ObserverInfoGatherer myObserver);
	/**
	 * 
	 * @return the link of the observer to the rest of the threads
	 */
	public ObserverInfoGatherer getMyObserver();
	/**
	 * 
	 * @throws InterruptedException throws the exception to the programmer
	 */
	public void updateCompletedPhase() throws InterruptedException;
}