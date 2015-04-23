package sokoban.Models;

import java.util.HashMap;

public class BlockSet<T> {
	protected HashMap<Integer,  T> blockHash = new HashMap<Integer,  T>(); 
	protected int calcKey(int x, int y){
		return (x << 7) + y;
	}
	public T get(int x, int y){
		return blockHash.get(calcKey(x, y));
	}
	public void add(int x, int y, T block) {
		int key = calcKey(x, y);
		this.blockHash.put(key, block);
	}
}
