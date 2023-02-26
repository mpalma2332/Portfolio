import java.util.concurrent.Semaphore;

public class SemaphoreProblem extends MutexProblem {

	public static final int NUM_IN_CS = 3;
	// create semaphore with NUM_IN_CS permits
	Semaphore lock = new Semaphore(NUM_IN_CS);
	// like the spin lock create createProcess methods
	@Override
	AbstractProcess createProcess(int type) {
		return new SemaphoreProcess(type);
		// write entry section to give permission to go in to critcal section
		// exit section to go out of the entry section
	}

	@Override
	public String toString() {
		return String.format("Avaiblable permits = %d", lock.availablePermits());
	}
	
	class SemaphoreProcess extends AbstractProcess {
		public SemaphoreProcess(int type) {
			super(type);
		}

		@Override
		protected boolean entrySection() {
			// wait
			// blocks if no permit
			try {
				lock.acquire();// acquire here automatic action 
				// once its done we are allowed to go in
			} catch (InterruptedException e) {
				return false;
			} 
			return true;
		}

		@Override
		protected void exitSection() {
			// signal
			lock.release(); // EXIT the ACQUIRE ^^^
		}
	}

	
	
}
