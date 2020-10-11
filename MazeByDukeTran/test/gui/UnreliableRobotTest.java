package gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

public class UnreliableRobotTest extends RobotTest {	
	/**
	 * Assign the controller to the robot
	 */
	@Before
	public final void setUp() {
		// instantiate the robot
		robot = new UnreliableRobot(0, 0, 0, 0);
		// set up the robot by assigning the controller to the it
		robot.setController(controller);			
	}
	
	/**
	 * Test case: Correctness of the parameterized constructor
	 * <p>
	 * Method under test: UnreliableRobot(int f, int l, int r, int b)
	 * <p>
	 * Correct behavior:
	 * instantiate an UnreliableRobot object with reliable or unreliable sensors
	 * corresponding to the inputed parameters
	 */
	@Test
	public final void testUnreliableRobot() {
		// make an UnreliableRobot with all unreliable sensors, check that all sensors are non-null and UnreliableSensors
		assertNotNull(robot.forwardSensor);
		assertNotNull(robot.leftSensor);
		assertNotNull(robot.rightSensor);
		assertNotNull(robot.backwardSensor);
		assertEquals(UnreliableSensor.class, robot.forwardSensor.getClass());
		assertEquals(UnreliableSensor.class, robot.leftSensor.getClass());
		assertEquals(UnreliableSensor.class, robot.rightSensor.getClass());
		assertEquals(UnreliableSensor.class, robot.backwardSensor.getClass());

		
		// make an UnreliableRobot with all reliable sensors, check that all sensors are non-null and ReliableSensors
		robot = new UnreliableRobot(1, 1, 1, 1);
		assertNotNull(robot.forwardSensor);
		assertNotNull(robot.leftSensor);
		assertNotNull(robot.rightSensor);
		assertNotNull(robot.backwardSensor);
		assertEquals(ReliableSensor.class, robot.forwardSensor.getClass());
		assertEquals(ReliableSensor.class, robot.leftSensor.getClass());
		assertEquals(ReliableSensor.class, robot.rightSensor.getClass());
		assertEquals(ReliableSensor.class, robot.backwardSensor.getClass());
		
		// make an UnreliableRobot with one reliable sensor and all other unreliable sensors
		robot = new UnreliableRobot(1, 0, 0, 0);
		assertEquals(ReliableSensor.class, robot.forwardSensor.getClass());
		assertEquals(UnreliableSensor.class, robot.leftSensor.getClass());
		assertEquals(UnreliableSensor.class, robot.rightSensor.getClass());
		assertEquals(UnreliableSensor.class, robot.backwardSensor.getClass());
		robot = new UnreliableRobot(0, 1, 0, 0);		
		assertEquals(UnreliableSensor.class, robot.forwardSensor.getClass());
		assertEquals(ReliableSensor.class, robot.leftSensor.getClass());
		assertEquals(UnreliableSensor.class, robot.rightSensor.getClass());
		assertEquals(UnreliableSensor.class, robot.backwardSensor.getClass());
		robot = new UnreliableRobot(0, 0, 1, 0);
		assertEquals(UnreliableSensor.class, robot.forwardSensor.getClass());
		assertEquals(UnreliableSensor.class, robot.leftSensor.getClass());
		assertEquals(ReliableSensor.class, robot.rightSensor.getClass());
		assertEquals(UnreliableSensor.class, robot.backwardSensor.getClass());
		robot = new UnreliableRobot(0, 0, 0, 1);
		assertEquals(UnreliableSensor.class, robot.forwardSensor.getClass());
		assertEquals(UnreliableSensor.class, robot.leftSensor.getClass());
		assertEquals(UnreliableSensor.class, robot.rightSensor.getClass());
		assertEquals(ReliableSensor.class, robot.backwardSensor.getClass());
		
		// make an UnreliableRobot with one unreliable sensor and all other reliable sensors
		robot = new UnreliableRobot(0, 1, 1, 1);
		assertEquals(UnreliableSensor.class, robot.forwardSensor.getClass());
		assertEquals(ReliableSensor.class, robot.leftSensor.getClass());
		assertEquals(ReliableSensor.class, robot.rightSensor.getClass());
		assertEquals(ReliableSensor.class, robot.backwardSensor.getClass());
		robot = new UnreliableRobot(1, 0, 1, 1);
		assertEquals(ReliableSensor.class, robot.forwardSensor.getClass());
		assertEquals(UnreliableSensor.class, robot.leftSensor.getClass());
		assertEquals(ReliableSensor.class, robot.rightSensor.getClass());
		assertEquals(ReliableSensor.class, robot.backwardSensor.getClass());
		robot = new UnreliableRobot(1, 1, 0, 1);
		assertEquals(ReliableSensor.class, robot.forwardSensor.getClass());
		assertEquals(ReliableSensor.class, robot.leftSensor.getClass());
		assertEquals(UnreliableSensor.class, robot.rightSensor.getClass());
		assertEquals(ReliableSensor.class, robot.backwardSensor.getClass());
		robot = new UnreliableRobot(1, 1, 1, 0);
		assertEquals(ReliableSensor.class, robot.forwardSensor.getClass());
		assertEquals(ReliableSensor.class, robot.leftSensor.getClass());
		assertEquals(ReliableSensor.class, robot.rightSensor.getClass());
		assertEquals(UnreliableSensor.class, robot.backwardSensor.getClass());
	}
	
	/**
	 * Test case: Correctness of the startFailureAndRepairProcess method
	 * <p>
	 * Method under test: startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures,
	 * int meanTimeToRepair))
	 * <p>
	 * Correct behavior:
	 * starts a new thread to manage the failure and repair process for a given sensor
	 */
	@Test
	public final void testStartFailureAndRepairProcess() {
		final int MEAN_TIME_BETWEEN_FAILURES = 400;
		final int MEAN_TIME_TO_REPAIR = 200;
		// check that a repair process thread has been instantiated for each sensor
		for (Direction direction: Direction.values()) {
			robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
			assertNotNull(((UnreliableSensor)robot.leftSensor).repairProcess);
		}
		
		// make an UnreliableRobot with all unreliable sensors, check that no error is thrown when running the method
		robot = new UnreliableRobot(0, 0, 0, 0);
		for (Direction direction: Direction.values())
			robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
		
		// make an UnreliableRobot with all reliable sensors, check that all sensors throw an exception when the method is called
		robot = new UnreliableRobot(1, 1, 1, 1);	
		for (Direction direction: Direction.values())
			assertThrows(UnsupportedOperationException.class, () -> robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR));
		
		// make an UnreliableRobot with one reliable sensor and all other unreliable sensors
		robot = new UnreliableRobot(1, 0, 0, 0);
		for (Direction direction: Direction.values()) {
			if (direction == Direction.FORWARD) 
				assertThrows(UnsupportedOperationException.class, () -> robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR));
			else
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
		}
		robot = new UnreliableRobot(0, 1, 0, 0);		
		for (Direction direction: Direction.values()) {
			if (direction == Direction.LEFT) 
				assertThrows(UnsupportedOperationException.class, () -> robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR));
			else
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
		}
		robot = new UnreliableRobot(0, 0, 1, 0);
		for (Direction direction: Direction.values()) {
			if (direction == Direction.RIGHT) 
				assertThrows(UnsupportedOperationException.class, () -> robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR));
			else
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
		}
		robot = new UnreliableRobot(0, 0, 0, 1);
		for (Direction direction: Direction.values()) {
			if (direction == Direction.BACKWARD) 
				assertThrows(UnsupportedOperationException.class, () -> robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR));
			else
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
		}
		
		// make an UnreliableRobot with one unreliable sensor and all other reliable sensors
		robot = new UnreliableRobot(0, 1, 1, 1);
		for (Direction direction: Direction.values()) {
			if (direction == Direction.FORWARD) 
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
			else
				assertThrows(UnsupportedOperationException.class, () -> robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR));
		}
		robot = new UnreliableRobot(1, 0, 1, 1);
		for (Direction direction: Direction.values()) {
			if (direction == Direction.LEFT) 
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
			else
				assertThrows(UnsupportedOperationException.class, () -> robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR));
		}
		robot = new UnreliableRobot(1, 1, 0, 1);
		for (Direction direction: Direction.values()) {
			if (direction == Direction.RIGHT) 
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
			else
				assertThrows(UnsupportedOperationException.class, () -> robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR));
		}
		robot = new UnreliableRobot(1, 1, 1, 0);
		for (Direction direction: Direction.values()) {
			if (direction == Direction.BACKWARD) 
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
			else
				assertThrows(UnsupportedOperationException.class, () -> robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR));
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
		final int MEAN_TIME_BETWEEN_FAILURES = 400;
		final int MEAN_TIME_TO_REPAIR = 200;
		for (Direction direction: Direction.values()) {
			// check that an exception is thrown if no repair process thread has been started
			assertThrows(UnsupportedOperationException.class, () -> robot.stopFailureAndRepairProcess(direction));
			robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
			robot.stopFailureAndRepairProcess(direction);
		}
		assertEquals(null, ((UnreliableSensor)robot.forwardSensor).repairProcess);
		assertEquals(null, ((UnreliableSensor)robot.leftSensor).repairProcess);
		assertEquals(null, ((UnreliableSensor)robot.rightSensor).repairProcess);
		assertEquals(null, ((UnreliableSensor)robot.backwardSensor).repairProcess);

		// make an UnreliableRobot with all unreliable sensors, check that no error is thrown when running the method
		robot = new UnreliableRobot(0, 0, 0, 0);
		for (Direction direction: Direction.values()) {
			robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
			robot.stopFailureAndRepairProcess(direction);
		}
		
		// make an UnreliableRobot with all reliable sensors, check that all sensors throw an exception when the method is called
		robot = new UnreliableRobot(1, 1, 1, 1);	
		for (Direction direction: Direction.values())
			assertThrows(UnsupportedOperationException.class, () -> robot.stopFailureAndRepairProcess(direction));
		
		// make an UnreliableRobot with one reliable sensor and all other unreliable sensors
		robot = new UnreliableRobot(1, 0, 0, 0);
		for (Direction direction: Direction.values()) {
			if (direction == Direction.FORWARD)
				assertThrows(UnsupportedOperationException.class, () -> robot.stopFailureAndRepairProcess(direction));
			else {
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
				robot.stopFailureAndRepairProcess(direction);
			}
		}
		robot = new UnreliableRobot(0, 1, 0, 0);
		for (Direction direction: Direction.values()) {
			if (direction == Direction.LEFT)
				assertThrows(UnsupportedOperationException.class, () -> robot.stopFailureAndRepairProcess(direction));
			else {
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
				robot.stopFailureAndRepairProcess(direction);
			}
		}
		robot = new UnreliableRobot(0, 0, 1, 0);
		for (Direction direction: Direction.values()) {
			if (direction == Direction.RIGHT)
				assertThrows(UnsupportedOperationException.class, () -> robot.stopFailureAndRepairProcess(direction));
			else {
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
				robot.stopFailureAndRepairProcess(direction);
			}
		}
		robot = new UnreliableRobot(0, 0, 0, 1);
		for (Direction direction: Direction.values()) {
			if (direction == Direction.BACKWARD)
				assertThrows(UnsupportedOperationException.class, () -> robot.stopFailureAndRepairProcess(direction));
			else {
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
				robot.stopFailureAndRepairProcess(direction);
			}
		}

		// make an UnreliableRobot with one unreliable sensor and all other reliable sensors
		robot = new UnreliableRobot(0, 1, 1, 1);
		for (Direction direction: Direction.values()) {
			if (direction == Direction.FORWARD) {
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
				robot.stopFailureAndRepairProcess(direction);
			}
			else 
				assertThrows(UnsupportedOperationException.class, () -> robot.stopFailureAndRepairProcess(direction));
		}
		robot = new UnreliableRobot(1, 0, 1, 1);
		for (Direction direction: Direction.values()) {
			if (direction == Direction.LEFT) {
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
				robot.stopFailureAndRepairProcess(direction);
			}
			else 
				assertThrows(UnsupportedOperationException.class, () -> robot.stopFailureAndRepairProcess(direction));
		}
		robot = new UnreliableRobot(1, 1, 0, 1);
		for (Direction direction: Direction.values()) {
			if (direction == Direction.RIGHT) {
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
				robot.stopFailureAndRepairProcess(direction);
			}
			else 
				assertThrows(UnsupportedOperationException.class, () -> robot.stopFailureAndRepairProcess(direction));
		}
		robot = new UnreliableRobot(1, 1, 1, 0);
		for (Direction direction: Direction.values()) {
			if (direction == Direction.BACKWARD) {
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
				robot.stopFailureAndRepairProcess(direction);
			}
			else 
				assertThrows(UnsupportedOperationException.class, () -> robot.stopFailureAndRepairProcess(direction));
		}
	}
}
