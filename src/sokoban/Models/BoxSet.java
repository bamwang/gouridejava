package sokoban.Models;
import java.util.HashMap;

import sokoban.Constants;

public class BoxSet extends BlockSet{
	GoalSet goals = new GoalSet();
	int remain = 0;
//	public void add(Box box) {
//		int x = box.getCurrentPosition().x;
//		int y = box.getCurrentPosition().y;
//		int key = calcKey(x, y);
//		this.blockHash.put(key, box);
//	}
	public void addBox(int x, int y, int n) {
		Box box = new Box(x, y, n);
		super.add(x, y, box);
	}
	public HashMap<Integer,Block> getAll(){
		return blockHash;
	}
	public Box get(int x, int y){
		return (Box) super.get(x, y);
	}
	public int move(Box box, int direction){
		Position pos = box.getCurrentPosition();
		int x = pos.getX();
		int y = pos.getY();
		int key = calcKey(x, y);
		int newX = x;
		int newY = y;
		switch (direction) {
		case Constants.UP:
			newX--;
			break;
		case Constants.DOWN:
			newX++;
			break;
		case Constants.LEFT:
			newY--;
			break;
		case Constants.RIGHT:
			newY++;
			break;
		default:
			break;
		}
		int newKey = calcKey(newX, newY);
		blockHash.remove(key);
		blockHash.put(newKey, box);
		boolean BeforeMove = isOnTheGoal(box);
		box.move(direction);
		boolean afterMove = isOnTheGoal(box);
		if (BeforeMove) {
			remain ++;
		}
		if (afterMove) {
			remain --;
		}
//		System.out.println("remain:" + remain);
		return remain;
	}
	public void addGoal( int x, int y ){
		Goal goal = new Goal(x, y);
		goals.add(x, y, goal);
		remain ++;
	}
	public boolean isOnTheGoal(Box box){
		int x = box.getCurrentPosition().x;
		int y = box.getCurrentPosition().y;
		Goal goal = goals.get(x, y);
		if( goal == null )
			return false;
		if(goal.getX() == x && goal.getY() == y)
			return true;
		else
			return false;
	}
}
