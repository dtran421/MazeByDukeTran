package generation;

public class StubOrder implements Order {
	private Factory factory;
	private int skillLevel;
	private Builder builder;
	private boolean perfect;
	private int seed;
	
	private int percentdone;
	private boolean started;
		
	public StubOrder() {
        factory = new MazeFactory();
        skillLevel = 0; // default size for maze
        builder = Order.Builder.DFS; // default algorithm
        perfect = false; // default: maze can have rooms
        percentdone = 0;
        started = false;
        seed = 13; // default: an arbitrary fixed value
	}
	
	public StubOrder(int inSkillLevel, boolean inPerfect, Builder inBuilder) {
		skillLevel = inSkillLevel;
        perfect = inPerfect;
		factory = new MazeFactory();
        builder = inBuilder; // default algorithm
        percentdone = 0;
        started = false;
        seed = 13; // default: an arbitrary fixed value
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
        //control.switchFromGeneratingToPlaying(mazeConfig);
	}

	@Override
	public void updateProgress(int percentage) {
		if (this.percentdone < percentage && percentage <= 100)
            this.percentdone = percentage;
	}

}
