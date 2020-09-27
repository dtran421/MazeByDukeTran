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

	@Override
	public void setRobot(Robot r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaze(Maze maze) {
		// TODO Auto-generated method stub

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

	@Override
	public float getEnergyConsumption() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return 0;
	}

}
