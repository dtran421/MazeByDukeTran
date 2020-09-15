package generation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MazeBuilderEller extends MazeBuilder implements Runnable {
	private Map<int[], Integer> cellToId;
	private Map<Integer, Set<int[]>> idToSet;

	public MazeBuilderEller() {
		super();
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	}
	
	/*
	 * 1. For the first row, put each cell in its own set.
	 * 2. For each row, randomly pick and join adjacent cells that aren't in the same set. 
	 * 	  When joining adjacent cells, merge the two sets.
	 * 3. For each set, randomly create vertical connections to the next row. 
	 * 	  Each remaining set must have at least one vertical connection. 
	 * 	  Any cell in the next row that is connected must belong to the same set as the cell above it.
	 * 4. Any remaining unconnected cells in the next row must have their own sets.
	 * 5. Repeat until the last row is reached.
	 * 6. For the last row, join all adjacent cells that do not share a set.
	 * 	  Don't do any more vertical connections (because there's no row below).
	 */
	@Override
	protected void generatePathways() {
		// instantiate a cell-id HashMap to store each cell and its respective set
		// (default set value can be 0) and a id-set HashMap to store set ids
		// and their respective sets
		cellToId = new HashMap<int[], Integer>();
		idToSet = new HashMap<Integer, Set<int[]>>();
		
		// declare a variable that will be incremented with each new set to serve
		// as the set id
		int setId = 1; 
		
		// iterate through the first row of the floorplan
		for (int x = 0; x < width; x++) {
			// add cell to its own new set and update the HashMaps
			newSet(x, 0, setId);
			// increment the id
			setId++;
		}
		
		// loop over the rows of the floorplan 0 -> height-2
		for (int y = 0; y < height-1; y++) {
			
			// loop over the columns of that row 0 -> width-2
			for (int x = 0; x < width-1; x++) {
				
				// generate a random integer within the range [0, 100) to
				// decide whether to tear down the eastern wall or not 
				// (less than 50 means no, greater than or equal to 50 means yes)
				if (SingleRandom.getRandom().nextIntWithinInterval(0, 100) >= 50) {
					Wallboard wallboard = new Wallboard(x, y, CardinalDirection.East);
					// if wallboard can be torn down (it's not load-bearing or a border)
					if (floorplan.canTearDown(wallboard)) {
						
						// tear down the eastern wall
						floorplan.deleteWallboard(wallboard);
						
						// update HashMaps
						mergeSets(x, y, "left-right");					
					}
				}
			}
				
			// flags for current set and if a vertical connection 
			// has been made yet
			int currSet = 0;
			int setIdx = 0;
			boolean newVerticalConnection = false;
			
			// loop over the columns of the next row
			for (int x = 0; x < width; x++) {
				Wallboard wallboard = new Wallboard(x, y+1, CardinalDirection.North);
				
				// generate a random integer within the range [0, 100) to
				// decide whether to create a vertical connection or not 
				// (less than 50 means no, greater than or equal to 50 means yes) and
				// determine if wallboard can be torn down (it's not load-bearing or a border)						
				if (SingleRandom.getRandom().nextIntWithinInterval(0, 100) < 50 || 
					!floorplan.canTearDown(wallboard)) {
					
					// if the current cell is in a different set from the previous,
					// then update the flags
					int tempSet = cellToId.get(getCell(x, y+1));
					if (tempSet != currSet) {
						currSet = tempSet;
						setIdx++;
						newVerticalConnection = false;
					}
				} else {
					// tear down the northern wall
					floorplan.deleteWallboard(wallboard);
					
					// update HashMaps
					mergeSets(x, y+1, "up-down");
					
					// update flag
					newVerticalConnection = true;
				}
		
				// if this is this is the last cell in the set (the next cell is a
				// different cell) and a connection has not been made, then it must
				// be made with this cell
				if (!newVerticalConnection && setIdx == idToSet.get(currSet).size()) {
					// tear down the northern wall
					floorplan.deleteWallboard(wallboard);
					
					// update HashMaps
					mergeSets(x, y+1, "up-down");
				}
			}
				
			// loop over the columns of the next row again
			for (int x = 0; x < width; x++) {
				// if a cell doesn't have a vertical connection, make a new set for itself
				if (floorplan.hasWall(x, y+1, CardinalDirection.North)) {
					// add cell to its own new set and update the HashMaps
					newSet(x, y+1, setId);
					// increment the id
					setId++;
				}
			}
		}
			
		// iterate through the last row of the floorplan 0 -> width-2
		// and put all of the cells into the same set
		for (int x = 0; x < width-1; x++) {
			// TODO: see how rooms are placed and if we need to check for possibility
			// to tear down
			int[] origCell = getCell(height-1, x);
			int[] newCell = getCell(height-1, x+1);
			
			int origId = cellToId.get(origCell);
			int newId = cellToId.get(newCell);
			if (origId != newId)
				mergeSets(x, height-1, "left-right");
		}
	}

	@Override
	protected int generateRooms() {
		// TODO Auto-generated method stub
		return super.generateRooms();
	}
	
	// private methods
	
	private void newSet(int x, int y, int setId) {
		// make a new set for each cell and add the cell to it
		int[] cell = getCell(x, y);
		Set<int[]> cells = new HashSet<int[]>();
		cells.add(cell);
		
		// bind the cell to the id in the cell-id HashMap and
		// bind the id to the set in the id-set HashMap
		cellToId.put(cell, setId);
		idToSet.put(setId, cells);
	}
	
	private int[] getCell(int x, int y) {
		int[] currCell = new int[2];
		currCell[0] = x; currCell[1] = y;
		return currCell;
	}
	
	private void mergeSets(int x, int y, String dir) {
		int[] origCell; int[] newCell;
		
		if (dir == "left-right") {
			origCell = getCell(x, y);
			newCell = getCell(x+1, y);
		} else {
			origCell = getCell(x, y-1);
			newCell = getCell(x, y);
		}

		// merge the original set with the new set
		int origId = cellToId.get(origCell);
		int newId = cellToId.get(newCell);
		Set<int[]> origCells = idToSet.get(origId);
		Set<int[]> newCells = idToSet.get(newId);
		origCells.addAll(newCells);
		
		// for all of the new cells, update their ids to the new set's id
		for (int[] cell: newCells)
			cellToId.put(cell, origId);
		
		// remove the new cell set
		idToSet.remove(newId);
		
		// replace the old set with the updated set
		idToSet.put(origId, origCells);
	}

}
