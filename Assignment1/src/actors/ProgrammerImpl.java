/**
 * 
 */
package actors;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import resources.ProgrammerResourceHandler;
import resources.Resource;
import comparators.HashComaparator;

/**
 * @author Alon Segal
 *
 */
public class ProgrammerImpl implements Programmer, Runnable {
	
	private String name;
	private double productivityRate;
	private double workPhaseHours;
	private double budget;
	private List<String> specializations;
	
	private ProgrammerResourceHandler prh;
	
	//Constructors
	
	/**
	 * 
	 */
	public ProgrammerImpl() {
	}
	
	/**
	 * 
	 * @param _name name of the programmer
	 * @param _specializations list of specializations
	 * @param _productivityRate productivity rate of working per hour
	 * @param _workPhaseHours number of hours in which the programmer commits to his work
	 * @param _budget programmer's initial budget
	 */
	public ProgrammerImpl(String _name, List<String> _specializations, double _productivityRate,
			double _workPhaseHours, double _budget, ProgrammerResourceHandler _prh) {
		this.name = _name;
		this.specializations = _specializations;
		this.productivityRate = _productivityRate;
		this.workPhaseHours = _workPhaseHours;
		this.budget = _budget;
		
		this.prh = _prh;
	}
	
	/**
	 * 
	 * @param p Project to test if ready for assignment.
	 * @return true if project fits and false otherwise.
	 */
	public boolean isProjectFits(Project p) {
		return false;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the productivityRate
	 */
	public double getProductivityRate() {
		return this.productivityRate;
	}

	/**
	 * @return the workPhaseHours
	 */
	public double getWorkPhaseHours() {
		return this.workPhaseHours;
	}

	/**
	 * @return the budget
	 */
	public double getBudget() {
		return this.budget;
	}

	/**
	 * @return the specializations
	 */
	public List<String> getSpecializations() {
		return this.specializations;
	}

	@Override
	public void run() {
	}
	
	//Private functions
	
	private void acquireResources(List<String> ls) {
		List<Resource> listRes = this.prh.parseStringToObjects(ls);
		for(Iterator<Resource> i = listRes.iterator(); i.hasNext();) {
			Resource r = i.next();
			r.acquire();
		}
		
		//TODO Finish and test this method.
	}
}
