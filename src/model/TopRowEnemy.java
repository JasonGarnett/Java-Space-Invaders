package model;

public class TopRowEnemy extends Enemy {

	String[] imageList = {"Alien20.gif","Alien21.gif"};
	
	public TopRowEnemy(int pW, int pH) {
		  super(pW,pH);
	}
	
	public int getPointsValue() { return 50; }
	
	public String getImage(int num) { return imageList[num]; }
	
}
