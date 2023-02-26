import java.util.concurrent.Semaphore;

public class ProducerConsumerProblem extends MutexProblem {

	public static final int MAX_ITEMS = 10;
	// shared data 
	Semaphore empty, full, mutex;
	int items;
	public ProducerConsumerProblem() {
		items = 0;
		empty = new Semaphore(MAX_ITEMS); // to see how many are empty
		full = new Semaphore(MAX_ITEMS);
		mutex = new Semaphore(1); // ensure mutal exclusion
		
		// there are no full items 
		
		try {
			full.acquire(MAX_ITEMS);
		} catch (InterruptedException ie ) {
			ie.printStackTrace();
		}

	}
	@Override
	AbstractProcess createProcess(int type) {
		if (type == 0) {
			return new Producer();
		} // notice how i didn't do an else if becuase i would have to handle other things user inputs 
		return new Consumer();
	}

	@Override
	public String toString() {
		return "Prod/Cons items = " + items;
	}

	class Producer extends AbstractProcess {
		public Producer() {
			super(0);
		}

		@Override
		protected boolean entrySection() {
			try {
				empty.acquire(); // reserve an empty spot
				mutex.acquire(); // reserve the use of the data structure
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}
			return true;
			
			
		}

		@Override
		protected void exitSection() {
			mutex.release(); // done with critical section
			full.release();
		}

		@Override
		protected void criticalSection() {
			CriticalSectionSim.getInstance().logMessage("Producers  " +
					getPID() + " produced an item.");
			items++;
		}
	}

	class Consumer extends AbstractProcess {
		public Consumer() {
			super(1);
		}

		@Override
		protected boolean entrySection() {
			try {
				// consuming
				full.acquire(); // reserve an item to consume
				mutex.acquire(); // get access to critical section
			}
			catch(InterruptedException ie) {
				ie.printStackTrace();
			}
			return true;
		}

		@Override
		protected void exitSection() {
			mutex.release();
			empty.release();
		}

		@Override
		protected void criticalSection() {
			CriticalSectionSim.getInstance().logMessage("Consumer  " +
					getPID() + " consumed an item.");
			items--;
		}
	}

}
