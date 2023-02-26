
public class If extends Exp{
	
	Exp exp1; // condition
	Exp exp2; // true
	Exp exp3; // optionalFalse
	
	public If(Exp exp1, Exp exp2, Exp exp3) {
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
	}
	
	@Override
	ValEnv eval(Env env) {
		ValEnv ve = exp1.eval(env);
		Object veVal = ve.val; 
		
		if(!(veVal instanceof Boolean) || (veVal instanceof Boolean && (Boolean) veVal == true)) { 
			ValEnv ve2 = exp2.eval(ve.env);
			Object veVal2 = ve2.val; 
			return new ValEnv(veVal2,ve.env);
		}
		else if(exp3 != null) { 
			ValEnv ve3 = exp3.eval(ve.env);
			Object veVal3 = ve3.val; 
			return new ValEnv(veVal3,ve.env);
		}
		
		return ve;
	}
	
	@Override
	void print() {
		System.out.print("(if ");
		exp1.print();
		System.out.print(" ");
		exp2.print();
		System.out.print(" ");
		exp3.print();
		System.out.print(")");
	}

}
