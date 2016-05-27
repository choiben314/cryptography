import java.io.*;
import java.util.Scanner;

public class Cipher {

	private String msgName = "msg.txt";
	private String encryptName = "encrypt.txt";
	private String decryptName = "decrypt.txt";
	private String gridKeyFile = "key.txt";
	private String currentGridKey;

	public static Cell[][] grid;
	private String[] gridKey;

	public Cipher() {
		gridKey = new String[16];
		grid = new Cell[8][8];

		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				grid[r][c] = new Cell();
			}
		}

		initGrid(grid);
		// genKey();
		fileToKey(gridKeyFile);
		currentGridKey = gridKeyFile;
	}

	private void initGrid(Cell[][] grid) {
		for (int r = 0; r < 4; r++) {
			for (int c = 0; c < 4; c++) {
				grid[r][c].setNum(4 * r + c + 1);
				grid[r][c].setQuad('a');
			}
			for (int c = 4; c < 8; c++) {
				grid[r][c].setNum(4 * (7 - c) + r + 1);
				grid[r][c].setQuad('b');
			}
		}
		for (int r = 4; r < 8; r++) {
			for (int c = 4; c < 8; c++) {
				grid[r][c].setNum(16 - (4 * (r - 4)) - (c - 4));
				grid[r][c].setQuad('c');
			}
			for (int c = 0; c < 4; c++) {
				grid[r][c].setNum(4 * (c + 1) - (r - 4));
				grid[r][c].setQuad('d');
			}
		}
	}

	private void genKey(String keyName) {

		PrintWriter pw = null;
		File file = new File(keyName);
		try {
			pw = new PrintWriter(file);
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot create " + keyName + " file.");
			System.exit(1);
		}

		for (int i = 1; i <= 16; i++) {
			char let = (char) ('a' + (int) (Math.random() * 4));
			pw.println(i + "" + let);
		}

		pw.close();
	}

	private void fileToKey(String keyName) {
		File keyFile = new File(keyName);
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(keyFile);
		} catch (FileNotFoundException ex) {
			System.out.println("File not found at \"" + keyFile.getName()
					+ "\".");
			ex.printStackTrace();
		}

		int k = 0;

		currentGridKey = keyName;
		while (fileReader.hasNextLine()) {
			gridKey[k++] = fileReader.nextLine();
		}
	}

	@SuppressWarnings("resource")
	public void encryptToFile() {
		Scanner userIn = new Scanner(System.in);
		System.out.print("\nEnter name of file with text: ");
		msgName = userIn.nextLine();

		File msgFile = new File(msgName);

		Scanner fileReader = null;
		try {
			fileReader = new Scanner(msgFile);
		} catch (FileNotFoundException ex) {
			System.out.println("File not found at \"" + msgFile.getName()
					+ "\".");
			ex.printStackTrace();
		}

		System.out
				.print("Enter name of file to input encrypted message into: ");
		if (userIn.hasNextLine()) {
			encryptName = userIn.nextLine();
		}

		File encryptFile = new File(encryptName);

		PrintWriter encryptWrite = null;
		try {
			encryptWrite = new PrintWriter(encryptFile);
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot create " + encryptFile.getName()
					+ " file.");
			System.exit(1);
		}

		System.out.println();

		while (fileReader.hasNextLine()) {
			String line = fileReader.nextLine();
			line = encrypt(line);
			System.out.println(line);
			encryptWrite.println(line);
		}
		encryptWrite.close();
	}

	@SuppressWarnings("resource")
	public void decryptToFile() {

		Scanner userIn = new Scanner(System.in);
		System.out.print("\nEnter text file name for message to decode: ");
		encryptName = userIn.nextLine();

		File msgFile = new File(encryptName);

		Scanner fileReader = null;
		try {
			fileReader = new Scanner(msgFile);
		} catch (FileNotFoundException ex) {
			System.out.println("File not found at \"" + msgFile.getName()
					+ "\".");
			ex.printStackTrace();
		}

		System.out
				.print("Enter name of file to input decrypted message into: ");
		if (userIn.hasNextLine()) {
			decryptName = userIn.nextLine();
		}

		File decryptFile = new File(decryptName);

		PrintWriter decryptWrite = null;
		try {
			decryptWrite = new PrintWriter(decryptFile);
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot create " + decryptFile.getName()
					+ " file.");
			System.exit(1);
		}

		System.out.println();
		while (fileReader.hasNextLine()) {
			String line = fileReader.nextLine();
			line = decrypt(gridKey, line);
			System.out.println(line);
			decryptWrite.println(line);
		}
		decryptWrite.close();
	}

	// Precondition: LINES ARE ALL 64 CHARS IN LENGTH
	public String decrypt(String[] gridKey, String lineInput) {
		Cell[][] decode = new Cell[8][8];
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				decode[r][c] = new Cell();
			}
		}
		initGrid(decode);

		int k = 0;

		for (int r = 0; r < decode.length; r++) {
			for (int c = 0; c < decode[0].length; c++) {
				decode[r][c].setValue(lineInput.charAt(k++));
			}
		}

		String line = "";

		for (int rot = 0; rot < 4; rot++) {
			for (int r = 0; r < decode.length; r++) {
				for (int c = 0; c < decode[0].length; c++) {
					if (matches(decode[r][c], gridKey)) {
						line += decode[r][c].getValue();
					}
				}
			}
			rotateKey(gridKey);
		}

		line = reverse(line);
		int caretLoc = line.length() - 1;
		while (line.charAt(caretLoc) == '^') {
			caretLoc--;
		}
		line = line.substring(0, caretLoc + 1);

		return line;
	}

	public String encrypt(String line) {
		return fillKey(grid, gridKey, reverse(fillCarets(line)));
	}

	// Fills grid through key with lineInput parameter
	private String fillKey(Cell[][] grid, String[] gridKey, String lineInput) {
		int k = 0;
		for (int rot = 0; rot < 4; rot++) {
			for (int r = 0; r < grid.length; r++) {
				for (int c = 0; c < grid[0].length; c++) {
					if (matches(grid[r][c], gridKey)) {
						grid[r][c].setValue(lineInput.charAt(k++));
					}
				}
			}
			rotateKey(gridKey);
		}
		return gridToString(grid);
	}

	// checks if any element in gridKey matches cell member variables (num and
	// letter)
	private boolean matches(Cell c, String[] gridKey) {
		for (String s : gridKey) {
			char quad = s.charAt(s.length() - 1);
			int num = Integer.parseInt(s.substring(0, s.length() - 1));
			if (c.getNum() == num && c.getQuad() == quad) {
				return true;
			}
		}

		return false;
	}

	private void rotateKey(String[] gridKey) {
		for (int i = 0; i < gridKey.length; i++) {
			String s = gridKey[i];
			if (s.charAt(s.length() - 1) == 'd') {
				gridKey[i] = s.substring(0, s.length() - 1) + 'a';
			} else {
				gridKey[i] = s.substring(0, s.length() - 1)
						+ (char) (s.charAt(s.length() - 1) + 1);
			}
		}
	}

	private String gridToString(Cell[][] grid) {
		String ret = "";
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				ret += grid[r][c].getValue();
			}
		}
		return ret;
	}

	// PRECONDITION: line.length() <= 64
	private String fillCarets(String line) {
		int k = 64 - line.length();
		for (int i = 0; i < k; i++) {
			line += "^";
		}
		return line;
	}

	private String reverse(String line) {
		String temp = "";
		for (int i = 0; i < line.length(); i++) {
			temp += line.charAt(line.length() - i - 1);
		}
		return temp;
	}

	@SuppressWarnings("resource")
	public void setNewGridKey() {
		Scanner read = new Scanner(System.in);
		System.out
				.print("Enter name of file where you would like to store your key: ");
		String gridName = read.nextLine();

		PrintWriter pw = null;
		File file = new File(gridName);
		try {
			pw = new PrintWriter(file);
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot create " + gridName + " file.");
			System.exit(1);
		}

		System.out
				.println("\nNote: There are four quadrants (a, b, c, d), starting with 'a' in the top left and moving clockwise.");
		System.out
				.println("\nEnter quadrant (a,b,c,d) for each of 16 key elements:\n");
		for (int i = 0; i < 16; i++) {
			System.out.print("Key " + (i + 1) + "/16: ");
			char let = read.nextLine().charAt(0);
			pw.println((i + 1) + "" + let);
		}
		pw.close();
	}

	@SuppressWarnings("resource")
	public void setRandomKey() {
		Scanner read = new Scanner(System.in);
		System.out
				.print("\nEnter name of file where you would like to store your key: ");
		String gridName = read.nextLine();
		genKey(gridName);
	}

	@SuppressWarnings("resource")
	public void setKeyFromFile() {
		Scanner read = new Scanner(System.in);
		System.out.print("\nEnter text file name of desired key: ");
		String keyName = read.nextLine();

		fileToKey(keyName);
	}

	public String getCurrentGridKey() {
		return currentGridKey;
	}
}
