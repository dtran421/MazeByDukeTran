package gui;

import generation.CardinalDirection;
import generation.Maze;
import gui.Robot.Direction;

/**
 * @author Duke Tran
 * Class: ReliableSensor
 * <p>
 * Responsibilites: sense distance to obstacle (wall), obtain energy cost for sensing,
 * determine when a failure occurs and start and stop the repair process
 * <p>
 * Collaborators: Maze, RobotDriver (Wall-follower and Wizard), Robot
 */
public class ReliableSensor implements DistanceSensor {
	private Maze maze;
	private Direction mountedDirection;
	
	private boolean isOperational;
	
	public ReliableSensor(Maze maze, Direction direction) {
		setMaze(maze);
		setSensorDirection(direction);
		isOperational = true;
	}

	/**
	 * Tells the distance to an obstacle (wall) that the sensor
	 * measures.
	 * 
	 * This method requires that the sensor has been given a reference
	 * to the current maze and a mountedDirection.
	 * 
	 * @param currentPosition is the current location as (x,y) coordinates
	 * @param currentDirection specifies the direction of the robot
	 * @param powersupply is an array of length 1 containing the current battery level
	 * of the robot
	 * @return number of steps towards obstacle or Integer.MAX_VALUE if there's no obstacle 
	 * (the exit is visible).
	 * @throws IllegalArgumentException if any parameter is null
	 * or if currentPosition is outside of the maze
	 * @throws IndexOutOfBoundsException if the battery level is less than 0
	 * @throws Exception with message 
	 * SensorFailure if the sensor is currently not operational
	 * PowerFailure if the power supply is insufficient for the operation
	 */
	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
			// check if any of the parameters are null or if currentPosition is outside of the maze
			// and throw an exception if so
		
			// check if the powersupply is less than 0 and throw an exception if so
		
			// check if sensor is not operational or the powersupply is insufficient
			// and throw the appropriate exception with message if so
				
		
			// use the maze to fetch the dimensions
		
			// keep track of the distance with a counter
			
			// while there isn't a wallboard in the given direction
				// check if the next cell is outside of the maze (meaning the current cell is located at the exit) and
				// return Integer.MAX_VALUE if so
				// else sense the next cell and update the distance counter

		return 0;
	}

	/**
	 * Assigns the maze to the sensor.
	 * @param maze the maze for this game
	 * @throws IllegalArgumentException if parameter is null
	 * or if it does not contain a floor plan
	 */
	@Override
	public void setMaze(Maze maze) {
		// check if the maze or its floor plan is null and throw an exception if so
		
		// assign the maze parameter to the maze field of the sensor
	}

	/**
	 * Assigns the relative direction at which the sensor is mounted on the robot.
	 * @param mountedDirection is the sensor's relative direction
	 * @throws IllegalArgumentException if parameter is null
	 */
	@Override
	public void setSensorDirection(Direction mountedDirection) {
		// check if mountedDirection is null and throw an exception if so
		
		// assign the mountedDirection parameter to the mountedDirection field of the sensor
	}

	/**
	 * Returns the amount of energy the sensor uses for calculating the distance to an obstacle exactly once.
	 * This amount is a fixed constant for the sensor.
	 * @return the amount of energy used for using the sensor once
	 */
	@Override
	public float getEnergyConsumptionForSensing() {
		// return the constant amount of energy required to use the sensor once
		return 0;
	}

	/**
	 * Starts a concurrent, independent failure and repair process that fails the sensor and repairs it.
	 * For P3, this method won't be implemented and will just throw an exception.
	 * @param direction of a given sensor
	 * @param meanTimeBetweenFailures is the mean time between failures in seconds, must be greater than zero
	 * @param meanTimeToRepair is the mean time to repair in seconds, must be greater than zero
	 * @throws UnsupportedOperationException if method not supported
	 */
	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Stops a failure and repair process and restores the sensor in an operational state.
	 * If called after starting a process, it will stop the process as soon as the sensor is operational.
	 * If called with no running failure and repair process, it will return an UnsupportedOperationException.
	 * For P3, this method won't be implemented and will just throw an exception.
	 * @param direction of a given sensor
	 * @throws UnsupportedOperationException if method not supported
	 */
	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

}
