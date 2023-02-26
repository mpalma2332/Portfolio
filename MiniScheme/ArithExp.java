public class ArithExp extends Exp {

	String operator;
	Exp operand1, operand2;

	public ArithExp(String operator, Exp operand1, Exp operand2) {
		this.operator = operator;
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	@Override
	ValEnv eval(Env env) {
		ValEnv ve1 = operand1.eval(env);

		ValEnv ve2 = operand2.eval(ve1.env);

		if(!(ve1.val instanceof Integer)) {
			System.err.println(operator + ": expects type <integer> as 1st argument");
			System.exit(3);
		}
		else if(!(ve2.val instanceof Integer)) {
			System.err.println(operator + ": expects type <integer> as 2nd argument");
			System.exit(3);
		}

		// unbox these
		int i1 = (Integer) ve1.val;
		int i2 = (Integer) ve2.val;
		int result = 0;
		if(operator.equals("+")) {
			result = i1 + i2;
		}
		else if(operator.equals("-")) {
			result = i1 - i2;
		}
		else if(operator.equals("*")) {
			result = i1 * i2;
		}
		else if(operator.equals("quotient")) {
			if(i2 == 0) {
				System.err.println("quotient: undefined for 0");
				System.exit(2);
			}
			result = i1 / i2;
		}
		else {
			throw new RuntimeException( "Invalid arithmetic operator " + operator);

		}
		return new ValEnv(result, ve2.env);
	}

	@Override
	void print() {
		System.out.print("(" + operator + " ");
		operand1.print();
		System.out.print(" ");
		operand2.print();
		System.out.print(")");
	}

}
