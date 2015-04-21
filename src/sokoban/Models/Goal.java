package sokoban.Models;

public class Goal{
	int x;
	int y;
	public Goal(int i, int j) {
		this.x = i;
		this.y = j;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}