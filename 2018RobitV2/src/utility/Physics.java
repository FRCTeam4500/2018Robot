package utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team4500.robot.subsystems.SwerveDrive;

/**
 * Used for determining the velocity and acceleration of the robot for motion profiling. 
 * 
 * Use the following spreadsheet to generate the graphs:
 * https://docs.google.com/spreadsheets/d/1k02sIJPePy809SHoSbIqir7js27MVeVhOR8FPC8Uod0/edit?usp=sharing
 * 
 * @author Nicolas
 * 
 */
public class Physics {
	
	private long startTime;
	private boolean started = false;
	
	private ScheduledExecutorService exec;
	
	private File f;
	private FileOutputStream fos;
	private BufferedWriter bw;
	
	private SwerveDrive swerve;
	
	public Physics(SwerveDrive swerve) {
		this.swerve = swerve;
	}
	
	/**
	 * First call will start the thread, the second call will stop it.
	 * @throws IOException
	 */
	public void run() throws IOException {
		if (!started) {
			startTime = System.nanoTime();
			
			f = new File("/home/lvuser/pvt.csv");
			f.delete();
			f.createNewFile();
			fos = new FileOutputStream(f);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			exec = Executors.newSingleThreadScheduledExecutor();
			exec.scheduleAtFixedRate(() -> {
				try {
					long elapsedTime = System.nanoTime() - startTime;
					String line = getTimeMeasure(elapsedTime) + "," + getPositionMeasure() + "\n";
					System.out.println(line);
					bw.write(line);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}, 0, 100, TimeUnit.MILLISECONDS);
			
			started = true;
		} else {
			exec.shutdown();
			bw.close();
			started = false;
		}
	}
	
	/**
	 * Obtains the position from a sensor to create the p v. t graph. This needs to be changed based on the robots configuration
	 * @return
	 */
	private double getPositionMeasure() {
		return swerve.getFL().getDrivePosition();
	}
	
	/**
	 * Converts the ellapsed time in nano seconds to another unit. Change based on the unit needed
	 * @param timeInNano the time ellapsed in nano seconds
	 * @return the time converted to the desired unit
	 */
	private long getTimeMeasure(long timeInNano) {
		return TimeUnit.NANOSECONDS.toSeconds(timeInNano);
	}
}
