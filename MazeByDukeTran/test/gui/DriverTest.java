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
import gui.Robot.Turn;

public class DriverTest extends Wizard {
	protected Wizard driver;
	protected Maze maze;
	protected Robot robot;
	
	/**
	 * Set up the maze, robot, and controller. 
	 */
	@Before
	public final void setUpParent() {
		// set up controller and maze
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
		// instantiate the robot and assign the controller to the robot
		robot = new ReliableRobot();
		robot.setController(controller);
	}
	
	/**
	 * Test case: See if the setUp properly sets up the driver
	 * <p>
	 * Method under test: own set up
	 * <p>
	 * Correct behavior:
	 * the maze and robot are not null, and the driver is not null and can access a non-null robot and maze
	 */
	@Test
	public final void testWizard() {
		// check that the maze is not null
		assertNotNull(maze);
		assertNotNull(robot);
		// check that the driver is not null
		assertNotNull(driver);
		// check that the driver's robot is not null
		assertNotNull(driver.robot);
		// check that the driver's maze is not null
		assertNotNull(driver.maze);
	}
	
	/**
	 * Test case: Correctness of the setMaze method
	 * <p>
	 * Method under test: setMaze(Maze maze)
	 * <p>
	 * Correct behavior:
	 * assigns the inputed maze to the maze field
	 */
	@Test
	public final void testSetMaze() {
		// check that the driver's maze equals the inputed maze
		assertEquals(maze, driver.maze);
	}
	
	/**
	 * Test case: Correctness of the getEnergyConsumption method
	 * <p>
	 * Method under test: getEnergyConsumption()
	 * <p>
	 * Correct behavior:
	 * returns the difference between the initial battery level and the robot's current battery level
	 */
	@Test
	public final void testGetEnergyConsumption() {
		// check that the initial energy consumption is 0 (since it hasn't moved
		assertEquals(0, driver.getEnergyConsumption(), 0);
		
		// check that the returned value equals the difference between the initial and current
		// battery levels
		robot.setBatteryLevel(50);
		assertEquals(driver.initialBatteryLevel-50, driver.getEnergyConsumption(), 0);
		robot.setBatteryLevel(driver.initialBatteryLevel);

		// rotate the robot and move the robot 1 step
		robot.rotate(Turn.RIGHT);
		robot.move(1);

		// check that the energy consumption is equal to the rotate cost + move cost
		assertEquals(robot.getEnergyForFullRotation()/4+robot.getEnergyForStepForward(),
			driver.getEnergyConsumption(), 0);
	}
	
	/**
	 * Test case: Correctness of the getPathLength method
	 * <p>
	 * Method under test: getPathLength()
	 * <p>
	 * Correct behavior:
	 * returns the robot's distance traveled
	 */
	@Test
	public final void testGetPathLength() {
		// check that the returned value equals the value returned from the robot's odometer
		assertEquals(robot.getOdometerReading(), driver.getPathLength());
		// check that the initial path length is 0
		assertEquals(0, driver.getPathLength());
		
		// rotate the robot and move the robot 1 step
		robot.rotate(Turn.RIGHT);
		robot.move(1);
		// check that the path length is 1
		assertEquals(1, driver.getPathLength());
	}
	
	/**
	 * Test case: Correctness of the crossExit2Win method
	 * <p>
	 * Method under test: crossExit2Win(int[] currentPosition)
	 * <p>
	 * Correct behavior:
	 * assuming the robot is already at the exit, it will locate which direction
	 * it is and cross it to win the game
	 */
	@Test
	public final void testCrossExit2Win() {
		// place the robot at the exit
		robot.rotate(Turn.RIGHT);
		robot.move(1);
		robot.rotate(Turn.LEFT);
		robot.move(1);
		robot.jump();
		
		// make sure it ends up facing the exit and crossing it 
		// (it should end up outside of the maze)
		try {
			driver.crossExit2Win(robot.getCurrentPosition());
			// an exception should be thrown since the robot is now outside of the maze
			assertThrows(Exception.class, () -> robot.getCurrentPosition());
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			return;
		}
	}
	
	/**
	 * Test case: Correctness of the neighborOutsideMaze method
	 * <p>
	 * Method under test: neighborOutsideMaze(int[] currentPosition, CardinalDirection currentDirection)
	 * <p>
	 * Correct behavior:
	 * determines if the neighboring cell is outside of the maze
	 */
	@Test
	public final void testNeighborOutsideMaze() {
		// at the starting position, only the western neighbor should be outside of the maze
		int[] currPos = {0, 1};
		assertFalse(driver.neighborOutsideMaze(currPos, CardinalDirection.North));
		assertFalse(driver.neighborOutsideMaze(currPos, CardinalDirection.East));
		assertFalse(driver.neighborOutsideMaze(currPos, CardinalDirection.South));
		assertTrue(driver.neighborOutsideMaze(currPos, CardinalDirection.West));
		
		// at a position in the middle of the maze, all neighbors should be inside of the maze
		currPos[0] = 1; currPos[1] = 2;
		assertFalse(driver.neighborOutsideMaze(currPos, CardinalDirection.North));
		assertFalse(driver.neighborOutsideMaze(currPos, CardinalDirection.East));
		assertFalse(driver.neighborOutsideMaze(currPos, CardinalDirection.South));
		assertFalse(driver.neighborOutsideMaze(currPos, CardinalDirection.West));
		
		// at the corner position, there should be exactly two neighbors outside of the maze
		currPos[0] = 3; currPos[1] = 0;
		assertTrue(driver.neighborOutsideMaze(currPos, CardinalDirection.North));
		assertTrue(driver.neighborOutsideMaze(currPos, CardinalDirection.East));
		assertFalse(driver.neighborOutsideMaze(currPos, CardinalDirection.South));
		assertFalse(driver.neighborOutsideMaze(currPos, CardinalDirection.West));
	}
	
	/**
	 * Re-instantiates the robot with the same maze and controller
	 * (to reset the stopped field to false)
	 */
	protected final void resetRobot(String driver) {
		Controller controller = new Controller();
		controller.turnOffGraphics();
		controller.setDeterministic(true);
		controller.switchFromGeneratingToPlaying(maze);
		
		robot = new ReliableRobot();
		robot.setController(controller);
		switch (driver) {
			case "Wizard":
				this.driver = new Wizard();
				break;
			case "WallFollower":
				this.driver = new WallFollower();
				break;
		}
		this.driver.setRobot(robot);
		this.driver.setMaze(maze);		
	}	
}
