package Model;

import java.util.Objects;

public class Player {
	private int playerId;
	private String name;
	private Color  color;
	private int position;


	public void setName(String name) {
		this.name = name;
	}
	public void setPosition(int newPosition) {
        this.position = newPosition; 
    }

	public Player(String name ,Color color) {
	    this.name = name;
	    this.position = 1; // Starting position
	    this.color = color;
	    
	}

	public String getName() {
	    return name;
	}

	public int getPosition() {
		
	    return position;
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color  color) {
		this.color = color;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(name, other.name);
	}

}
