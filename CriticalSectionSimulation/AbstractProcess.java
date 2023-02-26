import java.awt.*;

public class AbstractProcess implements Runnable{

	protected static final Color[] TYPE_LINE_COLORS = 
		{Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.PINK};
	

	public static int NUM_PROCESSES = 0;
	protected static final int SIZE = 10;
	private static final int ATTEMPT_MULTIPLIER = 32;
	private static final int SLEEP_TIME = 100;
	
	protected static int FIELD_WIDTH;
	protected static int FIELD_HEIGHT;
	
	protected int pid;
	protected int type;
	protected boolean in_cs;
	protected int attempts;
	
	private int x, y, dx, dy;
	private Color color;
	
	private volatile boolean done = false;
	private static boolean pause = false;
	
	public AbstractProcess(int type){
		pid = NUM_PROCESSES++;
		this.type = type;
		color = Color.BLACK;
		in_cs = false;
		attempts = 0;
	}
	
	public int getPID(){
		return pid;
	}
	
	public void run(){
		while(!isDone()){
			if(pause)
				continue;
			
			//if the next step puts the process in the critical section
			if(!in_cs && (inCriticalSection(x + dx))){
				//enter the critical section
				if(entrySection()){
					in_cs = true;
					attempts = 0;
					color = Color.BLACK;
					//run the critical section
					criticalSection();
				}
				else {
					//entry returned false
					//go back the way you came
					dx *= -1;
					dy *= -1;
					attempts++;
					int offset = ATTEMPT_MULTIPLIER * attempts;
					if(offset > 255){
						offset = 255;
					}
					color = new Color(offset, offset, offset);
					
				}
			
			}
			else if(in_cs && (!inCriticalSection(x+dx))){
				//exit the critical section
				exitSection();
				in_cs = false;
				//run the remainder section
				remainderSection();
			}
			updateLocation();
			
			try {
				Thread.sleep(SLEEP_TIME);
			}
			catch(InterruptedException e){
				
			}
		}
		if(in_cs)
			exitSection(); //clean up
	}
	
	public final synchronized void updateLocation(){
		
		if(x + dx + SIZE > FIELD_WIDTH || x + dx < 0){
			dx = -1 * dx;
		}
		if(y + dy + SIZE > FIELD_HEIGHT || y + dy < 0){
			dy = -1 * dy;
		}
		x += dx;
		y += dy;
		
	}
	
	public final void setLocation(int newX, int newY){
		x = newX;
		y = newY;
	}
	
	public final void setDirection(int newDX, int newDY){
		dx = newDX;
		dy = newDY;
	}
	
	
	
	public final int getX(){
		return x;
	}
	
	public final int getY(){
		return y;
	}
	
	public synchronized void draw(Graphics g){
		g.setColor(color);
		g.fillOval(x, y, SIZE, SIZE);
		g.setColor(TYPE_LINE_COLORS[type]);
		g.drawOval(x, y, SIZE, SIZE);
		
	}
	
	/**
	 * Called to request entry into the critical section.
	 * This method should may wait/block until the process is permitted to 
	 * enter its critical section.
	 * @return true if the process is allowed to enter the critical section, 
	 * false if it is denied.
	 */
	protected boolean entrySection(){
		CriticalSectionSim.getInstance().logMessage("Proc_" + pid , "entry section");
		return true;
	}
	
	/**
	 * Called when the process exits the critical section
	 */
	protected void exitSection(){
		CriticalSectionSim.getInstance().logMessage("Proc_" + pid , "exit section");
		
	}
	
	/**
	 * Called once when the process enters the simulated critical section.
	 */
	protected void criticalSection(){
		CriticalSectionSim.getInstance().logMessage("Proc_" + pid , "critical section");
		
	}

	/**
	 * Called once when the process enters the simulated remainder section.
	 * 
	 */
	protected void remainderSection(){
		CriticalSectionSim.getInstance().logMessage("Proc_" + pid , "remainder section");

	}


	public boolean isDone(){
		return done;
	}
	
	public void setDone(){
		done = true;
	}
	
	public static void pause(){
		pause = true;
	}
	
	public static void resume(){ 
		pause = false;
	}
	
	public static boolean isPaused(){
		return pause;
	}
	
	public static void setFieldSize(int w, int h){
		FIELD_WIDTH = w;
		FIELD_HEIGHT = h;
	}
	
	private boolean inCriticalSection(int x){
		return x >= (1.0 - SimPanel.CS_PERCENTAGE)*FIELD_WIDTH;
	}
	
}
