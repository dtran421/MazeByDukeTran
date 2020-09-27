package gui;

import generation.CardinalDirection;
import java.util.Map;

/**
 * @author Duke Tran
 * Class: ReliableRobot
 * <p>
 * Responsibilites: perform move and rotate operations, interact with its Sensors,
 * monitor and manage its energy consumption
 * <p>
 * Collaborators: Controller, RobotDriver (Wall-follower and Wizard)
 */
public class ReliableRobot implements Robot {
	private Controller controller;
	
	private int[] currPos;
	private CardinalDirection currDir;
	
	private float batteryLevel;
	private float distTraveled;
	private boolean stopped;
	//private Map<Direction, boolean> sensorStatus;
	
	public ReliableRobot() {
	}

	/**
	 * Sets the controller of the robot so that it can interact with and move within the maze.
	 * @param controller allows robot to interact with the maze
	 * @throws IllegalArgumentException if controller is null, 
	 * or if controller is not in playing state, 
	 * or if controller does not have a maze
	 */
	@Override
	public void setController(Controller controller) throws IllegalArgumentException {
		// check if controller is null and throw an exception if so
		
		// check if controller is not in playing state or doesn't have a maze
		// and throw an exception if so
		
		// assign controller field to inputted controller
	}

	/**
	 * Provides the current maze position as [x, y].
	 * @return array of the x- and y-coordinates
	 * @throws Exception if position is outside of the maze
	 */
	@Override
	public int[] getCurrentPosition() throws Exception {
		// fetch the current position from the controller
		
		// check if the current position is outside of the maze and throw an exception if so
		
		// return position array
		return null;
	}

	/**
	 * Provides the robot's current direction.
	 * @return cardinal direction that the robot is currently facing
	 */	
	@Override
	public CardinalDirection getCurrentDirection() {
		// return fetched direction from the controller
		return null;
	}

	/**
	 * Provides the current battery level.
	 * If the battery level is 0, then robot stops.
	 * @return current battery level
	 */
	@Override
	public float getBatteryLevel() {
		// return the battery level
		return 0;
	}

	/**
	 * Sets the current battery level.
	 * @param level is the current battery level
	 * @throws IllegalArgumentException if level is negative 
	 */
	@Override
	public void setBatteryLevel(float level) throws IllegalArgumentException {
		// check if level is negative and throw an exception if so
		
		// set the battery level to the inputted level
	}

	/**
	 * Provides the energy consumption for a full 360 degree rotation.
	 * @return energy for a full rotation
	 */
	@Override
	public float getEnergyForFullRotation() {
		// return the energy required for a full rotation
		return 0;
	}

	/**
	 * Provides the energy consumption for moving forward for a distance of 1 step.
	 * @return energy for a single step forward
	 */
	@Override
	public float getEnergyForStepForward() {
		// return the energy required for a single step forward
		return 0;
	}

	/** 
	 * Provides the distance traveled by the robot.
	 * @return the distance traveled measured in single-cell steps forward
	 */
	@Override
	public int getOdometerReading() {
		// return the distance traveled
		return 0;
	}

	/** 
     * Resets the distance traveled to zero.
     */
	@Override
	public void resetOdometer() {
		// set the distance traveled to zero
	}

	/**
	 * Turn robot on the spot for amount of degrees. If robot runs out of energy, it stops.
	 * @param turn is the direction to turn, which is relative to the current direction. 
	 */
	@Override
	public void rotate(Turn turn) {
		// use the controller to turn the robot
		
		// check if the battery level is 0 and stop the robot if so

	}

	/**
	 * Moves robot forward a given number of steps. If the robot runs out of energy at any point, then it stops. 
	 * If the robot hits an obstacle like a wall, it remains at the position in front of the obstacle and stops.
	 * @param distance is the number of cells to move in the robot's current direction 
	 * @throws IllegalArgumentException if distance not positive
	 */
	@Override
	public void move(int distance) throws IllegalArgumentException {
		// check if distance is not positive and throw an exception if so
		
		// create a new variable to keep track of distance moved
		// while the distance moved is less than the inputted distance
			// check if there is an obstacle (wall) directly in front of the robot
			// and stop if so
		
			// move the robot one step forward and update the distance traveled
		
			// check if the energy has been depleted and stop if so
			// (make sure to break out of the loop)

	}

	/**
	 * Makes robot move one step in a forward direction even if there is a wall
	 * in front of it (jumps over the wall). If the robot runs out of energy at any point, then it stops.
	 * If the robot tries to jump over an exterior wall (it would land outside of the maze),
	 * it remains at its current location and direction and stops.
	 */
	@Override
	public void jump() {
		// check if the wall in front is an exterior wall and stop if so
		
		// check if the energy has been depleted and stop if so
		
		// execute the jump and update the distance traveled
	}
	
	/**
	 * Tells whether the current position is at the exit (inside the maze). 
	 * @return if robot is at the exit
	 */
	@Override
	public boolean isAtExit() {
		// fetch the maze from the controller and determine if the current position is at the exit
		// (at least one wallboard is missing and the adjacent cell in that direction is outside of the maze)
		return false;
	}

	/**
	 * Tells if current position is inside a room. 
	 * @return if robot is inside a room
	 */	
	@Override
	public boolean isInsideRoom() {
		// return whether the current position is inside a room from the controller
		return false;
	}

	/**
	 * Tells if the robot has stopped.
	 * @return if the robot has stopped
	 */
	@Override
	public boolean hasStopped() {
		// returns stopped
		return false;
	}

	/**
	 * Provides the distance to an obstacle (wall) in the given direction.
	 * The direction is relative to the robot's current direction.
	 * @param direction specifies the sensor direction
	 * @return number of steps towards obstacle, Integer.MAX_VALUE if we're facing the exit (no wall, goes to eternity)
	 * @throws UnsupportedOperationException if robot has no sensor in this direction
	 * or the sensor exists but is currently not operational (failure or battery depleted)
	 */
	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		// check if the direction is invalid or the sensor is not operational and throw an exception if so
		
		// use the controller to fetch the maze
		
		// call canSeeThroughTheExitIntoEternity to see if the given direction contains the exit
		// and return Integer.MAX_VALUE if so
		
		// calculate the distance from the current position to a wall in the given direction
		
		return 0;
	}

	/**
	 * Tells if a sensor can identify the exit from the current position in the given direction.
	 * The direction is relative to the robot's current direction.
	 * @param direction specifies the sensor direction
	 * @return true if the exit of the maze is visible in a straight line of sight
	 * @throws UnsupportedOperationException if robot has no sensor in this direction
	 * or the sensor exists but is currently not operational (failure or battery depleted)
	 */
	@Override
	public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException {
		// check if the direction is invalid or the sensor is not operational and throw an exception if so
		
		// use the controller to fetch the maze
		
		// keep track of the current cell
		
		// while the current cell doesn't have a wallboard in the given direction
			// check if the next cell is outside of the maze and return true if so
		
			// take a step in the given direction
		
		// return false since a wallboard was encountered
		return false;
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
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
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
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

}
