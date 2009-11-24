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
public class Programmer implements Runnable {
	
	private String name;
	private double productivityRate;
	private double workPhaseHours;
	private double budget;
	private List<String> specializations;
	private ProgrammerInfo pInfo;
	
	private final int simulatedSecond = 1000;
	
	private ProgrammerResourceHandler prh;
	private BlockingQueue<Project> mailbox;
	private boolean shouldStop;
	private Board board;
	
	private LogHelper logger;
	
	//Constructors
	
	/**
	 * 
	 * @param _name name of the programmer
	 * @param _specializations list of specializations
	 * @param _productivityRate productivity rate of working per hour
	 * @param _workPhaseHours number of hours in which the programmer commits to his work
	 * @param _budget programmer's initial budget
	 * @param _prh ProgrammerResourceHandler instance
	 */
	public Programmer(String _name, List<String> _specializations, double _productivityRate,
			double _workPhaseHours, double _budget, ProgrammerResourceHandler _prh, Board _board) {
		this.name = _name;
		this.specializations = _specializations;
		this.productivityRate = _productivityRate;
		this.workPhaseHours = _workPhaseHours;
		this.budget = _budget;
		
		this.pInfo= new ProgrammerInfo(this.name);
		
		this.prh = _prh;
		this.board = _board;
		
		this.logger = new LogHelper(LogHelper.LOG_FILE_NAME);
		
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
		logger.log(this.name+" started working");
		while(!this.shouldStop) {
			try {
				Project projectToDo = this.mailbox.take();
				this.checkForNewBudget();

				if (this.isBudgetEnough(projectToDo) & projectToDo.isAnotherHandNeeded(this.workPhaseHours, this.productivityRate)) {
					this.acquireResources(projectToDo.getResources());
					
					logger.log(this.name+" committed to "+projectToDo.getId()+" for "+
							(int)this.workPhaseHours);
					
					this.budget = this.budget - this.workPhaseHours;
					
					projectToDo.commit(this);
					try {
						Thread.sleep((int)(this.workPhaseHours*MainParser.SIMULATION_HOUR)*
								this.simulatedSecond);
					} catch(InterruptedException e) {}
					
					this.checkForNewBudget();
					
					//logging
					logger.log(this.name+" is done with commitement on project "+
							projectToDo.getId());
					if(this.budget<this.workPhaseHours)
						logger.log(this.name+"'s budget run out");
					
					this.releaseResources(projectToDo.getResources());
					projectToDo.done(this);
					//handles budget
					this.budget = this.budget - this.workPhaseHours;
					
				}
			}	catch(InterruptedException e) {}
		}
		logger.log(this.name+" finished working");
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
	
	//Private functions
	
	private void acquireResources(List<String> ls) {
		List<Resource> listRes = this.prh.parseStringToObjects(ls);
		for(Iterator<Resource> i = listRes.iterator(); i.hasNext();) {
			Resource r = i.next();
			r.acquire();
			logger.log(this.name+" acquired "+r.getType());
		}
		
		//TODO Finish and test this method.
	}
	
	private void releaseResources(List<String> ls) {
		List<Resource> listRes = this.prh.parseStringToObjects(ls);
		for(Iterator<Resource> i = listRes.iterator(); i.hasNext();) {
			Resource r = i.next();
			logger.log(this.name+" released "+r.getType());
			r.realese();
		}
		
		//TODO Finish and test this method.
	}
	
	private boolean isBudgetEnough(Project p) {
		if (this.budget<p.getSize())
			return false;
		return true;
	}
	
	private void checkForNewBudget() {
		if(this.pInfo.isThereNewBudget()) {
			this.budget+=this.pInfo.getNewBudget();
		}
	}
	
	//TODO Remove this testing method
	public void sendNewProject(Project p) {
		try {
			this.mailbox.put(p);
		} catch(InterruptedException e) {}
	}
}
