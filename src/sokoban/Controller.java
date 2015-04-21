package sokoban;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import sokoban.Models.Box;
import sokoban.Models.BoxSet;
import sokoban.Models.Field;
import sokoban.Models.Goal;
import sokoban.Models.Player;
import sokoban.Models.Position;

public class Controller {
	private static Field field = new Field();
	private static BoxSet boxSet = new BoxSet();
	private static Player player = new Player();
	private static final int M = 8;
	private static final int N = 8;
	
	private static final String presetMap[] = {
	                                           "########",
	                                           "##  ####",
	                                           "##     #",
	                                           "##o.. .#",
	                                           "#  ##p##",
	                                           "# oo  ##",
	                                           "#     ##",
	                                           "########"
	};

	public Controller() {
	}

	public static void setModelsByMapData(String mapData[]) {
		if( mapData == null )
			mapData = presetMap;
		char map[][] = new char[M][N];
		ArrayList<Goal> goalList = new ArrayList<Goal>();
		for (int i = 0; i < mapData.length; i++) {
			int length = mapData[i].length();
			for (int j = 0; j < length; j++) {
				
				char c = mapData[i].charAt(j);
				switch ((char) c) {
				case '#':
					map[i][j] = Constants.WALL;
					break;
				case '.':
					map[i][j] = Constants.GOAL;
					goalList.add(new Goal(i, j));
					break;
				case 'p':
					map[i][j] = Constants.SPACE;
					player.init(i, j);
					break;
				case 'o':
					map[i][j] = Constants.SPACE;
					Box box = new Box();
					box.init(i, j);
					boxSet.add(box);
				default:
					map[i][j] = Constants.SPACE;
					break;
				}
//				System.out.print(map[i][j]);
			}
//			System.out.println();
		}
		field.init(map, goalList);
	}

	private static String getMapDataFromFile(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName)));
			String mapData = br.toString();
			br.close();
			return mapData;
		} catch (Exception e) {
			return null;
		}
	}

	public void draw() {
		char[][] map;
		map = field.getField();
		Position playerPosition = player.getCurrentPosition();
//		playerPosition.toString();
		map[playerPosition.getX()][playerPosition.getY()] = 'P';
		System.out.println(playerPosition.getX());
		System.out.println(playerPosition.getY());
		
		HashMap<Integer, Box> hash = boxSet.getAll();
		
		hash.forEach((i, box)->{
			map[box.getX()][box.getY()] = 'o';
		});
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}
	
	public static void move(int direction){
		int x = player.getX();
		int y = player.getY();
		boolean toMove = false;
		
		boolean isUp = direction == Constants.UP;
		boolean isDown = direction == Constants.DOWN;
		boolean isLeft = direction == Constants.LEFT;
		boolean isRight = direction == Constants.RIGHT;
		boolean isUpSpace = field.getField(x-1, y) == Constants.SPACE;
		boolean isDownSpace = field.getField(x+1, y) == Constants.SPACE;
		boolean isLeftSpace = field.getField(x, y-1) == Constants.SPACE;
		boolean isRightSpace = field.getField(x, y+1) == Constants.SPACE;
		boolean isUp2Space = field.getField(x-2, y) == Constants.SPACE;
		boolean isDown2Space = field.getField(x+2, y) == Constants.SPACE;
		boolean isLeft2Space = field.getField(x, y-2) == Constants.SPACE;
		boolean isRight2Space = field.getField(x, y+2) == Constants.SPACE;
		Box toBeMovedBox = null;
		Box upBox = boxSet.get(x-1, y);
		Box downBox = boxSet.get(x+1, y);
		Box leftBox = boxSet.get(x, y-1);
		Box rightBox = boxSet.get(x, y+1);
		boolean isUpBox = upBox != null;
		boolean isDownBox = downBox != null;
		boolean isLeftBox = leftBox != null;
		boolean isRightBox = rightBox != null;
		if ( isUp && ( ( isUpSpace && !isUpBox ) || ( isUp2Space && isUpBox ) ) ) {
	        toMove = true;
	        if ( isUpBox ) {
	            toBeMovedBox = upBox;
	        }
	    }else if( isDown && ( ( isDownSpace && !isDownBox )|| ( isDown2Space && isDownBox ) ) ){
	    	toMove = true;
	        if ( isDownBox ) {
	            toBeMovedBox = downBox;
	        }
	    }else if ( isLeft && ( ( isLeftSpace && !isLeftBox )|| ( isLeft2Space && isLeftBox ) ) ) {
	    	toMove = true;
	        if ( isLeftBox ) {
	            toBeMovedBox = leftBox;
	        }
	    }else if( isRight && ( ( isRightSpace && !isRightBox ) || ( isRight2Space && isRightBox ) ) ){
	    	toMove = true;
	        if ( isRightBox ) {
	            toBeMovedBox = rightBox;
	        }
	    }
		if( toMove ){
			player.move(direction);
			if( toBeMovedBox != null )
				toBeMovedBox.move(direction);
		}
	}
	
	public static void test(){
		Controller controller = new Controller();
		setModelsByMapData(null);
		controller.draw();
//		getMapDataFromFile(null);
		move(Constants.UP);
		controller.draw();
		move(Constants.UP);
		controller.draw();
		controller.draw();
	}
}
