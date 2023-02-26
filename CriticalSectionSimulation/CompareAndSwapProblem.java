
public class CompareAndSwapProblem extends MutexProblem {

	class Compare extends AbstractProcess {
		public Compare() {
			super(0);
		}
	}
	
	private volatile int lock = 0;

	
	@Override 
	public AbstractProcess createProcess(int type) {
		return new AbstractProcess(type) {
			@Override 
			protected boolean entrySection() {
				
				while(compare_and_swap(0, 1) != 0);
				
				return true;
			}
			
			@Override 
			protected void exitSection() {
				setLock(0);
			}
		};
	}
	
	public synchronized void setLock(int newValue) {
		lock = newValue;
	}
	
	public synchronized int getLock() {
		return lock;
	}

	public synchronized int compare_and_swap(int expected, int new_value) {
		int temp = lock;
		if(lock == expected) {
			lock = new_value;
		}
		return temp;
	}
	
}
