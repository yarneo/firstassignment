/**
 * 
 */
package utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import actors.Project;
import actors.ProjectImpl;
import resources.Resource;

/**
 * @author Alon Segal
 *
 */
public class ManagerPropertyParser extends PropertyParser {

	public static int NUM_OF_PROJECTS;
	private String type;
	private List<String> projectIds;
	private List<Project> projects;
	private String nameOfManager;
	
	/**
	 * @param name the name of the manager
	 */
	public ManagerPropertyParser(String name) {
		super(name);
		this.projects = new ArrayList<Project>();
		this.nameOfManager = name;
		this.parse();
		ManagerPropertyParser.NUM_OF_PROJECTS+=this.projects.size();
	}

	/* (non-Javadoc)
	 * @see utils.PropertyParser#parse()
	 */
	@Override
	public void parse() {
		this.parseManager();
	}
	
	// private functions
	
	private void parseManager() {
		this.type = prop.getProperty("type");
		this.projectIds= Arrays.asList(prop.getProperty("projectIds").split(","));
		for(int i=0;i<this.projectIds.size();i++){
			String name1 = this.projectIds.get(i);
			Integer size = new Integer(prop.getProperty("size" + name1));
			Project projy = new ProjectImpl(this.projectIds.get(i),
			this.nameOfManager,this.type,size.intValue(),
			Arrays.asList(prop.getProperty("prerequisiteProjects" + name1).split(",")),
			Arrays.asList(prop.getProperty("resourcesNeeded" + name1).split(",")));
			this.projects.add(projy);
		}
			
	//	System.out.print(type);
	//	System.out.print(projectIds);
	//	System.out.print(projectIds.size());
	//	System.out.print(projects);
		//projects =
	//	projectTypes = Arrays.asList(prop.getProperty("projectTypes").split(", "));
	//	for(Iterator<String> i = projectTypes.iterator(); i.hasNext(); ) {

	}
	/**
	 * 
	 * @return projects
	 */
	public List<Project> getProjects() {
		return this.projects;
	}
	/**
	 * 
	 * @param _projects list of projects
	 */
	public void setProjects(List<Project> _projects) {
		this.projects = _projects;
	}
	/**
	 * 
	 * @return type
	 */
	public String getType() {
		return this.type;
	}
	/**
	 * 
	 * @param _type type of projects
	 */
	public void setType(String _type) {
		this.type = _type;
	}
	/**
	 * 
	 * @return list of projectids
	 */
	public List<String> getProjectIds() {
		return this.projectIds;
	}
	/**
	 * 
	 * @param _projectIds sets the projectids
	 */
	public void setProjectIds(List<String> _projectIds) {
		this.projectIds = _projectIds;
	}

	}
