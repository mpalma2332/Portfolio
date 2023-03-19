import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RPSLSServer implements Runnable {

	public static final int PORT = 12345;

	protected Socket sock;
	protected PrintWriter outToServer;
	protected BufferedReader inFromServer;

	private static final int WAITING = 0;
	private static final int ZEROtoZERO = 1;
	private static final int ONEtoZERO = 2;
	private static final int ZEROtoONE = 3;
	private static final int ONEtoONE = 4;
	private static final int TWO = 5;
	private static final int END = 6;
	private static final int ANOTHER = 7;



	private volatile int state = WAITING;

	public RPSLSServer(Socket sock) throws IOException {
		this.sock = sock;
		outToServer = new PrintWriter(sock.getOutputStream());
		inFromServer = new BufferedReader(
				new InputStreamReader(sock.getInputStream()));
	}

	public void sendMessage(String msg) throws IOException{
		if(msg == null) {
			throw new IllegalArgumentException("Message is null.");
		}
		if(sock.isClosed()) {
			throw new IOException("Socket is closed!");
		}
		outToServer.println(msg);
		outToServer.flush();
	}

	public String Choose_item() {
		String[] items = {"Rock", "Paper", "Scissors", "Lizard", "Spock"};
		Random random = new Random();
		int picked = random.nextInt(items.length);
		return items[picked];
	}

	public boolean isValidInput(String input){
		String[] items = {"Rock", "Paper", "Scissors", "Lizard", "Spock", "y", "n"};
		for(String item : items) {
			if(item.equalsIgnoreCase(input)) {
				return true;
			}
		}
		return false;

	}
	
	public boolean clientWins(String line, String serverChoice) {
		if( (line.equalsIgnoreCase("Scissors") && serverChoice.equalsIgnoreCase("Lizard")) 
				|| (line.equalsIgnoreCase("Scissors") && serverChoice.equalsIgnoreCase("Paper")) 
				|| (line.equalsIgnoreCase("Paper") && serverChoice.equalsIgnoreCase("Rock")) 
				|| (line.equalsIgnoreCase("Paper") && serverChoice.equalsIgnoreCase("Spock")) 
				|| (line.equalsIgnoreCase("Rock") && serverChoice.equalsIgnoreCase("Scissors")) 
				|| (line.equalsIgnoreCase("Rock") && serverChoice.equalsIgnoreCase("Lizard")) 
				|| (line.equalsIgnoreCase("Lizard") && serverChoice.equalsIgnoreCase("Paper")) 
				|| (line.equalsIgnoreCase("Lizard") && serverChoice.equalsIgnoreCase("Spock")) 
				|| (line.equalsIgnoreCase("Spock") && serverChoice.equalsIgnoreCase("Rock")) 
				|| (line.equalsIgnoreCase("Spock") && serverChoice.equalsIgnoreCase("Scissors")) 
				) {
			return true;
		}
		return false;
	}


	@Override
	public void run() {
		try {
			String line;
			String serverChoice = "";
			int clientScore = 0;
			int serverScore = 0;

			if(state == WAITING) {
				sendMessage("START");
				serverChoice = Choose_item();
				state = ZEROtoZERO;
			}

			while((line = inFromServer.readLine()) != null) {
				if(isValidInput(line)) {
					if(state == ZEROtoZERO) {
						// they tie 
						if(line.equalsIgnoreCase(serverChoice)) {

							sendMessage("Tie_Server chose: " + serverChoice + "_" + clientScore + "," + serverScore);
							serverChoice = Choose_item();
							state = ZEROtoZERO;
						}
						// checking when client wins to move to 1-0, where the 1 is the client score
						else if( clientWins(line, serverChoice)) {
							clientScore++;
							sendMessage("You won!_Server chose: " + serverChoice + "_" + clientScore + "," + serverScore);
							serverChoice = Choose_item();
							state = ONEtoZERO;

						}
						// the server won move to 0-1
						else {
							serverScore++;
							sendMessage("You LOST_Server chose: " + serverChoice + "_" + clientScore + "," + serverScore);
							serverChoice = Choose_item();
							state = ZEROtoONE;
						}
					}
					else if(state == ONEtoZERO) {
						// they tie 
						if(line.equalsIgnoreCase(serverChoice)) {
							sendMessage("Tie_Server chose: " + serverChoice  + "_" + clientScore + "," + serverScore);
							serverChoice = Choose_item();
							state = ONEtoZERO;
						}
						// checking when client wins to move to 2-0, where the 2 is the client score
						else if( clientWins(line, serverChoice)) {
							clientScore++;
							sendMessage("You won!_Server chose: " + serverChoice + "_" + clientScore + "," + serverScore + "_Another? (y/n)");
							state = TWO;

						}
						// the server won move to 1-1
						else {
							serverScore++;
							sendMessage("You LOST_Server chose: " + serverChoice + "_" + clientScore + "," + serverScore);
							serverChoice = Choose_item();
							state = ONEtoONE;
						}
					}
					else if(state == ZEROtoONE) {
						// they tie 
						if(line.equalsIgnoreCase(serverChoice)) {
							sendMessage("Tie_Server chose: " + serverChoice + "_" + clientScore + "," + serverScore);
							serverChoice = Choose_item();
							state = ZEROtoONE;
						}
						// checking when client wins to move to 1-1
						else if( clientWins(line, serverChoice)) {
							clientScore++;
							sendMessage("You won!_Server chose: " + serverChoice + "_" + clientScore + "," + serverScore);
							state = ONEtoONE;

						}
						// the server won move to 2
						else {
							serverScore++;
							sendMessage("You LOST_Server chose: " + serverChoice + "_" + clientScore + "," + serverScore + "_Another? (y/n)");
							serverChoice = Choose_item();
							state = TWO;
						}
					}
					else if(state == ONEtoONE) {
						// they tie 
						if(line.equalsIgnoreCase(serverChoice)) {

							sendMessage("Tie_Server chose: " + serverChoice  + "_" + clientScore + "," + serverScore);
							serverChoice = Choose_item(); 
							state = ONEtoONE;
						}
						// checking when client wins to move to 2
						else if( clientWins(line, serverChoice)) {
							clientScore++;
							sendMessage("You won!_Server chose: " + serverChoice + "_" + clientScore + "," + serverScore + "_Another? (y/n)");
							state = TWO;

						}
						// the server won move to 2
						else {
							serverScore++;
							sendMessage("You LOST_Server chose: " + serverChoice  + "_" + clientScore + "," + serverScore + "_Another? (y/n)");
							state = TWO;
						}
					}
					else if(state == TWO) {
						if(line.equalsIgnoreCase("Y")) {
							serverChoice = Choose_item(); 
							clientScore = 0;
							serverScore = 0;
							sendMessage("GO!");
							state = ZEROtoZERO;
						}
						else if(line.equalsIgnoreCase("N")) {
							state = END;
							
						}
					}
					else if(state == END) {
						sendMessage("Bye");
						break;
					}
				}
				else {
					sendMessage("Enter a valid item!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			boolean done = false;
			Socket clientSocket = null;
			while(!done) {
				clientSocket = serverSocket.accept();
				RPSLSServer game = new RPSLSServer(clientSocket);
				Thread thread = new Thread(game);
				thread.start();
				
			}
			serverSocket.close();
			clientSocket.close();
			

		}
		catch(IOException e) {

		}
	}
}


