
public class Cell {
	private int num;
	private char quad;
	private char value;
	
	public Cell () {
		num = -1;
		quad = 'z';
	}
	public Cell(int num, char quad, char value) {
		this.num = num;
		this.quad = quad;
		this.value = value;
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
	public char getValue() {
		return value;
	}
	public void setValue(char value) {
		this.value = value;
	}
}