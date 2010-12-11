package model;

public class BottomRowEnemy extends Enemy {

	String[] imageList = {"Alien01.gif","Alien00.gif"};
	
	public BottomRowEnemy(int pW, int pH) {
		  super(pW,pH);
	}

	public int getPointsValue() { return 10; }
	
	public String getImage(int num) { return imageList[num]; }
	
}
