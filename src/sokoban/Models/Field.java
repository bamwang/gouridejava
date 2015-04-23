package sokoban.Models;

import sokoban.Constants;

public class Field {
	private int limit = Constants.UNLIMITIED;
	private ArrayList2D<Character> data;
	public void init(ArrayList2D<Character> map){
		data = map;
	}
	@SuppressWarnings("unchecked")
	public ArrayList2D<Character> getClonedField(){
		return (ArrayList2D<Character>) data.clone();
	}
	public Character getField(int x, int y){
		char a = data.get(x, y);
//		System.out.println(a);
		return a;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getLimit() {
		return limit;
	}
}
