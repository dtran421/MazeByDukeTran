package gui;

public class RepairProcess implements Runnable {
	private UnreliableSensor sensor;
	private int operatingTime;
	private int repairTime;
	
	public RepairProcess(UnreliableSensor sensor, int operatingTime, int repairTime) {
		this.sensor = sensor;
		this.operatingTime = operatingTime;
		this.repairTime = repairTime;
	}
	
	/**
	 * Main method to start the failure and repair process in a thread.
	 */
	@Override
	public void run() {
		try {
			// wait the time between failures
			Thread.sleep(operatingTime);
			// set the sensor to non-operational
			sensor.setOperational(false);
			
			// wait the time necessary for repair
			Thread.sleep(repairTime);
			// set the sensor to operational
			sensor.setOperational(true);
		} catch (InterruptedException e) {
			System.out.println("Terminating repair thread...");
			return;
		}
	}
	
}
