import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) {

		// number grid 1-16 in each quadrant
		// generate key with 1-16
		// get each line from message and fill end of each line with ^'s
		// make line backwards
		// put each character in line into one of 16 spaces in key (left to
		// right for each row)
		// rotate key 90 degrees clockwise and fill in 16 more spaces. Keep
		// rotating until 64 spaces are filled (one line).
		// copy each 1*8 row of 8*8 grid = coded group.
		// this is the coded message!

		Scanner userIn = new Scanner(System.in);
		int opt = 0;

		while (opt != 5) {			
			System.out.println("\n~~~~MENU~~~~");

			System.out.println("\n1.) Encrypt file.");
			System.out.println("2.) Decrypt file.");
			System.out.println("3.) Custom key.");
			System.out.println("4.) Generate new key.");
			System.out.println("5.) Exit.\n");

			System.out.println("Enter option: ");
			opt = userIn.nextInt();

			switch (opt) {
			case 1:
				Cipher d = new Cipher();
				d.encryptToFile();
			}
		}
	}
}