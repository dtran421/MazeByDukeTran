package gui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class UnreliableSensorTest extends SensorTest {
	private UnreliableSensor sensor;
	private final int MEAN_TIME_BETWEEN_FAILURES = 400; // milliseconds
	private final int MEAN_TIME_TO_REPAIR = 200;
	
	/**
	 * Create a new unreliable sensor for each test
	 */
	@Before
	public final void setUp() {
		// create an unreliable sensor	
		sensor = new UnreliableSensor();
	}
	
	/**
	 * Test case: See if the setUp properly sets up the unreliable sensor
	 * <p>
	 * Method under test: own set up
	 * <p>
	 * Correct behavior:
	 * the unreliable sensor is not null
	 */
	@Test
	public final void testUnreliableSensor() {
		assertNotNull(sensor);
		assertTrue(sensor.isOperational);
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
		assertNotNull(sensor.repairProcess);
		
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
		sensor.stopFailureAndRepairProcess();
		assertTrue(sensor.isOperational);
		// check that the thread is terminated
		assertTrue(sensor.repairProcess == null);
	}
}
