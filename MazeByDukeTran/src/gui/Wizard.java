package gui;

import generation.Maze;

/**
 * @author Duke Tran
 * Class: Wizard
 * <p>
 * Responsibilites: direct the robot towards the maze exit
 * <p>
 * Collaborators: Maze, Robot (ReliableRobot), DistanceSensor (ReliableSensor and UnreliableSensor)
 */
public class Wizard implements RobotDriver {
	private Robot robot;
	private Maze maze;

	/**
	 * Assigns a robot for the Wizard. 
	 * @param r robot to assign
	 */
	@Override
	public void setRobot(Robot r) {
		// assign robot to the robot field
	}

	/**
	 * Provides the Wizard with the maze.
	 * @param maze represents the maze
	 */
	@Override
	public void setMaze(Maze maze) {
		// assign maze to the maze field		
	}

	/**
	 * Drives the robot towards the exit.
	 * @return true if Wizard successfully reaches the exit
	 * @throws Exception thrown if robot stopped (lack of energy, hit a wall, or somehow ended outside the maze
	 * without passing the exit)
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		// while the robot hasn't stopped
			
			// try to get to the exit by running drive1Step2Exit
		
			// check if it has reached the exit and return true if so
		
		return false;
	}

	/**
	 * Drives the robot one step towards the exit.
	 * @return true if driver successfully performed one step
	 * @throws Exception thrown if robot stopped (lack of energy, hit a wall, or somehow ended outside the maze
	 * without passing the exit)
	 */
	@Override
	public boolean drive1Step2Exit() throws Exception {
		// get the next neighbor closest to the exit
		
		// rotate robot in that direction 
		
		// attempt to get to the cell either by moving or jumping
		
		// if robot is stopped, then throw an exception
				
		return true;
	}

	/**
	 * Returns the total energy consumption of the journey (difference between the robot's
	 * initial battery level and its battery level at the exit position). 
	 * @return the total energy consumption of the journey
	 */
	@Override
	public float getEnergyConsumption() {
		// return the difference between the initial battery level and the ending battery level
		return 0;
	}

	/**
	 * Returns the total length of the journey in number of cells traversed 
	 * (the initial position has path length 0). 
	 * @return the total length of the journey in number of cells traversed
	 */
	@Override
	public int getPathLength() {
		// return distance traveled by robot from the robot's odometer
		return 0;
	}

}
