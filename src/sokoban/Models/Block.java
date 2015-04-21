package sokoban.Models;

import java.util.Stack;

import sokoban.Constants;

public class Block {
	protected Stack<Position> positions = new Stack<Position>();
	public void init(int x, int y) {
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
	public Position getCurrentPosition(){
		return positions.peek();
	}

	public Position back(){
		positions.pop();
		return getCurrentPosition();
	}
	public Position backIfN(int n){
		Position position = getCurrentPosition();
		if(n == position.n)
			return back();
		return position;
	}
	public int getX() {
		return getCurrentPosition().x;
	}
	public int getY() {
		return getCurrentPosition().y;
	}
	public int getN() {
		return getCurrentPosition().n;
	}
}
