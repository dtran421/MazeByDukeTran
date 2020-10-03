package gui;

import org.junit.Before;
import org.junit.Test;

import generation.Maze;

public class WizardTest extends Wizard {
	private Wizard wizard;
	private Maze maze;
	
	/**
	 * Set up the maze, robot, and controller and create a new wizard and
	 * assign the maze and robot to it
	 */
	@Before
	public final void setUp() {
		// set up controller and maze
		// assign the controller to the robot
		// assign the robot and maze to the wizard
	}
	
	/**
	 * Test case: See if the setUp properly sets up the wizard
	 * <p>
	 * Method under test: own set up
	 * <p>
	 * Correct behavior:
	 * the wizard and maze are not null, and the wizard can access a non-null robot and maze
	 */
	@Test
	public final void testWizard() {
		// check that the wizard is not null
		// check that the maze is not null
		// check that the wizard's robot is not null
		// check that the wizard's maze is not null
	}
	
	/**
	 * Test case: Correctness of the setRobot method
	 * <p>
	 * Method under test: setRobot(Robot r)
	 * <p>
	 * Correct behavior:
	 * assigns the inputed robot to the robot field
	 */
	@Test
	public final void testSetRobot() {
		// check that the wizard's robot equals the inputed robot
		// check that the initial battery is instantiated and equals the robot's initial battery
	}
	
	/**
	 * Test case: Correctness of the setMaze method
	 * <p>
	 * Method under test: setMaze(Maze maze)
	 * <p>
	 * Correct behavior:
	 * assigns the inputed maze to the maze field
	 */
	@Test
	public final void testSetMaze() {
		// check that the wizard's maze equals the inputed maze
	}
	
	/**
	 * Test case: Correctness of the drive2Exit method
	 * <p>
	 * Method under test: drive2Exit()
	 * <p>
	 * Correct behavior:
	 * drives the robot to the exit of the maze
	 */
	@Test
	public final void testDrive2Exit() {
		// run the method and check that the robot is at the exit (should return true)
		
		// move the robot 1 step into a wall (so that it stops)
		
		// check that an exception is thrown since the robot is stopped
	}
	
	/**
	 * Test case: Correctness of the drive1Step2Exit method
	 * <p>
	 * Method under test: drive1Step2Exit()
	 * <p>
	 * Correct behavior:
	 * drives the robot 1 step towards the exit of the maze
	 */
	@Test
	public final void testDrive1Step2Exit() {
		// check that an exception is thrown if the robot doesn't have enough energy for a rotation
		// check that an exception is thrown if the robot doesn't have enough energy for a move
		// check that an exception is thrown if the robot hits a wall
		// check that an exception is thrown if the robot tries to jump over an external wall
		
		// get the neighbor closest to the exit
		// run the method and check that it ends up on the same cell as the selected neighbor
	}
	
	/**
	 * Test case: Correctness of the getEnergyConsumption method
	 * <p>
	 * Method under test: getEnergyConsumption()
	 * <p>
	 * Correct behavior:
	 * returns the difference between the initial battery level and the robot's current battery level
	 */
	@Test
	public final void testGetEnergyConsumption() {
		// check that the returned value equals the difference between the initial and current
		// battery levels
		
		// check that the initial energy consumption is 0 (since it hasn't moved)
		
		// rotate the robot and move the robot 1 step
		
		// check that the energy consumption is equal to the rotate cost + move cost 
	}
	
	/**
	 * Test case: Correctness of the getPathLength method
	 * <p>
	 * Method under test: getPathLength()
	 * <p>
	 * Correct behavior:
	 * returns the robot's distance traveled
	 */
	@Test
	public final void testGetPathLength() {
		// check that the returned value equals the value returned from the robot's odometer
		
		// check that the initial path length is 0
		
		// rotate the robot and move the robot 1 step
		
		// check that the path length is 1
	}
}
