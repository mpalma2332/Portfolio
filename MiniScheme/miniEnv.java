
public class miniEnv extends Env {

	public miniEnv(Env nextEnv) {
		this.nextEnv = nextEnv;
	}


	public void define(String varName, Object value) {
		if (varNames.contains(varName)) {
			if (nextEnv != null) {
				System.err.print("duplicate identifier: " + varName);
				System.exit(5);
			}
		}
		if(nextEnv == null) {
			try {
				super.assign(varName, value);
//			env.assign(v.i, ve.val);
		}
		catch(RuntimeException e) {
			super.define(varName, value);
//			ve.env.define(v.i, ve.val);
		}
		}
		else { // not contained in the current environment
			varNames.add(varName);
			values.add(value);
		}
	}
}
