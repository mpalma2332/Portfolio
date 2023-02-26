import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierProblem extends MutexProblem {

	protected CyclicBarrier barrier;
	private volatile boolean lock = false;

	public CyclicBarrierProblem() {
		barrier = new CyclicBarrier(5);
	}

	@Override 
	public AbstractProcess createProcess(int type) {
		return new AbstractProcess(type) {
			@Override 
			protected boolean entrySection() {
//				while(true) {
					Worker w = new Worker(barrier);
					w.run();
					return true;
//				}
			}

			@Override 
			protected void exitSection() {
				setLock(false);
			}
		};

	}

	public synchronized void setLock(boolean newValue) {
		lock = newValue;
	}

	public synchronized boolean getLock() {
		return lock;
	}

	class Worker implements Runnable {

		CyclicBarrier bar;

		public Worker(CyclicBarrier cb) {
			bar = cb;
		}

		@Override
		public void run() {
			try {
				bar.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}
}
