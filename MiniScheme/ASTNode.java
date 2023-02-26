
public abstract class ASTNode {
	abstract ValEnv eval(Env env);
	abstract void print();
}
