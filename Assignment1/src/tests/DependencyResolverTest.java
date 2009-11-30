/**
 *
 */
package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exceptions.NoProjectsException;

import actors.Project;
import actors.ProjectImpl;

import utils.DependencyResolver;
import utils.DependencyResolverImpl;

/**
 * @author Alon Segal
 *
 */
public class DependencyResolverTest {

	private DependencyResolver dependencyResolver;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dependencyResolver = createDependencyResolver();
	}

	/**
     * This creates the object under test.
     *
     * @return a {@link Stack} instance.
     */
    protected DependencyResolver createDependencyResolver() {
    	//Creating dummy data for the tested instance
    	List<Project> l = new ArrayList<Project>();
		Project p1 = new ProjectImpl("1", "Project1", "gui", 3, new ArrayList<String>(),
				new ArrayList<String>());
		Project p2 = new ProjectImpl("2", "Project2", "qa", 10, new ArrayList<String>(),
				new ArrayList<String>());
		Project p3 = new ProjectImpl("3", "Project3", "qa", 44, new ArrayList<String>(),
				new ArrayList<String>());
		List<String> pp1 = new ArrayList<String>();
		List<String> pp2 = new ArrayList<String>();
		List<String> pp3 = new ArrayList<String>();

		pp1.add("11");
		pp1.add("33");
		pp1.add("44");

		pp2.add("13");
		pp2.add("23");
		pp2.add("113");
		pp2.add("87");

		pp3.add("33");
		pp3.add("23");
		pp3.add("119");

		p1.setPrequesiteProjects(pp1);
		p2.setPrequesiteProjects(pp2);
		p3.setPrequesiteProjects(pp3);

		l.add(p1);
		l.add(p2);
		l.add(p3);

		return new DependencyResolverImpl(l);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link utils.DependencyResolverImpl#DependencyResolverImpl()}.
	 */
	@Test
	public final void testDependencyResolverImpl() {
		assertNotNull("new DependencyResolver is not null", this.dependencyResolver);
		assertTrue("Not all projects in the queue", dependencyResolver.getAllProjects().size()==3);
	}

	/**
	 * Test method for {@link utils.DependencyResolverImpl#getReadyProjects()}.
	 */
	@Test
	public final void testGetReadyProjects() {
		try {
            dependencyResolver.getReadyProjects();
            fail("Exception expected!");
        } catch (NoProjectsException e) {
        	System.out.print("");
        }

		dependencyResolver.updateCompProj("23");
		dependencyResolver.updateCompProj("33");
		dependencyResolver.updateCompProj("13");
		dependencyResolver.updateCompProj("113");
		dependencyResolver.updateCompProj("87");
		dependencyResolver.updateCompProj("119");

		List<Project> ls;
		ls = dependencyResolver.getReadyProjects();

		assertEquals("Not all the ready projects", ls.size(),2);
		assertEquals("Done Projects were no removed", dependencyResolver.getAllProjects().size(),1);

	}

	/**
	 * Test method for {@link utils.DependencyResolverImpl#updateCompProj(int)}.
	 */
	@Test
	public final void testUpdateCompProj() {
		dependencyResolver.updateCompProj("23");
		dependencyResolver.updateCompProj("33");
		dependencyResolver.updateCompProj("13");
		dependencyResolver.updateCompProj("113");
		dependencyResolver.updateCompProj("87");

		PriorityQueue<Project> ls = dependencyResolver.getAllProjects();
		assertEquals("The update hasn't succeded", ls.remove().getName(),"Project2");
		assertEquals("The update hasn't succeded", ls.remove().getName(),"Project3");
		assertEquals("The update hasn't succeded", ls.remove().getName(),"Project1");
	}

	/**
	 * Test method for {@link utils.DependencyResolverImpl#getAllProjects()}.
	 */
	@Test
	public final void testGetAllProjects() {
		PriorityQueue<Project> ls = dependencyResolver.getAllProjects();
		assertEquals("Not all projects in the queue", ls.size(),3);
	}

	/**
	 * Test method for {@link utils.DependencyResolverImpl#areThereReadyProjects()}.
	 */
	@Test
	public final void testAreThereReadyProjects() {
		assertFalse("There are no ready projects, but indicated that there are", dependencyResolver.areThereReadyProjects());
		dependencyResolver.updateCompProj("11");
		dependencyResolver.updateCompProj("33");
		dependencyResolver.updateCompProj("44");
		assertTrue("There are ready project, wrong indication", dependencyResolver.areThereReadyProjects());
	}
}
