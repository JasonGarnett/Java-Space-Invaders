package model;

import java.awt.Graphics;

import sound.PlayClip;

public class UFO extends SpaceObject {

	private boolean startFromLeftSide = false;
	Score score = Score.getInstance();
	
	public UFO(int pW, int pH) {
		super(pW, pH);
		
		 
		  setSizeX(Integer.parseInt(getProps().getProperty("UFO_SIZE_X")));
		  setSizeY(Integer.parseInt(getProps().getProperty("UFO_SIZE_Y")));
		  
		setyVelocity(0);

		setxVelocity(Integer.parseInt(getProps().getProperty("UFO_VELOCITY")));
		
		setActive(false);
		setY(50);
		
		setImageName("ufo.png");

	}
	
	private void startUFO() {
		setActive(true);
		startFromLeftSide = !startFromLeftSide;
		if (startFromLeftSide)
			setX(0);
		else setX(getpWidth());
		
		
	}
	
	
	 void flipSprite() {
		  new PlayClip("ringout.wav");
	  }
	
	@Override
	  public void move(){
		moveCounterPlusOne();
		  //moveCounterPlusOne();
		  if ((getMoveCounter()%Integer.parseInt(getProps().getProperty("UFO_FREQUENCY")))==0) {
			  startUFO();
			  System.out.println("Starting UFO on move counter "+getMoveCounter());
			  
		  }
		  if (isActive() && (getMoveCounter()%Integer.parseInt(getProps().getProperty("SPRITE_FLIP_FREQUENCY")))==0)
			  flipSprite();

		  if (isActive() && (getX() <0) || (getX() > getpWidth()))
				  setActive(false);

		  if (isActive()) {
			  if (startFromLeftSide)
				  setX(getX()+Integer.parseInt(getProps().getProperty("UFO_VELOCITY")));
			  else setX(getX()-Integer.parseInt(getProps().getProperty("UFO_VELOCITY")));
			  
			  
			  
		  }
		  /*
		   for (Enemy e: enemies) {
		  	e.setX(e.getX()+ getxVelocity());
		    e.setY(e.getY() + getyVelocity());
		    
		    if ((e.getY() + e.getSizeY()) >= getpHeight()) {
		    	
		    	hitBottom = true;
		    	System.out.println("HIT BOTTOM");
		    }
		    
		    if (ship.didHit(e))
		    	System.out.println("HIT SHIP");
		    
		    if (barriers.didHit(e))
		    	System.out.println("HIT BARRIERS");
		    
		    
	  }
		    
		    if ((overShotX(rightEdge) > 0) || (underShotX(leftEdge)>0)) {
		    	System.out.println("I should be bouncing back right now");
		    	setxVelocity(-getxVelocity());
		    	dropDownOneLevel();

		    }*/
		
		}
	
	public boolean didHit(SpaceObject e){

		if (isActive() && didCollide(e)) {
			setActive(false);
			score.addScore(getPointsValue());
			
			return true;
		}
		else return false;
	}	
 
	
	public void draw(Graphics g) { 
		//Image im = Toolkit.getDefaultToolkit().getImage(imageName);
		if (isActive())
			g.drawImage(getImgLoader().getImage(getImageName()),(int)getX(), (int)getY(), getSizeX(), getSizeY(), null);
		
	 }  // end of draw()
	
	
	 public int getPointsValue() { return 300; }
	
	 
}
