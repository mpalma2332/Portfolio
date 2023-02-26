
public class Identifier extends Var {

	String i;
	
	public Identifier(String i) {
		this.i = i;
	}

	@Override
	ValEnv eval(Env env) {
		return new ValEnv(env.lookup(i), env);
	}

	@Override
	void print() {
		System.out.print(i);

	}

}
