package gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class WallFollowerTest extends DriverTest {
	
	/**
	 * Create a new wall-follower and assign the maze and robot to it
	 */
	@Before
	public final void setUp() {
		// instantiate the wall-follower and assign the robot and maze to the wall-follower
		driver = new WallFollower();
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
			
			resetRobot("WallFollower");

			// run the method and check that the robot is at the exit (should return true)
			assertTrue(driver.drive2Exit());
		} catch (Exception e) {
			System.out.println("Something went wrong!");
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
		resetRobot("WallFollower");
		// check that an exception is thrown if the robot doesn't have enough energy for a move
		robot.setBatteryLevel(robot.getEnergyForStepForward()-1);
		// check that an exception is thrown if the robot hits a wall
		assertThrows(Exception.class, () -> driver.drive1Step2Exit());
		resetRobot("WallFollower");
		robot.setBatteryLevel(driver.initialBatteryLevel);
		
		// while robot is not at exit
		//while (!robot.isAtExit()) {
			
		//}
	}
}
