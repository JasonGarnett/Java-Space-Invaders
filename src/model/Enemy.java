package model;

import java.util.Random;

public class Enemy extends SpaceObject{

	boolean spriteShape = true;
	private int column;
	private int row;
	 
	  public Enemy(int pW, int pH) {
		  super(pW,pH);
		  
		  setSizeX(Integer.parseInt(getProps().getProperty("ENEMY_SIZE_X")));
		  setSizeY(Integer.parseInt(getProps().getProperty("ENEMY_SIZE_Y")));
		  
		setyVelocity(0);
		Random rand = new Random();
		setxVelocity(rand.nextDouble()* Integer.parseInt(getProps().getProperty("SQUARE_VELOCITY")));
		while (!inBounds()){
			setX(rand.nextDouble()*pW);
			setY(rand.nextDouble()*pH);
		}

		setImageName("null");
	  } 
	
	  public int getPointsValue() { return 1000; }
	  
	  public int getColumn() { return column; }
	  
	  public void setColumn(int c) { column = c; }
	  
	  public int getRow() { return row; }
	  
	  public void setRow(int r) { row = r; }
	  
	  public String getImage(int num) { return "null"; }
	  
	  
	  void flipSprite() {
		  spriteShape = !spriteShape;
		  if (spriteShape)
			  setImageName(getImage(0));
		  if (!spriteShape)
			  setImageName(getImage(1));
	  }
	
		
}


