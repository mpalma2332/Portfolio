import java.util.concurrent.Semaphore;

public class ReaderWriterProblem extends MutexProblem {

	// and contains shared items such as semaphores
	Semaphore rw_mutex, mutex;
	int read_count;
	
	public ReaderWriterProblem() {
		rw_mutex = new Semaphore(1);
		mutex = new Semaphore(1);
		read_count = 0;
	}
	@Override
	AbstractProcess createProcess(int type) {
		if (type == 0) {
			return new Reader();
		}  
		return new Writer();
	}
	
	public class Reader extends AbstractProcess {
		
		public Reader() {
			super(0);
		}

		@Override
		protected boolean entrySection() {
			try {
				mutex.acquire();
				read_count++;
				if(read_count == 1) {
					rw_mutex.acquire();
				}
				mutex.release();
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}
			return true;
		}

		@Override
		protected void exitSection() {
			try {
				mutex.acquire();
				read_count--;
				if(read_count == 0) {
					rw_mutex.release();
				}
				mutex.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public class Writer extends AbstractProcess {
		public Writer() {
			super(1);
		}

		@Override
		protected boolean entrySection() {
			try {
				rw_mutex.acquire();
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}
			return true;
		}

		@Override
		protected void exitSection() {
			rw_mutex.release();
		}
	}
}
