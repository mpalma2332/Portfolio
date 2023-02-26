
public class isBoolean extends Exp{
	
	Exp e;
	
	public isBoolean(Exp e) {
		this.e = e;
	}

	@Override
	ValEnv eval(Env env) {
		ValEnv ve = e.eval(env);
		if(ve.val instanceof Boolean) {
			return new ValEnv(true, ve.env);
		}
		return new ValEnv(false, ve.env);
	}

	@Override
	void print() {
		System.out.print("(boolean? ");
		e.print();
		System.out.print(")");
	}

}
