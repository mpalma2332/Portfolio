import java.util.Scanner;

public class Read extends Exp{

	public static Scanner scan = new Scanner(System.in);

	public Read() {
		
	}

	@Override
	ValEnv eval(Env env) {
		String val = scan.next();
		try {
		int intVal = Integer.parseInt(val);
		ValEnv ve = new ValEnv(intVal, env);
		return ve; 
		}
		catch (NumberFormatException e) {
			System.err.println("read expects type <integer>, given: " + val); // give it the non-integer input
			System.exit(13);
		}
		return null; 
	}

	@Override
	void print() {
		System.out.print("(read)");
	}
}	