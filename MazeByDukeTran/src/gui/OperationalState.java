package gui;

import gui.Robot.Direction;
import gui.Robot.Turn;

/**
 * @author Duke Tran
 * Class: OperationalState
 * <p>
 * Responsibilities: represents the driver state in which all sensors are operational 
 * <p>
 * Collaborators: RobotDriver (Wall-Follower)
 */
public class OperationalState implements SensorState {
	private Robot robot;
	
	public OperationalState(Robot robot) {
		this.robot = robot;
	}
	
	/**
	 * Decides what action to take next (move or rotate) and executes it based on sensor information
	 */
	@Override
	public boolean performNextAction() {
		// detect if there's no wall to the left of the robot (distance to obstacle is 0)
		if (robot.distanceToObstacle(Direction.LEFT) != 0) {
			// turn left and take one step forward
			robot.rotate(Turn.LEFT);
			robot.move(1);
			return true;
		}
		// detect if there's no wall in front of the robot
		else if (robot.distanceToObstacle(Direction.FORWARD) != 0) {
			// take one step forward
			robot.move(1);
			return true;
		}
		// if none of the above, then turn right
		else {
			robot.rotate(Turn.RIGHT);
			return false;
		}
	}
}
