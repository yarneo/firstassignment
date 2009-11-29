import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
public class ConfCreator  {
	//General specifications:
	private static final double SIMULATION_HOUR = 0.0; //simulation hour.
	private static final double PREREQUISITE_CHANCE = 0.5; //the chance for each regular project to be a prerequisite of an advanced project
	
	//Max/Exact values:
	//SPECIFY POSITIVE NUMBER FOR AN UPPER LIMIT OR NEGATIVE NUMBER FOR EXACT SPECIFICATION
	//for example: PROGRAMMERS=20 will create between 1 to 20 programmers (randomly chosen)
	//     whereas PROGRAMMERS=-20 will create EXACTLY 20 programmers.
	
	private static final int MANAGERS=20; //managers count
	
	private static final int RESOURCE_TYPES=1; //amount of resource types
	private static final int RESOURCE_AMOUNTS=50;// for each resource type, the amount of such resources
	
	private static final int PROJECT_TYPES=1; //amount of project types
	private static final int PROJECT_MAX_SIZE=30; //maximum project size
	private static final int REGULAR_PROJECTS=10; //amount of projects with no prerequisites
	private static final int ADVANCED_PROJECTS=10; //amount of projects with prerequisites (only a regular projects can be a prerequisite of an advanced projet) 
	//note that the overall amount of projects won't be less than the amount of managers.
	
	
	private static final int PROGRAMMERS=50; //amount of programmers
	private static final int PROGRAMMER_SKILLS_AMOUNT=3; //amount of each programmer's skills
	private static final int PROGRAMMER_BUDGET=200; //amount of budget for each programmer
	private static final int PROGRAMMER_WORK_PHASE=3;//well, work phase, obviously.
	//these values should not be negative:
	private static final double PROGRAMMER_PRODUCTIVITY_MIN = 0.1; //minimum productivity
	private static final double PROGRAMMER_PRODUCTIVITY_MAX = 2; //maximum productivity

	
	//PROTIP: Don't even try to understand the code (it was written rather hastily).
	/**
	 * @param args unused.
	 */
	public static void main(String[] args) {
		ConfCreator.createConf();
	}
	private static int randValue(int maxvalue, int minvalue){
		if (maxvalue<0)
			return -1*maxvalue;
		return (int)((Math.random()*maxvalue))+minvalue;
	}
	private static double randValue(double maxvalue, double minvalue) {
		if (maxvalue<0)
			return -1*maxvalue;
		return (int)((Math.random()*maxvalue)+1)+minvalue;
	}
	private static String[] randListArr(int size,String prefix){
		String[] ans=new String[size];
		for (int i=1;i<=size;i++)
			ans[i-1]=prefix + i; //randName();
		return ans;
	}
	private static String arrToString(String[] arr){
		String ans="";
		for (int i=0;i<arr.length-1;i++)
			ans+=arr[i] + ",";
		//if (ans.length()>0)
			ans+=arr[arr.length-1];
		return ans;
	}
	private static String[] randArr(String[] arr, int maxCount) {
		String[] newArr=ConfCreator.randArr(arr);
		String[] ans=new String[Math.min(Math.abs(maxCount), arr.length)];
		int start=ConfCreator.randValue(newArr.length-1, 0);
		for (int i=0;i<ans.length;i++)
			ans[i]=newArr[(start+i)%newArr.length];
		return ans;
	}
	private static String[] randArr(String[] arr) {
		Vector<String>ansVec=new Vector<String>();
		while (ansVec.size()==0)
			for (int i=0;i<arr.length;i++)
				if (Math.random()>(double)(1/2))
					ansVec.add(arr[i]);
		String[]ans=new String[ansVec.size()];
		for (int i=0;i<ansVec.size();i++)
			ans[i]=ansVec.elementAt(i);
		return ans;
	}
	/**
	 * creates configuration.
	 */
	@SuppressWarnings("unchecked")
	public static void  createConf(){
		Properties p=new Properties();
		int programmersCount=ConfCreator.randValue(ConfCreator.PROGRAMMERS,1);
		int resourcesCount=ConfCreator.randValue(ConfCreator.RESOURCE_TYPES,1);
		int projectTypesCount=ConfCreator.randValue(ConfCreator.PROJECT_TYPES,1);
		int normalProjectsCount=ConfCreator.randValue(ConfCreator.REGULAR_PROJECTS,1);
		int advancedProjectsCount=ConfCreator.randValue(ConfCreator.ADVANCED_PROJECTS,0);
		int managersCount=ConfCreator.randValue(ConfCreator.MANAGERS,1);
		managersCount=Math.min(managersCount, normalProjectsCount+advancedProjectsCount);
		String[] resources=ConfCreator.randListArr(resourcesCount, "Resource_");
		String[] managers=ConfCreator.randListArr(managersCount, "Manager_");
		String[] programmers=ConfCreator.randListArr(programmersCount, "Programmer_");
		String[] projectTypes=ConfCreator.randListArr(projectTypesCount, "Project_Type");

		p.setProperty("projectTypes", ConfCreator.arrToString(projectTypes));
		p.setProperty("resourceTypes", ConfCreator.arrToString(resources));
		for (int i=0;i<resourcesCount;i++)
			p.setProperty("amount_"+resources[i], ""+ConfCreator.randValue(ConfCreator.RESOURCE_AMOUNTS,1));

		for (int i=1;i<=managersCount;i++)
			p.setProperty("projectManager"+i+"Name", managers[i-1]);

		p.setProperty("numOfProgrammers",""+programmersCount);
		p.setProperty("numOfProjectManagers",""+managersCount);
		p.setProperty("simulationHour",""+ConfCreator.SIMULATION_HOUR);

		for (int i=1;i<=programmersCount;i++){
			p.setProperty("programmer"+i+"Name", programmers[i-1]);
			p.setProperty("programmer" + i + "ProductivityRate", ""+ConfCreator.randValue(ConfCreator.PROGRAMMER_PRODUCTIVITY_MIN,ConfCreator.PROGRAMMER_PRODUCTIVITY_MAX));
			p.setProperty("programmer" + i + "WorkPhaseHours",""+ConfCreator.randValue(ConfCreator.PROGRAMMER_WORK_PHASE,1));
			p.setProperty("programmer" + i + "Budget",""+ConfCreator.randValue(ConfCreator.PROGRAMMER_BUDGET,1));
			p.setProperty("programmer" + i + "Specialization",ConfCreator.arrToString((ConfCreator.randArr(projectTypes,ConfCreator.randValue(ConfCreator.PROGRAMMER_SKILLS_AMOUNT,1)))));
		}

		//Map<Integer, Vector<Integer>> projectsLists=new HashMap<Integer, Vector<Integer>>();
		Vector<Integer>[] projectsLists=new Vector[managersCount];

		Properties[] pr=new Properties[managersCount];
		for (int i=0;i<managersCount;i++){
			projectsLists[i]=new Vector<Integer>();
			pr[i]=new Properties();
			pr[i].setProperty("type", projectTypes[ConfCreator.randValue(projectTypesCount-1, 0)]);
		}
		

		//String[] normalProjectsArr=new String[normalProjectsCount];
		int filledProjectsCounter=0;
		for (int i=1;i<=normalProjectsCount;i++){
			int man=ConfCreator.getManagerIndex(filledProjectsCounter,managersCount);
			ConfCreator.fillProject(projectsLists, pr, i, man, resources);
			pr[man].setProperty("prerequisiteProjects"+i, "none");
			filledProjectsCounter++;
		}
		for (int j=1;j<=advancedProjectsCount;j++){
			int man=ConfCreator.getManagerIndex(filledProjectsCounter,managersCount);
			int id=j+normalProjectsCount;
			projectsLists[man].add(new Integer(id));
			pr[man].setProperty("size"+id, ""+ConfCreator.randValue(ConfCreator.PROJECT_MAX_SIZE,1));
			String preReqs="";//+((int)Math.random()*normalProjectsCount);
			for (int k=1;k<=normalProjectsCount;k++)
				if (Math.random()<ConfCreator.PREREQUISITE_CHANCE)
					preReqs+=k+",";
			if (preReqs.length()>0)
				preReqs=preReqs.substring(0, preReqs.length()-1);
			else
				preReqs="none";
			pr[man].setProperty("prerequisiteProjects"+id, preReqs);
			pr[man].setProperty("resourcesNeeded"+id, ConfCreator.arrToString(ConfCreator.randArr(resources)));
			filledProjectsCounter++;
		}

		for (int i=0;i<pr.length;i++){
			pr[i].setProperty("projectIds", ConfCreator.vectorToString(projectsLists[i]));
			try {
				FileWriter fw=new FileWriter(managers[i]+".txt");
				pr[i].store(fw, "project manager configuration file - " + managers[i]);
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		final String fn="confrand.txt";
		try {
			p.store(new FileWriter(fn), "SPL Assignment1 random configuration file - generated by ConfCreator");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void fillProject(Vector<Integer>[] projectsLists, Properties[] pr, int i, int man, String[] resources) {
		projectsLists[man].add(new Integer(i));
		pr[man].setProperty("size"+i, ""+ConfCreator.randValue(ConfCreator.PROJECT_MAX_SIZE,1));
		pr[man].setProperty("resourcesNeeded"+i, ConfCreator.arrToString(ConfCreator.randArr(resources)));
	}
	private static int getManagerIndex(int filledProjects, int managersCount){
		if (filledProjects<managersCount)
			return filledProjects;
		else
			return (int)((Math.random()*managersCount));
	}
	private static String vectorToString(Vector<Integer> vector) {
		String ans="";
		Iterator<Integer>itr=vector.iterator();
		while (itr.hasNext()){
			ans+=itr.next()+",";
		}
		if (ans.length()>0)
			return ans.substring(0, ans.length()-1);
		else
			return "none";
	}

}
