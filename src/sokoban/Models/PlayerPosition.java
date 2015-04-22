package sokoban.Models;

public class PlayerPosition extends Position {
	Box box;
	public PlayerPosition(int x, int y, int n, Box box) {
		super(x, y, n);
		this.box = box;
	}
	
}
