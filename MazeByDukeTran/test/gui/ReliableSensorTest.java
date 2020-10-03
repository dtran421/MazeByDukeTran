package gui;

import org.junit.Before;
import org.junit.Test;

import generation.Maze;

public class ReliableSensorTest extends ReliableSensor {
	private ReliableSensor sensor;
	private Maze maze;
	
	/**
	 * Set up the maze and create a new reliable sensor to simulate a robot
	 * inside the maze for each test 
	 */
	@Before
	public final void setUp() {
		// create a reliable sensor
		
		// create a maze and assign it to the sensor
	}
	
	/**
	 * Test case: See if the setUp properly sets up the reliable sensor
	 * <p>
	 * Method under test: own set up
	 * <p>
	 * Correct behavior:
	 * the reliable sensor and maze is not null
	 */
	@Test
	public final void testReliableSensor() {
		// make sure that the reliable sensor is not null
		
		// make sure that it has a maze that is not null
	}
	
	/**
	 * Test case: Correctness of the distanceToObstacle method
	 * <p>
	 * Method under test: distanceToObstacle(int[] currentPosition, 
	 * CardinalDirection currentDirection, float[] powersupply) 
	 * <p>
	 * Correct behavior:
	 * returns the distance to the closest obstacle (wall) in the sensor's 
	 * designated direction
	 */
	@Test
	public final void testDistanceToObstacle() {
		// for each of the four Cardinal Directions (of which the robot could be facing)
		// check the distances with each relative direction
		
		// jump one block and repeat the above 
	}
	
	/**
	 * Test case: Correctness of the setMaze method
	 * <p>
	 * Method under test: setMaze(Maze maze) 
	 * <p>
	 * Correct behavior:
	 * assigns the inputed maze to the sensor's maze field
	 */
	@Test
	public final void testSetMaze() {
		// check that an exception is thrown if the inputed maze is null
		
		// check that an exception is thrown if the inputed maze's floorplan is null
		
		// call the method with a maze and then check that the sensor's maze field matches
		// the inputed maze
	}
	
	/**
	 * Test case: Correctness of the setSensorDirection method
	 * <p>
	 * Method under test: setSensorDirection(Direction mountedDirection) 
	 * <p>
	 * Correct behavior:
	 * assigns the inputed direction to the sensor's direction field
	 */
	@Test
	public final void testSetSensorDirection() {
		// check that an exception is thrown if the inputed direction is null
		
		// call the method with a direction and then check that the sensor's direction field matches
		// the inputed direction
	}
	
	/**
	 * Test case: Correctness of the getEnergyConsumptionForSensing method
	 * <p>
	 * Method under test: getEnergyConsumptionForSensing() 
	 * <p>
	 * Correct behavior:
	 * returns the sensing cost
	 */
	@Test
	public final void testGetEnergyConsumptionForSensing() {		
		// call the method and check that the returned value matches the sensing cost
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
	}
	
	/**
	 * Test case: Correctness of the convertToAbsoluteDirection method
	 * <p>
	 * Method under test: convertToAbsoluteDirection(Direction direction, CardinalDirection currDir) 
	 * <p>
	 * Correct behavior:
	 * returns the absolute direction of the inputed relative direction in relation to the robot's current
	 * cardinal (absolute) direction
	 */
	@Test
	public final void testConvertToAbsoluteDirection() {
		// hard-code all possible direction conversions (i.e. Left, North -> West)
	}
}
