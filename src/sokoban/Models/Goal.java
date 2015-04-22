package sokoban.Models;

public class Goal extends Block{
	int x;
	int y;
	public Goal(int i, int j) {
		this.x = i;
		this.y = j;
	}
	@Override
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}