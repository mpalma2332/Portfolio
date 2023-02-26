
public class Not extends Exp {

	Exp e;
	
	public Not(Exp e ) {
		this.e = e;
	}
	
	@Override
	ValEnv eval(Env env) {
		ValEnv ve = e.eval(env);
		boolean flag = false;
		if(!(ve.val instanceof Boolean) || (ve.val instanceof Boolean && (Boolean) ve.val == true)) {
			flag = true;
		}
		return new ValEnv(!flag, ve.env);
	}

	@Override
	void print() {
		System.out.print("(not ");
		e.print();
		System.out.print(")");
	}

}
