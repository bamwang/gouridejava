package sokoban.Models;

import java.util.HashMap;

public class BoxSet {
	private HashMap<Integer,  Box> boxHash = new HashMap<Integer,  Box>(); 
	private int calcKey(int x, int y){
		return x << 7 + y;
	}
	public void add(Box box) {
		int x = box.getCurrentPosition().x;
		int y = box.getCurrentPosition().y;
		int key = calcKey(x, y);
		this.boxHash.put(key, box);
	}
	public Box get(int x, int y){
		return boxHash.get(calcKey(x, y));
	}
	public HashMap<Integer, Box> getAll(){
		return boxHash;
	}
}
