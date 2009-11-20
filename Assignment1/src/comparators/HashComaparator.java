/**
 * 
 */
package comparators;

import java.util.Comparator;

import resources.Resource;

/**
 * @author Alon Segal
 *
 */
public class HashComaparator implements Comparator<Resource> {

	/**
	 * 
	 */
	public HashComaparator() {
	}

	@Override
	public int compare(Resource res1, Resource res2) {
		if (res1.hashCode() < res2.hashCode()) {
			return 1;        
		}  else if(res1.hashCode() > res2.hashCode()) {
			return -1;
		}
		return 0;
	}

}
