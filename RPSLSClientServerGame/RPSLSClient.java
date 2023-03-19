import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class RPSLSClient {

	 
	public static final int PORT = 12345;

	public static void main(String[] args) throws UnknownHostException, IOException {

		 	Scanner scan = new Scanner(System.in);
			String[] items = {"Rock", "Paper", "Scissors", "Lizard", "Spock"};
		 	System.out.print("Enter a hostname: ");
		 	String hostName = scan.next();
		 	

			try (
					Socket sock = new Socket(hostName, PORT);
					
					// set up streams
					PrintWriter outToServer = new PrintWriter(sock.getOutputStream());
					BufferedReader inFromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					)
			{
				//server talks first
				String reply = inFromServer.readLine();
				System.out.println("Server says: " + reply);
				

				String fromUser;
				boolean done = false;
				
				while(!done) {
					
	                System.out.print("Choose an item ");
	                System.out.print(Arrays.toString(items) + ": ");
	                fromUser = scan.next();
	                if(fromUser.equalsIgnoreCase("n")) {
	                	sock.close();
	                	done = true;
	                }
	                // send the users input to the server
	                outToServer.println(fromUser);
	                
	                outToServer.flush();
	                
	                // get a reply
	                reply = inFromServer.readLine();
	                String[] replyLines = reply.split("_");
	                for(String line : replyLines) {
	                	System.out.println(line);
	                }
	                

	                
	                
				}
				
			}
			catch(Exception e) {
				System.err.println(e);
			}

	}

}
