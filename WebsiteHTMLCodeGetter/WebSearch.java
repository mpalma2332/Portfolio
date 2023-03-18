import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;


public class WebSearch {

	public static void main(String[] args) {

		// Ask user for input (URL and string to Search)
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a URL: ");
		String url = scan.nextLine();
		
		String outText = "Empty";
		int lineNumber = 0;
		int matches = 0;

		System.out.print("What are you looking for? ");
		String searchKey = scan.nextLine();

		try {
			URL connection = new URL(url);
			// open its data with an inputStream
			InputStream inStream = connection.openStream();
			// read data with a scanner
			Scanner inFromNet = new Scanner(inStream);
			StringBuffer buffer = new StringBuffer();
			
			while(inFromNet.hasNextLine()) {
				lineNumber++;
				String line = inFromNet.nextLine();
				// checks if the line contains the searchKey and appends the line
				if(line.contains(searchKey)) {
					matches++;
					buffer.append(lineNumber);
					buffer.append(". ");
					buffer.append(line);
					buffer.append('\n');
				
				}
			}
			
			outText = buffer.toString(); 
			inFromNet.close();
			scan.close();
			
		}
		catch (IOException e) {
			outText = "Unable to load: " + url + "\n";
		}
		
		System.out.print(outText);
		System.out.println(matches + " matches.");
	}

}
