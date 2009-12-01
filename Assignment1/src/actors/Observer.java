/**
 * 
 */
package actors;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import actorobjects.ProgrammerObject;

import resources.ProgrammerResourceHandler;

import utils.MainParser;
import utils.ObserverInfoGatherer;

/**
 * @author Alon Segal
 *
 */
public class Observer implements Runnable {
	
	private Scanner scanner;
	private String userInput;
	private ObserverInfoGatherer myInfo;
	private Board board;
	private ProgrammerResourceHandler prh;
	
	private final int const1 = 11;
	private final int const2 = 14;
	private final int const3 = 5;
	private final int const4 = 10;
	private final int const5 = 3;
	private final int const6 = 4;
	/**
	 * @param _mp my main parser
	 */
	public Observer(MainParser _mp) {
		this.myInfo = _mp.getObserverGatherer();
		this.scanner = new java.util.Scanner(System.in);
		this.userInput = "";
		this.prh = _mp.getPRH();
		this.board = _mp.getBoard();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (! this.userInput.equals("stop")) { 
			System.out.print("Observer> "); 
			this.userInput = this.scanner.nextLine(); 
			if (this.userInput.equals("completedProjects"))
				this.completedProjects();
			if (this.userInput.equals("pendingProjects"))
				this.pendingProjects();
			if (this.userInput.equals("currentProjects"))
				this.currentProjects();
			if (this.userInput.equals("programmers"))
				this.programmer();
			if (this.userInput.startsWith("programmer ")) {
				String str;
				str = this.userInput.substring(this.const1);
				this.programmerInfo(str);
			}
			if (this.userInput.startsWith("addProgrammer ")) {
				String str;
				str = this.userInput.substring(this.const2);
				String[] strArr;
				strArr = str.split(" ");
				if(strArr.length != this.const3) {
					System.out.println("are you trying to fool me? try again.");
				}
				else {
					String name;
					List<String> types;
					double rate;
					double budget;
					double workHours;
					name = strArr[0];
					types = Arrays.asList(strArr[1].split(","));
					try {
						rate = Double.parseDouble(strArr[2]);
						budget = Double.parseDouble(strArr[this.const5]);
						workHours = Double.parseDouble(strArr[this.const6]);
						this.addProgrammer(name, types, rate, budget, workHours);
					} 
					catch(NumberFormatException e) {  
						e.printStackTrace();
					}
				}
			}
			if (this.userInput.startsWith("addBudget ")) {
				String str;
				str = this.userInput.substring(this.const4);
				String[] strArr;
				strArr = str.split(" ");
				if(strArr.length != 2) {
					System.out.println("are you trying to fool me? try again.");
				}
				else {
					String name;
					try {
					double budget;
					name = strArr[0];
					budget = Double.parseDouble(strArr[1]);
					this.addBudget(name, budget);
					} 
					catch(NumberFormatException e) {  
						e.printStackTrace();
					}
				}
			}
			if (this.userInput.equals("stop")) {
				this.stop();
			}
	     } 
		System.out.println("Bye");
	}
	
	//private functions
	
	private void currentProjects() {
	for(Iterator<Project> i = this.myInfo.getCurrentProjects().iterator(); i.hasNext(); ) {
		Project tempProject = i.next();
		//id number
		System.out.println("Project ID: "+tempProject.getId());
		//type
		System.out.println("Type: "+tempProject.getType());
		//a project needs to tell me how much of it has been completed
		System.out.println("Hours completed: "+tempProject.getHoursCompleted());
		//which programmers work on it now
		System.out.println("Programmers that are working: "+tempProject.getProgrammers().toString());
	}
	}
	
	private void pendingProjects() {
	for(Iterator<Project> i = this.myInfo.getPendingProjects().iterator(); i.hasNext();) {
		Project tempProject = i.next();
		//id number
		System.out.println("Pending Project ID: "+tempProject.getId());
		//type
		System.out.println("Type: "+tempProject.getType());
		//dependancies
		System.out.println("Prerequisite projects: "+tempProject.getPrequesiteProjects().toString());
		/*for(Iterator<String> j = tempProject.getPrequesiteProjects().iterator(); i.hasNext();) {
			System.out.println(j.next());
		}*/
	}
	}
	
	private void completedProjects() {
	for(Iterator<Project> i = this.myInfo.getCompletedProjects().iterator(); i.hasNext();) {
		Project tempProject = i.next();
		//id number
		System.out.println("Info about project with ID "+tempProject.getId());
		//type
		System.out.println("Type: "+tempProject.getType());
		//time taken
		//obviously if the project hasent been worked on yet or hasent finished the time there
		//will be 0, or null. name of field will be time, and getter will be getTime
		System.out.println("Time elapsed: "+tempProject.getTimeElapsed()+" milliseconds");
	}
	}
	
	private void programmer() {
	for(Iterator<ProgrammerInfo> i = this.myInfo.getProgrammers().iterator(); i.hasNext();) {
		ProgrammerInfo tempProgrammer = i.next();
		//name of programmers
		System.out.println(tempProgrammer.getName());
	}
	}
	private void programmerInfo(String name) {
		for(Iterator<ProgrammerInfo> i = this.myInfo.getProgrammers().iterator(); i.hasNext();) {
			//still need the getter for the field name, as written in the todo above
			//and as written above, need to make an object ProgrammerInfo which has all the 
			//info about the programmers as the info written below:
			ProgrammerInfo tempProgrammer = i.next();
			if(tempProgrammer.getName().equals(name)) {
				System.out.println("Info about the programmer: "+tempProgrammer.getName());
				System.out.println("Productivity rate - "+tempProgrammer.getProductivityRate());
				System.out.println("Work phase hours - "+tempProgrammer.getWorkPhaseHours());
				System.out.println("Budget - "+tempProgrammer.getBudget());
				System.out.println("Specializations - "+tempProgrammer.getSpecializations().toString());
				//need to add a field on the programmer which is the programmers' current 
				//status which means: is he assigned to a project and if so, what
				//project id, and what resources does he hold
				if(tempProgrammer.isCurrentlyWorking()) {
					//current project he is working on
					System.out.println("Currently working on project ID: "+
							tempProgrammer.getCurrentProject().getId());
					//current resources he is holding
					System.out.println("Using resources: "+tempProgrammer.getCurrentResources().toString());
				}
				else {
					System.out.println("the programmer isnt currently assigned to any project");
				}
			}
		}
	}
	
	private void addProgrammer(String name, List<String> types, double rate, double budget, double workHours) {
		//TODO what do i do with the board and the programresourcehandler
		//also, how do i activate the thread using the initialthreadhandler?
		ProgrammerObject po = new ProgrammerObject();
		po.setName(name);
		po.setProductivityRate(rate);
		po.setWorkPhaseHours(workHours);
		po.setSpecializations(types);
		po.setBudget(budget);
		ProgrammerInfo newprog = new ProgrammerInfo(po);
		Programmer progy = new Programmer(po,newprog,this.prh,this.board);
		
		Thread t = new Thread((Runnable)progy);
		List<Thread> ls = this.myInfo.getMyThreads();
		t.start();
		ls.add(t);
		this.myInfo.setMyThreads(ls);
		try {
			this.board.doYourMagic();
		} catch (InterruptedException e) { System.out.print(""); }
	}
	
	private void addBudget(String programmerName ,Double budget) {
		for(Iterator<ProgrammerInfo> i = this.myInfo.getProgrammers().iterator(); i.hasNext();) {
			ProgrammerInfo tempy = i.next();
			if(tempy.getName().equals(programmerName)) {
				tempy.addBudget(budget);
			}
		}
	}
	
	private void stop() {
		for(Iterator<Thread> i = this.myInfo.getMyThreads().iterator(); i.hasNext();) {
			i.next().interrupt();
		}
	}

}
