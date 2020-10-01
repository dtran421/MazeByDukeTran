package generation;

public class StubOrder implements Order {
	private int skillLevel;
	private Builder builder;
	private boolean perfect;
	private int seed;
	private Maze maze;
	
	private int percentdone;
		
	public StubOrder() {
        skillLevel = 0; // default size for maze
        builder = Order.Builder.DFS; // default algorithm
        perfect = false; // default: maze can have rooms
        percentdone = 0;
        seed = 13; // default: an arbitrary fixed value
	}
	
	public StubOrder(int skillLevel, boolean perfect, Builder builder) {
		this.skillLevel = skillLevel;
        this.perfect = perfect;
        this.builder = builder;
        percentdone = 0;
        seed = 13; // default: an arbitrary fixed value
	}
	
	public Maze getMaze() {
		return maze;
	}

	@Override
	public int getSkillLevel() {
		return skillLevel;
	}

	@Override
	public Builder getBuilder() {
		return builder;
	}

	@Override
	public boolean isPerfect() {
		return perfect;
	}

	@Override
	public int getSeed() {
		return seed;
	}

	@Override
	public void deliver(Maze mazeConfig) {
        if (Floorplan.deepdebugWall)
        {   // for debugging: dump the sequence of all deleted walls to a log file
            // This reveals how the maze was generated
            mazeConfig.getFloorplan().saveLogFile(Floorplan.deepedebugWallFileName);
        }
        maze = mazeConfig;
	}

	@Override
	public void updateProgress(int percentage) {
		if (this.percentdone < percentage && percentage <= 100)
            this.percentdone = percentage;
	}

}
