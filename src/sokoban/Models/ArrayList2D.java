package sokoban.Models;

import java.util.ArrayList;

public class ArrayList2D<E> extends ArrayList<E>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int width = 0;
	protected int height = 0;
	public ArrayList2D() {
		super();
	}
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public void add(int i, int j, E element){
		if ( j > width )
			width = j + 1;
		if ( j > height )
			height = i + 1;
		super.add(i * width + j, element);
	}
	public void set(int i, int j, E element) {
		if ( j > width )
			width = j + 1;
		if ( j > height )
			height = i + 1;
		super.set(i * width + j, element);
	}
	public E get(int i, int j){
		return super.get(i * width + j);
	}
}
