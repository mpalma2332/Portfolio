import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;


public class WebHTMLCodeGetter {
	public static void main(String[] args) {

		// Ask user for input (URL)
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a URL: ");
		String url = scan.nextLine();

		String outText = "Empty";
		int lineNumber = 0;
		
		try {
			URL connection = new URL(url);
			// open its data with an inputStream
			InputStream inStream = connection.openStream();
			// read data with a scanner
			Scanner inFromNet = new Scanner(inStream);
			StringBuffer buffer = new StringBuffer();

			// read each line and append it the buffer with along with its line number
			while(inFromNet.hasNextLine()) {
				lineNumber++;
				String line = inFromNet.nextLine();
				buffer.append(lineNumber);
				buffer.append(".");
				buffer.append(line);
				buffer.append('\n');

			}

			outText = buffer.toString(); 
			inFromNet.close();
			scan.close();

		}
		catch (IOException e) {
			outText = "Unable to load: " + url + "\n";
		}

		System.out.print(outText);
	}

}

