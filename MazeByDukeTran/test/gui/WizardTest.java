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
import gui.Robot.Turn;

public class WizardTest extends Wizard {
	private Wizard wizard;
	private Maze maze;
	private Robot robot;
	
	/**
	 * Set up the maze, robot, and controller and create a new wizard and
	 * assign the maze and robot to it
	 */
	@Before
	public final void setUp() {
		// set up controller and maze
		Controller controller = new Controller();
		controller.turnOffGraphics();
		StubOrder order = new StubOrder();
		MazeFactory factory = new MazeFactory();
		factory.order(order);
		factory.waitTillDelivered();
		controller.switchFromGeneratingToPlaying(order.getMaze());
		maze = controller.getMazeConfiguration();
		
		// instantiate the robot and assign the controller to the robot
		robot = new ReliableRobot();
		robot.setController(controller);
		// instantiate the wizard and assign the robot and maze to the wizard
		wizard = new Wizard();
		wizard.setRobot(robot);
		wizard.setMaze(maze);		
	}
	
	/**
	 * Test case: See if the setUp properly sets up the wizard
	 * <p>
	 * Method under test: own set up
	 * <p>
	 * Correct behavior:
	 * the wizard and maze are not null, and the wizard can access a non-null robot and maze
	 */
	@Test
	public final void testWizard() {
		// check that the wizard is not null
		assertNotNull(wizard);
		// check that the maze is not null
		assertNotNull(maze);
		// check that the wizard's robot is not null
		assertNotNull(wizard.robot);
		// check that the wizard's maze is not null
		assertNotNull(wizard.maze);
	}
	
	/**
	 * Test case: Correctness of the setRobot method
	 * <p>
	 * Method under test: setRobot(Robot r)
	 * <p>
	 * Correct behavior:
	 * assigns the inputed robot to the robot field
	 */
	@Test
	public final void testSetRobot() {
		// check that the wizard's robot equals the inputed robot
		assertEquals(robot, wizard.robot);
		// check that the initial battery is instantiated and equals the robot's initial battery
		assertEquals(robot.getBatteryLevel(), wizard.initialBatteryLevel, 0);
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
		// check that the wizard's maze equals the inputed maze
		assertEquals(maze, wizard.maze);
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
			assertFalse(wizard.drive2Exit());
			
			resetRobot();

			// run the method and check that the robot is at the exit (should return true)
			assertTrue(wizard.drive2Exit());
		} catch (Exception e) {
			System.out.println("Something went wrong!");
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
		assertThrows(Exception.class, () -> wizard.drive1Step2Exit());
		resetRobot();
		// check that an exception is thrown if the robot doesn't have enough energy for a move
		robot.setBatteryLevel(robot.getEnergyForStepForward()-1);
		// check that an exception is thrown if the robot hits a wall
		assertThrows(Exception.class, () -> wizard.drive1Step2Exit());
		resetRobot();
		robot.setBatteryLevel(wizard.initialBatteryLevel);
		
		// while robot is not at exit
		while (!robot.isAtExit()) {
			try {
				int[] currPos = robot.getCurrentPosition();
				// get the neighbor closest to the exit
				int[] nextPos = maze.getNeighborCloserToExit(currPos[0], currPos[1]);
				
				// run the method and check that it ends up on the same cell as the selected neighbor
				wizard.drive1Step2Exit();
				currPos = robot.getCurrentPosition();
				
				assertEquals(nextPos[0], currPos[0]);
				assertEquals(nextPos[1], currPos[1]);
			} catch (Exception e) {
				System.out.println("Something went wrong!");
				return;
			}
		}
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
		assertEquals(0, wizard.getEnergyConsumption(), 0);
		
		// check that the returned value equals the difference between the initial and current
		// battery levels
		robot.setBatteryLevel(50);
		assertEquals(wizard.initialBatteryLevel-50, wizard.getEnergyConsumption(), 0);
		robot.setBatteryLevel(wizard.initialBatteryLevel);

		// rotate the robot and move the robot 1 step
		robot.rotate(Turn.RIGHT);
		robot.move(1);

		// check that the energy consumption is equal to the rotate cost + move cost
		assertEquals(robot.getEnergyForFullRotation()/4+robot.getEnergyForStepForward(),
			wizard.getEnergyConsumption(), 0);
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
		assertEquals(robot.getOdometerReading(), wizard.getPathLength());
		// check that the initial path length is 0
		assertEquals(0, wizard.getPathLength());
		
		// rotate the robot and move the robot 1 step
		robot.rotate(Turn.RIGHT);
		robot.move(1);
		// check that the path length is 1
		assertEquals(1, wizard.getPathLength());
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
			wizard.crossExit2Win(robot.getCurrentPosition());
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
		assertFalse(wizard.neighborOutsideMaze(currPos, CardinalDirection.North));
		assertFalse(wizard.neighborOutsideMaze(currPos, CardinalDirection.East));
		assertFalse(wizard.neighborOutsideMaze(currPos, CardinalDirection.South));
		assertTrue(wizard.neighborOutsideMaze(currPos, CardinalDirection.West));
		
		// at a position in the middle of the maze, all neighbors should be inside of the maze
		currPos[0] = 1; currPos[1] = 2;
		assertFalse(wizard.neighborOutsideMaze(currPos, CardinalDirection.North));
		assertFalse(wizard.neighborOutsideMaze(currPos, CardinalDirection.East));
		assertFalse(wizard.neighborOutsideMaze(currPos, CardinalDirection.South));
		assertFalse(wizard.neighborOutsideMaze(currPos, CardinalDirection.West));
		
		// at the corner position, there should be exactly two neighbors outside of the maze
		currPos[0] = 3; currPos[1] = 0;
		assertTrue(wizard.neighborOutsideMaze(currPos, CardinalDirection.North));
		assertTrue(wizard.neighborOutsideMaze(currPos, CardinalDirection.East));
		assertFalse(wizard.neighborOutsideMaze(currPos, CardinalDirection.South));
		assertFalse(wizard.neighborOutsideMaze(currPos, CardinalDirection.West));
	}
	
	/**
	 * Re-instantiates the robot with the same maze and controller
	 * (to reset the stopped field to false)
	 */
	private final void resetRobot() {
		Controller controller = new Controller();
		controller.turnOffGraphics();
		controller.switchFromGeneratingToPlaying(maze);
		
		robot = new ReliableRobot();
		robot.setController(controller);
		wizard = new Wizard();
		wizard.setRobot(robot);
		wizard.setMaze(maze);		
	}	
}
