package line;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.lang.Math;
import java.time.Instant;

public class LineRotate extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 881376820135734223L;
	
	private static Data data;
	private Timer t = new Timer(16, this);
	private int[] MARKED_POINT = new int[2];
	private HashMap<Integer, Integer> COORDS = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> TIMES_MARKED = new HashMap<Integer, Integer>();
	private ArrayList<Integer> X_COORDS = new ArrayList<Integer>();
	private Random r_gen = new Random();
	private int curr_angle = 0;
	
	LineRotate(Data dt, KeyListener listener) {
		data = dt;
		this.addKeyListener(listener);
		this.setFocusable(true);
		long start_time = Instant.now().getEpochSecond();
		for (int row = 0; row < data.total_points; ) {
			int x_coord = 200 + this.r_gen.nextInt(1520);
			boolean valid_point = true;
			if (Instant.now().getEpochSecond() - start_time > 5) {
				data.recommended_points = row - 3;
				data.exception = true;
				return;
			}
			
			if (!this.COORDS.containsKey(x_coord)) {
				int y_coord = 200 + this.r_gen.nextInt(680);
				
				if (data.limitations) {
					for(int x1: this.X_COORDS) {
						for(int x2: this.X_COORDS) {
							if (x1 == x2)
								continue;
							int y1 = this.COORDS.get(x1);
							int y2 = this.COORDS.get(x2);
							double slope1 = (y2 - y1) / (x2 - x1);
							double slope2 = (y2 - y_coord) / (x2 - x_coord);
							double slope3 = (y1 - y_coord) / (x1 - x_coord);
							//check if point is collinear with 2 other points
							//or if point is too close to other point
							if ((slope1 == slope2 && slope2 == slope3) ||
							(y_coord > y1 - 30 && y_coord < y1 + 30) || (y_coord > y2 - 30 && y_coord < y2 + 30)) {
								valid_point = false;
								break;
							}
						}
						if (!valid_point)
							break;
					}
				}
				
				if (valid_point) {
					this.COORDS.put(x_coord, y_coord);
					this.TIMES_MARKED.put(x_coord, 0);
					this.X_COORDS.add(x_coord);
					++row;
				}
			}	
		}
		
		Collections.sort(this.X_COORDS);
		
		int x_coord_ind = this.X_COORDS.size() / 2;
		
		int x_coord = this.X_COORDS.get(x_coord_ind);
		this.MARKED_POINT[0] = x_coord;
		this.MARKED_POINT[1] = this.COORDS.get(x_coord);
		int times_marked = this.TIMES_MARKED.get(x_coord);
		this.TIMES_MARKED.put(x_coord, times_marked + 1);
		
		this.t.start();
		data.started_sim = true;
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		if (data.exception) return;
		
		if (data.simulating) {
		
			super.paintComponent(g);
			g.setColor(Color.black);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.red);
			g.setFont(data.titleFont);
			final Graphics2D g2d = (Graphics2D) g;
			g2d.drawString(data.total_points + " POINTS", 5, 40);
			g.setFont(data.regFont);
			
			for (int x_coord: this.X_COORDS) {
				
				if (x_coord == this.MARKED_POINT[0])
					g.setColor(Color.blue);
				else
					g.setColor(Color.red);
				
				g2d.drawString(String.valueOf(this.TIMES_MARKED.get(x_coord)), x_coord, this.COORDS.get(x_coord) - 5);
				g2d.fillOval(x_coord, this.COORDS.get(x_coord), 10, 10);
			}
			
			if (!data.paused)
				this.curr_angle = (curr_angle == 180) ? 1 : curr_angle + 1;
			
			Line2D line = new Line2D.Double(this.MARKED_POINT[0] + 5, -3000, this.MARKED_POINT[0] + 5, 3000);
			
			AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(this.curr_angle), this.MARKED_POINT[0] + 5, this.MARKED_POINT[1] + 5);
			
			g.setColor(Color.green);
			g2d.draw(at.createTransformedShape(line));
	
			for (int x: this.X_COORDS) {
				if (x == this.MARKED_POINT[0])
					continue;
				int y = this.COORDS.get(x);
				
				int dy = this.MARKED_POINT[1] - y;
				int dx = x - this.MARKED_POINT[0];
				
				int line_angle = 90 - (int)Math.toDegrees(Math.atan2(dy, dx));
				int line_angle2 = 90 - (int)Math.toDegrees(Math.atan2(-dy, -dx));
				
				if ((this.curr_angle == line_angle || this.curr_angle == line_angle2) && !data.paused) {
					this.MARKED_POINT[0] = x;
					this.MARKED_POINT[1] = y;
					int times_marked = this.TIMES_MARKED.get(x);
					this.TIMES_MARKED.put(x, times_marked + 1);
					break;
				}
			}
			
			this.t.setDelay(data.ms_update);
			
			Toolkit.getDefaultToolkit().sync();
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
		this.repaint();
	}
	
	public static class AL extends KeyAdapter {
		
		
		
	}
	

}
