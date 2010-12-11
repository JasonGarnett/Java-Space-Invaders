package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import utilities.*;

public class SpaceObject {
	
	  private SIProps props = SIProps.getInstance();
	  private int sizeX;
	  private int sizeY;
	  private double xVelocity;
	  private double yVelocity;
	  private double x = 0;
	  private double y = 0;
	  private int pWidth;
	  private int pHeight;
	  private String imageName;
	  private boolean active = true;
	  private ImageLoader imgLoader = ImageLoader.getInstance();
	  private long moveCounter = 0;
	  
	  public SpaceObject(int pW, int pH) {
		  
		  pWidth = pW;
		  pHeight = pH;
		  
	  }
	  
	  public SIProps getProps() {
			return props;
		}

		public void setProps(SIProps props) {
			this.props = props;
		}

		public ImageLoader getImgLoader() { return imgLoader; }

		public int getSizeX() {
			return sizeX;
		}

		public long getMoveCounter() { return moveCounter; }
		
		public void moveCounterPlusOne() { moveCounter++; }

		public void setSizeX(int sizeX) {
			this.sizeX = sizeX;
		}


		public int getSizeY() {
			return sizeY;
		}


		public void setSizeY(int sizeY) {
			this.sizeY = sizeY;
		}


		public int getpWidth() {
			return pWidth;
		}


		public void setpWidth(int pWidth) {
			this.pWidth = pWidth;
		}


		public int getpHeight() {
			return pHeight;
		}


		public void setpHeight(int pHeight) {
			this.pHeight = pHeight;
		}


		public String getImageName() {
			return imageName;
		}


		public void setImageName(String imageName) {
			this.imageName = imageName;
		}
	  
	  
	  /**
	 * @return the xVelocity
	 */
	public double getxVelocity() {
		return xVelocity;
	}


	/**
	 * @param xVelocity the xVelocity to set
	 */
	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}


	/**
	 * @return the yVelocity
	 */
	public double getyVelocity() {
		return yVelocity;
	}


	/**
	 * @param yVelocity the yVelocity to set
	 */
	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}


	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}


	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}


	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}


	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}


	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}


	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}


	double overShotX() {
		  
		  return (x+sizeX)-pWidth;
	  }
	  
	  double overShotY() {
		  return (y+sizeY)-pHeight;
	  }
	  
	  double underShotX() {
		  return 0-x;
	  }
	  
	  double underShotY() {
		  return 0-y;
	  }
	  
	  boolean inBounds() {
		  return (overShotX() < 0) && (overShotY() < 0) && (underShotX() <0) && (underShotY() < 0);
		  
	  }
	  
	public void move(){
		moveCounter++;
	    x += xVelocity;
	    y += yVelocity;
	    if ((overShotX() > 0) || (underShotX()>0)) {
	    	if (overShotX() > 0)
	    		x -= overShotX();
	    	else x += underShotX();
	    	
	    	xVelocity = -xVelocity;
	    }
	    else if ((overShotY() > 0) || underShotY() >0 ) {
	    	if (overShotY() > 0)
	    		y -= overShotY();
	    	else y += underShotY();
	   
	    	yVelocity = -yVelocity;
	    }
	
	}

	public void draw(Graphics g) { 
		//Image im = Toolkit.getDefaultToolkit().getImage(imageName);
		g.drawImage(imgLoader.getImage(imageName), (int)x, (int)y, sizeX, sizeY, null);
		
	 }  // end of draw()
	

	public boolean didHit(SpaceObject e){

		if (didCollide(e)) {
			active = false;
			return true;
		}
		else return false;
	}
		/*
	public boolean didCollide(SpaceObject otherObject){
		if ((otherObject.x >= x && otherObject.x <= x + sizeX) && (otherObject.y >= y && otherObject.y <= y + sizeY))
			return true;
		else return false;
	}*/
	
	
	// Algorithm taken from: http://wiki.gamedev.net/index.php/Simple_2D_collision_algorithms
	public boolean didCollide(SpaceObject other) {
	  if (x+sizeX < other.x) 
		  return false;
	  if (x > other.x+other.sizeX)
		  return false;
	  if (y+sizeY < other.y) 
		  return false;
	  if (y > other.y+other.sizeY) 
		  return false;
	 
	  return true;
}
	
	
}