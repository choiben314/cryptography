import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		Cipher grille = new Cipher();
		char opt = 0;
		Scanner input = new Scanner(System.in);

		while (opt != '6') {
			System.out.println("\n~~~~MENU~~~~");

			System.out.println("\n1.) Encrypt file.");
			System.out.println("2.) Decrypt file.");
			System.out.println("3.) Custom key.");
			System.out.println("4.) Generate random key.");
			System.out.println("5.) Set key from file for use.");
			System.out.println("6.) Exit.\n");

			System.out.println("Currently using key from \""
					+ grille.getCurrentGridKey() + "\"");

			System.out.print("\nEnter option: ");

			opt = input.nextLine().charAt(0);

			switch (opt) {
			case '1':
				grille.encryptToFile();
				break;
			case '2':
				grille.decryptToFile();
				break;
			case '3':
				grille.setNewGridKey();
				break;
			case '4':
				grille.setRandomKey();
				break;
			case '5':
				grille.setKeyFromFile();
				break;
			}
		}
		input.close();
	}
}