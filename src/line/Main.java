package line;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import java.util.concurrent.TimeUnit;

public class Main {
	
	private static Data data = new Data();

	public static void main(String[] args) {
		
		KeyListener SIMlistener = new KeyListener() {		
			public void keyPressed(KeyEvent event) {
				int keycode = event.getKeyCode();
				if (keycode == KeyEvent.VK_P)
					data.paused = (data.paused) ? false : true;
				else if (keycode == KeyEvent.VK_RIGHT) {
					if (data.ms_update > 0)
						data.ms_update -= 2;
				}
				else if (keycode == KeyEvent.VK_LEFT)
					data.ms_update += 2;
				else if (keycode == KeyEvent.VK_R)
					data.ms_update = 16;
				else if (keycode == KeyEvent.VK_ESCAPE)
					System.exit(0);
			}
			public void keyReleased(KeyEvent event) {
				
			}
			public void keyTyped(KeyEvent event) {
				
			}
		};
		
		while (true) {
			JFrame frame = new JFrame("P2");
			
			frame.setSize(1200, 1200);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("Line");
			frame.setBackground(Color.black);
			
			Menu m = new Menu(data);
	        frame.add(m);
	        frame.pack();
	        frame.setLocationRelativeTo(null);
	        
	        while (!data.simulating) {
	        	try {
					TimeUnit.SECONDS.sleep(1);
				} 
	        	catch (InterruptedException e) {
					System.exit(0);
				}
	        }
	        frame.remove(m);
	        
			LineRotate l = new LineRotate(data, SIMlistener);
			l.setFocusable(true);
			frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
			frame.add(l);
			frame.pack();
			l.requestFocusInWindow();
			
			while (!data.started_sim) {
				try {
					TimeUnit.SECONDS.sleep(1);
				}
				catch (InterruptedException e) {
					System.exit(0);
				}
				if (data.exception)
					break;					
			}
			
			if (data.exception) {
				frame.dispose();
				data.exception = false;
				data.simulating = false;
				data.total_points = 0;
				continue;
			}
			
			break;
			
		}

	}

}
