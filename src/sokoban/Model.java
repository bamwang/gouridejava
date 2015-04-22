package sokoban;

import sokoban.Models.BoxSet;
import sokoban.Models.Field;
import sokoban.Models.Player;

public class Model {
	private Field field = new Field();
	private BoxSet boxSet = new BoxSet();
	private Player player = new Player();
	public Model() {
		
	}
	public Field getField() {
		return field;
	}
	public BoxSet getBoxSet() {
		return boxSet;
	}
	public Player getPlayer() {
		return player;
	}
}
