import java.util.ArrayList;

public class Lambda extends Exp {
	
	
	/*
	 * Example of lambda Exp
	 * form: (lambda <list of identifier> <body expression>)
	 * | (lambda ( <variable> )* <body>) )
	 * > (define add (lambda (x y) (+ x y)))
	 * > (add 2 3)
	 * 5
	 */
	ArrayList<Identifier> var = new ArrayList<>();
	ArrayList<Exp> bodyExp = new ArrayList<>();


	// Evaluation of a lambda results 
	// in a new closure value
	// containing the 
	// ! lambda AST node and 
	// ! the defining env
	@Override
	ValEnv eval(Env env) {
		Closure cl = new Closure(env,this);
		return new ValEnv(cl, env); 
	}

	@Override
	void print() {
		System.out.print("(lambda (");
		for(Identifier v : var) {
			v.print();
		}
		System.out.print(")");
		for(Exp b : bodyExp) {
			b.print();
		}
		System.out.print(")");

	}

}
