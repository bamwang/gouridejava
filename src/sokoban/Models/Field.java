package sokoban.Models;

public class Field {
	private int limit;
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
}
