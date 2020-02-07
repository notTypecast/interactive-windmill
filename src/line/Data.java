package line;
import java.awt.Font;

public class Data {
	
	public boolean simulating = false;
	public boolean limitations = true;
	public int total_points;
	public boolean paused = false;
	public int ms_update = 16;
	public Font titleFont = new Font("TimesRoman", Font.PLAIN, 40);
	public Font regFont = new Font("TimesRoman", Font.PLAIN, 8);
	public Font textFont = new Font("TimesRoman", Font.PLAIN, 26);
	
	public boolean started_sim = false;
	public boolean exception = false;
	public int recommended_points = 0;

}
