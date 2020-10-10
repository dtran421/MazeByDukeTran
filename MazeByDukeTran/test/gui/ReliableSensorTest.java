package gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import gui.Robot.Direction;

public class ReliableSensorTest extends SensorTest {
	/**
	 * Set up the maze and create a new reliable sensor to simulate a robot
	 * inside the maze for each test 
	 */
	@Before
	public final void setUp() {
		// create a reliable sensor
		sensor = new ReliableSensor();
		// assign the maze to the sensor
		sensor.setMaze(maze);
	}
	
	/**
	 * Test case: Correctness of the parameterized constructor 
	 * <p>
	 * Method under test: ReliableSensor(Direction direction)
	 * <p>
	 * Correct behavior:
	 * the reliable sensor is instantiated with the correct direction
	 */
	@Test
	public final void testReliableSensor() {
		// make sure the parameterized constructor instantiates a non-null sensor
		ReliableSensor testSensor = new ReliableSensor(Direction.LEFT);
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
	 * currently, it only throws an exception
	 */
	@Test
	public final void testStartFailureAndRepairProcess() {		
		// check that an exception is thrown 
		assertThrows(UnsupportedOperationException.class, () -> sensor.startFailureAndRepairProcess(0, 0));
	}
	
	/**
	 * Test case: Correctness of the stopFailureAndRepairProcess method
	 * <p>
	 * Method under test: stopFailureAndRepairProcess() 
	 * <p>
	 * Correct behavior:
	 * currently, it only throws an exception
	 */
	@Test
	public final void testStopFailureAndRepairProcess() {		
		// check that an exception is thrown 
		assertThrows(UnsupportedOperationException.class, () -> sensor.stopFailureAndRepairProcess());
	}
}
