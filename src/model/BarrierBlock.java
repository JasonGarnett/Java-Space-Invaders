package model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import utilities.*;


public class BarrierBlock extends SpaceObject {
	
	ArrayList<Barrier> barriers = new ArrayList<Barrier>();
	Barrier lastHit;
	int width;
	
	public BarrierBlock(int pW, int pH) {
		  super(pW,pH);

		  width = pW;
		  
		  setyVelocity(0); 
		  setxVelocity(0);
		
		  initBarrierBlock();
	  } 
	
	public Barrier getLastHit() { return lastHit; }
	
	private void initBarrierBlock() {
		  
		int blockSize = Integer.parseInt(getProps().getProperty("BARRIER_SIZE_X"));
		int numberOfBlocks = Integer.parseInt(getProps().getProperty("NUMBER_OF_BARRIERS"));
		double seperation = (width-(blockSize*numberOfBlocks))/(numberOfBlocks+1);
	//	double seperation = 64;
		double xPos = seperation;
			  
			  for (int x=0; x<= numberOfBlocks-1; x++) {
				Barrier temp = new Barrier(getpWidth(), getpHeight());
				temp.setX(xPos);
				temp.setY(getpHeight()-200);
				temp.initComponents();
				barriers.add(temp);
				xPos += (seperation+blockSize);
		  }
	}

	  public void move(){}
	  
	  public void draw(Graphics g) { 
		  for (Barrier e: barriers)
			e.draw(g);
			
		 } 

	  @Override
	  public boolean didHit(SpaceObject other){
			if ((other.getY() >= (getpHeight() - 200))) {
				for (Barrier b: barriers)
					if (b.isActive() && b.didHit(other)) {
						lastHit = b;
						return true;
					}
				
			}
			
			return false;
		}
}
