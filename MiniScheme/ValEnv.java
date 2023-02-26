
public class ValEnv {
	Object val;
	Env env;
	
	public ValEnv(Object val, Env env) {
		this.val = val;
		this.env = env;
	}

	public ValEnv(Env env) {
		this.val = null;
		this.env = env;
	}
}
