package actors;

import java.util.List;

public interface Board {
	/**
	 * 
	 */
public void showBoardAnouncements();
/**
 * 
 * @param projects list of projects
 */
public void addAnnouncement(List<Project> projects);
/**
 * 
 * @param p1 project to remove
 */
public void removeAnouncement(Project p1);
/**
 * 
 */
public void editAnouncement();
}
