import java.io.*;
import java.util.Scanner;

public class Cipher {
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
		genKey();
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
		File file = new File("key.txt");
		try {
			pw = new PrintWriter(file);
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot create key.txt file");
			System.exit(1);
		}

		for (int i = 1; i <= 16; i++) {
			char let = (char) ('a' + (int) (Math.random() * 4));
			pw.println(i + "" + let);
		}

		pw.close();
	}

	public void encrypt(File msgFile, File encryptFile) {
		// probably split into functions later.
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
			line = fillCarets(line);
			line = reverse(line);
			System.out.println(line);
		}

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
