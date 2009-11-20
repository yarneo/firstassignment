/**
 * 
 */
package comparators;

import java.util.Comparator;

import actors.Project;

/**
 * @author Alon Segal
 *
 */
public class NumOfPreqComparator implements Comparator<Project> {

	/**
	 * 
	 */
	public NumOfPreqComparator() {
	}
	
	@Override    
	public int compare(Project proj1, Project proj2)    {        
		// Assume neither string is null. Real code should        
		// probably be more robust        
		if (proj1.getPrequesiteProjects().size() > proj2.getPrequesiteProjects().size())        {
			return 1;        
		}    
		return -1;
	}

}
