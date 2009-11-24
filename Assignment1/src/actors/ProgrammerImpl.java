/**
 * 
 */
package actors;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import resources.ProgrammerResourceHandler;
import resources.Resource;
import utils.LogHelper;
import utils.MainParser;

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
	
	private final int simulatedSecond = 1000;
	
	private ProgrammerResourceHandler prh;
	private BlockingQueue<Project> mailbox;
	private boolean shouldStop;
	
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
	 * @param _prh ProgrammerResourceHandler instance
	 */
	public ProgrammerImpl(String _name, List<String> _specializations, double _productivityRate,
			double _workPhaseHours, double _budget, ProgrammerResourceHandler _prh) {
		this.name = _name;
		this.specializations = _specializations;
		this.productivityRate = _productivityRate;
		this.workPhaseHours = _workPhaseHours;
		this.budget = _budget;
		
		this.prh = _prh;
		
		//TODO Get to the original state
		this.mailbox = new ArrayBlockingQueue<Project>(/*ManagerPropertyParser.NUM_OF_PROJECTS*/10, true);
	}
	
	/**
	 * Trying to stop the thread
	 */
	public synchronized void stop() {
		this.shouldStop = true; 
	}
	
	@Override
	public void run() {
		LogHelper.log(this.getName()+" started working");
		while(!this.shouldStop) {
			try {
				Project projectToDo = this.mailbox.take();

				if (this.isBudgetEnough(projectToDo) & projectToDo.isAnotherHandNeeded(this)) {
					this.acquireResources(projectToDo.getResources());
					
					LogHelper.log(this.getName()+" committed to "+projectToDo.getId()+" for "+
							(int)this.getWorkPhaseHours());
					
					this.budget = this.budget - this.workPhaseHours;
					
					projectToDo.commit(this);
					try {
						Thread.sleep((int)(this.getWorkPhaseHours()*MainParser.SIMULATION_HOUR)*
								this.simulatedSecond);
					} catch(InterruptedException e) {}
					
					//logging
					LogHelper.log(this.getName()+" is done with commitement on project "+
							projectToDo.getId());
					if(this.getBudget()<this.getWorkPhaseHours())
						LogHelper.log(this.getName()+"'s budget run out");
					
					this.releaseResources(projectToDo.getResources());
					projectToDo.done(this);
					//handles budget
					this.budget = this.getBudget() - this.getWorkPhaseHours();
					
				}
			}	catch(InterruptedException e) {}
		}
		LogHelper.log(this.getName()+" finished working");
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
	
	/**
	 * @param type Type of the project
	 * @return is the programmer capable with this type of work
	 */
	public boolean isCapable(String type) {
		if (!this.specializations.contains(type))
			return false;
		return true;
	}
	
	/**
	 * Putting a new project in the programmer mailbox
	 * @param p Project applied
	 */
	public void sendNewProject(Project p) {
		try {
			this.mailbox.put(p);
		} catch(InterruptedException e) {}
	}

	//Private functions
	
	private void acquireResources(List<String> ls) {
		List<Resource> listRes = this.prh.parseStringToObjects(ls);
		for(Iterator<Resource> i = listRes.iterator(); i.hasNext();) {
			Resource r = i.next();
			r.acquire();
			LogHelper.log(this.getName()+" acquired "+r.getType());
		}
		
		//TODO Finish and test this method.
	}
	
	private void releaseResources(List<String> ls) {
		List<Resource> listRes = this.prh.parseStringToObjects(ls);
		for(Iterator<Resource> i = listRes.iterator(); i.hasNext();) {
			Resource r = i.next();
			LogHelper.log(this.getName()+" released "+r.getType());
			r.realese();
		}
		
		//TODO Finish and test this method.
	}
	
	private boolean isBudgetEnough(Project p) {
		if (this.budget<p.getSize())
			return false;
		return true;
	}
}
