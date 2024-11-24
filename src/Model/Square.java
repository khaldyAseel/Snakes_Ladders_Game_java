package Model;

public class Square {
	private int row;
	private int col;
	private SquareType type;
	private int boundsX;
	private int boundsY;
	private int value;

	public Square(int row, int col, SquareType type, int boundsX, int boundsY, int value) {
		super();
		this.row = row;
		this.col = col;
		this.type = type;
		this.boundsX = boundsX;
		this.boundsY = boundsY;
		this.value = value;
	}

	public Square(int row, int col, int value) {
		super();
		this.row = row;
		this.col = col;
		this.value = value;
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public SquareType getType() {
		return type;
	}
	public void setType(SquareType type) {
		this.type = type;
	}
	public int getBoundsX() {
		return boundsX;
	}
	public void setBoundsX(int boundsX) {
		this.boundsX = boundsX;
	}
	public int getBoundsY() {
		return boundsY;
	}
	public void setBoundsY(int boundsY) {
		this.boundsY = boundsY;
	}
	@Override
	public String toString() {
		return "Square [row=" + row + ", col=" + col + ", type=" + type + ", boundsX=" + boundsX + ", boundsY="
				+ boundsY + ", value=" + value + "]";
	}

	
}
