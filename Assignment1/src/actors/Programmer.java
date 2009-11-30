/**
 * 
 */
package actors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

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
	private BlockingQueue<ProgrammerMessage> mailbox;
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
			double _workPhaseHours, double _budget, ProgrammerInfo _pi, 
			ProgrammerResourceHandler _prh, Board _board) {
		this.name = _name;
		this.specializations = _specializations;
		this.productivityRate = _productivityRate;
		this.workPhaseHours = _workPhaseHours;
		this.budget = _budget;
		
		this.pInfo= _pi;
		
		this.prh = _prh;
		this.board = _board;
		
		this.logger = new LogHelper(LogHelper.LOG_FILE_NAME);
		this.mailbox = new ArrayBlockingQueue<ProgrammerMessage>(/*ManagerPropertyParser.NUM_OF_PROJECTS*/100, true);
		this.addInfoToBoard();
		List<ProgrammerInfo> tempList;
		tempList = this.board.getMyObserver().getProgrammers();
		tempList.add(this.pInfo);
		this.board.getMyObserver().setProgrammers(tempList);
		
		
	}
	
	@Override
	public void run() {
		this.logger.log(this.name+" started working");
		while(!this.shouldStop) {
			try {
				ProgrammerMessage message = this.mailbox.take();
				
				if (message.getType().equals(ProgrammerMessage.PROGRAMMER_PROJECT)) 
					this.takeProject(message.getProject());
			
				if (message.getType().equals(ProgrammerMessage.PROGRAMMER_BUDGET)) 
					this.budget += message.getBudget();
				//Project projectToDo = this.mailbox.take();
			}	catch(InterruptedException e) {}
				
		}
		this.logger.log(this.name+" finished working");
	}
	
	//Private functions
	
	/**
	 * Trying to stop the thread
	 */
	private void stop() {
		this.shouldStop = true; 
	}
	
	private void addInfoToBoard() {
        ConcurrentHashMap<String,Collection<BlockingQueue<ProgrammerMessage>>> temp;
        temp = this.board.getMyProgrammersLink();
        Collection<BlockingQueue<ProgrammerMessage>> c = new ArrayList<BlockingQueue<ProgrammerMessage>>();
        for(Iterator<String> i = this.specializations.iterator(); i.hasNext();) {
            String tempType = i.next();
            if(temp!=null && !temp.containsKey(tempType)) {
            temp.putIfAbsent(tempType, c);
            c.add(this.mailbox);
            }
            else {
                temp.get(tempType).add(this.mailbox);
            }
        }
        this.board.setMyProgrammersLink(temp);    
    }
	
	private void acquireResources(List<String> ls) {
		List<Resource> listRes = this.prh.parseStringToObjects(ls);
		for(Iterator<Resource> i = listRes.iterator(); i.hasNext();) {
			Resource r = i.next();
			r.acquire();
			this.logger.log(this.name+" acquired "+r.getType());
		}
	}
	
	private void releaseResources(List<String> ls) {
		List<Resource> listRes = this.prh.parseStringToObjects(ls);
		for(Iterator<Resource> i = listRes.iterator(); i.hasNext();) {
			Resource r = i.next();
			this.logger.log(this.name+" released "+r.getType());
			r.realese();
		}
	}
	
	private boolean isBudgetEnough(Project p) {
		if (this.budget<p.getSize())
			return false;
		return true;
	}
	
	private void takeProject(Project projectToDo) {
		//TODO check the threads bug here
		if (this.isBudgetEnough(projectToDo) & projectToDo.isAnotherHandNeeded(this.workPhaseHours, this.productivityRate)) {
			this.logger.log(this.name+" committed to "+projectToDo.getId()+" for "+
					(int)this.workPhaseHours);
			this.acquireResources(projectToDo.getResources());
			
			this.budget = this.budget - this.workPhaseHours;
			
			this.pInfo.setIsWorking(true);
			this.pInfo.setCurrentProject(projectToDo);
			//Parse the string to objects
			this.pInfo.setCurrentResources(this.prh.parseStringToObjects(projectToDo.getResources()));
			
			projectToDo.commit(this.pInfo);

			//System.out.println((int)(this.workPhaseHours*MainParser.SIMULATION_HOUR)*
					//this.simulatedSecond);

			//adding data to the observerinfogatherer about current projects being worked on
			List<Project> tempList;
			tempList = this.board.getMyObserver().getCurrentProjects();
			if(!tempList.contains(projectToDo)) {
			tempList.add(projectToDo);
			this.board.getMyObserver().setCurrentProjects(tempList);
			}
			//System.out.println(this.name+" Went to sleep");
			try {
				Thread.sleep((int)(this.workPhaseHours*MainParser.SIMULATION_HOUR)*
						this.simulatedSecond);
			} catch(InterruptedException e) {System.out.println("Can't sleep");}
			
			//COMPLETED Working
			this.pInfo.setIsWorking(false);
			if (projectToDo.isCompleted()) {
				this.board.doneWithProject(projectToDo);
				//remove the project from the current projects and move it to the completedprojects
				//in the observerinfogatherer
				List<Project> tempList2;
				tempList2 = this.board.getMyObserver().getCompletedProjects();
				tempList = this.board.getMyObserver().getCurrentProjects();
				if(tempList.contains(projectToDo)) {
				tempList.remove(projectToDo);
				this.board.getMyObserver().setCurrentProjects(tempList);
				}
				if(!tempList2.contains(projectToDo)) {
				tempList2.add(projectToDo);
				this.board.getMyObserver().setCompletedProjects(tempList2);
				}
			} else {
				this.board.updateCompletedPhase();
			}
			//logging
			this.logger.log(this.name+" is done with commitement on project "+
					projectToDo.getId());
			if(this.budget<this.workPhaseHours)
				this.logger.log(this.name+"'s budget run out");
			
			this.releaseResources(projectToDo.getResources());
			projectToDo.done(this.pInfo);
			//handles budget
			this.budget = this.budget - this.workPhaseHours;
			
		}
	}
	/*
	public void sendNewProject(Project p) {
		try {
			this.mailbox.put(new ProgrammerMessage(ProgrammerMessage.PROGRAMMER_PROJECT, p));
		} catch(InterruptedException e) {}
	}*/
}
