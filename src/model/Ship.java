package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import sound.*;

public class Ship extends SpaceObject{

	private int movementSpeed = Integer.parseInt(getProps().getProperty("SHIP_MOVEMENT_SPEED"));
//	private int lives = Integer.parseInt(getProps().getProperty("STARTING_NUM_LIVES"));
	private Laser laser = Laser.getInstance();
	private SpaceInvadersLevel lvl;
	
	  public Ship(int pW, int pH) {
		  super(pW,pH);
		  
		  setSizeX(Integer.parseInt(getProps().getProperty("SHIP_SIZE_X")));
		  setSizeY(Integer.parseInt(getProps().getProperty("SHIP_SIZE_Y")));
		  
		  
		  
		setyVelocity(0);
		Random rand = new Random();
		setxVelocity(rand.nextDouble()* Integer.parseInt(getProps().getProperty("SQUARE_VELOCITY")));
		while (!inBounds()){
			setX(rand.nextDouble()*pW);
			setY(getpHeight()-100.0);
		}
		
		setImageName("Player.gif");
	  } 
	  
	  public void setLevel(SpaceInvadersLevel l) { lvl = l; }
	  
	  public void moveRight() {

		  if (!((getX()+getSizeX()) > getpWidth()))
		  	  setX(getX()+movementSpeed);
	  }
	  
	  public void moveLeft() {
		  if (getX()>0)
			  setX(getX()-movementSpeed);
	  }

	  public void fireLaser() {
		  if (laser.canFire()) {
			  System.out.println("ZAP!");
			  laser.setFired();
			  new PlayClip("shoot.wav");
		  }
		  else System.out.println("Cant shoot, blocked!");
		  
	  }
	  
	//  public int getLives() { return lives; }
	  
	  public void move(){}
	  
	  public boolean didHit(SpaceObject e){

			if (didCollide(e)) {
				lvl.setLives(lvl.getLives()-1);
				return true;
			}
			else return false;
		}
	  
	  public void drawLives(Graphics g) {
		  g.setColor(Color.WHITE);
		  g.drawString(lvl.getLives()+"", 20, getpHeight()-20);
		  //Image im = Toolkit.getDefaultToolkit().getImage(getImageName());
		  g.setColor(Color.GREEN);
		  for (int x=0; x<=lvl.getLives()-2; x++)
			  g.drawImage(getImgLoader().getImage(getImageName()), 60+(x*(getSizeX()+20)), getpHeight()-40, getSizeX(), getSizeY(), null);
	  }
	  
}
