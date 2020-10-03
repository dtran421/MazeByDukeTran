package gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import generation.CardinalDirection;
import generation.Maze;
import generation.MazeFactory;
import generation.StubOrder;

public class ReliableRobotTest extends ReliableRobot {
	private ReliableRobot robot;
	private Maze maze;
	
	/**
	 * Set up the maze and place the robot inside the maze for each test 
	 */
	@Before
	public final void setUp() {
		// instantiate the controller
		Controller controller = new Controller();
		// generate a maze using a MazeFactory and StubOrder
		StubOrder order = new StubOrder();
		MazeFactory factory = new MazeFactory(); 
		factory.order(order);
		factory.waitTillDelivered();
		maze = order.getMaze();

		controller.currentState = controller.states[2];
		controller.currentState.setMazeConfiguration(maze);
		
		// instantiate the robot
		robot = new ReliableRobot();
		// set up the robot by assigning the controller to the it
		robot.setController(controller);
			
		// start the game but with no panel (to skip drawing the graphics)
		controller.currentState.start(controller, null);
	}
	
	/**
	 * Test case: See if the setUp properly sets up the robot
	 * <p>
	 * Method under test: own set up
	 * <p>
	 * Correct behavior:
	 * the robot and controller is not null
	 */
	@Test
	public final void testReliableRobot() {
		// check if robot is not null
		assertNotNull(robot);
		// check if controller is not null
		assertNotNull(robot.controller);
		// check that the fields of the robot are instantiated 
		assertEquals(3500, robot.getBatteryLevel(), 0);
		assertEquals(0, robot.getOdometerReading());
		assertFalse(robot.hasStopped());
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
		assertThrows(IllegalArgumentException.class, () -> {robot.setController(null);});
		// check that an exception is thrown when the controller's state isn't StatePlaying
		Controller controller = new Controller();
		assertThrows(IllegalArgumentException.class, () -> {robot.setController(controller);});
		// check that an exception is thrown when the controller's maze is null
		controller.currentState = controller.states[2];
		assertThrows(IllegalArgumentException.class, () -> {robot.setController(controller);});
		
		// when the controller is valid, it should be appropriately assigned to the controller field
		controller.currentState.setMazeConfiguration(maze);
		robot.setController(controller);
		assertNotNull(controller);
		
		// check that all 4 sensors are instantiated
		assertNotNull(robot.leftSensor);
		assertNotNull(robot.rightSensor);
		assertNotNull(robot.forwardSensor);
		assertNotNull(robot.backwardSensor);
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
		try {
			// check that the position is fetched (is not null)
			// check that it returns an array of size 2
			int[] currPos = robot.getCurrentPosition();
			assertEquals(2, currPos.length);
			
			// check that the position matches the maze's starting position
			// (since the robot hasn't moved anywhere)
			int[] startPos = maze.getStartingPosition();
			for (int i = 0; i < 2; i++)
				assertEquals(startPos[i], currPos[i]);
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		
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
		assertNotNull(robot.getCurrentDirection());
		// check that the direction is East (since you always start out facing east)
		assertEquals(CardinalDirection.East, robot.getCurrentDirection());
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
		assertNotNull(robot.getBatteryLevel());
		// check that the level matches the initial battery level
		assertEquals(robot.INITIAL_BATTERY, robot.getBatteryLevel(), 0);
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
		assertThrows(IllegalArgumentException.class, () -> {robot.setBatteryLevel(-1);});
		// check that the battery level is changed
		float origLevel = robot.getBatteryLevel();
		robot.setBatteryLevel(10);
		assertFalse(origLevel == robot.getBatteryLevel());
		// check that the battery level reflects the inputed level
		assertEquals(10, robot.getBatteryLevel(), 0);
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
		// check that a value is returned (is not null)
		assertNotNull(robot.getEnergyForFullRotation());
		// check that the value matches the rotation cost times 4
		// since the rotation cost is for 90 degree turns
		assertEquals(robot.ROTATE_COST*4, robot.getEnergyForFullRotation(), 0);
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
		assertNotNull(robot.getEnergyForStepForward());
		// check that the value matches the movement cost
		assertEquals(robot.MOVE_COST, robot.getEnergyForStepForward(), 0);
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
		assertNotNull(robot.getOdometerReading());
		// check that the value is initially 0 (since the robot hasn't moved yet)
		assertEquals(0, robot.getOdometerReading());
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
		robot.resetOdometer();
		assertEquals(0, robot.getOdometerReading());
		
		robot.distTraveled++;
		robot.resetOdometer();
		assertEquals(0, robot.getOdometerReading());
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
		robot.setBatteryLevel(robot.ROTATE_COST-1);
		robot.rotate(Turn.LEFT);
		assertTrue(robot.hasStopped());
		assertEquals(0, robot.getBatteryLevel(), 0);
		assertEquals(CardinalDirection.East, robot.getCurrentDirection());
		
		robot.setBatteryLevel(robot.INITIAL_BATTERY);
		Turn[] turns = Turn.values();
		CardinalDirection[] correctDirections = {CardinalDirection.South, CardinalDirection.East, CardinalDirection.West};
		// for all 3 turns (left, right, around)
		for (int i = 0; i < turns.length; i++) {
			// obtain the current direction
			CardinalDirection origDir = robot.getCurrentDirection();
			int origDistance = robot.getOdometerReading();
			float origBattery = robot.getBatteryLevel();
			// execute the rotation
			robot.rotate(turns[i]);
			
			// check if the new direction is correct according to the old direction
			// (i.e. if the robot is facing east and turning left, it should end up facing north)
			assertFalse(origDir == robot.getCurrentDirection());
			assertEquals(correctDirections[i], robot.getCurrentDirection());
			// check that the odometer is not affected by this operation
			assertEquals(origDistance, robot.getOdometerReading());
			// check that the battery level is updated by the rotation cost
			assertEquals(turns[i] != Turn.AROUND ? origBattery-robot.ROTATE_COST : origBattery-robot.ROTATE_COST*2, 
				robot.getBatteryLevel(), 0);
		}
			
		// set the battery level to the rotation cost and execute a rotation
		robot.setBatteryLevel(robot.ROTATE_COST-1);
		int origDistance = robot.getOdometerReading();
		robot.rotate(Turn.LEFT);
		// make sure that it is stopped
		assertTrue(robot.hasStopped());
		assertEquals(0, robot.getBatteryLevel(), 0);
		// check that the odometer is not affected by this operation
		assertEquals(origDistance, robot.getOdometerReading());
		
		// set the battery level to higher than the rotation cost but lower than the cost times two
		// and execute a 180 rotation
		robot.setBatteryLevel(robot.ROTATE_COST*2-1);
		origDistance = robot.getOdometerReading();
		robot.rotate(Turn.AROUND);
		// make sure that it is stopped
		assertTrue(robot.hasStopped());
		assertEquals(0, robot.getBatteryLevel(), 0);
		// check that the odometer is not affected by this operation
		assertEquals(origDistance, robot.getOdometerReading());
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
		assertThrows(IllegalArgumentException.class, () -> {robot.move(-1);});
		// check that the robot is stopped if it doesn't initially have sufficient energy
		robot.setBatteryLevel(robot.MOVE_COST*10-1);
		robot.move(10);
		assertTrue(robot.hasStopped());
		assertEquals(0, robot.getBatteryLevel(), 0);
		
		robot.stopped = false;
		robot.setBatteryLevel(robot.INITIAL_BATTERY);
		// find a direction that the robot can move in (doesn't have a wall immediately in front
		// of it)
		robot.rotate(Turn.RIGHT);
		int[] origPos;
		try {
			origPos = robot.getCurrentPosition();
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		int origDistance = robot.getOdometerReading();
		float origBattery = robot.getBatteryLevel();
		
		// try to move the robot 0 steps
		int travelDistance = 0;
		robot.move(travelDistance);
		// check that its current position matches its previous position
		try {
			int[] currPos = robot.getCurrentPosition();
			assertEquals(origPos[0], currPos[0]);
			assertEquals(origPos[1], currPos[1]);
			
			// check that the odometer reads the same
			assertEquals(origDistance, robot.getOdometerReading());
			// check that its battery level didn't change
			assertEquals(origBattery, robot.getBatteryLevel(), 0);
			// check that it isn't stopped
			assertFalse(robot.hasStopped());
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		
		// try to move the robot 1 step
		travelDistance = 1;
		robot.move(travelDistance);		
		
		try {
			int[] currPos = robot.getCurrentPosition();
			// check that its current position is one ahead of the previous position
			assertEquals(origPos[0], currPos[0]);
			assertEquals(origPos[1]-travelDistance, currPos[1]);
			// check that the odometer reads 1
			assertEquals(travelDistance, robot.getOdometerReading());
			// check that its battery level is updated to subtract the movement cost
			assertEquals(origBattery-MOVE_COST, robot.getBatteryLevel(), 0);
			// check that it isn't stopped
			assertFalse(robot.hasStopped());
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		
		robot.rotate(Turn.LEFT);
		robot.move(1);
		robot.rotate(Turn.LEFT);
		try {
			origPos = robot.getCurrentPosition();
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		origDistance = robot.getOdometerReading();
		origBattery = robot.getBatteryLevel();
		// try to move the robot all the way to a wall
		travelDistance = 2;
		robot.move(travelDistance);
		
		try {
			int[] currPos = robot.getCurrentPosition();
			// check that its current position matches the position right before the wall
			assertEquals(origPos[0], currPos[0]);
			assertEquals(origPos[1]+travelDistance, currPos[1]);
			// check that the odometer is updated according to the distance moved
			assertEquals(origDistance+travelDistance, robot.getOdometerReading());
			// check that its battery level corresponds to the movement cost multiplied by
			// the distance
			assertEquals(origBattery-travelDistance*MOVE_COST, robot.getBatteryLevel(), 0);
			// check that it isn't stopped
			assertFalse(robot.hasStopped());
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		
		robot.rotate(Turn.LEFT);
		origDistance = robot.getOdometerReading();
		origBattery = robot.getBatteryLevel();
		// try to move the robot past a wall
		robot.move(travelDistance);
		
		try {
			int[] currPos = robot.getCurrentPosition();
			// check that its current position remains at the position before the wall
			assertEquals(0, currPos[0]);
			assertEquals(2, currPos[1]);
			// check that the odometer shows the distance right up to the wall
			assertEquals(origDistance+travelDistance-1, robot.getOdometerReading());
			// check that the battery level is 0
			assertEquals(0, robot.getBatteryLevel(), 0);
			// check that it is stopped
			assertTrue(robot.hasStopped());
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		
		robot.stopped = false;
		robot.setBatteryLevel(robot.INITIAL_BATTERY);
		robot.rotate(Turn.RIGHT);
		robot.move(1);
		robot.rotate(Turn.RIGHT);
		
		try {
			origPos = robot.getCurrentPosition();
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		origDistance = robot.getOdometerReading();
		origBattery = robot.getBatteryLevel();
		// set the battery level to a level that is less than the energy required to move
		// a certain distance
		travelDistance = 3;
		robot.setBatteryLevel(robot.MOVE_COST*travelDistance-1);
		robot.move(travelDistance);
		
		try {
			int[] currPos = robot.getCurrentPosition();
			// check that its current position is where it ran out of battery
			assertEquals(origPos[0]+travelDistance-1, currPos[0]);
			assertEquals(origPos[1], currPos[1]);
			// check that the odometer shows the distance at which it stopped
			assertEquals(origDistance+travelDistance-1, robot.getOdometerReading());
			// check that the battery level is 0
			assertEquals(0, robot.getBatteryLevel(), 0);
			// check that it is stopped
			assertTrue(robot.hasStopped());
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		
		// set the battery level to exactly the move cost for a certain distance
		robot.stopped = false;
		robot.setBatteryLevel(robot.MOVE_COST);
		
		try {
			origPos = robot.getCurrentPosition();
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		origDistance = robot.getOdometerReading();
		travelDistance = 1;
		robot.move(travelDistance);
		try {
			int[] currPos = robot.getCurrentPosition();
			// make sure the robot moved 1 step
			assertEquals(origPos[0]+travelDistance, currPos[0]);
			assertEquals(origPos[1], currPos[1]);
			// check that the odometer shows the distance at which it stopped
			assertEquals(origDistance+travelDistance, robot.getOdometerReading());
			// check that the battery level is 0
			assertEquals(0, robot.getBatteryLevel(), 0);
			// make sure robot stops since it's out of energy
			assertTrue(robot.hasStopped());
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		
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
		robot.setBatteryLevel(robot.JUMP_COST-1);
		robot.jump();
		assertTrue(robot.hasStopped());
		assertEquals(0, robot.getBatteryLevel(), 0);
		
		robot.stopped = false;
		robot.setBatteryLevel(robot.INITIAL_BATTERY);
		int[] origPos;
		try {
			origPos = robot.getCurrentPosition();
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		// rotate the robot so that it tries to jump over an external wall
		robot.rotate(Turn.AROUND);
		robot.jump();
		
		try {
			// check that it remains in its current position
			int[] currPos = robot.getCurrentPosition();
			assertEquals(origPos[0], currPos[0]);
			assertEquals(origPos[1], currPos[1]);
			// check that its odometer doesn't change
			assertEquals(0, robot.getOdometerReading());
			// check that its battery level is 0
			assertEquals(0, robot.getBatteryLevel(), 0);
			// check that it is stopped
			assertTrue(robot.hasStopped());
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		
		robot.stopped = false;
		robot.setBatteryLevel(robot.INITIAL_BATTERY);
		// execute a regular jump move (no wall)
		robot.rotate(Turn.LEFT);
		float origBattery = robot.getBatteryLevel();
		robot.jump();

		try {
			// check that its current position is one cell ahead of the original
			int[] currPos = robot.getCurrentPosition();
			assertEquals(origPos[0], currPos[0]);
			assertEquals(origPos[1]-1, currPos[1]);
			// check that its odometer is 1
			assertEquals(1, robot.getOdometerReading());
			// check that the battery level is updated to subtract the jump cost
			assertEquals(origBattery-robot.JUMP_COST, robot.getBatteryLevel(), 0);
			// check that it isn't stopped
			assertFalse(robot.hasStopped());
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		
		robot.rotate(Turn.LEFT);
		robot.move(1);
		try {
			origPos = robot.getCurrentPosition();
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		int origDistance = robot.getOdometerReading();
		origBattery = robot.getBatteryLevel();

		// execute a jump over a wall
		robot.jump();
		// test for all the same conditions as above
		try {
			int[] currPos = robot.getCurrentPosition();
			assertEquals(origPos[0]+1, currPos[0]);
			assertEquals(origPos[1], currPos[1]);
			assertEquals(origDistance+1, robot.getOdometerReading());
			assertEquals(origBattery-robot.JUMP_COST, robot.getBatteryLevel(), 0);
			assertFalse(robot.hasStopped());
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		
		// set the battery to exactly the jump cost
		robot.setBatteryLevel(robot.JUMP_COST);
		try {
			origPos = robot.getCurrentPosition();
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
		origDistance = robot.getOdometerReading();
		// execute a jump and check that it is stopped
		robot.jump();
		
		try {
			int[] currPos = robot.getCurrentPosition();
			assertEquals(origPos[0]+1, currPos[0]);
			assertEquals(origPos[1], currPos[1]);
			assertEquals(origDistance+1, robot.getOdometerReading());
			assertEquals(0, robot.getBatteryLevel(), 0);
			assertTrue(robot.hasStopped());
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
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
		assertFalse(robot.isAtExit());
		
		// since the maze is being generated in a deterministic manner, move the
		// robot in a hard-coded manner to the exit
		robot.rotate(Turn.RIGHT);
		robot.move(1);
		// make sure random cell doesn't return true
		assertFalse(robot.isAtExit());
		robot.rotate(Turn.LEFT);
		robot.move(1);
		assertFalse(robot.isAtExit());
		robot.jump();
		
		// check if the cell is the exit
		assertTrue(robot.isAtExit());
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
		assertFalse(robot.isInsideRoom());
		
		// regenerate the maze with rooms this time
		Controller controller = new Controller();
		StubOrder order = new StubOrder(1, false);
		MazeFactory factory = new MazeFactory(); 
		factory.order(order);
		factory.waitTillDelivered();
		maze = order.getMaze();

		controller.currentState = controller.states[2];
		controller.currentState.setMazeConfiguration(maze);
		
		robot = new ReliableRobot();
		robot.setController(controller);
		controller.currentState.start(controller, null);
		
		// move the robot in a hard-coded manner inside a room
		robot.rotate(Turn.RIGHT);
		robot.jump();
		assertFalse(robot.isInsideRoom());
		robot.jump();
		
		// check if the cell is inside a room
		assertTrue(robot.isInsideRoom());
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
		assertFalse(robot.hasStopped());
		// make an illegal move (like move into a wall)
		robot.move(1);
		// check that the method returns true and the robot is stopped
		assertTrue(robot.hasStopped());
		
		robot.stopped = false;
		// set the battery level to the exact amount of energy required
		// to execute a rotation and step, and do those actions
		robot.setBatteryLevel(robot.ROTATE_COST+robot.MOVE_COST);
		robot.rotate(Turn.RIGHT);
		robot.move(1);
		
		// check that the robot is stopped due to energy depletion
		assertTrue(robot.hasStopped());
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
		robot.setBatteryLevel(robot.leftSensor.getEnergyConsumptionForSensing()-1);
		robot.distanceToObstacle(Direction.FORWARD);
		assertTrue(robot.hasStopped());
		assertEquals(0, robot.getBatteryLevel(), 0);
		
		robot.setBatteryLevel(robot.INITIAL_BATTERY);
		robot.stopped = false;
		
		robot.jump();
		robot.move(1);
		robot.rotate(Turn.LEFT);
		// test sensors with non-zero distances for Forward and Left 
		// and zero for Backward and Right 
		int[] correctDists = {1, 0, 1, 0};
		Direction[] sensors = Direction.values();
		for (int i = 0; i < sensors.length; i++) {
			float origBattery = robot.getBatteryLevel();
			// try to get the distance in the direction
			int testDist = robot.distanceToObstacle(sensors[i]);
	
			// check that it corresponds with the maze
			assertEquals(correctDists[i], testDist);
			// check that the battery level is updated to subtract the sensing cost
			assertEquals(origBattery-robot.leftSensor.getEnergyConsumptionForSensing(), robot.getBatteryLevel(), 0);
		}
		
		robot.rotate(Turn.AROUND);
		// test sensors with non-zero distances for Backward and Right 
		// and zero for Forward and Left
		int[] correctDists2 = {0, 1, 0, 1};
		for (int i = 0; i < sensors.length; i++) {
			float origBattery = robot.getBatteryLevel();
			// try to get the distance in the direction
			int testDist = robot.distanceToObstacle(sensors[i]);
	
			// check that it corresponds with the maze
			assertEquals(correctDists2[i], testDist);
			// check that the battery level is updated to subtract the sensing cost
			assertEquals(origBattery-robot.leftSensor.getEnergyConsumptionForSensing(), robot.getBatteryLevel(), 0);
		}
		
		robot.rotate(Turn.RIGHT);
		robot.move(1);
		robot.rotate(Turn.RIGHT);
		robot.move(1);
		// test sensors with distances greater than 1
		int[] correctDists3 = {1, 0, 0, 2};
		for (int i = 0; i < sensors.length; i++) {
			float origBattery = robot.getBatteryLevel();
			// try to get the distance in the direction
			int testDist = robot.distanceToObstacle(sensors[i]);
	
			// check that it corresponds with the maze
			assertEquals(correctDists3[i], testDist);
			// check that the battery level is updated to subtract the sensing cost
			assertEquals(origBattery-robot.leftSensor.getEnergyConsumptionForSensing(), robot.getBatteryLevel(), 0);
		}
		
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
		for (Direction direction: Direction.values()) {
			assertThrows(UnsupportedOperationException.class, 
				() -> {robot.startFailureAndRepairProcess(direction, 0, 0);});
		}
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
		for (Direction direction: Direction.values()) {
			assertThrows(UnsupportedOperationException.class, 
				() -> {robot.startFailureAndRepairProcess(direction, 0, 0);});
		}
	}
}
