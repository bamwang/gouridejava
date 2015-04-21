package sokoban.Models;


public class Box extends Block {
	private boolean isGoal = false;
	public void init(int x, int y) {
		super.init(x, y);
//		this.num = num;
	}
	public boolean isGoal() {
		return isGoal;
	}
	public void setGoal(boolean isGoal) {
		this.isGoal = isGoal;
	}
//	public int getnum() {
//		return num;
//	}
}
