package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import controller.SpaceInvaderPanel;
import sound.PlayClip;
import utilities.SIProps;

public class Bomb extends SpaceObject{

	BombState state;
	static SIProps props = SIProps.getInstance();
	static Bomb instance = new Bomb(Integer.parseInt(props.getProperty("PWIDTH")),Integer.parseInt(props.getProperty("PHEIGHT")));
	Ship ship;
	EnemyBlock enemies;
	Enemy platform;
	BarrierBlock barriers;
	Image explosion = getImgLoader().getImage("Burst.gif");
	Image groundExplosion = getImgLoader().getImage("groundExplosion.png");
	//Image bomb = getImgLoader().getImage("bomb_1.png");
	String[] imageList = {"bomb_1.png","bomb_2.png"};
	int bottomLine;
	boolean spriteShape = true;
	Laser laser;
	
	  public Bomb(int pW, int pH) {
		  super(pW,pH);
		  
		  setSizeX(Integer.parseInt(props.getProperty("BOMB_SIZE_X")));
		  setSizeY(Integer.parseInt(props.getProperty("BOMB_SIZE_Y")));
		  
		  bottomLine = pH - Integer.parseInt(props.getProperty("BOTTOM_LINE"));
		  
	//	yVelocity = Integer.parseInt(props.getProperty("LASER_VELOCITY"));
		setxVelocity(0);
		//movingVelocity = Integer.parseInt(props.getProperty("LASER_VELOCITY"));
		
		setReady();
		
		//imageName = "images/monster.png";
	  } 
	  
	public static Bomb getInstance() { return instance; }
	  
	  public void move(){
		  state.move();
	 }
	  
	  public String getImage(int num) { return imageList[num]; }
	  
	  public void setEnviron(Ship s, EnemyBlock b, BarrierBlock bar, Laser l) { 
		  ship = s; 
		  enemies = b;
		  platform = b.getRightEdge();
		  barriers = bar;
		  laser = l;
	  
	 	}
	  
	  void flipSprite() {
		  spriteShape = !spriteShape;
		  if (spriteShape)
			  setImageName(getImage(0));
		  if (!spriteShape)
			  setImageName(getImage(1));
	  }
	  
	//  public double getX() { return x; }
	  
	 // public void setX(double x){ this.x = x; }
	 // public void setY(double y){ this.y = y; }
	  
	  public void setPlatform(Enemy e) {platform = e; }
	  
	  public void setFired() { state = new FiredState(this); }
	  
	  public void setReady() { state = new ReadyState(this); }
	  
	  public void setExploded() { 
		  new PlayClip("Explosion0.wav");
		  state = new ExplodedState(this); }
	  
	  public void setGroundExploded() {
		  new PlayClip("Explosion0.wav");
		  state = new GroundExplodedState(this); }
	  
	  public void draw(Graphics g) { 
			state.draw(g);
			
		 }

	  interface BombState {
		  
		  public void draw(Graphics g);
		  public void move();
		  
	  }
	  
	  class ReadyState implements BombState{
		  
		  Bomb bomb;
		  
		  ReadyState (Bomb b) {
			  bomb = b;
		  }
		  
		  public void move(){
			  setyVelocity(0);
			  setX(platform.getX()+(Integer.parseInt(props.getProperty("SHIP_SIZE_X"))/2));
			  setY(platform.getY());

		  }
		  
		  public void draw(Graphics g) {}
	  }
	  
	  class FiredState implements BombState{
		  
		  Bomb bomb;
		  
		  FiredState (Bomb b) {
			  bomb = b;
		  }
		  
		  public void move(){
			  moveCounterPlusOne();
			  if ((getMoveCounter()%Integer.parseInt(getProps().getProperty("SPRITE_FLIP_FREQUENCY")))==0)
				  flipSprite();
			  setyVelocity(Integer.parseInt(props.getProperty("BOMB_VELOCITY")));
			  setY(getY()+getyVelocity());
			//  System.out.println("BOmb dropping, height="+y);
			  
			    if ((getY()+getSizeY())>= bottomLine) {
			    	setGroundExploded();
			    	System.out.println("BOMB MISSED");
			    }
			    
			    else if (ship.didHit(bomb)) {
			    	setExploded();
			    	System.out.println("BOOM!!");
			    }
			    else if (barriers.didHit(bomb)) {
			    	setExploded();
			    	System.out.println("BOOM!");
			    }
			    else if (laser.didHit(bomb)) {
			    	setExploded();
			    	System.out.println("BOOM!");
			    }
			    
			   }
		  
		  public void draw(Graphics g) { 
				//Image im = Toolkit.getDefaultToolkit().getImage("images/bomb.png");
				g.drawImage(getImgLoader().getImage(getImageName()), (int)getX(), (int)getY(), Integer.parseInt(props.getProperty("BOMB_SIZE_X")), 
						Integer.parseInt(props.getProperty("BOMB_SIZE_Y")), null);
				
			 }
		  
	  }
	  
class ExplodedState implements BombState{

	int counter;
	Bomb bomb;
	
	ExplodedState(Bomb b) {
		bomb = b;
		counter = 0;
	}
		  
		  public void move(){
			   setyVelocity(0);
			   counter++;
			   if (counter >= 20) 
				   setReady();
			   }
		  
		  public void draw(Graphics g) { 
			  	//Image im = Toolkit.getDefaultToolkit().getImage("images/explosion.png");
				g.drawImage(explosion, (int)getX(), (int)getY(), Integer.parseInt(props.getProperty("SHIP_SIZE_X")), 
						Integer.parseInt(props.getProperty("SHIP_SIZE_Y")), null);
				
			 }
	  }
	  
class GroundExplodedState implements BombState{
	
	int counter;
	Bomb bomb;
	
		GroundExplodedState(Bomb b) {
			bomb = b;
			counter = 0;
		}
	
		  public void move(){
			   setyVelocity(0);
			   counter++;
			   if (counter >= 20)
				   setReady();
			   }
		  
		  public void draw(Graphics g) { 
			  	//Image im = Toolkit.getDefaultToolkit().getImage("images/explosion.png");
				g.drawImage(groundExplosion, (int)getX()-(Integer.parseInt(props.getProperty("SHIP_SIZE_Y"))/2), bottomLine-Integer.parseInt(props.getProperty("SHIP_SIZE_Y")), 
						Integer.parseInt(props.getProperty("SHIP_SIZE_X")), 
						Integer.parseInt(props.getProperty("SHIP_SIZE_Y")), null);
				
			 }
	  }

}


