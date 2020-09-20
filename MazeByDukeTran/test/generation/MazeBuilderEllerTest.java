package generation;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class MazeBuilderEllerTest extends MazeBuilderEller {

	// private variables
	private MazeBuilderEller mazeBuilder; // setup makes this a MazeBuilderEller object
	private int mazeWidth;
	private int mazeHeight;
	
	/**
	 * Instantiate a new MazeBuilder object for each test
	 */
	@Before
	public void setUp() {
		// make a new MazeBuilderEller object and order
		mazeBuilder = new MazeBuilderEller();
		Order order = new StubOrder(2, false, Order.Builder.Eller);
		// make a new floorplan of level 2 width and height
		mazeBuilder.buildOrder(order);
		mazeBuilder.floorplan.initialize();
		// define private fields
		mazeWidth = mazeBuilder.width;
		mazeHeight = mazeBuilder.height;
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
		assertNotNull(mazeBuilder);
		// check that the HashMaps are not null
		assertNotNull(mazeBuilder.cellToId);
		assertNotNull(mazeBuilder.idToSet);
		// check that the dimensions are greater than 0
		assertTrue(mazeWidth > 0 && mazeHeight > 0);
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
		assertNotNull(mazeBuilder.floorplan);
		
		// ensure that all cells
	}

	/**
	 * Test case: Correctness of the getCell method
	 * <p>
	 * Method tested: getCell(int x, int y)
	 * <p>
	 * Correct behavior:
	 * returns an ArrayList containing the coordinates of the
	 * requested cell
	 */
	@Test
	public final void testGetCell() {		
		// check for invalid inputs (cell is out of bounds)
		assertNull(mazeBuilder.getCell(mazeWidth, 0));
		assertNull(mazeBuilder.getCell(0, mazeHeight));
		assertNull(mazeBuilder.getCell(-1, 0));
		assertNull(mazeBuilder.getCell(0, -1));
		assertNull(mazeBuilder.getCell(mazeWidth, -1));
		assertNull(mazeBuilder.getCell(-1, mazeHeight));
		
		// check that the returned value is of the correct type of ArrayList<Integer>
		assertTrue(mazeBuilder.getCell(0, 0) instanceof ArrayList<?>);
		assertTrue(mazeBuilder.getCell(2, 1) instanceof ArrayList<?>);
		assertTrue(mazeBuilder.getCell(mazeWidth-1, mazeHeight-1) instanceof ArrayList<?>);
		
		// check that the returned cell matches the inputted cell
		assertTrue(mazeBuilder.getCell(0, 0).get(0) == 0);
		assertTrue(mazeBuilder.getCell(0, 0).get(1) == 0);
		assertTrue(mazeBuilder.getCell(2, 1).get(0) == 2);
		assertTrue(mazeBuilder.getCell(2, 1).get(1) == 1);
		assertTrue(mazeBuilder.getCell(mazeWidth-1, mazeHeight-1).get(0) == mazeWidth-1);
		assertTrue(mazeBuilder.getCell(mazeWidth-1, mazeHeight-1).get(1) == mazeHeight-1);
	}
	
	/**
	 * Test case: Correctness of the newSet method
	 * <p>
	 * Method tested: newSet(int x, int y)
	 * <p>
	 * Correct behavior:
	 * a new set has been made for the requested cell
	 * and added to the HashMap
	 */
	@Test
	public final void testNewSet() {
		// check for invalid inputs
		int oldCISize = mazeBuilder.cellToId.size();
		int oldISSize = mazeBuilder.idToSet.size();
		mazeBuilder.newSet(mazeWidth, 0);
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		mazeBuilder.newSet(0, mazeHeight);
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		mazeBuilder.newSet(-1, 0);
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		mazeBuilder.newSet(0, -1);
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		mazeBuilder.newSet(mazeWidth, -1);
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		mazeBuilder.newSet(-1, mazeHeight);
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		
		// check that a new set has been added to the HashMap
		mazeBuilder.newSet(0, 0);
		assertTrue(mazeBuilder.cellToId.size() == oldCISize+1);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize+1);
		oldCISize = mazeBuilder.cellToId.size();
		oldISSize = mazeBuilder.idToSet.size();
		mazeBuilder.newSet(2, 1);
		assertTrue(mazeBuilder.cellToId.size() == oldCISize+1);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize+1);
		oldCISize = mazeBuilder.cellToId.size();
		oldISSize = mazeBuilder.idToSet.size();
		mazeBuilder.newSet(mazeWidth-1, mazeHeight-1);
		assertTrue(mazeBuilder.cellToId.size() == oldCISize+1);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize+1);
		
		// check that the set belongs to the requested cell
		mazeBuilder.cellToId = new HashMap<ArrayList<Integer>, Integer>();
		mazeBuilder.idToSet = new HashMap<Integer, Set<ArrayList<Integer>>>();
		mazeBuilder.newSet(0, 0);
		ArrayList<Integer> cell = mazeBuilder.getCell(0, 0);
		assertTrue(mazeBuilder.cellToId.containsKey(cell));
		Integer id = mazeBuilder.cellToId.get(cell);
		assertTrue(mazeBuilder.idToSet.containsKey(id) && mazeBuilder.idToSet.get(id).contains(cell));
		mazeBuilder.newSet(2, 1);
		cell = mazeBuilder.getCell(2, 1);
		assertTrue(mazeBuilder.cellToId.containsKey(cell));
		id = mazeBuilder.cellToId.get(cell);
		assertTrue(mazeBuilder.idToSet.containsKey(id) && mazeBuilder.idToSet.get(id).contains(cell));		
		mazeBuilder.newSet(mazeWidth-1, mazeHeight-1);
		cell = mazeBuilder.getCell(mazeWidth-1, mazeHeight-1);
		assertTrue(mazeBuilder.cellToId.containsKey(cell));
		id = mazeBuilder.cellToId.get(cell);
		assertTrue(mazeBuilder.idToSet.containsKey(id) && mazeBuilder.idToSet.get(id).contains(cell));		
	}
	
	/**
	 * Test case: Correctness of the mergeSets method
	 * <p>
	 * Method tested: mergeSets(int x, int y, String dir)
	 * <p>
	 * Correct behavior:
	 * merges the cell with the adjacent cell in the requested
	 * direction and updates the HashMaps correctly
	 */
	@Test
	public final void testMergeSets() {
		// check for invalid cell inputs
		int oldCISize = mazeBuilder.cellToId.size();
		int oldISSize = mazeBuilder.idToSet.size();
		mazeBuilder.mergeSets(mazeWidth, 0, "left-right");
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		mazeBuilder.mergeSets(0, mazeHeight, "left-right");
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		mazeBuilder.mergeSets(-1, 0, "left-right");
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		mazeBuilder.mergeSets(0, -1, "left-right");
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		mazeBuilder.mergeSets(mazeWidth, -1, "left-right");
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		mazeBuilder.mergeSets(-1, mazeHeight, "left-right");
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		
		mazeBuilder.mergeSets(mazeWidth-1, 0, "left-right");
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		mazeBuilder.mergeSets(0, mazeHeight-1, "up-down");
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		
		// check for invalid direction input
		mazeBuilder.mergeSets(0, 0, "");
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		mazeBuilder.mergeSets(2, 1, "");
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		mazeBuilder.mergeSets(mazeWidth-1, mazeHeight-1, "");
		assertTrue(mazeBuilder.cellToId.size() == oldCISize);
		assertTrue(mazeBuilder.idToSet.size() == oldISSize);
		
		
		// check that the sets have been successfully merged
		// left-right
		mazeBuilder.newSet(0, 0);
		mazeBuilder.newSet(1, 0);
		mazeBuilder.mergeSets(0, 0, "left-right");
		assertTrue(mazeBuilder.cellToId.containsKey(mazeBuilder.getCell(0, 0)) &&
			mazeBuilder.cellToId.containsKey(mazeBuilder.getCell(1, 0)));
		Integer id = mazeBuilder.cellToId.get(mazeBuilder.getCell(0, 0));
		assertEquals(id, mazeBuilder.cellToId.get(mazeBuilder.getCell(0, 0)),
			mazeBuilder.cellToId.get(mazeBuilder.getCell(1, 0)));
		assertTrue(mazeBuilder.idToSet.containsKey(id));
		Set<ArrayList<Integer>> cells = mazeBuilder.idToSet.get(id);
		assertTrue(cells.contains(mazeBuilder.getCell(0, 0)) &&
			cells.contains(mazeBuilder.getCell(1, 0)));
		mazeBuilder.newSet(mazeWidth-2, mazeHeight-1);
		mazeBuilder.newSet(mazeWidth-1, mazeHeight-1);
		mazeBuilder.mergeSets(mazeWidth-2, mazeHeight-1, "left-right");
		assertTrue(mazeBuilder.cellToId.containsKey(mazeBuilder.getCell(mazeWidth-2, mazeHeight-1)) &&
			mazeBuilder.cellToId.containsKey(mazeBuilder.getCell(mazeWidth-1, mazeHeight-1)));
		id = mazeBuilder.cellToId.get(mazeBuilder.getCell(mazeWidth-2, mazeHeight-1));
		assertEquals(id, mazeBuilder.cellToId.get(mazeBuilder.getCell(mazeWidth-2, mazeHeight-1)),
			mazeBuilder.cellToId.get(mazeBuilder.getCell(mazeWidth-1, mazeHeight-1)));
		assertTrue(mazeBuilder.idToSet.containsKey(id));
		cells = mazeBuilder.idToSet.get(id);
		assertTrue(cells.contains(mazeBuilder.getCell(mazeWidth-2, mazeHeight-1)) &&
			cells.contains(mazeBuilder.getCell(mazeWidth-1, mazeHeight-1)));
		
		// up-down
		mazeBuilder.cellToId = new HashMap<ArrayList<Integer>, Integer>();
		mazeBuilder.idToSet = new HashMap<Integer, Set<ArrayList<Integer>>>();
		mazeBuilder.newSet(0, 0);
		mazeBuilder.mergeSets(0, 0, "up-down");
		assertTrue(mazeBuilder.cellToId.containsKey(mazeBuilder.getCell(0, 0)) &&
			mazeBuilder.cellToId.containsKey(mazeBuilder.getCell(0, 1)));
		id = mazeBuilder.cellToId.get(mazeBuilder.getCell(0, 0));
		assertEquals(id, mazeBuilder.cellToId.get(mazeBuilder.getCell(0, 0)),
			mazeBuilder.cellToId.get(mazeBuilder.getCell(0, 1)));
		assertTrue(mazeBuilder.idToSet.containsKey(id));
		cells = mazeBuilder.idToSet.get(id);
		assertTrue(cells.contains(mazeBuilder.getCell(0, 0)) &&
			cells.contains(mazeBuilder.getCell(0 ,1)));
		mazeBuilder.newSet(mazeWidth-1, mazeHeight-2);
		mazeBuilder.mergeSets(mazeWidth-1, mazeHeight-2, "up-down");
		assertTrue(mazeBuilder.cellToId.containsKey(mazeBuilder.getCell(mazeWidth-1, mazeHeight-2)) &&
			mazeBuilder.cellToId.containsKey(mazeBuilder.getCell(mazeWidth-1, mazeHeight-1)));
		id = mazeBuilder.cellToId.get(mazeBuilder.getCell(mazeWidth-1, mazeHeight-2));
		assertEquals(id, mazeBuilder.cellToId.get(mazeBuilder.getCell(mazeWidth-1, mazeHeight-2)),
			mazeBuilder.cellToId.get(mazeBuilder.getCell(mazeWidth-1, mazeHeight-1)));
		assertTrue(mazeBuilder.idToSet.containsKey(id));
		cells = mazeBuilder.idToSet.get(id);
		assertTrue(cells.contains(mazeBuilder.getCell(mazeWidth-1, mazeHeight-2)) &&
			cells.contains(mazeBuilder.getCell(mazeWidth-1, mazeHeight-1)));
		
		// check that the new set contains elements from both sets
		// left-right
		mazeBuilder.cellToId = new HashMap<ArrayList<Integer>, Integer>();
		mazeBuilder.idToSet = new HashMap<Integer, Set<ArrayList<Integer>>>();
		mazeBuilder.newSet(0, 0);
		mazeBuilder.newSet(1, 0);
		mazeBuilder.newSet(2, 0);
		mazeBuilder.newSet(3, 0);
		mazeBuilder.mergeSets(0, 0, "left-right");
		mazeBuilder.mergeSets(2, 0, "left-right");
		mazeBuilder.mergeSets(1, 0, "left-right");
		int[] test = new int[4];
		int[] ans = new int[4];
		for (int x = 0; x < 4; x++) {
			test[x] = mazeBuilder.cellToId.get(mazeBuilder.getCell(x, 0));
			ans[x] = mazeBuilder.cellToId.get(mazeBuilder.getCell(0, 0));
		}
		assertArrayEquals(test, ans);
		
		// up-down
		mazeBuilder.cellToId = new HashMap<ArrayList<Integer>, Integer>();
		mazeBuilder.idToSet = new HashMap<Integer, Set<ArrayList<Integer>>>();
		mazeBuilder.newSet(mazeWidth-1, mazeHeight-4);
		mazeBuilder.mergeSets(mazeWidth-1, mazeHeight-4, "up-down");
		mazeBuilder.mergeSets(mazeWidth-1, mazeHeight-3, "up-down");
		mazeBuilder.mergeSets(mazeWidth-1, mazeHeight-2, "up-down");
		test = new int[4];
		ans = new int[4];
		for (int y = 1; y <= 4; y++) {
			test[y-1] = mazeBuilder.cellToId.get(mazeBuilder.getCell(mazeWidth-1, mazeHeight-y));
			ans[y-1] = mazeBuilder.cellToId.get(mazeBuilder.getCell(mazeWidth-1, mazeHeight-4));
		}
		assertArrayEquals(test, ans);
		
		// both
		mazeBuilder.cellToId = new HashMap<ArrayList<Integer>, Integer>();
		mazeBuilder.idToSet = new HashMap<Integer, Set<ArrayList<Integer>>>();
		mazeBuilder.newSet(2, 1);
		mazeBuilder.newSet(3, 1);
		mazeBuilder.mergeSets(2, 1, "left-right");
		mazeBuilder.mergeSets(2, 1, "up-down");
		mazeBuilder.mergeSets(3, 1, "up-down");
		test = new int[4];
		ans = new int[4];
		test[0] = mazeBuilder.cellToId.get(mazeBuilder.getCell(2, 1));
		test[1] = mazeBuilder.cellToId.get(mazeBuilder.getCell(3, 1));
		test[2] = mazeBuilder.cellToId.get(mazeBuilder.getCell(2, 2));
		test[3] = mazeBuilder.cellToId.get(mazeBuilder.getCell(3, 2));
		for (int i = 0; i <= 3; i++) {
			ans[i] = mazeBuilder.cellToId.get(mazeBuilder.getCell(2, 1));
		}
		assertArrayEquals(test, ans);
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
		mazeBuilder.generatePathways();
		
		// check that the size of the HashMap is width x height of the maze
		// (since all the cells should be present and belong to a set)
		assertEquals(mazeBuilder.cellToId.size(), mazeWidth*mazeHeight);
		
		// check that each cell in the maze has an entry in the HashMap
		for (int x = 0; x < mazeWidth; x++) {
			for (int y = 0; y < mazeHeight; y++) {
				assertTrue(mazeBuilder.cellToId.containsKey(mazeBuilder.getCell(x, y)));
			}
		}
		
		// check that each id is the same (since all cells should belong to a
		// single, common set
		Integer id = mazeBuilder.cellToId.get(mazeBuilder.getCell(0, 0));
		for (int x = 0; x < mazeWidth; x++) {
			for (int y = 0; y < mazeHeight; y++) {
				assertEquals(mazeBuilder.cellToId.get(mazeBuilder.getCell(x, y)), id);
			}
		}
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
		mazeBuilder.generatePathways();
		// check that the size of the HashMap is 1 (since all cells should
		// belong to a single, common set)
		assertEquals(mazeBuilder.idToSet.size(), 1);
		
		// check that each cell in the maze belongs in the single set
		Integer id = mazeBuilder.cellToId.get(mazeBuilder.getCell(0, 0));
		Set<ArrayList<Integer>> cells = mazeBuilder.idToSet.get(id);
		for (int x = 0; x < mazeWidth; x++) {
			for (int y = 0; y < mazeHeight; y++) {
				assertTrue(cells.contains(mazeBuilder.getCell(x, y)));
			}
		}
	}
}
	
	