
public class Int extends Exp {
	
	int i;
	
	public Int(int i) {
		this.i = i;
	}

	@Override
	ValEnv eval(Env env) {
		return new ValEnv(i, env);
	}

	@Override
	void print() {
		System.out.print(i);

	}

}
