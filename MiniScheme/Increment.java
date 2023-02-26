
public class Increment extends Exp{

	Identifier v;
	
	public Increment(Identifier v) {
		this.v = v;
	}
	
	
	@Override
	ValEnv eval(Env env) {
		ValEnv ve = v.eval(env);
		int val = (int) ve.val;
		return new ValEnv(val++, env);
	}

	@Override
	void print() {
		// TODO Auto-generated method stub
		
	}

}
