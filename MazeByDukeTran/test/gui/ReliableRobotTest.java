package gui;

import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

public class ReliableRobotTest extends RobotTest {
	
	/**
	 * Assign the controller to the robot
	 */
	@Before
	public final void setUp() {
		// instantiate the robot
		robot = new ReliableRobot();
		// set up the robot by assigning the controller to the it
		robot.setController(controller);			
	}
	
	/**
	 * Test case: Correctness of the startFailureAndRepairProcess method
	 * <p>
	 * Method under test: startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures,
	 * int meanTimeToRepair))
	 * <p>
	 * Correct behavior:
	 * currently, since it is unimplemented, it will just throw an exception
	 */
	@Test
	public final void testStartFailureAndRepairProcess() {
		// check that an exception is thrown for all 4 directions
		for (Direction direction: Direction.values()) {
			assertThrows(UnsupportedOperationException.class, 
				() -> robot.startFailureAndRepairProcess(direction, 0, 0));
		}
	}
	
	/**
	 * Test case: Correctness of the stopFailureAndRepairProcess method
	 * <p>
	 * Method under test: stopFailureAndRepairProcess(Direction direction)
	 * <p>
	 * Correct behavior:
	 * currently, since it is unimplemented, it will just throw an exception
	 */
	@Test
	public final void testStopFailureAndRepairProcess() {
		// check that an exception is thrown for all 4 directions
		for (Direction direction: Direction.values()) {
			assertThrows(UnsupportedOperationException.class, 
				() -> robot.stopFailureAndRepairProcess(direction));
		}
	}
}
