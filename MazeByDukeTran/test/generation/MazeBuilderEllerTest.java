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
	private MazeBuilder builder; // setup makes this a MazeBuilder object
	
	private final int INFINITY = Integer.MAX_VALUE; 

	/**
	 * Instantiate a new MazeBuilder object for each test
	 */
	@Before
	public void setUp() {
		MazeBuilder builder = new MazeBuilderEller();
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
		assertNotNull(builder);
		// check that the private HashMaps are instantiated properly
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
		// make a new MazeFactory object and order
				
		// build the maze
		
		// check that the dimensions match the floorplan's
		
		// check that there are no enclosed areas
		
		// check that there is an exit
		
		// check that there is a path to the exit
				
		// repeat test with new saved floorplan that has rooms
		
		// check for the same things as above
		
		// compare this end floorplan with the previously saved one to make sure
		// all load-bearing walls are still standing 
		
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
		
		// check that the returned value is of the correct type of ArrayList<Integer>
		
		// check that the returned cell matches the inputted cell
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
		
		// check that a new set has been added to the HashMap
		
		// check that the set belongs to the requested cell
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
		
		// check for invalid direction input
		
		// check that the sets have been successfully merged
		
		// check that the new set contains elements from both sets
				
	}
	
}
	
	