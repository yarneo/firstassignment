package utils;

import java.util.ArrayList;
import java.util.List;

import actors.ProgrammerInfo;
import actors.Project;


public class ObserverInfoGatherer {
private List<Project> currentProjects;
private List<Project> pendingProjects;
private List<Project> completedProjects;
private List<String> timeOfCompletedProjects;
private List<ProgrammerInfo> programmersInfo;
private List<Thread> myThreads;

/**
 * the constructor that builds the parameters
 * @param _mp my main parser
 */
public ObserverInfoGatherer(MainParser _mp) {
	this.currentProjects = new ArrayList<Project>();
	this.pendingProjects = new ArrayList<Project>();
	this.completedProjects = new ArrayList<Project>();
	this.timeOfCompletedProjects = new ArrayList<String>();
	this.programmersInfo = _mp.getProgrammersInfo();
	this.myThreads = new ArrayList<Thread>();
}

/**
 * @param _currentProjects the currentProjects to set
 */
public void setCurrentProjects(List<Project> _currentProjects) {
	this.currentProjects = _currentProjects;
}
/**
 * @return the currentProjects
 */
public List<Project> getCurrentProjects() {
	return this.currentProjects;
}
/**
 * @param _pendingProjects the pendingProjects to set
 */
public void setPendingProjects(List<Project> _pendingProjects) {
	this.pendingProjects = _pendingProjects;
}
/**
 * @return the pendingProjects
 */
public List<Project> getPendingProjects() {
	return this.pendingProjects;
}
/**
 * @param _completedProjects the completedProjects to set
 */
public void setCompletedProjects(List<Project> _completedProjects) {
	this.completedProjects = _completedProjects;
}
/**
 * @return the completedProjects
 */
public List<Project> getCompletedProjects() {
	return this.completedProjects;
}
/**
 * @param _timeOfCompletedProjects the timeOfCompletedProjects to set
 */
public void setTimeOfCompletedProjects(List<String> _timeOfCompletedProjects) {
	this.timeOfCompletedProjects = _timeOfCompletedProjects;
}
/**
 * @return the timeOfCompletedProjects
 */
public List<String> getTimeOfCompletedProjects() {
	return this.timeOfCompletedProjects;
}
/**
 * @param _programmersInfo the programmers to set
 */

public void setProgrammers(List<ProgrammerInfo> _programmersInfo) {
	this.programmersInfo = _programmersInfo;
}
/**
 * @return the programmers
 */

public List<ProgrammerInfo> getProgrammers() {
	return this.programmersInfo;
}

/**
 * @param _myThreads the myThreads to set
 */
public void setMyThreads(List<Thread> _myThreads) {
	this.myThreads = _myThreads;
}

/**
 * @return the myThreads
 */
public List<Thread> getMyThreads() {
	return this.myThreads;
}


}
