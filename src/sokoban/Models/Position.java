package sokoban.Models;

public class Position {
	protected int x;
	protected int y;
	protected int n;
	public Position(int x, int y, int n) {
		this.x = x;
		this.y = y;
		this.n = n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getN() {
		return n;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}
