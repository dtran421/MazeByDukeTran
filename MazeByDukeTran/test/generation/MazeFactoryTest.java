package generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MazeFactoryTest {

	// private variables
	private MazeBuilder mazeBuilder; // setup makes this a MazeBuilder object
	
	/**
	 * Instantiate a new MazeBuilder object for each test
	 */
	@Before
	public void setUp() {
		mazeBuilder = new MazeBuilder();
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
		
	}
}
