
public class Closure extends Env {

	// a closure needs 
	// the env 
	// and lambda exp
	
	Env env = null;
	Lambda lamda;
	
	public Closure(Env env, Lambda lamda) {
		this.env = env;
		this.lamda = lamda;
	}
	
	
	
	
	
	
	
	
}
