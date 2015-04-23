package sokoban.Models;

public class GoalSet extends BlockSet<Goal>{
	public void addGoal(int x, int y) {
		Goal goal = new Goal(x ,y);
		super.add(x, y, goal);
	}
	public Goal get(int x, int y){
		return super.get(x, y);
	}
}
