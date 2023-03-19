import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

// protects code from being sub-classed/ make it "final"
public final class ImprovedPasswords {

	HashMap<String, String> fileManager;
	
	HashMap<String, String> hashedPasswordsFile = new HashMap<>();


	File file;
	File hashedFile;
	
	// constant variables for hashing passwords
	public  final int SALT_BYTE_SIZE = 8;
	public  final String HASH_ALGORITHM = "PBKDF2WithHmacSHA256";
	public  final int DERIVED_KEY_LENGTH = 256;
	public  final int NUM_ITERATION = 20000;

	public ImprovedPasswords(String filename) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		// reading file and storing data in HashMap
		file = new File(filename);
		Scanner scan;
		scan = new Scanner(file);
		fileManager = new HashMap<>();
		while(scan.hasNextLine()) {
			String[] line = scan.nextLine().split(":");
			if(line.length != 2) {
				System.err.println("Invalid format. Username and password must be separated by \":\" ");
			}
			fileManager.put(line[0], line[1]);
		}
		scan.close();
		// make a defensive copy of the file
		this.file = new File(file.getPath());
		// hashing data in original file and placing them in a new file
		hashedFile = new File("hashed" + filename);
		
		HashAllPasswords();
		hashWriter();

	}

	// Overwrites plain text user and password credentials to a file
	protected void fileWriter() throws IOException {
		FileWriter writer = new FileWriter(file);
		for(String users : fileManager.keySet()) {
			writer.write(users + ":" + fileManager.get(users) + "\n");
		}
		
		writer.flush();
		writer.close();
	}
	
	// Overwrites user and hashed password credentials to a file
	protected void hashWriter() throws IOException {
        FileWriter hashWriter = new FileWriter("hashed" + file.getName());
		for(String users : hashedPasswordsFile.keySet()) {
			hashWriter.write(users + ":" + hashedPasswordsFile.get(users) + "\n");
		}
		hashWriter.flush();
		hashWriter.close();

	}
	
	public void printFileManager() {
		for(String user : fileManager.keySet()) {
			System.out.println(user + ":" + fileManager.get(user));
		}
	}
	
	public void printHashedFile() {
		for(String user : hashedPasswordsFile.keySet()) {
			System.out.println(user + ":" + hashedPasswordsFile.get(user));
		}
	}

	public boolean addUser(String user, char[] password) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		// validate parameters
		if(user == null || password == null) {
	        throw new NullPointerException();
	    }
		// defensive copy of given password 
	    char[] passwordCopy = Arrays.copyOf(password, password.length);
	    
	    
	    if(!isUser(user)) {
	    	// places the plain user in a hashmap and writes it to a file 
	    	fileManager.put(user, new String(passwordCopy));
	        fileWriter();
	        
	        // hashes the new user and writes it the file
	        String saltUser = generateSalt(SALT_BYTE_SIZE);
	        String hashPassUser = hashAndEncodePassword(user, new String(passwordCopy), saltUser);
	        
	        hashedPasswordsFile.put(user, hashPassUser + "," + saltUser);
	        hashWriter();
	        
	        return true;
	    }
	    return false;
	}


	
	public void HashAllPasswords() throws NoSuchAlgorithmException, InvalidKeySpecException {
	    for(String user : fileManager.keySet()) {
	    	String userSalt = generateSalt(SALT_BYTE_SIZE);
	        String hashPassword = hashAndEncodePassword(user, fileManager.get(user), userSalt);
	        hashedPasswordsFile.put(user, hashPassword + "," + userSalt);
	    }
	}

	/*
	 * To authenticate a user, 
	 * 	- hash the given password with the user's salt value 
	- compare it to the stored hashed password value. 
	 */
	public boolean authenticateUser(String user, char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// validate parameters
	    if(user == null || password == null) {
	        throw new NullPointerException();
	    }

	    if(isUser(user)) {
	    	// creates an array to separate the hashed password and salt
	        String[] parts = hashedPasswordsFile.get(user).split(",");
	        String hashedPassword = parts[0];
	        String salts = parts[1];

	        char[] passwordCopy = Arrays.copyOf(password, password.length);
	        String hashedInputPassword = hashAndEncodePassword(user, new String(passwordCopy), salts);
	        return hashedPassword.equals(hashedInputPassword);
	      
	    }

	    return false;
	}


	public boolean isUser(String user) {
		// validate parameter
		if(user == null) {
			throw new NullPointerException();
		}

		if(fileManager.containsKey(user)) {
			return true;
		}
		return false;
	}

	public boolean removeUser(String user) throws IOException {
		// validate parameter
		if(user == null) {
			throw new NullPointerException();
		}

		if(isUser(user)) {
			fileManager.remove(user);
			fileWriter();
			
			hashedPasswordsFile.remove(user);
			hashWriter();
			return true;
		}
		return false;
	}
	
	// Driver code to hashing a password in Base64
	
	/**
	 * Used to generate a Base64 encoded string from a random byte sequence of length n.
	 * @param n size of salt in bytes
	 * @return String Base64-encoded salt value
	 */
	public String generateSalt(int n) {
		byte[] bytes = null;
		
		SecureRandom random = new SecureRandom();
		
		bytes = new byte[n];
		random.nextBytes(bytes);
		
		return encodeToBase64(bytes);
		
	}
	
	/**
	 * Encodes byte array to base 64 string
	 */
	private static String encodeToBase64(byte[] bytes) {
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.withoutPadding().encodeToString(bytes);
	}
	
	private static byte[] decodeFromBase64(String base64String) {
		Base64.Decoder decoder = Base64.getDecoder();
		return decoder.decode(base64String);
	}
	
	/**
	 * Hashes a password string with a salt and then
	 * converts the hash to base64 encoded string 
	 */
	public String hashAndEncodePassword(String username, String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException{
		// convert salt string to byte array
		byte[] saltBytes = decodeFromBase64(salt);
		KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, NUM_ITERATION, DERIVED_KEY_LENGTH);
		SecretKeyFactory f = null;
		byte[] hashedPswd = null;
		
		// create a secret key factory instance for 
		try {
			f = SecretKeyFactory.getInstance(HASH_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new InvalidKeySpecException(e);
		}
		
		// generate a secret key object from they key specification
		try {
			hashedPswd = f.generateSecret(spec).getEncoded();
		} catch(InvalidKeySpecException e) {
			throw new InvalidKeySpecException(e);
		}
		
		return encodeToBase64(hashedPswd);
		
	}
	
}
