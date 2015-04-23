package sokoban;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import sokoban.Models.ArrayList2D;
import sokoban.Models.Box;
import sokoban.Models.BoxSet;
import sokoban.Models.Field;
import sokoban.Models.Player;
import sokoban.Models.Position;

public class Controller {
	private Model model = new Model();
	private Field field = model.getField();
	private BoxSet boxSet = model.getBoxSet();
	private Player player = model.getPlayer();
	private final String division = "--------"; 
	private final String STATUS_FILE = "/tmp/status.dat";
	// private final int M = 8;
	// private final int N = 8;

	private final ArrayList<String> presetMap = new ArrayList<String>(
			Arrays.asList("########", "#      #", "#      #", "# o..o.#",
					"#    p #", "# o    #", "#      #", "########","<30000"));

	private void setModelsByMapData(ArrayList<String> mapData) {
		if (mapData == null)
			mapData = presetMap;
		ArrayList2D<Character> map = new ArrayList2D<Character>();
		int height = mapData.size();
		String limit = null;
		for (int i = 0; i < height; i++) {
			int width = mapData.get(i).length();
			for (int j = 0; j < width; j++) {
				map.add(i, j, Constants.SPACE);
				char c = mapData.get(i).charAt(j);
				switch ((char) c) {
				case '#':
					map.set(i, j, Constants.WALL);
					break;
				case '.':
					map.set(i, j, Constants.GOAL);
					boxSet.addGoal(i, j);
					break;
				case 'p':
					player.init(i, j);
					break;
				case 'o':
					boxSet.addBox(i, j, 0);
					break;
				case '<':
					limit = mapData.get(i);
					break;
				default:
					break;
				}
				// System.out.print(map[i][j]);
			}
			if (limit != null){
				int limitN = Integer.parseInt(limit.substring(1));
				try {
					System.out.println("Do you want to set a limit(" + limitN + ")?");
					
					while(true){
						int userInput = System.in.read();
						if (userInput == 121) {
							field.setLimit(limitN);
							break;
						}else if( userInput == 110)
							break;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		field.init(map);
	}

	private ArrayList<String> getMapDataFromFile(String fileName) {
		File file = new File(fileName);
		try( BufferedReader br = new BufferedReader(new FileReader(file)) ) {
			ArrayList<String> mapData = new ArrayList<String>();
			String line = br.readLine();
			while (line != null) {
				mapData.add(line);
				line = br.readLine();
			}
			return mapData;
		} catch (Exception e) {
			System.out.println("Cannot open the file, load preset map.");
			return null;
		}
	}

	private void draw() {
		ArrayList2D<Character> map = (ArrayList2D<Character>) field
				.getClonedField();
		Position playerPosition = player.getCurrentPosition();
		map.set(playerPosition.getX(), playerPosition.getY(), 'P');

		HashMap<Integer, Box> hash = boxSet.getAll();

		hash.forEach((i, block) -> {
			Box box = (Box) block;
			map.set(box.getX(), box.getY(), 'o');
		});
		int playerN = player.getN();
		int limit = field.getLimit();
		if( limit > 0 )
			System.out.println("Limit:" + playerN + "/" + limit);
		else
			System.out.println("Unlimit mode");
		int width = map.getWidth();
		int height = map.getWidth();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(map.get(i, j));
			}
			System.out.println();
		}
	}

	private void move(int direction) {
		int x = player.getX();
		int y = player.getY();
		boolean toMove = false;

		boolean isUp = direction == Constants.UP;
		boolean isDown = direction == Constants.DOWN;
		boolean isLeft = direction == Constants.LEFT;
		boolean isRight = direction == Constants.RIGHT;
		boolean isUpSpace = field.getField(x - 1, y) != Constants.WALL;
		boolean isDownSpace = field.getField(x + 1, y) != Constants.WALL;
		boolean isLeftSpace = field.getField(x, y - 1) != Constants.WALL;
		boolean isRightSpace = field.getField(x, y + 1) != Constants.WALL;
		boolean isUp2Space = isUpSpace ? field.getField(x - 2, y) != Constants.WALL
				: false;
		boolean isDown2Space = isDownSpace ? field.getField(x + 2, y) != Constants.WALL
				: false;
		boolean isLeft2Space = isLeftSpace ? field.getField(x, y - 2) != Constants.WALL
				: false;
		boolean isRight2Space = isRightSpace ? field.getField(x, y + 2) != Constants.WALL
				: false;
		Box toBeMovedBox = null;
		Box upBox = boxSet.get(x - 1, y);
		Box downBox = boxSet.get(x + 1, y);
		Box leftBox = boxSet.get(x, y - 1);
		Box rightBox = boxSet.get(x, y + 1);
		Box up2Box = boxSet.get(x - 2, y);
		Box down2Box = boxSet.get(x + 2, y);
		Box left2Box = boxSet.get(x, y - 2);
		Box right2Box = boxSet.get(x, y + 2);
		boolean isUpBox = upBox != null;
		boolean isDownBox = downBox != null;
		boolean isLeftBox = leftBox != null;
		boolean isRightBox = rightBox != null;
		boolean isUp2Box = up2Box != null;
		boolean isDown2Box = down2Box != null;
		boolean isLeft2Box = left2Box != null;
		boolean isRight2Box = right2Box != null;
		if (isUp
				&& ((isUpSpace && !isUpBox) || (isUp2Space && isUpBox && !isUp2Box))) {
			toMove = true;
			if (isUpBox) {
				toBeMovedBox = upBox;
			}
		} else if (isDown
				&& ((isDownSpace && !isDownBox) || (isDown2Space && isDownBox && !isDown2Box))) {
			toMove = true;
			if (isDownBox) {
				toBeMovedBox = downBox;
			}
		} else if (isLeft
				&& ((isLeftSpace && !isLeftBox) || (isLeft2Space && isLeftBox && !isLeft2Box))) {
			toMove = true;
			if (isLeftBox) {
				toBeMovedBox = leftBox;
			}
		} else if (isRight
				&& ((isRightSpace && !isRightBox) || (isRight2Space
						&& isRightBox && !isRight2Box))) {
			toMove = true;
			if (isRightBox) {
				toBeMovedBox = rightBox;
			}
		}
		if (toMove) {
			player.move(direction, toBeMovedBox);
			if (toBeMovedBox != null) {
				boxSet.move(toBeMovedBox, direction);
			}
		}
	}

	private void undo() {
		player.back();
	}
	
	private boolean isGameOver(){
		return player.getCurrentPosition().getN() == field.getLimit() ;
	}
	
	private boolean isWin(){
		return boxSet.getRemain() == 0;
	}

	private void keyListener() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int c = 0;
		try {
			while (c != 3) {
				c = in.read();
				switch (c) {
				case 119:
					move(Constants.UP);
					break;
				case 115:
					move(Constants.DOWN);
					break;
				case 97:
					move(Constants.LEFT);
					break;
				case 100:
					move(Constants.RIGHT);
					break;
				case 117:
					undo();
					break;
				case 112:
					exportStatus();
					break;
				case 114:
					reset();
					init();
					break;
				case 10:
					continue;
				default:
					break;
				}
				if (isWin()){
					System.out.println("YOU WIN");
				}else if (isGameOver()){
					System.out.println("Limit exceesed");
				}
//				System.out.println(c);
				draw();
			}
			in.close();
		} catch (Exception e) {

		}

	}

	private void reset() {
		model = null;
		model = new Model();
		field = model.getField();
		boxSet = model.getBoxSet();
		player = model.getPlayer();
	}
	
	private boolean isStatusFound(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	private void init() {
		if( isStatusFound(STATUS_FILE) ){
			try {
				System.out.println("Found saved status, load?");
				while(true){
					int userInput = System.in.read();
					if (userInput == 121) {
						importStatus(STATUS_FILE);
						break;
					}else if( userInput == 110){
						loadPreset();
						break;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			loadPreset();
		}
		draw();
		keyListener();
	}
	private void loadPreset(){
		System.out.println("Please input filename for map: Ex. /Users/a13894/Desktop/test.txt");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			String fileName = in.readLine();
			ArrayList<String> mapData = getMapDataFromFile(fileName);
			setModelsByMapData(mapData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void exportStatus(){
		ArrayList2D<Character> fieldData = field.getClonedField();
		File file = new File(STATUS_FILE);
		try( FileWriter fw = new FileWriter(file) ) {
			int width = fieldData.getWidth();
			int height = fieldData.getHeight();
			
			for (int i = 0; i < height; i++) {
				String temp = "";
				for (int j = 0; j < width; j++) {
					temp += fieldData.get(i, j);
				}
				if (temp != null) {
					fw.write(temp + "\n");
				}
			}
			fw.write(division + "\n");
			fw.write(player.getX() + "\n");
			fw.write(player.getY() + "\n");
			fw.write(player.getN() + "\n");
			fw.write(field.getLimit() + "\n");
			HashMap<Integer, Box> hash = boxSet.getAll();
			for (Entry<Integer, Box> entry : hash.entrySet()) {
			    Box box = entry.getValue();
			    fw.write(box.getX() + "\n");
				fw.write(box.getY() + "\n");
				fw.write(box.getN() + "\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean importStatus(String fileName){
		File file = new File(fileName);
		if( !file.exists() )
			return false;
		try( FileReader fr = new FileReader(file) ) {
			BufferedReader br = new BufferedReader(fr);
			ArrayList<String> field = new ArrayList<String>();
			String temp = br.readLine();
			while (!temp.equals(division)) {
				field.add(temp);
				temp = br.readLine();
			}
			setModelsByMapData(field);
			player.init(Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()));
			this.field.setLimit(Integer.parseInt(br.readLine()));
			temp = br.readLine();
			while (temp!=null) {
				boxSet.addBox(Integer.parseInt(temp), Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()));
				temp = br.readLine();
			}
			fr.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public static void test() {
		Controller controller = new Controller();
		controller.init();
		// controller.draw();
		// controller.move(Constants.UP);
		// controller.draw();
		// controller.move(Constants.UP);
		// controller.draw();
		// controller.draw();
	}
}
