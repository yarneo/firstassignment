/**
 *
 */
package utils;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import actors.Project;
import actors.ProjectImpl;

/**
 * @author Alon Segal
 *
 */
public class ManagerPropertyParser extends PropertyParser {

	private String type;
	private List<String> projectIds;
	private List<Project> projects;
	private String nameOfManager;

	/**
	 * @param name
	 */
	public ManagerPropertyParser(String name) {
		super(name);
		nameOfManager = name;
		this.parse();
	}

	/* (non-Javadoc)
	 * @see utils.PropertyParser#parse()
	 */
	@Override
	public void parse() {
		parseManager();
	}

	// private functions

	private void parseManager() {
		type = prop.getProperty("type");
		projectIds= Arrays.asList(prop.getProperty("projectIds").split(","));
		for(int i=0;i<projectIds.size();i++){
			List<String> programmmers = new ArrayList<String>();
			String name1 = projectIds.get(i);
			Integer size = new Integer(prop.getProperty("size" + name1));
			Project projy = new ProjectImpl(projectIds.get(i),
			nameOfManager,type,size.intValue(),
			Arrays.asList(prop.getProperty("prerequisiteProjects" + name1).split(",")),
			Arrays.asList(prop.getProperty("resourcesNeeded" + name1).split(",")),programmmers);
			projects.add(projy);
		}

	//	System.out.print(type);
	//	System.out.print(projectIds);
	//	System.out.print(projectIds.size());
	//	System.out.print(projects);
		//projects =
	//	projectTypes = Arrays.asList(prop.getProperty("projectTypes").split(", "));
	//	for(Iterator<String> i = projectTypes.iterator(); i.hasNext(); ) {

	}
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public List<String> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<String> projectIds) {
		this.projectIds = projectIds;
	}

	}
