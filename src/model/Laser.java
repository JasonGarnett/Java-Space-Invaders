package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import utilities.SIProps;
import sound.*;

public class Laser extends SpaceObject{

	LaserState state;
	static SIProps props = SIProps.getInstance();
	static Laser instance = new Laser(Integer.parseInt(props.getProperty("PWIDTH")),Integer.parseInt(props.getProperty("PHEIGHT")));
	Ship ship;
	EnemyBlock enemies;
	BarrierBlock barriers;
	UFO ufo;
	private boolean canFire = true;
	//private LaserState explodedState; // = new ExplodedState();
	//private LaserState readyState; // = new ReadyState();
	//private LaserState firedState;
	Image explosion = getImgLoader().getImage("Burst.gif");
	Image bomb = getImgLoader().getImage("bomb.png");
	
	  public Laser(int pW, int pH) {
		  super(pW,pH);
		  
		  setSizeX(Integer.parseInt(props.getProperty("LASER_SIZE_X")));
		  setSizeY(Integer.parseInt(props.getProperty("LASER_SIZE_Y")));
		  
		  setxVelocity(0);
		  setReady();

	  } 
	  
	 public static Laser getInstance() { return instance; }
	  
	  public boolean canFire() { return canFire; }
	  
	  
	  public void move(){
		  state.move();
	 }
	  
	  public void setEnviron(Ship s, EnemyBlock b, BarrierBlock bar, UFO u) { 
		  ship = s; 
		  enemies = b;
		  barriers = bar;
		  ufo = u;
	  
	 	}
	  
	  public void setFired() { 
		  state = new FiredState(); 
		  canFire = false;
	  }
	  
	  public void setReady() { 
		  state = new ReadyState();
		  canFire = true;
	  }
	  
	  public void setExploded(SpaceObject o) {
		  new PlayClip("Explosion0.wav");
		  state =  new ExplodedState(o);
		  canFire = false;
	  }
	  
	  public void draw(Graphics g) { 
			state.draw(g);
			
		 }

	  interface LaserState {
		  public void draw(Graphics g);
		  public void move();
		  
	  }
	  
	  class ReadyState implements LaserState{
		  
		  public void move(){
			  setyVelocity(0);
			  setX(ship.getX()+(Integer.parseInt(props.getProperty("SHIP_SIZE_X"))/2));
			  setY(ship.getY());

		  }
		  
		  public void draw(Graphics g) {}
	  }
	  
	  class FiredState implements LaserState{
		  
		  public void move(){
			  setyVelocity(Integer.parseInt(props.getProperty("LASER_VELOCITY")));
			  setY(getY()-getyVelocity());
			    if (getY()< 0) {
			    	//instance.setReady();
			    	instance.setExploded(instance);
			    	System.out.println("WENT OFF SCREEN!");
			    }
			    
			    else if (enemies.didHit(instance)) {
			    	
			    	instance.setExploded(enemies.getLastHit());
			    	System.out.println("BOOM ON ENEMY!!");
			    }
			    else if (barriers.didHit(instance)) {
			    	instance.setExploded(barriers);
			    	System.out.println("BOOM ON BARRIER!!");
			    	
			    
			   }
			    
			    else if (ufo.didHit(instance)) {
			    	instance.setExploded(ufo);
			    	System.out.println("BOOM ON UFO!!");
			    }
			    	
			    
			   }
		  
		  public void draw(Graphics g) { 
				g.setColor(Color.RED);
				g.fillRect((int)getX(), (int)getY(), getSizeX(), getSizeY());
				
			 }
		  
	  }
	  
	  class ExplodedState implements LaserState{
		  
		  int counter;
		  SpaceObject explodedOn;
		  
		  ExplodedState() {
			  counter = 0;
		  }
		  
		  ExplodedState(SpaceObject o) { explodedOn = o; }
		  
		  
		  public void move(){
			    setyVelocity(0);
			    
			    counter++;
			    
			    if (counter >= 20)
			    	instance.setReady();
			   }
		  
		  public void draw(Graphics g) { 
			//  	Image im = Toolkit.getDefaultToolkit().getImage("images/explosion.png");
			//	g.drawImage(explosion, (int)getX()-(getSizeX()/2), (int)getY()-(getSizeY()/2), Integer.parseInt(props.getProperty("SHIP_SIZE_X")), 
				//		Integer.parseInt(props.getProperty("SHIP_SIZE_Y")), null);
			  g.drawImage(explosion, (int)explodedOn.getX(), (int)explodedOn.getY(), explodedOn.getSizeX(), 
						explodedOn.getSizeY(), null);
			  
			  
			 }
	  }

}

