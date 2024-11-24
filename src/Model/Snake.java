package Model;

public class Snake {
	private Square squareStart;
	private Square squareEnd;
	private Enum<Model.Color> color;
	public Snake(Square squareStart, Square squareEnd) {
		super();
		this.squareStart = squareStart;
		this.squareEnd = squareEnd;
	}
	public Square getSquareStart() {
		return squareStart;
	}
	public void setSquareStart(Square squareStart) {
		this.squareStart = squareStart;
	}
	public Square getSquareEnd() {
		return squareEnd;
	}
	public void setSquareEnd(Square squareEnd) {
		this.squareEnd = squareEnd;
	}
	public Enum<Model.Color> getColor() {
		return color;
	}
	public void setColor(Enum<Model.Color> color) {
		this.color = color;
	}
		
		
	    
}
