package utilities;

import java.util.ArrayList;
import java.util.HashMap;

import model.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class LevelFactory {

//	EnemyBlock enemies;
//	Ship ship;
	private int width;
	private int height;
	private SIProps props = SIProps.getInstance();
	private String[] imageNames = { "barrier.png","bomb.png","explosion.png","monster.png","monster_2.png","monster2.png",
									"monster2_2.png","monster3.png","monster3_2.png","ship.png","ufo.png", "bomb_1.png",
									"bomb_2.png","groundExplosion.png", "Player.gif", "Alien00.gif","Alien01.gif","Alien10.gif",
									"Alien11.gif","Alien20.gif","Alien21.gif","Alien3.gif","Burst.gif"};
	private int level;
	
	//private HashMap<String, Image> imageMap = new HashMap<String, Image>();
	
	public LevelFactory() {}
	
	public LevelFactory(int w, int h){
		
	//	enemies = e;
	//	ship = s;
		width = w;
		height = h;
	//	level = lvl;
			
	}
	
	public Ship getShip() {
		return new Ship(width,height);
	}
	
	public EnemyBlock getEnemies() {
		return new EnemyBlock(width,height);
	}
	
	public UFO getUFO() {
		return new UFO(width,height);
	}
	
	public HashMap<String,Image> loadImages() {
		HashMap<String,Image> temp = new HashMap<String,Image>();
	
		for (String s: imageNames)
			temp.put(s,Toolkit.getDefaultToolkit().getImage("images/"+s));
		
		//Image im = Toolkit.getDefaultToolkit().getImage(imageName);
		//g.drawImage(im, (int)x, (int)y, sizeX, sizeY, null);
		
		return temp;
	}

	
	public ArrayList<SpaceObject> getLevelSetup(Ship ship, EnemyBlock enemies, UFO ufo) {
		ArrayList<SpaceObject> temp = new ArrayList<SpaceObject>();
		
		temp.add(enemies);
		temp.add(ship);
		temp.add(ufo);
		
		//ufo.setShip(ship);
		enemies.setShip(ship);
		
		BarrierBlock bar = new BarrierBlock(width,height);
		temp.add(bar);
		
		enemies.setBarriers(bar);
		
		Laser laser = Laser.getInstance();
		laser.setEnviron(ship, enemies, bar, ufo);
		temp.add(laser);
		
		Bomb bomb = Bomb.getInstance();
		bomb.setEnviron(ship, enemies, bar, laser);
		temp.add(bomb);
		
		
		return temp;
	}
	
	 public ArrayList<Enemy> initEnemyArrayList() {
		 
		 ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		 
		  int currentXPos = Integer.parseInt(props.getProperty("ENEMY_BLOCK_STARTING_X"));
		  int currentYPos = Integer.parseInt(props.getProperty("ENEMY_BLOCK_STARTING_Y"));
			  
		  for (int y=0; y<=Integer.parseInt(props.getProperty("ENEMY_BLOCK_NUMBER_OF_ROWS"))-1; y++) {
			  
			  for (int x=0; x<= Integer.parseInt(props.getProperty("ENEMY_BLOCK_ROW_LENGTH"))-1; x++) {
				 Enemy temp;
				 
				 if (y==0)
					 temp = new TopRowEnemy(width,height);
				 else if (y==1 || y==2)
					 temp = new MiddleRowEnemy(width,height);
				 else temp = new BottomRowEnemy(width,height);
				 
				 temp.setX(currentXPos);
				 temp.setY(currentYPos);
				 temp.setxVelocity(Double.parseDouble(props.getProperty("INITIAL_VELOCITY")));
				 temp.setColumn(x);
				 enemies.add(temp);
				 currentXPos += temp.getSizeX()+Integer.parseInt(props.getProperty("ENEMY_BLOCK_ROW_SPACE_SIZE"));
			  	}
			  currentXPos = Integer.parseInt(props.getProperty("ENEMY_BLOCK_STARTING_X"));
			  currentYPos += (Integer.parseInt(props.getProperty("ENEMY_SIZE_Y")) + Integer.parseInt(props.getProperty("ENEMY_BLOCK_COLUMN_SPACE_SIZE")));
		  }
		  
		  return enemies;
		  
	  }
	
	
	
}
