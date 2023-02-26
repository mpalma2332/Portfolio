
public class Eqv extends Exp{

	Exp exp1;
	Exp exp2;

	public Eqv(Exp exp1, Exp exp2) {
		this.exp1 = exp1;
		this.exp2 = exp2;
	}

	
	@Override
	ValEnv eval(Env env) {
		ValEnv ve1 = exp1.eval(env);
		ValEnv ve2 = exp2.eval(ve1.env);
		boolean flag = false;
		
		if(ve1.val == null && ve2.val == null) {
			flag = true;
		}
		else if(ve1.val.equals(ve2.val)) {
			flag = true;
		}	
		return new ValEnv(flag, ve2.env);
	}

	@Override
	void print() {
		System.out.print("(eqv? ");
		exp1.print();
		System.out.print(" ");
		exp2.print();
		System.out.print(")");

	}

}
