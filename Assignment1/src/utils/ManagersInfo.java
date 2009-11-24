package utils;

import java.util.concurrent.BlockingQueue;
import java.util.List;
import actors.Project;


public class ManagersInfo {
public List<Project> projectList;
public BlockingQueue<Project> managerInbox;

public ManagersInfo(List<Project> _projectList, BlockingQueue<Project> _managerInbox) {
	this.projectList = _projectList;
	this.managerInbox = _managerInbox;
}




}