/**
 * 
 */
package actors;

import java.util.ArrayList;
import java.util.List;

import utils.ManagerPropertyParser;

/**
 * @author yarneo
 *
 */
public class ProjectManagerImpl implements ProjectManager {
	
	//members
	private String name;
	private ArrayList<ProjectImpl> projects;
	private String type;
	private List<String> projectIds;
	

	/**
	 * 
	 */
	public ProjectManagerImpl(String _name) {
		this.name = _name;
		ManagerPropertyParser parseIt = new ManagerPropertyParser(name + ".txt");
		type = parseIt.getType();
		projects = parseIt.getProjects();
		projectIds = parseIt.getProjectIds();
	}
	public String publishProjects() {
		String rdyProj = "";
		for(int i=0;i<projects.size();i++) {
			if(projects.get(i).getPrequesiteProjects().isEmpty()) {
				rdyProj = rdyProj + "," + projects.get(i).getId();
			}
		}
		return rdyProj;
	}
	public String updateProjs(String finishedProject) {
		String preqRemoved = "";
		for(int i=0;i<projects.size();i++) {
			if(projects.get(i).getPrequesiteProjects().contains(finishedProject)) {
				projects.get(i).getPrequesiteProjects().remove(finishedProject);
				preqRemoved = preqRemoved + projects.get(i).getId() + ",";
			}
		}
		return preqRemoved;
	}
	public void Publish() {
		
	}
	

}
