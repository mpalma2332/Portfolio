
public class Set extends Exp {
	
	Identifier v; 
	Exp e;
	
	public Set(Identifier v, Exp e) {
		this.v = v;
		this.e = e;
	}

	@Override
	ValEnv eval(Env env) {
		ValEnv ve = e.eval(env); // expression
		
		try {
			
			ve.env.assign(v.i, ve.val);
		}
		catch(RuntimeException e) {
			ve.env.define(v.i, ve.val);
			if(ve.env.nextEnv == null) {
				System.err.print("cannot set undefined variable: " + v.i);
				System.exit(6);
			}
		}
		
		
		return new ValEnv(null,ve.env);
	}

	@Override
	void print() {
		System.out.print("(set! ");
		v.print();
		System.out.print(" ");
		e.print();
		System.out.print(")");
	}

}
