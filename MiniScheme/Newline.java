
public class Newline extends Exp {

	
	// will print a newline to standard output.
	@Override
	ValEnv eval(Env env) {
		ValEnv ve = new ValEnv(null,env);
		System.out.println();
		return new ValEnv(null,ve.env);
	}

	@Override
	void print() {
		System.out.print("(newline)");
	}

}
