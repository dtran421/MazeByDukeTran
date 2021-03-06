package generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


import org.junit.Before;
import org.junit.Test;

import gui.Constants;

public class MazeFactoryTest {

	// private variables
	private MazeFactory mazeFactory; // setup makes this a MazeFactory object
	private StubOrder order; // setup makes this a StubOrder object
	private int mazeWidth;
	private int mazeHeight;
	private Floorplan floorplan;
	private Distance mazeDists;
	
	private final String BUILDER = "Eller";
	private final int INFINITY = Integer.MAX_VALUE; 

	/**
	 * Instantiate a new MazeFactory object for each test
	 */
	@Before
	public void setUp() {
		mazeFactory = new MazeFactory();
		switch (BUILDER) {
			case "DFS":
				order = new StubOrder(0, true, Order.Builder.DFS);
				break;
			case "Eller":
				order = new StubOrder(0, true, Order.Builder.Eller);
				break;
		}
		// build the maze with specified skill level
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
	 * the mazeFactory field is not null
	 */
	@Test
	public final void testMazeFactory() {
		// check that mazeFactory is not null
		assertNotNull(mazeFactory);
		// check that the maze contained in order is not null
		assertNotNull(order.getMaze());
	}
	
	/**
	 * Test case: Correctness of the generated maze based on skill level's
	 * predetermined width and height.
	 * <p>
	 * Correct behavior:
	 * a maze should have dimensions that match the designated width and height
	 */
	@Test
	public final void testDimensions() {		
		// compare the maze's dimensions to the inputs
		assertEquals(mazeWidth, Constants.SKILL_X[0]);
		assertEquals(mazeHeight, Constants.SKILL_Y[0]);
		
		newMazeWithRooms();
		
		assertEquals(mazeWidth, Constants.SKILL_X[2]);
		assertEquals(mazeHeight, Constants.SKILL_Y[2]);
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
		computeDists();
		for (int x = 0; x < mazeWidth; x++) {
			for (int y = 0; y < mazeHeight; y++) {
				assertFalse(cellHas4Walls(x, y));
				assertNotEquals(mazeDists.getDistanceValue(x, y), INFINITY);
			}
		}	
		
		newMazeWithRooms();
		computeDists();
		for (int x = 0; x < mazeWidth; x++) {
			for (int y = 0; y < mazeHeight; y++) {
				assertFalse(cellHas4Walls(x, y));
				assertNotEquals(mazeDists.getDistanceValue(x, y), INFINITY);
			}
		}
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
		computeDists();
		int[] exit = mazeDists.getExitPosition();
		assertEquals(mazeDists.getDistanceValue(exit[0], exit[1]), 1);
		assertFalse(cellHas4Walls(exit[0], exit[1]));
		
		newMazeWithRooms();
		computeDists();
		exit = mazeDists.getExitPosition();
		assertEquals(mazeDists.getDistanceValue(exit[0], exit[1]), 1);
		assertFalse(cellHas4Walls(exit[0], exit[1]));
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
		computeDists();
		int[] start = mazeDists.getStartPosition();
		int maxDist = mazeDists.getMaxDistance();
		
		assertEquals(maxDist, mazeDists.getDistanceValue(start[0], start[1]));
		
		newMazeWithRooms();
		computeDists();
		start = mazeDists.getStartPosition();
		maxDist = mazeDists.getMaxDistance();
		
		assertEquals(maxDist, mazeDists.getDistanceValue(start[0], start[1]));
	}
	
	/**
	 * Test case: Correctness of the generated maze based on existence of path
	 * from starting position to exit
	 * <p>
	 * Correct behavior:
	 * a maze should have a path that is traversable from the starting position
	 * to the exit, and any cell that is not part of a room should have at least
	 * one wall.
	 */
	@Test
	public final void testPathExists() {
		// check that the distance from the starting position to the exit
		// is not infinity
		computeDists();
		int[] start = mazeDists.getStartPosition();
		assertFalse(start[0] == INFINITY || start[1] == INFINITY);
		
		// check that each cell has at least one wall
		for (int x = 0; x < mazeWidth; x++) {
			for (int y = 0; y < mazeHeight; y++) {
				if (!floorplan.isInRoom(x, y)) {
					assertTrue(cellHasWall(x, y));
				}
			}
		}
		
		newMazeWithRooms();
		computeDists();
		start = mazeDists.getStartPosition();
		assertFalse(start[0] == INFINITY || start[1] == INFINITY);
	}

	/**
	 * Test case: Correctness of the generated maze based on existence of 
	 * border walls.
	 * <p>
	 * Correct behavior:
	 * a maze should have a border surrounding the entire area less one wall which
	 * would serve to be the exit.
	 */
	@Test
	public final void testBorderWalls() {
		// check all walls along the exterior to make sure they are borders
		// one wall should be missing to be the exit
		computeDists();
		int[] exit = mazeDists.getExitPosition();
		
		for (int x = 0; x < mazeWidth; x++) {
			if (exit[0] != x && exit[1] != 0) 
				assertTrue(cellHasWall(x, 0));
			if (exit[0] != x && exit[1] != mazeHeight-1) 
				assertTrue(cellHasWall(x, mazeHeight-1));
		} 
		for (int y = 0; y < mazeHeight; y++) {
			if (exit[0] != 0 && exit[1] != y) 
				assertTrue(cellHasWall(0, y));
			if (exit[0] != mazeWidth-1 && exit[1] != y) 
				assertTrue(cellHasWall(mazeWidth-1, y));
		}
		
		newMazeWithRooms();
		computeDists();
		exit = mazeDists.getExitPosition();
		
		for (int x = 0; x < mazeWidth; x++) {
			if (exit[0] != x && exit[1] != 0) 
				assertTrue(cellHasWall(x, 0));
			if (exit[0] != x && exit[1] != mazeHeight-1) 
				assertTrue(cellHasWall(x, mazeHeight-1));
		} 
		for (int y = 0; y < mazeHeight; y++) {
			if (exit[0] != 0 && exit[1] != y) 
				assertTrue(cellHasWall(0, y));
			if (exit[0] != mazeWidth-1 && exit[1] != y) 
				assertTrue(cellHasWall(mazeWidth-1, y));
		}
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
		// all cells inside the room must be empty
		for (int x = 1; x < mazeWidth-1; x++) {
			for (int y = 1; y < mazeHeight-1; y++) {
				if (floorplan.isInRoom(x, y) && isRoomInterior(x, y))
					assertFalse(cellHasWall(x, y));
			}
		}
		
		newMazeWithRooms();
		computeDists();
		
		// iterate through cells marked as a room and ensure that there
		// is a possible path to the exit and at least one wall is missing to be a door
		boolean doorExists = false;
		for (int x = 0; x < mazeWidth; x++) {
			for (int y = 0; y < mazeHeight; y++) {
				if (floorplan.isInRoom(x, y)) {
					assertNotEquals(mazeDists.getDistanceValue(x, y), INFINITY);
					if (checkForDoor(x, y))
						doorExists = true;
				}
			}
		}
		assertTrue(doorExists);
	}
	
	// private methods
	
	/*
	 * make a new maze with a skill level of 2 so that it will
	 * have rooms
	 */
	private final void newMazeWithRooms() {
		switch (BUILDER) {
		case "DFS":
			order = new StubOrder(2, false, Order.Builder.DFS);
			break;
		case "Eller":
			order = new StubOrder(2, false, Order.Builder.Eller);
			break;
	}
		mazeFactory.order(order);
		mazeFactory.waitTillDelivered();

		mazeWidth = order.getMaze().getWidth();
		mazeHeight = order.getMaze().getHeight();
		floorplan = order.getMaze().getFloorplan();
	}
	
	/*
	 * check if the selected cell has 4 walls surrounding it
	 */
	private final boolean cellHas4Walls(int x, int y) {
		return floorplan.hasWall(x, y, CardinalDirection.North) && 
		floorplan.hasWall(x, y, CardinalDirection.East) &&
		floorplan.hasWall(x, y, CardinalDirection.South) &&
		floorplan.hasWall(x, y, CardinalDirection.West);
	}
	
	/*
	 * check if the selected cell has at least one wall
	 */
	private final boolean cellHasWall(int x, int y) {
		return floorplan.hasWall(x, y, CardinalDirection.North) || 
			floorplan.hasWall(x, y, CardinalDirection.East) ||
			floorplan.hasWall(x, y, CardinalDirection.South) ||
			floorplan.hasWall(x, y, CardinalDirection.West);
	}
	
	/*
	 * compute the maze distance matrix based on the floorplan
	 */
	private final void computeDists() {
		mazeDists = new Distance(mazeWidth, mazeHeight);
		mazeDists.computeDistances(floorplan);
	}
	
	/*
	 * determines if a cell is on the interior of a room if it has
	 * no walls and all cells surrounding it are in a room
	 */
	private final boolean isRoomInterior(int x, int y) {
		if (floorplan.isInRoom(x, y-1) && floorplan.isInRoom(x+1, y)
			&& floorplan.isInRoom(x, y+1) && floorplan.isInRoom(x-1, y)) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * check a cell of a room to see if it has an exit (it's missing a wall
	 * that connects to a cell that is not in the room)
	 */
	private final boolean checkForDoor(int x, int y) {
		if (!floorplan.hasWall(x, y, CardinalDirection.North) && !floorplan.isInRoom(x, y-1))
			return true;
		if (!floorplan.hasWall(x, y, CardinalDirection.East) && !floorplan.isInRoom(x+1, y))
			return true;
		if (!floorplan.hasWall(x, y, CardinalDirection.South) && !floorplan.isInRoom(x, y+1))
			return true;
		if (!floorplan.hasWall(x, y, CardinalDirection.West) && !floorplan.isInRoom(x-1, y))
			return true;
		return false;
	}
}
