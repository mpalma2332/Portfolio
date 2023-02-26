
public class isInteger extends Exp{

	Exp e;
	
	public isInteger(Exp e) {
		this.e = e;
	}
	
	@Override
	ValEnv eval(Env env) {
		ValEnv ve = e.eval(env);
		if(ve.val instanceof Integer) {
			return new ValEnv(true,ve.env);
		}
		else {
			return new ValEnv(false,ve.env);
		}
	}

	@Override
	void print() {
		System.out.print("(intger? ");
		e.print();
		System.out.print(")");
		
	}

}
