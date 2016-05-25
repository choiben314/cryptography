
public class Cell {
	private int num;
	private char quad;
	
	public Cell () {
		num = -1;
		quad = 'z';
	}
	public Cell(int num, char quad) {
		this.num = num;
		this.quad = quad;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public char getQuad() {
		return quad;
	}

	public void setQuad(char quad) {
		this.quad = quad;
	}
}