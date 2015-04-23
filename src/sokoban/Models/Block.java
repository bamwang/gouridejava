package sokoban.Models;

import java.util.Stack;

public abstract class Block {
	protected Stack<Position> positions = new Stack<Position>();
	public Block(){
	}
	public Position getCurrentPosition(){
		return positions.peek();
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
