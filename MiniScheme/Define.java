
public class Define extends Def {

	Identifier v;
	Exp e;

	public Define(Identifier v, Exp e) {
		this.v = v;
		this.e = e;
	}

	@Override
	ValEnv eval(Env env) {
		ValEnv ve = e.eval(env);
		if(ve.env.nextEnv == null) {
			try {
				ve.env.assign(v.i, ve.val); 
			}
			catch(RuntimeException ei) {
				ve.env.define(v.i, ve.val);
			}
		}
		
		if(ve.env.varNames.contains(v.i)) {
			if(ve.env.nextEnv != null) {
				System.err.print("duplicate identifier: " + v.i);
				System.exit(5);
			}
		}
		else {
			ve.env.define(v.i, ve.val);
		}
		return new ValEnv(null,ve.env);
	}

	@Override
	void print() {
		System.out.print("(define ");
		v.print();
		System.out.print(" ");
		e.print();
		System.out.print(")");

	}

}
