package gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import generation.CardinalDirection;
import gui.Robot.Direction;
import gui.Robot.Turn;

public class WallFollowerTest extends DriverTest {
	final int MEAN_TIME_BETWEEN_FAILURES = 4000;
	final int MEAN_TIME_TO_REPAIR = 2000;
	
	/**
	 * Create a new wall-follower and assign the maze and robot to it
	 */
	@Before
	public final void setUp() {
		// instantiate the robot and assign the controller to the robot
		robot = new UnreliableRobot(0, 0, 0, 0);
		robot.setController(controller);
		// instantiate the wall-follower and assign the robot and maze to the wall-follower
		driver = new WallFollower();
		driver.setRobot(robot);
		driver.setMaze(maze);	
	}
	
	/**
	 * Test case: Correctness of the drive2Exit method
	 * <p>
	 * Method under test: drive2Exit()
	 * <p>
	 * Correct behavior:
	 * drives the robot to the exit of the maze
	 */
	@Test
	public final void testDrive2Exit() {
		// move the robot 1 step into a wall (so that it stops)
		robot.move(1);
		try {
			// check that method returns false since it was stopped
			assertFalse(driver.drive2Exit());
			
			resetRobot("WallFollower", "Unreliable", 0, 0, 0, 0);
			// run the method and check that the robot is at the exit (should return true)
			assertTrue(driver.drive2Exit());
			
			resetRobot("WallFollower", "Unreliable", 0, 0, 0, 0);
			// run the method with repair process threads for the unreliable sensors
			for (Direction direction: Direction.values()) {
				Thread.sleep(1300);
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
			}
			assertTrue(driver.drive2Exit());
			
			resetRobot("WallFollower", "Unreliable", 0, 0, 0, 0);
			// run the method on a maze with rooms
			controller = new Controller();
			controller.setDeterministic(true);
			controller.turnOffGraphics();
			controller.switchFromTitleToGenerating(2);
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
				System.err.println("Something went wrong!");
				return;
			}
			maze = controller.getMazeConfiguration();
			controller.switchFromGeneratingToPlaying(maze);
			
			robot.setController(controller);
			driver.setMaze(maze);
			
			for (Direction direction: Direction.values()) {
				Thread.sleep(1300);
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
			}
			assertTrue(driver.drive2Exit());
		} catch (Exception e) {
			System.err.println("Something went wrong!");
			return;
		}
	}
	
	/**
	 * Test case: Correctness of the drive1Step2Exit method
	 * <p>
	 * Method under test: drive1Step2Exit()
	 * <p>
	 * Correct behavior:
	 * drives the robot 1 step towards the exit of the maze
	 */
	@Test
	public final void testDrive1Step2Exit() {
		// check that an exception is thrown if the robot doesn't have enough energy for a rotation
		robot.setBatteryLevel(robot.getEnergyForFullRotation()/4-1);
		assertThrows(Exception.class, () -> driver.drive1Step2Exit());
		resetRobot("WallFollower", "Unreliable", 0, 0, 0, 0);
		// check that an exception is thrown if the robot doesn't have enough energy for a move
		robot.setBatteryLevel(robot.getEnergyForStepForward()-1);
		// check that an exception is thrown if the robot hits a wall
		assertThrows(Exception.class, () -> driver.drive1Step2Exit());
		resetRobot("WallFollower", "Unreliable", 0, 0, 0, 0);
		
		// check that the state is changed to OperationalState if all sensors are currently operational
		try {
			driver.drive1Step2Exit();
		} catch (Exception e) {
			System.err.println("Something went wrong!");
			return;
		}
		assertEquals(OperationalState.class, ((WallFollower)driver).sensorState.getClass());
		
		resetRobot("WallFollower", "Unreliable", 0, 0, 0, 0);
		// check that the state is changed to RepairState if the forward sensor or left sensor are
		// under repair
		boolean tested = false;
		robot.startFailureAndRepairProcess(Direction.FORWARD, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
		while (!tested) {
			robot.setBatteryLevel(driver.initialBatteryLevel);
			if (!((WallFollower)driver).isOperational(Direction.FORWARD)) {
				try {
					tested = true;
					driver.drive1Step2Exit();
				} catch (Exception e) {
					System.err.println("Something went wrong!");
					return;
				}
			}
		}
		assertEquals(RepairState.class, ((WallFollower)driver).sensorState.getClass());
		
		resetRobot("WallFollower", "Unreliable", 0, 0, 0, 0);
		tested = false;
		robot.startFailureAndRepairProcess(Direction.LEFT, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
		while (!tested) {
			robot.setBatteryLevel(driver.initialBatteryLevel);
			if (!((WallFollower)driver).isOperational(Direction.LEFT)) {
				try {
					driver.drive1Step2Exit();
					tested = true;
				} catch (Exception e) {
					System.err.println("Something went wrong!");
					return;
				}
			}
		}
		assertEquals(RepairState.class, ((WallFollower)driver).sensorState.getClass());
	
		resetRobot("WallFollower", "Unreliable", 0, 0, 0, 0);
		// check that an exception is thrown if the robot is stopped
		robot.move(1);
		assertThrows(Exception.class, () -> driver.drive1Step2Exit());
		
		resetRobot("WallFollower", "Unreliable", 0, 0, 0, 0);
		// make sure the robot's first move is to turn right (since it has wallboards to its left and ahead of it)
		boolean moved = false;
		int[] currPos = new int[2];
		try {
			moved = driver.drive1Step2Exit();
			currPos = driver.robot.getCurrentPosition();
		} catch (Exception e) {
			System.err.println("Something went wrong!");
			return;
		}
		assertFalse(moved);
		int[] correctPos = {0, 1};
		assertEquals(correctPos[0], currPos[0]);
		assertEquals(correctPos[1], currPos[1]);
		assertEquals(CardinalDirection.North, robot.getCurrentDirection());
		
		// the robot's next move should be to move forward 1 since it has a wallboard to its left
		try {
			moved = driver.drive1Step2Exit();
			currPos = driver.robot.getCurrentPosition();
		} catch (Exception e) {
			System.err.println("Something went wrong!");
			return;
		}
		assertTrue(moved);
		correctPos[0] = 0; correctPos[1] = 0;
		assertEquals(correctPos[0], currPos[0]);
		assertEquals(correctPos[1], currPos[1]);
		assertEquals(CardinalDirection.North, driver.robot.getCurrentDirection());
		
		// the robot's next move should be to turn left and move 1 step forward since it has no wallboard there
		try {
			moved = driver.drive1Step2Exit();
			currPos = driver.robot.getCurrentPosition();
		} catch (Exception e) {
			System.err.println("Something went wrong!");
			return;
		}
		assertTrue(moved);
		correctPos[0] = 1; correctPos[1] = 0;
		assertEquals(correctPos[0], currPos[0]);
		assertEquals(correctPos[1], currPos[1]);
		assertEquals(CardinalDirection.East, driver.robot.getCurrentDirection());
		
		resetRobot("WallFollower", "Unreliable", 0, 0, 0, 0);
		// make sure Plan B (wait for a sensor to be operational) is activated when all
		// sensors are under repair
		for (Direction direction: Direction.values())
			robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
		moved = false;
		tested = false;
		while (!tested) {
			robot.setBatteryLevel(driver.initialBatteryLevel);
			if (!((WallFollower)driver).isOperational(Direction.LEFT) || 
				!((WallFollower)driver).isOperational(Direction.FORWARD)) {
				try {
					moved = driver.drive1Step2Exit();
					currPos = driver.robot.getCurrentPosition();
					tested = true;
				} catch (Exception e) {
					System.err.println("Something went wrong!");
					return;
				}
			}
		}
		assertFalse(moved);
		correctPos[0] = 0; correctPos[1] = 1;
		assertEquals(correctPos[0], currPos[0]);
		assertEquals(correctPos[1], currPos[1]);
		assertEquals(CardinalDirection.North, robot.getCurrentDirection());
		
		// repeat the above tests for when the left and forward sensors are under repair
		resetRobot("WallFollower", "Unreliable", 0, 0, 0, 0);
		for (Direction direction: Direction.values()) {
			try {
				Thread.sleep(1300);
				robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
			} catch (Exception e) {
				System.err.println("Something went wrong!");
				return;
			}
		}
		// check that an exception is thrown if the robot is stopped
		robot.move(1);
		assertThrows(Exception.class, () -> driver.drive1Step2Exit());
		
		resetRobot("WallFollower", "Unreliable", 0, 0, 0, 0);
		for (Direction direction: Direction.values())
			robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
		// make sure the robot's first move is to turn right (since it has wallboards to its left and ahead of it)
		moved = false;
		tested = false;
		while (!tested) {
			robot.setBatteryLevel(driver.initialBatteryLevel);
			if (!((WallFollower)driver).isOperational(Direction.LEFT) || 
				!((WallFollower)driver).isOperational(Direction.FORWARD)) {
				try {
					moved = driver.drive1Step2Exit();
					currPos = driver.robot.getCurrentPosition();
					tested = true;
				} catch (Exception e) {
					System.err.println("Something went wrong!");
					return;
				}
			}
		}
		assertFalse(moved);
		correctPos[0] = 0; correctPos[1] = 1;
		assertEquals(correctPos[0], currPos[0]);
		assertEquals(correctPos[1], currPos[1]);
		assertEquals(CardinalDirection.North, robot.getCurrentDirection());
		
		tested = false;
		// the robot's next move should be to move forward 1 since it has a wallboard to its left
		while (!tested) {
			robot.setBatteryLevel(driver.initialBatteryLevel);
			if (!((WallFollower)driver).isOperational(Direction.LEFT) || 
				!((WallFollower)driver).isOperational(Direction.FORWARD)) {
				try {
					moved = driver.drive1Step2Exit();
					currPos = driver.robot.getCurrentPosition();
					tested = true;
				} catch (Exception e) {
					System.err.println("Something went wrong!");
					return;
				}
			}
		}
		assertTrue(moved);
		correctPos[0] = 0; correctPos[1] = 0;
		assertEquals(correctPos[0], currPos[0]);
		assertEquals(correctPos[1], currPos[1]);
		assertEquals(CardinalDirection.North, driver.robot.getCurrentDirection());
		
		tested = false;
		// the robot's next move should be to turn left and move 1 step forward since it has no wallboard there
		while (!tested) {
			robot.setBatteryLevel(driver.initialBatteryLevel);
			if (!((WallFollower)driver).isOperational(Direction.LEFT) || 
				!((WallFollower)driver).isOperational(Direction.FORWARD)) {
				try {
					moved = driver.drive1Step2Exit();
					currPos = driver.robot.getCurrentPosition();
					tested = true;
				} catch (Exception e) {
					System.err.println("Something went wrong!");
					return;
				}
			}
		}
		assertTrue(moved);
		correctPos[0] = 1; correctPos[1] = 0;
		assertEquals(correctPos[0], currPos[0]);
		assertEquals(correctPos[1], currPos[1]);
		assertEquals(CardinalDirection.East, driver.robot.getCurrentDirection());
	}
	
	/**
	 * Test case: Correctness of the isOperational method
	 * <p>
	 * Method under test: isOperational(Direction direction)
	 * <p>
	 * Correct behavior:
	 * returns whether the sensor in the inputed direction is operational
	 */
	@Test
	public final void testIsOperational() {
		// check that the method returns false if the robot doesn't have enough energy to use the sensor
		// or is stopped
		robot.move(1);
		assertFalse(((WallFollower)driver).isOperational(Direction.LEFT));

		// check that the method can return false for all sensors at start position
		for (Direction direction: Direction.values()) {
			resetRobot("WallFollower", "Unreliable", 0, 0, 0, 0);
			driver.robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
			// test that the method returns true initially (since the sensor starts off as operational)
			assertTrue(((WallFollower)driver).isOperational(direction));
			assertEquals(0, ((WallFollower)driver).leftDistance);
			assertEquals(0, ((WallFollower)driver).forwardDistance);
			assertFalse(((WallFollower)driver).foundExit);
			
			// wait for the sensor to be under repair
			boolean tested = false;
			while (!tested) {
				robot.setBatteryLevel(driver.initialBatteryLevel);
				if (!((WallFollower)driver).isOperational(direction)) tested = true;
			}
			assertEquals(0, ((WallFollower)driver).leftDistance);
			assertEquals(0, ((WallFollower)driver).forwardDistance);
			assertFalse(((WallFollower)driver).foundExit);
		}
		
		// move the robot to a position where it has left distance and forward distance
		for (Direction direction: Direction.values()) {
			resetRobot("WallFollower", "Unreliable", 0, 0, 0, 0);
			robot.rotate(Turn.RIGHT);
			robot.move(1);
			robot.rotate(Turn.LEFT);
			robot.move(1);
			robot.rotate(Turn.LEFT);
			driver.robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
			
			// test that the method returns true initially (since the sensor starts off as operational)
			assertTrue(((WallFollower)driver).isOperational(direction));
			if (direction == Direction.LEFT) assertEquals(1, ((WallFollower)driver).leftDistance);
			if (direction == Direction.FORWARD) assertEquals(2, ((WallFollower)driver).forwardDistance);
			assertFalse(((WallFollower)driver).foundExit);
			
			// wait for the sensor to be under repair
			boolean tested = false;
			while (!tested) {
				robot.setBatteryLevel(driver.initialBatteryLevel);
				if (!((WallFollower)driver).isOperational(direction)) tested = true;
			}
			if (direction == Direction.LEFT) assertEquals(1, ((WallFollower)driver).leftDistance);
			if (direction == Direction.FORWARD) assertEquals(2, ((WallFollower)driver).forwardDistance);
			assertFalse(((WallFollower)driver).foundExit);
		}
	}
	
	/**
	 * Test case: Correctness of the setState method
	 * <p>
	 * Method under test: setState()
	 * <p>
	 * Correct behavior:
	 * changes the state of the wall-follower based on the operating status of the sensors
	 */
	@Test
	public final void testSetState() {
		robot = new ReliableRobot();
		robot.setController(controller);
		// when all sensors are operational, the state should be OperationalState
		driver.setRobot(robot);
		((WallFollower)driver).setState(((WallFollower)driver).isOperational(Direction.FORWARD), ((WallFollower)driver).isOperational(Direction.LEFT),
			((WallFollower)driver).isOperational(Direction.RIGHT), ((WallFollower)driver).isOperational(Direction.BACKWARD));
		assertEquals(OperationalState.class, ((WallFollower)driver).sensorState.getClass());
		
		resetRobot("WallFollower", "Unreliable", 0, 0, 0, 0);
		for (Direction direction: Direction.values()) 
			driver.robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
		// when no sensors are operational, the state should be RepairState
		boolean tested = false;
		while (!tested) {
			robot.setBatteryLevel(driver.initialBatteryLevel);
			boolean f = ((WallFollower)driver).isOperational(Direction.FORWARD);
			boolean l = ((WallFollower)driver).isOperational(Direction.LEFT);
			boolean r = ((WallFollower)driver).isOperational(Direction.RIGHT);
			boolean b = ((WallFollower)driver).isOperational(Direction.BACKWARD);
			if (!f && !l && !r && !b) {
				((WallFollower)driver).setState(f, l, r, b);
				tested = true;
			}
		}
		assertEquals(RepairState.class, ((WallFollower)driver).sensorState.getClass());

		int[] sensors = {1, 1, 1, 1}; // left, right, forward, backward
		for (int i = 0; i < 4; i++) {
			sensors[i] = 0;
			resetRobot("WallFollower", "Unreliable", sensors[2], sensors[0], sensors[1], sensors[3]);
			driver.robot.startFailureAndRepairProcess(Direction.values()[i], MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
			// when at least one sensor is not operational, the state should be RepairState
			tested = false;
			while (!tested) {
				robot.setBatteryLevel(driver.initialBatteryLevel);
				boolean f = ((WallFollower)driver).isOperational(Direction.FORWARD);
				boolean l = ((WallFollower)driver).isOperational(Direction.LEFT);
				boolean r = ((WallFollower)driver).isOperational(Direction.RIGHT);
				boolean b = ((WallFollower)driver).isOperational(Direction.BACKWARD);
				if (!f || !l || !r || !b) {
					((WallFollower)driver).setState(f, l, r, b);
					tested = true;
				}
			}
			assertEquals(RepairState.class, ((WallFollower)driver).sensorState.getClass());
			sensors[i] = 1;
		}
	}
	
	/**
	 * Test case: Correctness of the waitForOperationalSensor method
	 * <p>
	 * Method under test: waitForOperationalSensor(boolean f, boolean l, boolean r, boolean b)
	 * <p>
	 * Correct behavior:
	 * waits until a sensor becomes operational before proceeding to perform an action
	 */
	@Test
	public final void testWaitForOperationalSensor() {
		// start the repair threads for all sensors at once
		for (Direction direction: Direction.values())
			robot.startFailureAndRepairProcess(direction, MEAN_TIME_BETWEEN_FAILURES, MEAN_TIME_TO_REPAIR);
		
		// wait until all sensors are under repair
		boolean tested = false;
		boolean[] sensors = {true, true, true, true};
		while (!tested) {
			robot.setBatteryLevel(driver.initialBatteryLevel);
			sensors[0] = !((WallFollower)driver).isOperational(Direction.FORWARD);
			sensors[1] = !((WallFollower)driver).isOperational(Direction.LEFT);
			sensors[2] = !((WallFollower)driver).isOperational(Direction.RIGHT);
			sensors[3] = !((WallFollower)driver).isOperational(Direction.BACKWARD);
			if (sensors[0] && sensors[1] && sensors[2] && sensors[3]) {
				((WallFollower)driver).waitForOperationalSensor(sensors);
				tested = true;
			}
		}
		assertTrue(sensors[0] || sensors[1] || sensors[2] || sensors[3]);
	}
}
