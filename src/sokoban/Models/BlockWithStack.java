package sokoban.Models;

import java.util.Stack;

public class BlockWithStack extends Block{
	protected Stack<Position> positions = new Stack<Position>();
	public BlockWithStack(){
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
