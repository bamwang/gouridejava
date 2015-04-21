package sokoban;

public class Constants {
	// privateコンストラクタでインスタンス生成を抑止
	private Constants(){} 
	// 定数
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	public static final char WALL = '#';
	public static final char GOAL = '.';
	public static final char SPACE = ' ';
}
