package utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team4500.robot.commands.Misc_VA;
import org.usfirst.frc.team4500.robot.subsystems.SwerveDrive;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Used for determining the velocity and acceleration of the robot for motion profiling. 
 * 
 * Use the following spreadsheet to generate the graphs:
 * https://docs.google.com/spreadsheets/d/1k02sIJPePy809SHoSbIqir7js27MVeVhOR8FPC8Uod0/edit?usp=sharing
 * 
 * @author Nicolas
 * 
 */
public class Physics2 {
	
	private long startTime;
	private boolean started = false;
	
	private ScheduledExecutorService exec;
	
	private File f;
	private FileOutputStream fos;
	private BufferedWriter bw;
	private int i = 0;
	
	private SwerveDrive swerve;
	
	public Physics2(SwerveDrive swerve) {
		this.swerve = swerve;
	}
	
	/**
	 * First call will start the thread, the second call will stop it.
	 * @throws IOException
	 */
	public void run() throws IOException {
		if (!started) {
			System.out.println("Started Physics.run");
			startTime = System.nanoTime();
			
			f = new File("/home/lvuser/pvt.csv");
			f.delete();
			f.createNewFile();
			fos = new FileOutputStream(f);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write(getLine("time", "voltage", "fl pos", "fr pos", "bl pos", "br pos"));
			exec = Executors.newSingleThreadScheduledExecutor();
			exec.scheduleAtFixedRate(() -> {
				try {
					double voltage = .25*i;
					Command command = new Misc_VA(voltage);
					command.start();
					i++;
					long elapsedTime = getTimeMeasure(System.nanoTime()) - startTime;
					int[] pos = getPositionMeasure();
					
					bw.write(getLine(elapsedTime, voltage, pos[0], pos[1], pos[2], pos[3]));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}, 0, 1, TimeUnit.SECONDS);
			
			started = true;
		} else {
			System.out.println("Stopped Physics.run");
			exec.shutdown();
			bw.close();
			started = false;
		}
	}
	
	private String getLine(Object... data) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			if (i < data.length-2) {
				builder.append(data[i] + ",");
			} else {
				builder.append(data[i]);
			}
		}
		return builder.toString();
	}
	
	/**
	 * Obtains the position from a sensor to create the p v. t graph. This needs to be changed based on the robots configuration
	 * @return
	 */
	private int[] getPositionMeasure() {
		return swerve.getDrivePosition();
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
