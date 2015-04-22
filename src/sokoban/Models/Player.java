package sokoban.Models;

import sokoban.Constants;


public class Player extends Block  {
	public void init(int x, int y) {
		PlayerPosition position = new PlayerPosition(x, y, 0, null);
		positions.push(position);
	}
	public int move(int direction, Box box){
		int x = getX();
		int y = getY();
		int n = getN();
		switch(direction){
		case Constants.UP:
			x--;
			break;
		case Constants.DOWN:
			x++;
			break;
		case Constants.LEFT:
			y--;
			break;
		case Constants.RIGHT:
			y++;
			break;
		default:
			break;
		}
		PlayerPosition position = new PlayerPosition(x, y, n+1, box);
		positions.push(position);
		return 0;
	}
	public Position back(){
		if( positions.size() > 1 ){
			PlayerPosition pos = (PlayerPosition) positions.pop();
			if(pos.box != null)
				pos.box.back();
		}
		return getCurrentPosition();
	}
}
