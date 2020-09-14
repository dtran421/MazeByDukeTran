package generation;

public class MazeBuilderEller extends MazeBuilder implements Runnable {

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
		// instantiate a cell-set HashMap to store each cell and its respective set
		// (default set value can be 0) and a identifier-set HashMap to store set identifiers
		// and their respective sets
		
		// declare a variable that will be incremented with each new set to serve
		// as the set identifier
		
		// iterate through the first row of the floorplan and make a new set
		// for each cell and put it into the HashMaps
		
		// loop over the rows of the matrix 1 -> n-2
			
			// loop over the columns of that row 0 -> n-2
		
				// generate a random number within the range [0, 1) to
				// decide whether to tear down the eastern wall or not 
				// (less than 0.5 means no, greater than or equal to 0.5 means yes)
			
				// if yes, then tear down the eastern wall and put the current cell
				// and its eastern neighbor into the same set, updating the HashMaps
		
			// loop over the columns of the next row 0 -> n-1
		
				// flags for current set and if a vertical connection 
				// has been made yet
			
				// generate a random number within the range [0, 1) to
				// decide whether to create a vertical connection or not 
				// (less than 0.5 means no, greater than or equal to 0.5 means yes)
		
				// if yes, then tear down the northern wall and put the current cell
				// into the same set as its northern neighbor, updating the HashMaps
				// and the flags
		
				// if this is this is the last cell in the set (the next cell is a
				// different cell) and a connection has not been made, then it must
				// be made with this cell
		
			// loop over the columns of the next row again
		
				// if a cell doesn't have a vertical connection, make a new set for itself
		
		// iterate through the last row of the floorplan and put all of the cells
		// into the same set
	}

	@Override
	protected int generateRooms() {
		// TODO Auto-generated method stub
		return super.generateRooms();
	}

}
