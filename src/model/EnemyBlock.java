package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

import sound.PlayClip;
import utilities.*;

public class EnemyBlock extends SpaceObject{

	private ArrayList<Enemy> enemies;
	private Enemy rightEdge; 
	private Enemy leftEdge;
	private Bomb bomb = Bomb.getInstance();
	private Random rand = new Random();
	private Score score = Score.getInstance();
	private int stillAlive;
	private boolean hitBottom = false;
	private Ship ship;
	private BarrierBlock barriers;
	private LevelFactory factory;
	private SoundLoader soundLoader;
	private Enemy lastHit;
	private int level;
	
	  public EnemyBlock(int pW, int pH) {
		  super(pW,pH);
		  
		  setSizeX((Integer.parseInt(getProps().getProperty("ENEMY_BLOCK_ROW_LENGTH"))*Integer.parseInt(getProps().getProperty("ENEMY_SIZE_X"))) +
				  ((Integer.parseInt(getProps().getProperty("ENEMY_BLOCK_ROW_LENGTH"))-1)*Integer.parseInt(getProps().getProperty("ENEMY_BLOCK_ROW_SPACE_SIZE"))));

		  setSizeY((Integer.parseInt(getProps().getProperty("ENEMY_BLOCK_NUMBER_OF_ROWS"))*Integer.parseInt(getProps().getProperty("ENEMY_SIZE_Y"))) +
				  ((Integer.parseInt(getProps().getProperty("ENEMY_BLOCK_NUMBER_OF_ROWS"))-1)*Integer.parseInt(getProps().getProperty("ENEMY_BLOCK_COLUMN_SPACE_SIZE"))));

		  
		  stillAlive = Integer.parseInt(getProps().getProperty("ENEMY_BLOCK_NUMBER_OF_ROWS"))*
				  	   Integer.parseInt(getProps().getProperty("ENEMY_BLOCK_ROW_LENGTH"));
		  
		setyVelocity(0);
		setxVelocity(Double.parseDouble(getProps().getProperty("INITIAL_VELOCITY")));
		
		//soundLoader = new SoundLoader();
		
		factory = new LevelFactory(pW,pH);
		
		enemies = factory.initEnemyArrayList();

		leftEdge = enemies.get(0);
		rightEdge = enemies.get(enemies.size()-1);

	  } 
	  

	  public Enemy getRightEdge() {
		return rightEdge;
	}


	public void setRightEdge(Enemy rightEdge) {
		this.rightEdge = rightEdge;
	}


	public Enemy getLeftEdge() {
		return leftEdge;
	}

	public void setLevel(int lvl) { level = lvl; }
	
	public void setLeftEdge(Enemy leftEdge) {
		this.leftEdge = leftEdge;
	}


	public void setShip(Ship s) {ship = s; }
	  
	  public void setBarriers(BarrierBlock b) {barriers = b;}
	 	  
	  double overShotX(Enemy e) {
		  
		  return (e.getX()+e.getSizeX())-getpWidth();
	  }
	  
	  double underShotX(Enemy e) {
		  return 0-e.getX();
	  }
	  
	  void flipSpriteGroup() {
		  new PlayClip("hit.wav");
		  for (Enemy e: enemies)
			  e.flipSprite();
		  
	  }
	  
	  private void dropBomb() {

		  Enemy bombadier = enemies.get(rand.nextInt(enemies.size()-1));
		  while (!bombadier.isActive())
				bombadier = enemies.get(rand.nextInt(enemies.size()-1));

		  bomb.setPlatform(bombadier);
		  bomb.setX(bombadier.getX());
		  bomb.setY(bombadier.getY());
		  bomb.setFired();
		  
	  }
	  
	  public boolean hitTheEnd() {
		  return hitBottom;
	  }
	  
	  
	  public int getStillAlive() { return stillAlive; }

	  @Override
	  public void move(){
		  
		  moveCounterPlusOne();
		  //if ((getMoveCounter()%Integer.parseInt(getProps().getProperty("SPRITE_FLIP_FREQUENCY")))==0)
		  if ((getMoveCounter()%(int)(20/getxVelocity()))==0)
			  flipSpriteGroup();
		  
		  if ((getMoveCounter()%Integer.parseInt(getProps().getProperty("BOMB_FREQUENCY")))==0) {
			  dropBomb();
		  }
			  
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

		    }
		
		}
	  
	  public Enemy getLastHit() { return lastHit; }
	  
	  public void dropDownOneLevel() {
		  System.out.println("Dropping down one level");
		  for (Enemy e: enemies) {
			  	if (e.isActive())
			  		e.setY(e.getY()+ Integer.parseInt(getProps().getProperty("DROP_INCREMENT")));
		  }
	  }
	  @Override
	  public void draw(Graphics g) { 
		  for (Enemy e: enemies) {
			if (e.isActive())
			  e.draw(g);
		  }
			
		 }  // end of draw()
	  
	  
	  private Enemy findNewRightEdge() {
		  Enemy temp = enemies.get(0);
		  for (Enemy e: enemies){
			  if (e.getColumn() > temp.getColumn() && e.isActive())
				  temp = e;
		  }
		  
		  return temp;
	  }

	  private Enemy findNewLeftEdge() {
		  Enemy temp = enemies.get(enemies.size()-1);
		 		  
		  for (Enemy e: enemies) {
			  if (e.getColumn() < temp.getColumn() && e.isActive())
				  temp = e;
			  
		  }
		  
		  return temp;
		  
	  }
	  
	  private void tookHit(Enemy e) {
		 	score.addScore(e.getPointsValue());
			stillAlive--;
			if (getxVelocity() > 0)
				setxVelocity(getxVelocity()+Double.parseDouble(getProps().getProperty("VELOCITY_INCREMENT")));
			else setxVelocity(getxVelocity()-Double.parseDouble(getProps().getProperty("VELOCITY_INCREMENT")));
			
			if (e==rightEdge)
				rightEdge = findNewRightEdge();
			else if (e == leftEdge)
				leftEdge = findNewLeftEdge();
			
			System.out.println("Enemy died in column: "+e.getColumn());

			
			
	  }
	  @Override
	  public boolean didHit(SpaceObject other){
			if ((other.getX() >= leftEdge.getX() && other.getX() <= leftEdge.getX() + getSizeX()) && 
					(other.getY() >= leftEdge.getY() && other.getY() <= leftEdge.getY() + getSizeY())) {
				for (Enemy e: enemies)
					if (e.isActive() && e.didHit(other)) {
						lastHit = e;
						tookHit(e);
						

						return true;
					}
				
			}
			
			return false;
		}
	  
}