//1.  Make a class that extends MutuxProble
//2. Override CreateProcess methods
//3. Create subclass(es) AbstractProcess
//4. Override toStirng
public class SpinLockProblem extends MutexProblem {
	
	// "Shared" Data fields
	private volatile boolean lock = false;
	
	@Override 
	public AbstractProcess createProcess(int type) {
		return new AbstractProcess(type) {
			@Override 
			protected boolean entrySection() {
				// wait for critical section to be available
				//while(getLock()); // infinite loop
				//lock = true; // lock the door behind me
				//setLock(true);
				while(testAndSetLock());
				// return to allow entry into the critical section 
				return true;
				// 3 ways to traverse entry section 
				// true
				// false
				// waiting
				
			}
			
			@Override 
			protected void exitSection() {
//				lock = false;// when I leave unlock the door
				setLock(false);
			}
		};
		
	}
	
	public String toString() {
		return "spin lock = " + getLock();
	}
	
	public synchronized void setLock(boolean newValue) {
		lock = newValue;
	}
	
	public synchronized boolean getLock() {
		return lock;
	}
	
	public synchronized boolean testAndSetLock() {
		boolean temp = lock;
		lock = true;
		return temp;
	}
	
	public static void main(String[] args) {
		System.out.println();
	}
	
}
