
public class Bool extends Exp {
	
	boolean b;
	
	public Bool(String bStr) {
		this.b = bStr.equals("#t");
	}

	@Override
	ValEnv eval(Env env) {
		return new ValEnv(b,env);
	}

	@Override
	void print() {
		System.out.print(b ? "#t" : "#f");

	}


}
