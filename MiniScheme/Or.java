import java.util.ArrayList;

public class Or extends Exp{

	ArrayList<Exp> ors = new ArrayList<>();
	
	@Override
	ValEnv eval(Env env) {
		boolean flag = false;
		for(Exp exp : ors) {
			ValEnv ve = exp.eval(env);
			Object val = ve.val;
			if(!(val instanceof Boolean) || (val instanceof Boolean && (Boolean) val == true)) {
				return ve;
			}
			else if((Boolean)val != false) {
				return ve;
			}
		}
		return new ValEnv(flag,env);
	}

	@Override
	void print() {
		System.out.print("(or ");
		for(Exp exp : ors) {
			exp.print();
			System.out.print(" ");
		}
		System.out.print(")");
		
	}

}
