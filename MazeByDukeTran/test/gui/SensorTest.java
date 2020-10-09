package gui;

import org.junit.Before;

import generation.Maze;

public class SensorTest extends ReliableSensor {
	protected ReliableSensor sensor;
	protected Maze maze;
	
	@Before
	public final void setUpParent() {
		// create a maze
		Controller controller = new Controller();
		controller.setDeterministic(true);
		controller.turnOffGraphics();
		controller.switchFromTitleToGenerating(0);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		maze = controller.getMazeConfiguration();
	}
}
