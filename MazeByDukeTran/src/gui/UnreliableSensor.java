package gui;

import gui.Robot.Direction;

/**
 * @author Duke Tran
 * Class: UnreliableSensor
 * <p>
 * Responsibilities: sense distance to obstacle (wall), obtain energy cost for sensing,
 * determine when a failure occurs and start and stop the repair process
 * <p>
 * Collaborators: Maze, RobotDriver (Wall-follower and Wizard), Robot
 */
public class UnreliableSensor extends ReliableSensor {
	protected final int MEAN_TIME_BETWEEN_FAILURES = 400;
	protected final int MEAN_TIME_TO_REPAIR = 200;
	protected Thread repairProcess;
	
	public UnreliableSensor() {
		super();
	}
	
	public UnreliableSensor(Direction direction) {
		super(direction);
	}

	/**
	 * Starts a concurrent, independent failure and repair process that fails the sensor and repairs it.
	 * @param meanTimeBetweenFailures is the mean time between failures in seconds, must be greater than zero
	 * @param meanTimeToRepair is the mean time to repair in seconds, must be greater than zero
	 * @throws UnsupportedOperationException if method not supported
	 */
	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		try {
			// start the repair process thread
			RepairProcess rp = new RepairProcess(this, meanTimeBetweenFailures, meanTimeToRepair);
			repairProcess = new Thread(rp);
			repairProcess.start();
		} catch (Exception e) {
			System.out.println("Something went wrong!");
			repairProcess = null;
		}
	}

	/**
	 * Stops a failure and repair process and restores the sensor in an operational state.
	 * If called after starting a process, it will stop the process as soon as the sensor is operational.
	 * If called with no running failure and repair process, it will return an UnsupportedOperationException.
	 * @param direction of a given sensor
	 * @throws UnsupportedOperationException if method not supported
	 */
	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		// check that a thread exists and is currently running
		if (repairProcess != null && repairProcess.isAlive()) {
			// terminate the repair process
			try {
				repairProcess.join();
			} catch (InterruptedException e) {
				System.out.println("Thread interrupted! Cleaning up...");
				repairProcess = null;
				return;
			}
			// set the sensor to operational
			setOperational(true);
			// clean up
			repairProcess = null;
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	/**
	 * Returns whether the sensor is currently operational
	 * @return operational status of sensor 
	 */
	public boolean isOperational() {
		return isOperational;
	}
	
	/**
	 * Sets the operational status to the inputed value
	 * @param status used to update the sensor
	 */
	public void setOperational(boolean status) {
		isOperational = status;
	}
}