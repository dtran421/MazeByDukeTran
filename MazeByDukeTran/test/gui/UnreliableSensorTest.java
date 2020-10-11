package gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import gui.Robot.Direction;

public class UnreliableSensorTest extends SensorTest {
	private final int MEAN_TIME_BETWEEN_FAILURES = 4000; // milliseconds
	private final int MEAN_TIME_TO_REPAIR = 2000;
	
	/**
	 * Create a new unreliable sensor for each test
	 */
	@Before
	public final void setUp() {
		// create an unreliable sensor	
		sensor = new UnreliableSensor();
		// assign the maze to the sensor
		sensor.setMaze(maze);
	}
	
	/**
	 * Test case: Correctness of the parameterized constructor 
	 * <p>
	 * Method under test: UnreliableSensor(Direction direction)
	 * <p>
	 * Correct behavior:
	 * the unreliable sensor is instantiated with the correct direction
	 */
	@Test
	public final void testUnreliableSensor() {
		// make sure the parameterized constructor instantiates a non-null sensor
		ReliableSensor testSensor = new UnreliableSensor(Direction.LEFT);
		testSensor.setMaze(maze);
		assertNotNull(testSensor);
		// check that the mounted direction matches the inputed direction
		assertEquals(Direction.LEFT, testSensor.mountedDirection);
	}
	
	/**
	 * Test case: Correctness of the startFailureAndRepairProcess method
	 * <p>
	 * Method under test: startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair) 
	 * <p>
	 * Correct behavior:
	 * starts a new thread to run the failure and repair process
	 */
	@Test
	public final void testStartFailureAndRepairProcess() {		
		sensor.startFailureAndRepairProcess(MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
		
		// check that the repair process thread has been created (not null)
		assertNotNull(((UnreliableSensor)sensor).repairProcess);

		// check that the sensor is operational at the beginning
		assertTrue(sensor.isOperational);
		// check that at some point, the sensor is under repair
		boolean tested = false;
		while (!tested) {
			try {
				Thread.sleep(1000);
				if (!sensor.isOperational) tested = true;
			} catch (InterruptedException e) {
				System.out.println();
			}
		}
		// check that at some point later, the sensor is operational again
		tested = false;
		while (!tested) {
			try {
				Thread.sleep(1000);
				if (!sensor.isOperational) tested = true;
			} catch (InterruptedException e) {
				System.out.println();
			}
		}
	}
	
	/**
	 * Test case: Correctness of the stopFailureAndRepairProcess method
	 * <p>
	 * Method under test: stopFailureAndRepairProcess() 
	 * <p>
	 * Correct behavior:
	 * terminates the running thread and the robot should now be operational
	 */
	@Test
	public final void testStopFailureAndRepairProcess() {		
		// check that an exception if thrown if no thread is currently running
		assertThrows(UnsupportedOperationException.class, () -> sensor.stopFailureAndRepairProcess());
		// check that the sensor is operational after the method executes
		sensor.startFailureAndRepairProcess(MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
		boolean tested = false;
		while (!tested) {
			try {
				Thread.sleep(1000);
				if (!sensor.isOperational) tested = true;
			} catch (InterruptedException e) {
				System.out.println();
			}
		}
		assertFalse(sensor.isOperational);
		sensor.stopFailureAndRepairProcess();
		assertTrue(sensor.isOperational);
		// check that the thread is terminated
		assertTrue(((UnreliableSensor)sensor).repairProcess == null);
	}
}
