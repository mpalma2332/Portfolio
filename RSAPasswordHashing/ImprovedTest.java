import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class ImprovedTest {

	private static final int MAX_ATTEMPTS = 3;

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

		Scanner scan = new Scanner(System.in);
		System.out.print("Please Enter a filename: ");
		String fileName = scan.next();
		boolean isValidFile = false;

		int numOfAttempts = 1;

		ImprovedPasswords iPass = null;
		//---------------------------------------------
		// Code for asking users for a file with limited attempts
		do {
			try {
				iPass = new ImprovedPasswords(fileName + ".txt");
				isValidFile = true;
			} catch (FileNotFoundException e) {
				System.out.println("Error: Invalid file name");
				System.out.print("Please enter a valid filename: ");
				fileName = scan.next();
				numOfAttempts++;
			}
		} while (!isValidFile && numOfAttempts < MAX_ATTEMPTS);

		// limit number of attempts to stop automated inputs
		if(numOfAttempts == MAX_ATTEMPTS) {
			System.err.println("Sorry, you have entered too many invalid inputs. Goodbye");
			Runtime.getRuntime().exit(1);
		}

		//---------------------------------------------
		System.out.println("Is clif a user? " + iPass.isUser("clif"));

		// authenticate a user
		// Authenticates a user with the correct password
		char[] ch2 = {'p','s','w','d','?'};
		System.out.println("is clif authenticated? " + iPass.authenticateUser("clif", ch2)); // returns true

		// authenticate a user
		// Authenticates a user with the incorrect password 
		char[] wrong = {'q','s','w','d','?'};
		System.out.println("is clif authenticated now? " + iPass.authenticateUser("clif", wrong)); // returns false

		//	adds bobby to the original password file
		char[] ch33 = {'J', 'o','e'};
		iPass.addUser("Bobby", ch33);

		// checks if Bobby is a user
		System.out.println("Is Bobby a user? " + iPass.isUser("Bobby"));

		// removes bobby from both files
		iPass.removeUser("Bobby");

		// checks if Bobby is a user
		System.out.println("Is Bobby a user now? " + iPass.isUser("Bobby"));

		scan.close();





	}

}
