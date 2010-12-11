package model;

public class MiddleRowEnemy extends Enemy {
	
	String[] imageList = {"Alien10.gif","Alien11.gif"};

	public MiddleRowEnemy(int pW, int pH) {
		  super(pW,pH);
	}
	
	public int getPointsValue() { return 30; }
	
	public String getImage(int num) { return imageList[num]; }
	
}
