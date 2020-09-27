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
 * Collaborators: Maze, RobotDriver (Wall-follower and Wizard)
 */
public class ReliableSensor implements DistanceSensor {

	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaze(Maze maze) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSensorDirection(Direction mountedDirection) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getEnergyConsumptionForSensing() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

}
