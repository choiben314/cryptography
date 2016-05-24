import java.util.*;
import java.io.*;

public class Main {

	public static Scanner fileReader;
	public static Scanner userIn = new Scanner(System.in);
	public static String fileName = "default.txt";
	public static String encryptFile = "encrypt.txt";
	public static PrintWriter pw;
	
	public static void main(String[] args) {
		 
		// number grid 1-16 in each quadrant
		// generate key with 1-16
		// get each line from message and fill end of each line with ^'s
		// make line backwards
		// put each character in line into one of 16 spaces in key (left to right for each row)
		// rotate key 90 degrees clockwise and fill in 16 more spaces. Keep rotating until 64 spaces are filled (one line).
		// copy each 1*8 row of 8*8 grid = coded group.
		// this is the coded message!

		System.out.println("Enter file name with text.");
		fileName = userIn.next();
		
		initScanner(fileName);
		initOutput(encryptFile);
		
	}
	public static void initOutput(String fileName) {
		File file = new File(fileName);
		pw = null;
		try {
			pw = new PrintWriter(file);
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot create " + fileName);
			System.exit(1);
		}
	}
	
	public static void initScanner(String fileName) {
		File file = new File(fileName);
		try {
			fileReader = new Scanner(file);
		} catch (FileNotFoundException ex) {
			System.out.println("File not found at \"" + fileName + "\".");
			ex.printStackTrace();
		}
	}

}
