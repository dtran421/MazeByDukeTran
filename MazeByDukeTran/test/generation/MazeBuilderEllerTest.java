package generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


import org.junit.Before;
import org.junit.Test;

import gui.Constants;

public class MazeBuilderEllerTest {

	// private variables
	private MazeFactory mazeFactory; // setup makes this a MazeBuilder object
	private StubOrder order;
	private int mazeWidth;
	private int mazeHeight;
	private Floorplan floorplan;
	private final int INFINITY = Integer.MAX_VALUE; 

	/**
	 * Instantiate a new MazeBuilder object for each test
	 */
	@Before
	public void setUp() {
		// make a new MazeFactory object and order
		mazeFactory = new MazeFactory();
		order = new StubOrder(2, true, Order.Builder.Eller);		
		// build the maze
		mazeFactory.order(order);
		mazeFactory.waitTillDelivered();
		// assign maze dimensions
		mazeWidth = order.getMaze().getWidth();
		mazeHeight = order.getMaze().getHeight();
		floorplan = order.getMaze().getFloorplan();
	}
	
	/**
	 * Test case: See if constructor used in setUp delivers anything
	 * <p>
	 * Method under test: own set up
	 * <p>
	 * Correct behavior:
	 * the builder field is not null
	 */
	@Test
	public final void testMazeBuilderEller() {
		// check that builder is not null
		assertNotNull(mazeFactory);
	}
	
	/**
	 * Test case: Correctness of the generatePathways method
	 * <p>
	 * Method tested: generatePathways()
	 * <p>
	 * Correct behavior:
	 * a maze should be generated properly using Eller's algorithm
	 */
	@Test
	public final void testGeneratePathways() {	
		// ensure that the maze is not null
		assertNotNull(order.getMaze());
		
		// check that the dimensions match the skill level's
		assertEquals(mazeWidth, Constants.SKILL_X[2]);
		assertEquals(mazeHeight, Constants.SKILL_Y[2]);
		
		// check that there are no enclosed areas
		Distance mazeDists = new Distance(mazeWidth, mazeHeight);
		mazeDists.computeDistances(floorplan);
		for (int x = 0; x < mazeWidth; x++) {
			for (int y = 0; y < mazeHeight; y++) {
				assertFalse(cellHas4Walls(floorplan, x, y));
				assertNotEquals(mazeDists.getDistanceValue(x, y), INFINITY);
			}
		}	
		
		// check that there is an exit
		int[] exit = mazeDists.getExitPosition();
		assertEquals(mazeDists.getDistanceValue(exit[0], exit[1]), 1);
		assertTrue(floorplan.hasNoWall(exit[0], exit[1], CardinalDirection.North) || 
				floorplan.hasNoWall(exit[0], exit[1], CardinalDirection.East) ||
				floorplan.hasNoWall(exit[0], exit[1], CardinalDirection.South) ||
				floorplan.hasNoWall(exit[0], exit[1], CardinalDirection.West));
		
		// check that there is a path to the exit
		int[] start = mazeDists.getStartPosition();
		assertFalse(start[0] == INFINITY || start[1] == INFINITY);
		
		// check that each cell has at least one wall
		for (int x = 0; x < mazeWidth; x++) {
			for (int y = 0; y < mazeHeight; y++) {
				if (!floorplan.isInRoom(x, y)) {
					//assertTrue(cellHasWall(floorplan, x, y));
				}
			}
		}
				
		// repeat test with new saved floorplan that has rooms
		mazeFactory = new MazeFactory();
		order = new StubOrder(2, false, Order.Builder.Eller);
		// build the maze
		mazeFactory.order(order);
		mazeFactory.waitTillDelivered();
		floorplan = order.getMaze().getFloorplan();
		mazeWidth = order.getMaze().getWidth();
		mazeHeight = order.getMaze().getHeight();
		
		// check for the same things as above
		// check that the dimensions match the skill level's
		assertEquals(mazeWidth, Constants.SKILL_X[2]);
		assertEquals(mazeHeight, Constants.SKILL_Y[2]);
		
		// check that there are no enclosed areas
		mazeDists = new Distance(mazeWidth, mazeHeight);
		mazeDists.computeDistances(floorplan);
		for (int x = 0; x < mazeWidth; x++) {
			for (int y = 0; y < mazeHeight; y++) {
				assertFalse(cellHas4Walls(floorplan, x, y));
				assertNotEquals(mazeDists.getDistanceValue(x, y), INFINITY);
			}
		}	
		
		// check that there is an exit
		exit = mazeDists.getExitPosition();
		assertEquals(mazeDists.getDistanceValue(exit[0], exit[1]), 1);
		assertTrue(floorplan.hasNoWall(exit[0], exit[1], CardinalDirection.North) || 
				floorplan.hasNoWall(exit[0], exit[1], CardinalDirection.East) ||
				floorplan.hasNoWall(exit[0], exit[1], CardinalDirection.South) ||
				floorplan.hasNoWall(exit[0], exit[1], CardinalDirection.West));
		
		// check that there is a path to the exit
		start = mazeDists.getStartPosition();
		assertFalse(start[0] == INFINITY || start[1] == INFINITY);
	}
	
	/**
	 * Test case: Ensure the cellToId HashMap is valid after generatePathways
	 * runs
	 * <p>
	 * Correct behavior:
	 * the cellToId HashMap conforms to certain test conditions
	 */
	@Test
	public final void testCellToId() {
		// check that the size of the HashMap is width x height of the maze
		// (since all the cells should be present and belong to a set)
		
		// check that each cell in the maze has an entry in the HashMap
		
		// check that each id is the same (since all cells should belong to a
		// single, common set
	}
	
	/**
	 * Test case: Ensure the idToSet HashMap is valid after generatePathways
	 * runs
	 * <p>
	 * Correct behavior:
	 * the idToSet HashMap conforms to certain test conditions
	 */
	@Test
	public final void testIdToSet() {
		// check that the size of the HashMap is 1 (since all cells should
		// belong to a single, common set)
		
		// check that each cell in the maze belongs in the single set
				
		// check that the id matches the ids of all of the cells from the
		// cellToId HashMap
	}
	
	
	// private methods
	
	/*
	 * check if the selected cell has 4 walls surrounding it
	 */
	private final boolean cellHas4Walls(Floorplan floorplan, int x, int y) {
		return floorplan.hasWall(x, y, CardinalDirection.North) && 
		floorplan.hasWall(x, y, CardinalDirection.East) &&
		floorplan.hasWall(x, y, CardinalDirection.South) &&
		floorplan.hasWall(x, y, CardinalDirection.West);
	}
	
	/*
	 * check if the selected cell has at least one wall
	 */
	private final boolean cellHasWall(Floorplan floorplan, int x, int y) {
		return floorplan.hasWall(x, y, CardinalDirection.North) || 
			floorplan.hasWall(x, y, CardinalDirection.East) ||
			floorplan.hasWall(x, y, CardinalDirection.South) ||
			floorplan.hasWall(x, y, CardinalDirection.West);
	}
}
	
	