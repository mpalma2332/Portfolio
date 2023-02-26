import java.util.ArrayList;

public class Program extends ASTNode {

	ArrayList<Exp> expList = new ArrayList<>();

	
	@Override
	ValEnv eval(Env env) {
		ValEnv ve = new ValEnv(null, env);
		
		for(int i = 0; i < expList.size();i++) {
			Exp e = expList.get(i);
			ve = e.eval(ve.env);
			
		}
		// optional 
		// return new ValEnv(null, ve.env);
		return ve;
	}

	// going to leave this like this for now, then come back to
	// this to see how it prints
	@Override
	void print() {
		System.out.print("(");	
		for(int i = 0; i < expList.size();i++) {
			expList.get(i).print();
		}
		System.out.print(")");
	}

}
