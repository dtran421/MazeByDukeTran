package gui;

import generation.Maze;

/**
 * @author Duke Tran
 * Class: WallFollower
 * <p>
 * Responsibilites: direct the robot towards the maze exit
 * <p>
 * Collaborators: Maze, Robot (ReliableRobot and UnreliableRobot)
 */
public class WallFollower implements RobotDriver {
	protected Robot robot;
	protected Maze maze;

	protected float initialBatteryLevel;
	
	/**
	 * Assigns a robot for the Wizard. 
	 * @param r robot to assign
	 */
	@Override
	public void setRobot(Robot r) {
		// assign robot to the robot field
		robot = r;
		initialBatteryLevel = r.getBatteryLevel();
	}

	/**
	 * Provides the Wizard with the maze.
	 * @param maze represents the maze
	 */
	@Override
	public void setMaze(Maze maze) {
		// assign maze to the maze field	
		this.maze = maze;
	}
	
	@Override
	public boolean drive2Exit() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drive1Step2Exit() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Returns the total energy consumption of the journey (difference between the robot's
	 * initial battery level and its battery level at the exit position). 
	 * @return the total energy consumption of the journey
	 */
	@Override
	public float getEnergyConsumption() {
		// return the difference between the initial battery level and the ending battery level
		return initialBatteryLevel - robot.getBatteryLevel();
	}

	/**
	 * Returns the total length of the journey in number of cells traversed 
	 * (the initial position has path length 0). 
	 * @return the total length of the journey in number of cells traversed
	 */
	@Override
	public int getPathLength() {
		// return distance traveled by robot from the robot's odometer
		return robot.getOdometerReading();
	}

}
