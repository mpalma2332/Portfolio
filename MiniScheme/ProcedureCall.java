import java.util.ArrayList;

public class ProcedureCall extends Exp {

	ArrayList<Exp> exps = new ArrayList<>();

	@Override
	ValEnv eval(Env env) {
//		System.out.println("Im here");
		Exp operator = exps.get(0);
		ValEnv evalOp = operator.eval(env);
		if(!(evalOp.val instanceof Closure)) {
			System.err.print("procedure application: expected procedure, given non-procedure");
			System.exit(7);
		}
		Closure c = (Closure) evalOp.val;
		
		ValEnv evalOperand = new ValEnv(null, env);
		ArrayList<Object> vals = new ArrayList<>();
		
		for(int i = 1; i < exps.size();i++) {
			Exp evalOper = exps.get(i);
			evalOperand = evalOper.eval(evalOperand.env);
			vals.add(evalOperand.val);
		}
		
		if(!(exps.size() - 1 == c.lamda.var.size())) {
			System.err.print("procedure expects " + c.lamda.var.size() + " arguments, given " + (exps.size() - 1));
			System.exit(8);
		}
//		Closure cl = new Closure(env,this);
		Env localEnv = new Env(c.env);
		
		// get the vars and values and 
		// then evaluate that to allow to define the varaibles with the values using define
		for(int i = 0; i < c.lamda.var.size(); i++) {
			
			localEnv.define(c.lamda.var.get(i).i, vals.get(i));
		}
		for(int i = 0; i < c.lamda.bodyExp.size();i++) {
			Exp b = c.lamda.bodyExp.get(i);
			evalOperand = b.eval(localEnv);
//			System.out.println("* " + evalOperand.val);
		}
		
		
		return new ValEnv(evalOperand.val,env);
	}

	@Override
	void print() {
		System.out.print("(");
		for(Exp e : exps) {
			e.print();
		}
		System.out.print(")");
	}

}
