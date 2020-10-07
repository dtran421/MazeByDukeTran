package gui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class UnreliableSensorTest {
	private UnreliableSensor sensor;
	
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
	public final void testReliableSensor() {
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
		final int meanTimeBetweenFailures = 400; // milliseconds
		final int meanTimeToRepair = 200;
		sensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
		
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
		// check that the sensor is operational after the method executes
		// check that the thread is terminated
	}
}
