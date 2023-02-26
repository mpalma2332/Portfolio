import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SimPanel extends JPanel{
	private static int TIMER_DELAY = 20;
	public static final double CS_PERCENTAGE = 0.4;
	public static final int SPEED = 10;
	
	
	private ArrayList<AbstractProcess> processes;
	private Timer clock;
	//private BufferedImage image;
	
	public SimPanel(ArrayList<AbstractProcess> procs){
		processes = procs;
		
		clock = new Timer(TIMER_DELAY, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				repaint();
			}
		});
		clock.start();
		
		addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent e){
				//resize critical section
				if(e.getID() == ComponentEvent.COMPONENT_RESIZED){
					AbstractProcess.setFieldSize(e.getComponent().getWidth(),
							e.getComponent().getHeight());
					//image = new BufferedImage(e.getComponent().getWidth(), 
					//		e.getComponent().getHeight(), BufferedImage.TYPE_INT_RGB);
				}
			}
		});
		
		addMouseListener(new MouseAdapter(){
			MouseEvent e1;
			@Override
			public void mousePressed(MouseEvent e){
				e1 = e;
			}
			
			@Override
			public void mouseReleased(MouseEvent e){
				//Don't create a process in the critical section.
				int dx = SPEED;
				int dy = 0;
				if(e1 != null){
					dx = e.getX() - e1.getX();
					dy = e.getY() - e1.getY();
				}
				
				if(dx == 0)
					dx = SPEED;
				
				int type = 0;
				if(e.getButton() == MouseEvent.BUTTON2){
					type = 1;
				}
				else if(e.getButton() == MouseEvent.BUTTON3){
					type = 2;
				}
				if(e.isControlDown())
					type += 3;
				CriticalSectionSim.getInstance().logMessage("Create Process type: " + type);
				//CriticalSectionSim.getInstance().logMessage(
				//		String.format("(%d, %d) : (%d, %d)", e.getX(), e.getY(), dx, dy));
				int x = e.getX();
				if(x > (int)((1-CS_PERCENTAGE)*getWidth())){
					x = 0;
				}
				CriticalSectionSim.getInstance().addNewProcess(type, 
						x, e.getY(), dx, dy);
				e1 = null;
			}
		});
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics gIm = g;//image.getGraphics();
		
		//draw critical section
		//gIm.setColor(Color.WHITE);
		//gIm.fillRect(0, 0, getWidth(), getHeight());
		
		gIm.setColor(Color.LIGHT_GRAY);
		gIm.fillRect((int)((1-CS_PERCENTAGE)*getWidth()), 0, 
				getWidth(), getHeight());
		gIm.setColor(Color.RED);
		gIm.drawString("Critical Section", 
				(int)((1-CS_PERCENTAGE)*getWidth())+10, getHeight()-10);
		gIm.drawString(CriticalSectionSim.getInstance().getProblemText(), 
				10, 10);
		
		
		//g.drawOval(0, 0, getWidth(), getHeight());
		for(AbstractProcess p: processes){
			p.draw(gIm);
		}
		
		//g.drawImage(image, 0, 0, this);
	}
	
}
