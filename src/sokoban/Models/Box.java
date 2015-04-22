package sokoban.Models;

import sokoban.Constants;


public class Box extends Block {
	private boolean isGoal = false;
	
	public Box(int x, int y, int n) {
		Position position = new Position(x, y, 0);
		positions.push(position);
	}
	public int move(int direction){
		int x = getX();
		int y = getY();
		int n = getN();
		switch(direction){
		case Constants.UP:
			x--;
			break;
		case Constants.DOWN:
			x++;
			break;
		case Constants.LEFT:
			y--;
			break;
		case Constants.RIGHT:
			y++;
			break;
		default:
			break;
		}
		Position position = new Position(x, y, n);
		positions.push(position);
		return 0;
	}
	public Position back(){
		if( positions.size() > 1 )
			positions.pop();
		return getCurrentPosition();
	}
	public Position backIfN(int n){
		Position position = getCurrentPosition();
		if(n == position.n)
			return back();
		return position;
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
