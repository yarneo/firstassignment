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

	public static int NUM_OF_PROJECTS;
	private String type;
	private List<String> projectIds;
	private List<Project> projects;
	private String nameOfManager;
	private final int numOfDigitsToCut = 4;
	
	/**
	 * @param name the name of the manager
	 */
	public ManagerPropertyParser(String name) {
		super(name);
		this.projects = new ArrayList<Project>();
		this.nameOfManager = name.substring(0, name.length()-this.numOfDigitsToCut);
		ManagerPropertyParser.NUM_OF_PROJECTS+=this.projects.size();
		this.projectIds = new ArrayList<String>();
		this.nameOfManager = name.substring(0, name.length()-this.numOfDigitsToCut);
		this.parse();
	}
	
	/**
	 * Parsing all the info
	 */
	private void parse() {
		this.parseManager();
	}
	
	// private functions
	
	private void parseManager() {
		this.type = prop.getProperty("type");
		
		String[] tempush = prop.getProperty("projectIds").split(",");
		for(int k=0; k<tempush.length;k++) {
			this.projectIds.add(tempush[k]);
		}
		for(int i=0;i<this.projectIds.size();i++){
			String name1 = this.projectIds.get(i);
			Integer size = new Integer(prop.getProperty("size" + name1));
			
			List<String> prerequisite = new ArrayList<String>();
			if (!prop.getProperty("prerequisiteProjects" + name1).equals("none")) {
				String[] temp = prop.getProperty("prerequisiteProjects" + name1).split(",");
				for(int j = 0; j<temp.length;j++) {
					prerequisite.add(temp[j]);
				}
			}
			Project projy = new ProjectImpl(this.projectIds.get(i),
			this.nameOfManager,this.type,size.intValue(),
			prerequisite,
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
