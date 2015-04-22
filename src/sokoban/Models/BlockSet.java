package sokoban.Models;

import java.util.HashMap;

public class BlockSet {
	protected HashMap<Integer,  Block> blockHash = new HashMap<Integer,  Block>(); 
	protected int calcKey(int x, int y){
		return (x << 7) + y;
	}
	public Block get(int x, int y){
		return blockHash.get(calcKey(x, y));
	}
	public void add(int x, int y, Block block) {
		int key = calcKey(x, y);
		this.blockHash.put(key, block);
	}
}
