package Model;

public class Ladder {
	private Square squareStart;
	private Square squareEnd;
	
	public Ladder(Square startSquare, Square enSquare) {
		super();
		this.squareStart = startSquare;
		this.squareEnd = enSquare;
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
	@Override
	public String toString() {
		return "Ladder [squareStart=" + squareStart + ", squareEnd=" + squareEnd + "]";
	}
	
}
