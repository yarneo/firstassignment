package utils;

import java.util.concurrent.BlockingQueue;
import java.util.List;
import actors.Project;


public class ManagersInfo {
	//members
	public List<Project> projectList;
	public BlockingQueue<Project> managerInbox;
	
	/**
	 * Constructor
	 * @param _projectList all the projects of the manger
	 * @param _managerInbox The mailbox of the manager
	 */
	public ManagersInfo(List<Project> _projectList, BlockingQueue<Project> _managerInbox) {
		this.projectList = _projectList;
		this.managerInbox = _managerInbox;
}




}