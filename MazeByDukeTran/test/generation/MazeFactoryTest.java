package generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MazeFactoryTest {

	// private variables
	private MazeFactory mazeFactory; // setup makes this a MazeFactory object
	
	/**
	 * Instantiate a new MazeFactory object for each test
	 */
	@Before
	public void setUp() {
		mazeFactory = new MazeFactory();
	}
	
	/**
	 * Test case: See if constructor used in setUp delivers anything
	 * <p>
	 * Method under test: own set up
	 * <p>
	 * Correct behavior:
	 * it instantiates the mazeBuilder field and it is not null
	 */
	@Test
	public final void testMazeBuilder() {
		// check that mazeBuilder is not null
	}
	
	/**
	 * Test case: Correctness of the order method
	 * <p>
	 * Method under test: order(Order order)
	 * <p>
	 * Correct behavior:
	 * it selects the correct algorithm and calls the buildOrder method
	 * to begin the building process
	 */
	@Test
	public final void testOrder() {
		// test for if build thread already exists, call order and then
		// call order again
		
		// check each case when builder is DFS, Prim, or algorithm is missing
		// for selected builder (should return false)
		
		// when method finishes it should return true
	}
	
	/**
	 * Test case: Correctness of the cancel method
	 * <p>
	 * Method under test: cancel()
	 * <p>
	 * Correct behavior:
	 * it cancels the build process by stopping the thread 
	 * and cleaning up
	 */
	@Test
	public final void testCancel() {
		// check if build thread already exists, then cancel it and allow
		// the next thread to proceed
		
		// if not then no thread is cancelled
		
		// builder and order should be cleaned up
	}
	
	/**
	 * Test case: Correctness of the waitTillDelivered method
	 * <p>
	 * Method under test: waitTillDelivered()
	 * <p>
	 * Correct behavior:
	 * it should execute the join method on the current build thread so
	 * that it may be cleaned up properly once it finishes running
	 */
	@Test
	public final void testWaitTillDelivered() {
		// check if build thread already exists, then attempt to execute
		// the join method and wait for it to finish
		
		// if not then no thread needs to be awaited
		
		// builder and order should be cleaned up
	}
	
	/**
	 * Test case: Correctness of the buildOrder method
	 * <p>
	 * Method under test: buildOrder()
	 * <p>
	 * Correct behavior:
	 * it starts a new thread and uses the selected order 
	 * to begin the building process with the MazeBuilder
	 */
	@Test
	public final void testBuildOrder() {
		// if there's no builder then the method does not need to execute
		
		// if there is a builder, then it should build the order and start
		// a new thread
	}
	
	/**
	 * Test case: Correctness of the generated maze based on inputted width
	 * and height.
	 * <p>
	 * Correct behavior:
	 * a maze should have dimensions that match the designated width and height
	 */
	@Test
	public final void testDimensions() {
		// instantiate new width and height
		// build the maze
		// compare the maze's dimensions to the inputs
	}
	
	/**
	 * Test case: Correctness of the generated maze based on the absence of
	 * enclosed areas with no doors.
	 * <p>
	 * Correct behavior:
	 * a maze should have no enclosed areas that cannot be accessed.
	 */
	@Test
	public final void testNoEnclosedAreas() {		
		// iterate through the cells of the maze and ensure that no
		// cell has walls in all 4 directions
		
		// iterate through cells marked as a room and ensure that there
		// is at least one door
	}
	
	/**
	 * Test case: Correctness of the generated maze based on room design.
	 * <p>
	 * Correct behavior:
	 * if a maze has rooms, they should be empty and its walls should be load-bearing
	 * less at least one wall so that it's not an enclosed area.
	 */
	@Test
	public final void testRoomDesign() {
		// designate a room
		// ensure that all walls are borders
		// at least one wall is missing to be a door
		// all cells inside the room must be empty
	}
	
	/**
	 * Test case: Correctness of the generated maze based on existence
	 * of an exit on the border
	 * <p>
	 * Correct behavior:
	 * a maze should have an exit on the border
	 */
	@Test
	public final void testExitExists() {
		// check if one exterior wall is missing to be the exit
	}
	
	/**
	 * Test case: Correctness of the generated maze based on existence of 
	 * border walls.
	 * <p>
	 * Correct behavior:
	 * a maze should have a border surrounding the entire area less one wall which
	 * would serve to be the exit
	 */
	@Test
	public final void testBorderWalls() {
		// check all walls along the exterior to make sure they are borders
		// one wall should be missing to be the exit
	}
	
	/**
	 * Test case: Correctness of the generated maze based on starting
	 * position
	 * <p>
	 * Correct behavior:
	 * a maze should have a starting position that is furthest from the exit.
	 */
	@Test
	public final void testStartingPosition() {
		// check that the distance from the starting position to the exit
		// is the maximum
	}
	
	
	/**
	 * Test case: Correctness of the generated maze based on existence of path
	 * from starting position to exit
	 * <p>
	 * Correct behavior:
	 * a maze should have a path that is traversable from the starting position
	 * to the exit.
	 */
	@Test
	public final void testPathExists() {
		// check that the distance from the starting position to the exit
		// is not infinity
	}
}
