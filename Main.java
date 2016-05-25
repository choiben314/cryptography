import java.util.*;
import java.io.*;

public class Main {

	public static Scanner fileReader;
	public static Scanner userIn = new Scanner(System.in);
	public static String fileName = "msg.txt";
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
		
		// for (int r = 0; r < 8; r++) {
		// for (int c = 0; c < 8; c++) {
		// System.out.print(d.grid[r][c].getNum() + "" +
		// d.grid[r][c].getQuad());
		// }
		// System.out.println();
		// }

		// System.out.println("Enter file name with text.");
		// fileName = userIn.next();
		
//		initOutput(fileName);
		
		File file = new File(fileName);
//		initScanner(fileName);
		
		Cipher d = new Cipher();
		d.encrypt(file, file);
		
		
		
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
