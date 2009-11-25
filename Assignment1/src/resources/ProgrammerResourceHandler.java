/**
 * 
 */
package resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import comparators.HashComaparator;

/**
 * @author Alon Segal
 *
 */
public class ProgrammerResourceHandler {
	
	private List<Resource> resources;

	/**
	 * 
	 * @param ls List of resources.S
	 */
	public ProgrammerResourceHandler(List<Resource> ls) {
		this.resources = ls;
		
		Collections.sort(this.resources, new HashComaparator());
	}
	
	/**
	 * 
	 * @param type Type of the desired resource.
	 * @return The resource, Null if not exists.
	 */
	public Resource getResourceByType(String type) {
		for(Iterator<Resource> i = this.resources.iterator(); i.hasNext();) {
			Resource r = i.next();
			if(r.getType().equals(type))
				return r;
		}
		return null;
	}
	
	/**
	 * 
	 * @param ls list of strings of resources
	 * @return a hash sorted list of resources objects.
	 */
	public List<Resource> parseStringToObjects(List<String> ls) {
		List<Resource> res = new ArrayList<Resource>();
		
		for(Iterator<String> i = ls.iterator(); i.hasNext();) {
			Resource r = this.getResourceByType(i.next());
			if(r != null) {
				res.add(r);
			}
		}
		Collections.sort(res, new HashComaparator());
		return res;
	}
	
}
