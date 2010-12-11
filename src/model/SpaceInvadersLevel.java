package model;

import java.util.ArrayList;

import utilities.LevelFactory;
import utilities.SIProps;

public class SpaceInvadersLevel {
	
	private EnemyBlock enemies;
	private Ship ship;
	private UFO ufo;
	private int level;
	private Score score = Score.getInstance();
	private int loopNumber;
	private int pHeight;
	private int pWidth;
	private ArrayList<SpaceObject> objects;
	private int player;
	private SIProps props = SIProps.getInstance();
	private int lives = Integer.parseInt(props.getProperty("STARTING_NUM_LIVES"));
	private boolean gameOver = false;
	
	public SpaceInvadersLevel(int lvl, int p, int pH, int pW) {
		level = lvl;
		player = p;
		pHeight = pH;
		pWidth = pW;
		
		setupLevel();
		
	}
	
	public boolean isGameOver() { return gameOver; }
	
	public void setGameOver() { gameOver= true; }
	
	 private void setupLevel() {
		
	 	LevelFactory lFactory = new LevelFactory(pWidth,pHeight);

	 	enemies = lFactory.getEnemies();
	 	double velocityToAdd = level * (Double.parseDouble(props.getProperty("VELOCITY_INCREMENT"))*2);
	 	enemies.setxVelocity(enemies.getxVelocity()+velocityToAdd);
	 	
	 	for (int x=0; x<= level-2; x++) {
	 		enemies.dropDownOneLevel();
	 		enemies.dropDownOneLevel();
	 	}
	 		 	
		ship = lFactory.getShip();
		ufo = lFactory.getUFO();
		objects = lFactory.getLevelSetup(ship, enemies,ufo);
		ship.setLevel(this);  
		
	  }

	 public void setActive() {
		 
		Laser laser = Laser.getInstance();
		laser.setEnviron(ship, enemies, (BarrierBlock)objects.get(3), ufo);
						
		Bomb bomb = Bomb.getInstance();
		bomb.setEnviron(ship, enemies, (BarrierBlock)objects.get(3), laser);
		 
	 }
	 
	/**
	 * @return the enemies
	 */
	public EnemyBlock getEnemies() {
		return enemies;
	}


	public int getLives() { return lives; }
	
	public void setLives(int l) { lives = l; }
	/**
	 * @return the ship
	 */
	public Ship getShip() {
		return ship;
	}


	/**
	 * @return the ufo
	 */
	public UFO getUfo() {
		return ufo;
	}


	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
		setupLevel();
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		if (player==1)
			return score.getPlayerOneScore();
		else return score.getPlayerTwoScore();
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Score score) {
		this.score = score;
	}

	/**
	 * @return the loopNumber
	 */
	public int getLoopNumber() {
		return loopNumber;
	}

	/**
	 * @param loopNumber the loopNumber to set
	 */
	public void setLoopNumber(int loopNumber) {
		this.loopNumber = loopNumber;
	}

	/**
	 * @return the pHeight
	 */
	public int getpHeight() {
		return pHeight;
	}

	/**
	 * @return the pWidth
	 */
	public int getpWidth() {
		return pWidth;
	}

	/**
	 * @return the objects
	 */
	public ArrayList<SpaceObject> getObjects() {
		return objects;
	}

	/**
	 * @param objects the objects to set
	 */
	public void setObjects(ArrayList<SpaceObject> objects) {
		this.objects = objects;
	}

	/**
	 * @return the player
	 */
	public int getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(int player) {
		this.player = player;
	}
	
	
}
