package gui;

import org.junit.Before;
import org.junit.Test;

public class ReliableRobotTest extends ReliableRobot {
	private Robot robot;
	
	/**
	 * Set up the maze and place the robot inside the maze for each test 
	 */
	@Before
	public final void setUp() {
		// instantiate the controller
		
		// generate a maze using a MazeBuilder and StubOrder
		
		// instantiate the robot
		
		// set up the robot by assigning the controller to the it
				
		// start the game but with no panel (to skip drawing the graphics)
	}
	
	/**
	 * Test case: See if the setUp properly sets up the robot and wizard
	 * <p>
	 * Method under test: own set up
	 * <p>
	 * Correct behavior:
	 * the controller and robot are not null and the robot has access to the controller
	 */
	@Test
	public final void testReliableRobot() {
		// check if robot is not null
		
		// check if robot can access controller (which isn't null)
	}
	
	/**
	 * Test case: Correctness of the setController method
	 * <p>
	 * Method under test: setController(Controller controller)
	 * <p>
	 * Correct behavior:
	 * the controller should be properly assigned to the controller field and
	 * the distance sensors should be instantiated
	 */
	@Test
	public final void testSetController() {
		// check that an exception is thrown when the controller parameter is null
		
		// check that an exception is thrown when the controller's state isn't StatePlaying
		
		// check that an exception is thrown when the controller's maze is null
		
		// when the controller is valid, it should be appropriately assigned to the controller field
		
		// check that all 4 sensors are instantiated
	}
	
	/**
	 * Test case: Correctness of the getCurrentPosition method
	 * <p>
	 * Method under test: getCurrentPosition()
	 * <p>
	 * Correct behavior:
	 * returns the robot's current position in the maze
	 */
	@Test
	public final void testGetCurrentPosition() {
		// check that the position is fetched (is not null)
		// check that it returns an array of size 2
		
		// check that the position matches the maze's starting position
		// (since the robot hasn't moved anywhere)
		
		// check that an exception is thrown when the position is outside of the maze
	}
	
	/**
	 * Test case: Correctness of the getCurrentDirection method
	 * <p>
	 * Method under test: getCurrentDirection()
	 * <p>
	 * Correct behavior:
	 * returns the robot's current direction in the maze
	 */
	@Test
	public final void testGetCurrentDirection() {
		// check that the direction is fetched (is not null)
		// check that it is a CardinalDirection
		
		// check that the direction is East (since you always start out facing
		// east)
	}
	
	/**
	 * Test case: Correctness of the getBatteryLevel method
	 * <p>
	 * Method under test: getBatteryLevel()
	 * <p>
	 * Correct behavior:
	 * returns the robot's current battery level
	 */
	@Test
	public final void testGetBatteryLevel() {
		// check that the battery level is fetched (is not null)
		// check that it is a float
		
		// check that the level matches the initial battery level
	}
	
	/**
	 * Test case: Correctness of the setBatteryLevel method
	 * <p>
	 * Method under test: setBatteryLevel(float level)
	 * <p>
	 * Correct behavior:
	 * assigns the inputed level to the battery level
	 */
	@Test
	public final void testSetBatteryLevel() {
		// check that an exception is thrown if the inputed level is negative
		
		// check that the battery level is changed
		
		// check that the battery level reflects the inputed level
	}
	
	/**
	 * Test case: Correctness of the getEnergyForFullRotation method
	 * <p>
	 * Method under test: getEnergyForFullRotation()
	 * <p>
	 * Correct behavior:
	 * returns the required energy for a 360 rotation
	 */
	@Test
	public final void testGetEnergyForFullRotation() {
		// check that a value is returned 
		// check that its type is a float
		
		// check that the value matches the rotation cost times 4
		// since the rotation cost is for 90 degree turns
	}
	
	/**
	 * Test case: Correctness of the getEnergyForStepForward method
	 * <p>
	 * Method under test: getEnergyForStepForward()
	 * <p>
	 * Correct behavior:
	 * returns the required energy for moving forward 1 step
	 */
	@Test
	public final void testGetEnergyForStepForward() {
		// check that a value is returned 
		// check that its type is a float
		
		// check that the value matches the movement cost
	}
	
	/**
	 * Test case: Correctness of the getOdometerReading method
	 * <p>
	 * Method under test: getOdometerReading()
	 * <p>
	 * Correct behavior:
	 * returns the distance traveled so far
	 */
	@Test
	public final void testGetOdometerReading() {
		// check that a value is returned 
		// check that its type is an int
		
		// check that the value is initially 0 (since the robot hasn't moved yet)
	}
	
	/**
	 * Test case: Correctness of the resetOdometer method
	 * <p>
	 * Method under test: resetOdometer()
	 * <p>
	 * Correct behavior:
	 * resets the odometer's reading to 0
	 */
	@Test
	public final void testResetOdometer() {
		// check that the odometer's value is equal to 0
	}
	
	/**
	 * Test case: Correctness of the rotate method
	 * <p>
	 * Method under test: rotate(Turn turn)
	 * <p>
	 * Correct behavior:
	 * the robot is rotated according to the turn parameter
	 */
	@Test
	public final void testRotate() {
		// check that the robot is stopped if it doesn't initially have sufficient energy
		
		// for all 3 turns (left, right, around)
			// obtain the current direction
		
			// execute the rotation
		
			// check if the new direction is correct according to the old direction
			// (i.e. if the robot is facing east and turning left, it should end up facing north)
			// check that the odometer is not affected by this operation
		
		// set the battery level to the rotation cost and execute a rotation
		
		// make sure that it is stopped
		// check that the odometer is not affected by this operation
		
		// set the battery level to higher than the rotation cost but lower than the cost times two
		// and execute a 180 rotation
		
		// make sure that it is stopped		
		// check that the odometer is not affected by this operation

	}
	
	/**
	 * Test case: Correctness of the move method
	 * <p>
	 * Method under test: move(int distance)
	 * <p>
	 * Correct behavior:
	 * moves the robot the inputed distance in its current direction
	 */
	@Test
	public final void testMove() {
		// check that an exception is thrown for an invalid distance (< 0)
		
		// check that the robot is stopped if it doesn't initially have sufficient energy
		
		// find a direction that the robot can move in (doesn't have a wall immediately in front
		// of it)
		
		// try to move the robot 0 steps
		
		// check that its current position matches its previous position
		// check that the odometer reads the same
		// check that its battery level didn't change
		// check that it isn't stopped
		
		// try to move the robot 1 step
		
		// check that its current position is one ahead of the previous position
		// check that the odometer reads 1
		// check that its battery level is updated to subtract the movement cost
		// check that it isn't stopped
		
		// try to move the robot all the way to a wall
		
		// check that its current position matches the position right before the wall
		// check that the odometer is updated according to the distance moved
		// check that its battery level corresponds to the movement cost multiplied by
		// the distance
		// check that it isn't stopped
		
		// try to move the robot past a wall
		
		// check that its current position remains at the position before the wall
		// check that the odometer shows the distance right up to the wall
		// check that the battery level is 0
		// check that it is stopped
		
		// set the battery level to a level that is less than the energy required to move
		// a certain distance
		
		// check that its current position is where it ran out of battery
		// check that the odometer shows the distance at which it stopped
		// check that the battery level is 0
		// check that it is stopped
	}
	
	/**
	 * Test case: Correctness of the jump method
	 * <p>
	 * Method under test: jump()
	 * <p>
	 * Correct behavior:
	 * the robot jumps a step forward (and goes over a wall if there is one)
	 */
	@Test
	public final void testJump() {
		// check that the robot is stopped if it doesn't initially have sufficient energy
		
		// rotate the robot so that it tries to jump over an external wall
		
		// check that it remains in its current position
		// check that its odometer doesn't change
		// check that its battery level is 0
		// check that it is stopped
		
		// execute a regular jump move (no wall)
		
		// check that its current position is one cell ahead of the original
		// check that its odometer is the old value + 1
		// check that the battery level is updated with the old value + the jump cost
		// check that it isn't stopped
		
		// execute a jump over a wall
		
		// test for all the same conditions as above
		
		// set the battery to exactly the jump cost
		
		// execute a jump and check that it is stopped
	}
	
	/**
	 * Test case: Correctness of the isAtExit method
	 * <p>
	 * Method under test: isAtExit()
	 * <p>
	 * Correct behavior:
	 * returns whether the robot is at the cell of the exit
	 */
	@Test
	public final void testIsAtExit() {
		// check if it initially returns false (since we're at the start)
		
		// since I'm generating the maze in a deterministic manner, I will move the
		// robot in a hard-coded manner to the exit
		
		// check if the cell is the exit
	}
	
	/**
	 * Test case: Correctness of the isInsideRoom method
	 * <p>
	 * Method under test: isInsideRoom()
	 * <p>
	 * Correct behavior:
	 * returns whether the robot is inside a room
	 */
	@Test
	public final void testIsInsideRoom() {
		// check that the initial cell is not inside a room (since the starting position
		// is always along the border of the maze and rooms can't generate along the border)
		
		// regenerate the maze with rooms this time
		
		// move the robot in a hard-coded manner inside a room
		
		// check if the cell is inside a room
	}
	
	/**
	 * Test case: Correctness of the hasStopped method
	 * <p>
	 * Method under test: hasStopped()
	 * <p>
	 * Correct behavior:
	 * returns whether the robot has stopped
	 */
	@Test
	public final void testHasStopped() {
		// at the start, the robot should not be stopped
		
		// make an illegal move (like move into a wall)
		
		// check that the method returns true and the robot is stopped
	}
	
	/**
	 * Test case: Correctness of the distanceToObstacle method
	 * <p>
	 * Method under test: distanceToObstacle(Direction direction)
	 * <p>
	 * Correct behavior:
	 * returns the distance to the closest obstacle in the given direction
	 */
	@Test
	public final void testDistanceToObstacle() {
		// check that the robot is stopped if it doesn't initially have sufficient energy
		
		// for each of the four directions
			// try to get the distance in the direction
		
			// check that it corresponds with the maze
			// check that the battery level is updated to subtract the sensing cost
		
			// try to get the distance with insufficient battery 
		
			// confirm that it throws an exception
			
	}
	
	/**
	 * Test case: Correctness of the canSeeThroughTheExitIntoEternity() method
	 * <p>
	 * Method under test: canSeeThroughTheExitIntoEternity(Direction direction)
	 * <p>
	 * Correct behavior:
	 * returns whether the robot can see the exit from the current cell and direction
	 */
	@Test
	public final void testCanSeeThroughTheExitIntoEternity() {
		// check that the robot is stopped if it doesn't initially have sufficient energy
		
		// for each of the four directions
			// call the method for the given direction to see if it can see the exit
		
			// check that it corresponds with the maze (should be false at the starting position)
			// check that the battery level is updated to subtract the sensing cost
		
			// try to get the distance with insufficient battery 
		
			// confirm that it throws an exception
		
		// move the robot to the exit position
		
		// for each of the four directions, test the same tests as above
			
	}
	
	/**
	 * Test case: Correctness of the startFailureAndRepairProcess() method
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
	}
	
	/**
	 * Test case: Correctness of the stopFailureAndRepairProcess() method
	 * <p>
	 * Method under test: stopFailureAndRepairProcess(Direction direction)
	 * <p>
	 * Correct behavior:
	 * currently, since it is unimplemented, it will just throw an exception
	 */
	@Test
	public final void testStopFailureAndRepairProcess() {
		// check that an exception is thrown for all 4 directions
	}
}
