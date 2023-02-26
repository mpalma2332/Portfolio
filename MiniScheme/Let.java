import java.util.ArrayList;

public class Let extends Exp {


	ArrayList<Identifier> var = new ArrayList<>();
	ArrayList<Exp> bindingExp = new ArrayList<>();
	ArrayList<Exp> bodyExp = new ArrayList<>();

	@Override
	ValEnv eval(Env env) {
		ValEnv ve = new ValEnv(null,env);
		ArrayList<ValEnv> bindingExpVals = new ArrayList<>();

		for(int i = 0; i < bindingExp.size();i++) {
			Exp e = bindingExp.get(i);
			ve = e.eval(ve.env);
			bindingExpVals.add(ve);
		}
		Env letEnv = new Env(ve.env);

		for(int i = 0; i < bindingExpVals.size();i++) {
			ValEnv v = bindingExpVals.get(i);
			Identifier id = var.get(i);
			if(letEnv.varNames.contains(id.i)) {
				if(letEnv.nextEnv != null) {
					System.err.print("duplicate identifier: " + id.i);
					System.exit(5);
				}
			}
			else {
				letEnv.define(id.i, v.val);
			}
		}


		for(int i = 0; i < bodyExp.size();i++) {
			Exp b = bodyExp.get(i);
			ve = b.eval(letEnv);

		}

		return new ValEnv(ve.val,env);
	}

	@Override
	void print() {
		System.out.print("let (");
		for(Exp e : bindingExp) {
			e.print();
		}
		System.out.print(") ");
		for(Exp b : bodyExp) {
			b.print();
		}
		System.out.print(")");

	}

}
