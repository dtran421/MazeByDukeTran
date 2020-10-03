package gui;

import generation.CardinalDirection;
import generation.Maze;
import gui.Robot.Turn;

/**
 * @author Duke Tran
 * Class: Wizard
 * <p>
 * Responsibilites: direct the robot towards the maze exit
 * <p>
 * Collaborators: Maze, Robot (ReliableRobot)
 */
public class Wizard implements RobotDriver {
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
	 * Drives the robot towards the exit.
	 * @return true if Wizard successfully reaches the exit
	 * @throws Exception thrown if robot stopped (lack of energy, hit a wall, or somehow ended outside the maze
	 * without passing the exit)
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		// while the robot hasn't stopped
		while (!robot.hasStopped()) {
			// try to get to the exit by running drive1Step2Exit
			try {
				drive1Step2Exit();
			} catch (Exception e) {
				throw new Exception();
			}
			// check if it has reached the exit and return true if so
			if (robot.isAtExit()) return true;
		}
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
		int[] currPos = robot.getCurrentPosition();
		int[] neighbor = maze.getNeighborCloserToExit(currPos[0], currPos[1]);
		
		// rotate robot in that direction
		CardinalDirection currDir = robot.getCurrentDirection();
		int[] changeDir = {neighbor[0]-currPos[0], neighbor[1]-currPos[1]};
		switch (currDir) {
			case North:
				if (changeDir[0] == -1) robot.rotate(Turn.RIGHT);
				else if (changeDir[0] == 1) robot.rotate(Turn.LEFT);
				else if (changeDir[1] == 1) robot.rotate(Turn.AROUND);
				break;
			case East:
				if (changeDir[0] == -1) robot.rotate(Turn.AROUND);
				else if (changeDir[1] == -1) robot.rotate(Turn.RIGHT);
				else if (changeDir[1] == 1) robot.rotate(Turn.LEFT);
				break;
			case South:
				if (changeDir[0] == -1) robot.rotate(Turn.LEFT);
				else if (changeDir[0] == 1) robot.rotate(Turn.RIGHT);
				else if (changeDir[1] == -1) robot.rotate(Turn.AROUND);
				break;
			case West:
				if (changeDir[0] == 1) robot.rotate(Turn.AROUND);
				else if (changeDir[1] == -1) robot.rotate(Turn.LEFT);
				else if (changeDir[1] == 1) robot.rotate(Turn.RIGHT);
				break;
		}
		
		// move to the cell by taking a step or jumping
		if (maze.hasWall(currPos[0], currPos[1], currDir)) robot.jump();
		else robot.move(1);	
		
		// if robot is stopped, then throw an exception
		if (robot.hasStopped()) throw new Exception();
				
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
