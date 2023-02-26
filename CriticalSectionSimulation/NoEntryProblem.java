
public class NoEntryProblem extends MutexProblem {
	public String toString(){
		return "No entry to CS";
	}
	

	AbstractProcess createProcess(int type){
		return new AbstractProcess(type){
			@Override
			protected boolean entrySection(){
				return false;
			}
		};
	}
}
