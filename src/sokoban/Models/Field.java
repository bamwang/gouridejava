package sokoban.Models;
import java.util.ArrayList;

public class Field {
	private ArrayList<Goal> goals;
	private char field[][];
	public void init(char[][] map, ArrayList<Goal> goals){
		/**
		 * @param map[][] 地図の
		 * 
		 * 
		 * 
		 * **/
		this.field = map.clone();
		this.goals = goals;
	}
	public char[][] getField(){
		return field;
	}
	public char getField(int x, int y){
		return field[x][y];
	}
	public ArrayList<Goal> getGoals(){
		return goals;
	}
}
