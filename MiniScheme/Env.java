import java.util.ArrayList;


public class Env {
	ArrayList<String> varNames = new ArrayList<String>();
	ArrayList<Object> values = new ArrayList<Object>();
	Env nextEnv = null;
	
	public Env(Env nextEnv) {
		this.nextEnv = nextEnv;
	}
	
	public Env() {
	}
	
	public void define(String varName, Object value) {
		varNames.add(varName);
		values.add(value);
	}
	
	public Object lookup(String varName) {
		int index = varNames.indexOf(varName);
		if (index >= 0)
			return values.get(index);
		else if (nextEnv == null) {
			System.err.print("reference to undefined identifier: " + varName);
			System.exit(4);
		}
			return nextEnv.lookup(varName);
	}
	
	public void assign(String varName, Object value) {
		int index = varNames.indexOf(varName);
		if (index >= 0)
			values.set(index, value);
		else if (nextEnv == null)
			throw new RuntimeException("Undefined identifier: " + varName);
		else
			nextEnv.assign(varName, value);
	}
}
