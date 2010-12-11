package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Barrier extends SpaceObject{

	private ArrayList<BarrierComponent> components = new ArrayList<BarrierComponent>();
	
	  public Barrier(int pW, int pH) {
		  super(pW,pH);
		  
		  setSizeX(Integer.parseInt(getProps().getProperty("BARRIER_SIZE_X")));
		  setSizeY(Integer.parseInt(getProps().getProperty("BARRIER_SIZE_Y")));
		  
		  setyVelocity(0);
		  setxVelocity(0);

	  } 

	  public void initComponents() {
		  double currentXPos = getX();
		  double currentYPos = getY();
		  
		  for (int y=0; y<=2; y++) {
			  
			  for (int x=0; x<= 4; x++) {
				 BarrierComponent temp = new BarrierComponent(getpWidth(),getpHeight());
				 temp.setX(currentXPos);
				 temp.setY(currentYPos);
			//	 System.out.println("New barrier component created at :["+currentXPos+","+currentYPos+"]");
				 components.add(temp);
				 currentXPos += temp.getSizeX();
			  	}
			  currentXPos = getX();
			  currentYPos += (getSizeY()/3);
		  }

		  components.get(0).setActive(false);
		  components.get(4).setActive(false);
		  components.get(components.size()-3).setActive(false);
		  
	  }
	  
	  public void move(){}
	  
	  public boolean didHit(SpaceObject other){
			for (BarrierComponent b: components){
				if (b.isActive() && b.didHit(other)) {
						b.setActive(false);
						System.out.println("HIT COMPONENT ["+b.getX()+","+b.getY()+"]");
						return true;
					}
				
			}
			
			return false;
		}
	  
	  public void draw(Graphics g) { 
			g.setColor(Color.GREEN);
			for (BarrierComponent b: components)
				if (b.isActive())
					b.draw(g);
			
		 }
	  
	  
	  class BarrierComponent extends SpaceObject{

			
			public BarrierComponent(int pW, int pH) {
				  super(pW,pH);
				  
				  setSizeX(Integer.parseInt(getProps().getProperty("BARRIER_SIZE_X"))/5);
				  setSizeY(Integer.parseInt(getProps().getProperty("BARRIER_SIZE_Y"))/3);
				  setyVelocity(0);
				  setxVelocity(0);

			  } 

			  public void move(){}
			  
			  public void draw(Graphics g) { 

				  g.setColor(Color.BLACK);
				  g.drawRect((int)getX(), (int)getY(), getSizeX(), getSizeY());
				  g.setColor(Color.GREEN);
				  g.fillRect((int)getX(), (int)getY(), getSizeX(), getSizeY());

					
			  }
	  }
}

