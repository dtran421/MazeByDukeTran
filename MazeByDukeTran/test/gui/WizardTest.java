package gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class WizardTest extends DriverTest {
	
	/**
	 * Create a new wizard and assign the maze and robot to it
	 */
	@Before
	public final void setUp() {
		// instantiate the robot and assign the controller to the robot
		robot = new ReliableRobot();
		robot.setController(controller);
		// instantiate the wizard and assign the robot and maze to the wizard
		driver = new Wizard();
		driver.setRobot(robot);
		driver.setMaze(maze);		
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
		// move the robot 1 step into a wall (so that it stops)
		robot.move(1);
		try {
			// check that method returns false since it was stopped
			assertFalse(driver.drive2Exit());
			
			resetRobot("Wizard", "Reliable", 1, 1, 1, 1);

			// run the method and check that the robot is at the exit (should return true)
			assertTrue(driver.drive2Exit());
		} catch (Exception e) {
			System.err.println("Something went wrong!");
			return;
		}
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
		robot.setBatteryLevel(robot.getEnergyForFullRotation()/4-1);
		assertThrows(Exception.class, () -> driver.drive1Step2Exit());
		resetRobot("Wizard", "Reliable", 1, 1, 1, 1);
		// check that an exception is thrown if the robot doesn't have enough energy for a move
		robot.setBatteryLevel(robot.getEnergyForStepForward()-1);
		// check that an exception is thrown if the robot hits a wall
		assertThrows(Exception.class, () -> driver.drive1Step2Exit());
		resetRobot("Wizard", "Reliable", 1, 1, 1, 1);
		robot.setBatteryLevel(driver.initialBatteryLevel);
		
		// while robot is not at exit
		while (!robot.isAtExit()) {
			try {
				int[] currPos = robot.getCurrentPosition();
				// get the neighbor closest to the exit
				int[] nextPos = maze.getNeighborCloserToExit(currPos[0], currPos[1]);
				
				// run the method and check that it ends up on the same cell as the selected neighbor
				driver.drive1Step2Exit();
				currPos = robot.getCurrentPosition();
				
				assertEquals(nextPos[0], currPos[0]);
				assertEquals(nextPos[1], currPos[1]);
			} catch (Exception e) {
				System.err.println("Something went wrong!");
				return;
			}
		}
	}
}
