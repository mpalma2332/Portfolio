import java.util.concurrent.Semaphore;

public class CigaretteSmokersProblem extends MutexProblem {

	public static final int MAX_ITEMS = 10;
	// shared data 
	Semaphore paper, tobacco, match,agent;

	public CigaretteSmokersProblem() {
		match = new Semaphore(MAX_ITEMS); 
		paper = new Semaphore(MAX_ITEMS);
		tobacco = new Semaphore(MAX_ITEMS); 
		agent = new Semaphore(1);
		
		try {
		//	match.acquire(MAX_ITEMS);
			paper.acquire(MAX_ITEMS);
			tobacco.acquire(MAX_ITEMS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	AbstractProcess createProcess(int type) {
		if (type == 0) { // one finger click 
			return new smokerWithTobacco();
		} else if (type == 2) { // 2 finger click
			return new smokerWithPaper();
		} else if (type == 3) { // ctrl + 1 finger
			return new smokerWithMatch();
		}
		return new Agent(); // anything else is an agent
	}



	class smokerWithTobacco extends AbstractProcess {
		public smokerWithTobacco() {
			super(0);
		}

		@Override
		protected boolean entrySection() {
			try {
				match.acquire(); // wait for a match
				paper.acquire();  // wait for paper
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}

		@Override
		protected void exitSection() {
			agent.release();
		}

	}

	class smokerWithPaper extends AbstractProcess {
		public smokerWithPaper() {
			super(2);
		}

		@Override
		protected boolean entrySection() {
			try {
				match.acquire(); // wait for a match
				tobacco.acquire(); // wait for tobacco
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}

		@Override
		protected void exitSection() {
			agent.release(); // signal the agent to leave
		}
	}

	class smokerWithMatch extends AbstractProcess {
		public smokerWithMatch() {
			super(3);
		}

		@Override
		protected boolean entrySection() {
			try {
				paper.acquire();
				tobacco.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}

		@Override
		protected void exitSection() {
			agent.release();
		}

	}


	class Agent extends AbstractProcess {
		public Agent() {
			super(5);
		}

		@Override
		protected boolean entrySection() {
			try {
				agent.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}

		@Override
		protected void exitSection() {
			int select = (int) (Math.random() * 3);
			if(select == 0) {
				match.release();
				paper.release();
			}
			else if (select == 1) {
				match.release();
				tobacco.release();
			}
			else if(select == 2) {
				paper.release();
				tobacco.release();
			}
		}
	}
}
