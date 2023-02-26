public class Write extends Exp {

	Exp e;

	public Write(Exp e) {
		this.e = e;
	}

	@Override
	ValEnv eval(Env env) {
//		System.out.println("WRite here " + e.toString());
		
		ValEnv ve =  e.eval(env);
//		System.out.println("VeVal: " + ve.val);
		if(!(ve.val instanceof Integer)) {
			System.err.println("write: expects type <integer> as 1st argument");
			System.exit(3);
		}
		
		System.out.print(ve.val); // will print the expression evaluates to standard output without newline
		return new ValEnv(null,ve.env);
	}

	@Override
	void print() {
		System.out.print("(write ");
		e.print();
		System.out.print(")");
	}
}
