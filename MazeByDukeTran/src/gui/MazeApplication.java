/**
 * 
 */
package gui;

import generation.Order;

import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFrame;


/**
 * This class is a wrapper class to startup the Maze game as a Java application
 * 
 * This code is refactored code from Maze.java by Paul Falstad, www.falstad.com, Copyright (C) 1998, all rights reserved
 * Paul Falstad granted permission to modify and use code for teaching purposes.
 * Refactored by Peter Kemper
 * 
 * TODO: use logger for output instead of Sys.out
 */
public class MazeApplication extends JFrame {

	// not used, just to make the compiler, static code checker happy
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public MazeApplication() {
		init(null);
	}

	/**
	 * Constructor that loads a maze from a given file or uses a particular method to generate a maze
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
     * or a filename that stores an already generated maze that is then loaded, or can be null
	 */
	public MazeApplication(String[] parameters) {
		init(parameters);
	}

	/**
	 * Instantiates a controller with settings according to the given parameter.
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
	 * or a filename that contains a generated maze that is then loaded,
	 * or can be null
	 * @return the newly instantiated and configured controller
	 */
	 Controller createController(String[] parameters) {
	    // need to instantiate a controller to return as a result in any case
	    Controller result = new Controller() ;
	    // can decide if user repeatedly plays the same mazes or 
	    // if mazes are different each and every time
	    // set to true for testing purposes
	    // set to false for playing the game
	    result.setDeterministic(true);
	    String msg = ""; // message for feedback
	    boolean file = false;
	    RobotDriver driver = null; Robot robot = null;
	    if (parameters == null) {
	    	System.out.println("MazeApplication: building maze using randomized algorithm in manual mode");
	    	return result;
	    }
	    if (parameters.length % 2 != 0) {
	    	System.err.println("Usage: MazeByDukeTran [-f file] [-g algorithm] [-d driver] [-r flrb]");
	    	System.exit(0);
	    }
	    int i = 0;
	    while (i < parameters.length && parameters[i].startsWith("-")) {
	    	String arg = parameters[i++];
	    	if (arg == "-f") {
	    		String fileName = parameters[i++];
	    		File f = new File(fileName) ;
		        if (f.exists() && f.canRead()) {
		            msg = "MazeApplication: loading maze from file: " + fileName + "\n";
		            result.setFileName(fileName);
		    		file = true;
		        }
		        else {
		        	System.out.println("" + f.exists() + " " + f);
		            // None of the predefined strings and not a filename either: 
		            msg = "MazeApplication: unknown parameter value: " + fileName + " ignored, operating in default mode.";
		        }
	    	}
	    	switch (arg) {
	    		case "-g":
	    			if (file)
    					msg += "Building maze from file, ignoring maze generation algorithm parameter\n";
	    			else {
	    				String algo = parameters[i++]; 
	    			    // Case 1: Prim
	    			    if ("Prim".equalsIgnoreCase(algo)) {
	    			        msg = "MazeApplication: generating random maze with Prim's algorithm.\n";
	    			        result.setBuilder(Order.Builder.Prim);
	    			    }
	    			    // Case 2 a and b: Eller, Kruskal or some other generation algorithm
	    			    else if ("Kruskal".equalsIgnoreCase(algo)) {
	    			        throw new RuntimeException("Don't know anybody named Kruskal ...");
	    			    }
	    			    else if ("Eller".equalsIgnoreCase(algo)) {
	    			    	msg = "MazeApplication: generating random maze with Eller's algorithm.\n";
	    			        result.setBuilder(Order.Builder.Eller);
	    			    }
	    			    // Case 3: no input
	    			    else {
	    			        msg = "MazeApplication: maze will be generated with a randomized algorithm.\n"; 
	    			    }
	    			}
	    			break;
	    		case "-d":
	    			String robotDriver = parameters[i++];
	    			// Case 1: Wizard
	    			if ("Wizard".equalsIgnoreCase(robotDriver)) {
	    				msg += "Using Wizard driver to solve the maze.\n";
	    				driver = new Wizard();
	    			}
	    			// Case 2: WallFollower
	    			else if ("WallFollower".equalsIgnoreCase(robotDriver)) {
	    				msg += "Using WallFollower driver to solve the maze.\n";
	    				driver = new WallFollower();
	    			}
	    			// Case 3: Manual
	    			else if ("Manual".equalsIgnoreCase(robotDriver)) {
	    				msg += "Using Manual Mode, you get to solve the maze.\n";
	    				driver = new WallFollower();
	    			}
	    			// Case 4: no input
	    			else {
	    				msg += "No driver selected, you have to solve the maze!\n";
	    			}
	    			break;
	    		case "-r":
	    			String sensors = parameters[i++];
	    			int[] operationalSensors = new int[4];
	    			boolean reliable = true;
	    			int unreliableSensors = 0;
	    			for (int j = 0; j < sensors.length(); j++) {
	    				int check = Integer.parseInt(sensors.substring(j, j+1));
	    				if (check == 0) {
	    					reliable = false;
	    					unreliableSensors++;
	    				}
	    				operationalSensors[j] = check; 
	    			}
	    			// Case 1: ReliableRobot
	    			if (reliable) {
	    				msg += "Using a ReliableRobot with all reliable sensors.\n";
	    				robot = new ReliableRobot();
	    			}
	    			// Case 2: UnreliableRobot
	    			else if (unreliableSensors > 0) {
	    				msg += "Using an UnreliableRobot with " + unreliableSensors + " unreliable sensor(s).\n";
	    				robot = new UnreliableRobot(operationalSensors[0], operationalSensors[1], 
	    	    				operationalSensors[2], operationalSensors[3]);
	    			}
	    			// Case 3: no input
	    			else {
	    				msg += "No robot specified, using a ReliableRobot by default.\n";
	    				robot = new ReliableRobot();
	    			}
	    			break;
	    	}
	    }
	    //System.out.println(i+" "+(parameters.length));
	    if (i != parameters.length) {
	    	System.err.println("Usage: MazeByDukeTran [-f file] [-g algorithm] [-d driver] [-r flrb]");
	    	System.exit(0);
	    }
	    else
		    if (driver != null) {
		    	if (robot != null) driver.setRobot(robot);
		    	else {
		    		robot = new ReliableRobot();
		    		driver.setRobot(robot);
		    	}
		    	result.setRobotAndDriver(robot, driver);
		    }
	    // controller instantiated and attributes set according to given input parameter
	    // output message and return controller
	    System.out.println(msg);
	    return result;
	}

	/**
	 * Initializes some internals and puts the game on display.
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
     * or a filename that contains a generated maze that is then loaded, or can be null
	 */
	private void init(String[] parameters) {
	    // instantiate a game controller and add it to the JFrame
	    Controller controller = createController(parameters);
		add(controller.getPanel()) ;
		// instantiate a key listener that feeds keyboard input into the controller
		// and add it to the JFrame
		KeyListener kl = new SimpleKeyListener(this, controller) ;
		addKeyListener(kl) ;
		// set the frame to a fixed size for its width and height and put it on display
		setSize(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT+22) ;
		setVisible(true) ;
		// focus should be on the JFrame of the MazeApplication and not on the maze panel
		// such that the SimpleKeyListener kl is used
		setFocusable(true) ;
		// start the game, hand over control to the game controller
		controller.start();
	}
	
	/**
	 * Main method to launch Maze game as a java application.
	 * The application can be operated in three ways. 
	 * 1) The intended normal operation is to provide no parameters
	 * and the maze will be generated by a randomized DFS algorithm (default). 
	 * 2) If a filename is given that contains a maze stored in xml format. 
	 * The maze will be loaded from that file. 
	 * This option is useful during development to test with a particular maze.
	 * 3) A predefined constant string is given to select a maze
	 * generation algorithm, currently supported is "Prim".
	 * @param args is optional, first string can be a fixed constant like Prim or
	 * the name of a file that stores a maze in XML format
	 */
	public static void main(String[] args) {
	    JFrame app ; 
		switch (args.length) {
			case 0: 
				app = new MazeApplication();
				break;
			default: 
				app = new MazeApplication(args);
				break;
		}
		app.repaint() ;
	}

}
