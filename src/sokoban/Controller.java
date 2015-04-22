package sokoban;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import sokoban.Models.ArrayList2D;
import sokoban.Models.Block;
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
	// private final int M = 8;
	// private final int N = 8;

	private final ArrayList<String> presetMap = new ArrayList<String>(
			Arrays.asList("########", "#      #", "#      #", "# o..o.#",
					"#    p #", "# o    #", "#      #", "########"));

	public void setModelsByMapData(ArrayList<String> mapData) {
		if (mapData == null)
			mapData = presetMap;
		ArrayList2D<Character> map = new ArrayList2D<Character>();
		int height = mapData.size();
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
				default:
					break;
				}
				// System.out.print(map[i][j]);
			}
			// System.out.println();
		}
		field.init(map);
	}

	private ArrayList<String> getMapDataFromFile(String fileName) {
		File file = new File(fileName);
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(file));
			ArrayList<String> mapData = new ArrayList<String>();
			String line = br.readLine();
			while (line != null) {
				mapData.add(line);
				line = br.readLine();
			}
			return mapData;
		} catch (Exception e) {
			return null;
		}
	}

	public void draw() {
		ArrayList2D<Character> map = (ArrayList2D<Character>) field
				.getClonedField();
		Position playerPosition = player.getCurrentPosition();
		map.set(playerPosition.getX(), playerPosition.getY(), 'P');
		System.out.println(playerPosition.getX());
		System.out.println(playerPosition.getY());

		HashMap<Integer, Block> hash = boxSet.getAll();

		hash.forEach((i, block) -> {
			Box box = (Box) block;
			map.set(box.getX(), box.getY(), 'o');
		});
		int width = map.getWidth();
		int height = map.getWidth();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(map.get(i, j));
			}
			System.out.println();
		}
	}

	public boolean move(int direction) {
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
				return boxSet.move(toBeMovedBox, direction) == 0;
			}
		}
		return false;
	}

	public void undo() {
		player.back();
	}

	public void keyListener() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int c = 0;
		boolean isWin = false;
		try {
			while (c != 3) {
				c = in.read();
				switch (c) {
				case 119:
					isWin = move(Constants.UP);
					break;
				case 115:
					isWin = move(Constants.DOWN);
					break;
				case 97:
					isWin = move(Constants.LEFT);
					break;
				case 100:
					isWin = move(Constants.RIGHT);
					break;
				case 117:
					undo();
					break;
				case 114:
					reset();
					break;
				case 10:
					continue;
				default:
					break;
				}
				if (isWin){
					System.out.println("YOU WIN");
				}
				System.out.println(c);
				draw();
			}
			in.close();
		} catch (Exception e) {

		}

	}

	public void reset() {
		model = new Model();
		field = model.getField();
		boxSet = model.getBoxSet();
		player = model.getPlayer();
		init();
	}

	public void init() {
		System.out.println("/Users/a13894/Desktop/test.txt");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {

			String fileName = in.readLine();
			ArrayList<String> mapData = getMapDataFromFile(fileName);
			setModelsByMapData(mapData);
			draw();
			keyListener();
		} catch (IOException e) {
			e.printStackTrace();
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
