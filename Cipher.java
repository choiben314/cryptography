import java.io.*;
import java.util.Scanner;

public class Cipher {

	private String msgName = "msg.txt";
	private String encryptName = "encrypt.txt";
	private String gridKeyFile = "key.txt";
	private String[] gridKey;

	public static Cell[][] grid;

	public Cipher() {
		gridKey = new String[16];
		grid = new Cell[8][8];

		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				grid[r][c] = new Cell();
			}
		}

		initGrid();
		// genKey();
		fileToKey();
	}

	private void initGrid() {
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

	private void genKey() {
		PrintWriter pw = null;
		File file = new File(gridKeyFile);
		try {
			pw = new PrintWriter(file);
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot create " + gridKeyFile + " file.");
			System.exit(1);
		}

		for (int i = 1; i <= 16; i++) {
			char let = (char) ('a' + (int) (Math.random() * 4));
			pw.println(i + "" + let);
		}

		pw.close();
	}

	private void fileToKey() {
		File keyFile = new File(gridKeyFile);
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(keyFile);
		} catch (FileNotFoundException ex) {
			System.out.println("File not found at \"" + keyFile.getName()
					+ "\".");
			ex.printStackTrace();
		}

		int k = 0;

		while (fileReader.hasNextLine()) {
			gridKey[k++] = fileReader.nextLine();
		}
	}

	public void encryptToFile() {
		Scanner userIn = new Scanner(System.in);
		System.out.print("Enter name of file with text: ");
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

		userIn.close();

		PrintWriter encryptWrite = null;
		try {
			encryptWrite = new PrintWriter(encryptFile);
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot create " + encryptFile.getName()
					+ " file.");
			System.exit(1);
		}

		while (fileReader.hasNextLine()) {
			String line = fileReader.nextLine();
			line = encrypt(line);
			System.out.println(line);
			encryptWrite.println(line);
		}
		encryptWrite.close();
	}

	public void decryptToFile() {
		
		Scanner userIn = new Scanner(System.in);
		System.out.println("Enter text file name for message to decode.");
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
		
		while (fileReader.hasNextLine()) {
			String line = fileReader.nextLine();
			line = decrypt(line);
			System.out.println(line);
			//encryptWrite.println(line);
		}
	}
	public String decrypt(String line) {
		return "";
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
			// System.out.println(s + " " + quad);
			int num = Integer.parseInt(s.substring(0, s.length() - 1));
			if (c.getNum() == num && c.getQuad() == quad) {
				// System.out.print(c.getNum() + "" + c.getQuad());
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
}
