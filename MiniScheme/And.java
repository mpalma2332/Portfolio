import java.util.ArrayList;

public class And extends Exp {


	ArrayList<Exp> e = new ArrayList<>();
	
	@Override
	ValEnv eval(Env env) {
		if(e.isEmpty()) {
			return new ValEnv(true,env);
		}
		boolean flag = false;
		ValEnv ve = new ValEnv(null, env);
		for(Exp exp : e) {
			ve = exp.eval(env);
			Object val = ve.val;
			if(val instanceof Boolean && (Boolean) val == false) {
				return new ValEnv(flag, env);
			}
		}
		return ve;
	}

	@Override
	void print() {
		System.out.print("(and ");
		for(Exp exp : e) {
			exp.print();
			System.out.print(" ");
		}
		System.out.print(")");
	}

}
