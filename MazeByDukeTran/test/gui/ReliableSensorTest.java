package gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import generation.CardinalDirection;
import generation.Maze;
import generation.MazeFactory;
import generation.StubOrder;
import gui.Robot.Direction;

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
		sensor = new ReliableSensor();
		// create a maze and assign it to the sensor
		Controller controller = new Controller();
		controller.setDeterministic(true);
		controller.turnOffGraphics();
		controller.switchFromTitleToGenerating(0);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		maze = controller.getMazeConfiguration();
		sensor.setMaze(maze);
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
		// make sure the parameterized constructor instantiates a non-null sensor
		ReliableSensor testSensor = new ReliableSensor(maze, Direction.LEFT);
		assertNotNull(testSensor);
		// make sure that the reliable sensor is not null
		assertNotNull(sensor);
		// make sure that it has a maze that is not null
		assertNotNull(maze);
		assertNotNull(sensor.maze);
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
		// test at the position (1, 0} since there are distances of 0, 1, and 2
		int[] currPos = {1, 0};
		final float INITIAL_BATTERY = 3500;
		float[] powersupply = new float[1];
		powersupply[0] = INITIAL_BATTERY;
		
		// check that an exception is thrown if the current position is null
		assertThrows(IllegalArgumentException.class, () -> sensor.distanceToObstacle(null, CardinalDirection.North, powersupply));
		// check that an exception is thrown if the current direction is null
		assertThrows(IllegalArgumentException.class, () -> sensor.distanceToObstacle(currPos, null, powersupply));
		// check that an exception is thrown if the powersupply is null
		assertThrows(IllegalArgumentException.class, () -> sensor.distanceToObstacle(currPos, CardinalDirection.North, null));
		// check that an exception is thrown if all of the parameters are null
		assertThrows(IllegalArgumentException.class, () -> sensor.distanceToObstacle(null, null, null));
		// check that an exception is thrown if the current position is outside of the maze
		int[] invalidPos = new int[2];
		invalidPos[0] = -1; invalidPos[1] = 0;
		assertThrows(IllegalArgumentException.class, () -> sensor.distanceToObstacle(invalidPos, CardinalDirection.North, powersupply));
		invalidPos[0] = 0; invalidPos[1] = -1;
		assertThrows(IllegalArgumentException.class, () -> sensor.distanceToObstacle(invalidPos, CardinalDirection.North, powersupply));
		invalidPos[0] = maze.getWidth(); invalidPos[1] = 0;
		assertThrows(IllegalArgumentException.class, () -> sensor.distanceToObstacle(invalidPos, CardinalDirection.North, powersupply));
		invalidPos[0] = 0; invalidPos[1] = maze.getHeight();
		assertThrows(IllegalArgumentException.class, () -> sensor.distanceToObstacle(invalidPos, CardinalDirection.North, powersupply));
		invalidPos[0] = -1; invalidPos[1] = -1;
		assertThrows(IllegalArgumentException.class, () -> sensor.distanceToObstacle(invalidPos, CardinalDirection.North, powersupply));
		invalidPos[0] = maze.getWidth(); invalidPos[1] = maze.getHeight();
		assertThrows(IllegalArgumentException.class, () -> sensor.distanceToObstacle(invalidPos, CardinalDirection.North, powersupply));
		
		// check that an exception is thrown if the powersupply is less than 0
		powersupply[0] = -1;
		assertThrows(IndexOutOfBoundsException.class, () -> sensor.distanceToObstacle(currPos, CardinalDirection.North, powersupply));
		powersupply[0] = INITIAL_BATTERY;
		
		// check that an exception is thrown if the sensor is not operational
		sensor.isOperational = false;
		Throwable exception = assertThrows(Exception.class, () -> sensor.distanceToObstacle(currPos, CardinalDirection.North, powersupply));
		assertEquals("SensorFailure", exception.getMessage());
		sensor.isOperational = true;
		// check that an exception is thrown if the powersupply is insufficient
		powersupply[0] = sensor.getEnergyConsumptionForSensing()-1;
		exception = assertThrows(Exception.class, () -> sensor.distanceToObstacle(currPos, CardinalDirection.North, powersupply));
		assertEquals("PowerFailure", exception.getMessage());
		powersupply[0] = INITIAL_BATTERY;

		// for each of the four Cardinal Directions (of which the robot could be facing)
		// check the distances with each relative direction
		try {
			sensor.setSensorDirection(Direction.FORWARD);
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.North, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing(), powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.East, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*2, powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			assertEquals(2, sensor.distanceToObstacle(currPos, CardinalDirection.South, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*3, powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			assertEquals(1, sensor.distanceToObstacle(currPos, CardinalDirection.West, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*4, powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			powersupply[0] = INITIAL_BATTERY;
			
			sensor.setSensorDirection(Direction.LEFT);
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.North, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing(), powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			assertEquals(2, sensor.distanceToObstacle(currPos, CardinalDirection.East, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*2, powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			assertEquals(1, sensor.distanceToObstacle(currPos, CardinalDirection.South, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*3, powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.West, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*4, powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			powersupply[0] = INITIAL_BATTERY;
			
			sensor.setSensorDirection(Direction.BACKWARD);
			assertEquals(2, sensor.distanceToObstacle(currPos, CardinalDirection.North, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing(), powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			assertEquals(1, sensor.distanceToObstacle(currPos, CardinalDirection.East, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*2, powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.South, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*3, powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.West, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*4, powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			powersupply[0] = INITIAL_BATTERY;

			sensor.setSensorDirection(Direction.RIGHT);
			assertEquals(1, sensor.distanceToObstacle(currPos, CardinalDirection.North, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing(), powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.East, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*2, powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.South, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*3, powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			assertEquals(2, sensor.distanceToObstacle(currPos, CardinalDirection.West, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*4, powersupply[0], 0);
			currPos[0] = 1; currPos[1] = 0;
			powersupply[0] = INITIAL_BATTERY;

			// test again for the position at the exit of the maze
			currPos[0] = 2; currPos[1] = 0;
			sensor.setSensorDirection(Direction.FORWARD);
			assertEquals(Integer.MAX_VALUE, sensor.distanceToObstacle(currPos, CardinalDirection.North, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing(), powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			assertEquals(1, sensor.distanceToObstacle(currPos, CardinalDirection.East, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*2, powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.South, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*3, powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.West, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*4, powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			powersupply[0] = INITIAL_BATTERY;
			
			sensor.setSensorDirection(Direction.LEFT);
			assertEquals(1, sensor.distanceToObstacle(currPos, CardinalDirection.North, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing(), powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.East, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*2, powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.South, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*3, powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			assertEquals(Integer.MAX_VALUE, sensor.distanceToObstacle(currPos, CardinalDirection.West, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*4, powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			powersupply[0] = INITIAL_BATTERY;
			
			sensor.setSensorDirection(Direction.BACKWARD);
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.North, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing(), powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.East, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*2, powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			assertEquals(Integer.MAX_VALUE, sensor.distanceToObstacle(currPos, CardinalDirection.South, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*3, powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			assertEquals(1, sensor.distanceToObstacle(currPos, CardinalDirection.West, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*4, powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			powersupply[0] = INITIAL_BATTERY;

			sensor.setSensorDirection(Direction.RIGHT);
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.North, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing(), powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			assertEquals(Integer.MAX_VALUE, sensor.distanceToObstacle(currPos, CardinalDirection.East, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*2, powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			assertEquals(1, sensor.distanceToObstacle(currPos, CardinalDirection.South, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*3, powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			assertEquals(0, sensor.distanceToObstacle(currPos, CardinalDirection.West, powersupply));
			assertEquals(INITIAL_BATTERY-sensor.getEnergyConsumptionForSensing()*4, powersupply[0], 0);
			currPos[0] = 2; currPos[1] = 0;
			powersupply[0] = INITIAL_BATTERY;
		} catch (Exception e) {
			System.out.println("Something went wrong! " + e.getMessage());
			return;
		}		
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
		assertThrows(IllegalArgumentException.class, () -> sensor.setMaze(null));
		
		// call the method with a maze and then check that the sensor's maze field matches
		// the inputed maze
		StubOrder order = new StubOrder();
		MazeFactory factory = new MazeFactory(); 
		factory.order(order);
		factory.waitTillDelivered();
		maze = order.getMaze();
		sensor.setMaze(maze);
		assertEquals(maze, sensor.maze);
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
		assertThrows(IllegalArgumentException.class, () -> sensor.setSensorDirection(null));
		
		// call the method with a direction and then check that the sensor's direction field matches
		// the inputed direction
		sensor.setSensorDirection(Direction.FORWARD);
		assertEquals(Direction.FORWARD, sensor.mountedDirection);
		sensor.setSensorDirection(Direction.LEFT);
		assertEquals(Direction.LEFT, sensor.mountedDirection);
		sensor.setSensorDirection(Direction.RIGHT);
		assertEquals(Direction.RIGHT, sensor.mountedDirection);
		sensor.setSensorDirection(Direction.BACKWARD);
		assertEquals(Direction.BACKWARD, sensor.mountedDirection);
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
		assertEquals(SENSE_COST, sensor.getEnergyConsumptionForSensing(), 0);
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
		assertEquals(CardinalDirection.North,
			sensor.convertToAbsoluteDirection(Direction.FORWARD, CardinalDirection.North));
		assertEquals(CardinalDirection.West,
				sensor.convertToAbsoluteDirection(Direction.RIGHT, CardinalDirection.North));
		assertEquals(CardinalDirection.South,
				sensor.convertToAbsoluteDirection(Direction.BACKWARD, CardinalDirection.North));
		assertEquals(CardinalDirection.East,
				sensor.convertToAbsoluteDirection(Direction.LEFT, CardinalDirection.North));
		
		assertEquals(CardinalDirection.East,
				sensor.convertToAbsoluteDirection(Direction.FORWARD, CardinalDirection.East));
		assertEquals(CardinalDirection.North,
				sensor.convertToAbsoluteDirection(Direction.RIGHT, CardinalDirection.East));
		assertEquals(CardinalDirection.West,
				sensor.convertToAbsoluteDirection(Direction.BACKWARD, CardinalDirection.East));
		assertEquals(CardinalDirection.South,
				sensor.convertToAbsoluteDirection(Direction.LEFT, CardinalDirection.East));
		
		assertEquals(CardinalDirection.South,
				sensor.convertToAbsoluteDirection(Direction.FORWARD, CardinalDirection.South));
		assertEquals(CardinalDirection.East,
				sensor.convertToAbsoluteDirection(Direction.RIGHT, CardinalDirection.South));
		assertEquals(CardinalDirection.North,
				sensor.convertToAbsoluteDirection(Direction.BACKWARD, CardinalDirection.South));
		assertEquals(CardinalDirection.West,
				sensor.convertToAbsoluteDirection(Direction.LEFT, CardinalDirection.South));
		
		assertEquals(CardinalDirection.West,
				sensor.convertToAbsoluteDirection(Direction.FORWARD, CardinalDirection.West));
		assertEquals(CardinalDirection.South,
				sensor.convertToAbsoluteDirection(Direction.RIGHT, CardinalDirection.West));
		assertEquals(CardinalDirection.East,
				sensor.convertToAbsoluteDirection(Direction.BACKWARD, CardinalDirection.West));
		assertEquals(CardinalDirection.North,
				sensor.convertToAbsoluteDirection(Direction.LEFT, CardinalDirection.West));
	}
}
