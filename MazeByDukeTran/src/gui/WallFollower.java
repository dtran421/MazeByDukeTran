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
	
	/**
	 * Drives the robot towards the exit using the left-wall-follower algorithm.
	 * @return true if WallFollower successfully reaches the exit
	 * @throws Exception thrown if robot stopped (lack of energy, hit a wall, or somehow ended outside the maze
	 * without passing the exit)
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		// TODO: think about checking if you can see the exit after each rotation
		// while the robot hasn't stopped
			// try to get to the exit by running drive1Step2Exit using the wall-follower algorithm
			// check if it has reached the exit and return true if so
				// cross the exit to win the game
		return false;
	}

	/**
	 * Drives the robot one step towards the exit. Makes decision according to the wall-follower algorithm.
	 * @return true if driver successfully performed one step
	 * @throws Exception thrown if robot stopped (lack of energy, hit a wall, or somehow ended outside the maze
	 * without passing the exit)
	 */
	@Override
	public boolean drive1Step2Exit() throws Exception {
		// keep track of whether the robot moved one step
		boolean moved = false;
		// detect if there's a wall to the left of the robot (distance to obstacle is 0)
			// if not, then turn left and take one step forward
		// detect if there's a wall in front of the robot
			// if not, then take one step forward
		// if none of the above, then turn right
		
		// if robot is stopped, then throw an exception
		return moved;
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
