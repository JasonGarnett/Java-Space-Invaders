package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.*;
import utilities.*;

import javax.swing.JPanel;

public class SpaceInvaderPanel extends JPanel implements Runnable{

	private SIProps props = SIProps.getInstance();
	private int PWIDTH = Integer.parseInt(props.getProperty("PWIDTH"));  
	private int PHEIGHT = Integer.parseInt(props.getProperty("PHEIGHT")); 
	private long MAX_STATS_INTERVAL = Long.parseLong(props.getProperty("MAX_STATS_INTERVAL"));
	private int NO_DELAYS_PER_YIELD = Integer.parseInt(props.getProperty("NO_DELAYS_PER_YIELD")); 
	private int MAX_FRAME_SKIPS = Integer.parseInt(props.getProperty("MAX_FRAME_SKIPS")); 
	private int NUM_FPS = Integer.parseInt(props.getProperty("NUM_FPS")); 
	private long statsInterval = 0L;    
	private long prevStatsTime;   
	private long totalElapsedTime = 0L;
	private long gameStartTime;
	private int timeSpentInGame = 0;  
	private long frameCount = 0;
	private double fpsStore[];
	private long statsCount = 0;
	private double averageFPS = 0.0;
	private long framesSkipped = 0L;
	private long totalFramesSkipped = 0L;
	private double upsStore[];
	private double averageUPS = 0.0;
	private DecimalFormat df = new DecimalFormat("0.##"); 
	private Thread animator;           
	private boolean running = false;  
	private boolean isPaused = false;
	private long period;                
    private SpaceInvader wcTop;
	private boolean gameOver = false;
	private Font font;
	private FontMetrics metrics;
	private Graphics dbg; 
	private Image dbImage = null;
	private double bottomLine = PHEIGHT- Integer.parseInt(props.getProperty("BOTTOM_LINE"));
	private boolean isMovingRight = false;
	private boolean isMovingLeft = false;
	private boolean isFiring = false;
	private Score score = Score.getInstance();
	private boolean onePlayer;
	private boolean inIntro;
	private SpaceInvadersLevel playerOne;
	private SpaceInvadersLevel playerTwo;
	private SpaceInvadersLevel whoIsPlaying;
	private SpaceInvadersLevel whoIsntPlaying;
	private int whoIsPlayingLevel = 1;
	private int whoIsPlayingLives = 3;
	private boolean justSwitchedOrStarted;
	private int switchedCounter = 0;

	  public SpaceInvaderPanel(SpaceInvader wc, long period){
	  
		  startGame(wc,period);
	  /*  wcTop = wc;
	    this.period = period;

	    setBackground(Color.WHITE);
	    setPreferredSize( new Dimension(PWIDTH, PHEIGHT));

	    setFocusable(true);
	    requestFocus();    // the JPanel now has focus, so receives key events
		readyForTermination();
		moveShip();
		selectOneOrTwoPlayers();

	    // create game components
		paintIntroScreen();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//setupLevel(level);
		
		playerOne = new SpaceInvadersLevel(1,1,PHEIGHT,PWIDTH);
		whoIsPlaying = playerOne;
		whoIsntPlaying = null;
		
	    // set up message font
	    font = new Font("CourierNew", Font.BOLD, Integer.parseInt(props.getProperty("MESSAGE_FONT_SIZE")));
	    metrics = this.getFontMetrics(font);

	    // initialise timing elements
	    fpsStore = new double[NUM_FPS];
	    upsStore = new double[NUM_FPS];
	    for (int i=0; i < NUM_FPS; i++) {
	      fpsStore[i] = 0.0;
	      upsStore[i] = 0.0;
	    }*/
	  } 
	  
	 void startGame(SpaceInvader wc, long period) {
		  wcTop = wc;
		    this.period = period;

		    setBackground(Color.WHITE);
		    setPreferredSize( new Dimension(PWIDTH, PHEIGHT));

		    setFocusable(true);
		    requestFocus();    // the JPanel now has focus, so receives key events
			readyForTermination();
			moveShip();
			selectOneOrTwoPlayers();

		    // create game components
			paintIntroScreen();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//setupLevel(level);
			
			playerOne = new SpaceInvadersLevel(1,1,PHEIGHT,PWIDTH);
			whoIsPlaying = playerOne;
			whoIsntPlaying = null;
			
		    // set up message font
		    font = new Font("CourierNew", Font.BOLD, Integer.parseInt(props.getProperty("MESSAGE_FONT_SIZE")));
		    metrics = this.getFontMetrics(font);

		    // initialise timing elements
		    fpsStore = new double[NUM_FPS];
		    upsStore = new double[NUM_FPS];
		    for (int i=0; i < NUM_FPS; i++) {
		      fpsStore[i] = 0.0;
		      upsStore[i] = 0.0;
		    }
	  }
	  
	  private void readyForTermination() {
		addKeyListener( new KeyAdapter() {
	       public void keyPressed(KeyEvent e)
	       { int keyCode = e.getKeyCode();
	         if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q) ||
	             (keyCode == KeyEvent.VK_END) ||
	             ((keyCode == KeyEvent.VK_C) && e.isControlDown()) ) {
	           running = false;
	         }
	       }
	     });
	  }  // end of readyForTermination()

	  private void selectOneOrTwoPlayers() {
			addKeyListener( new KeyAdapter() {
		       public void keyPressed(KeyEvent e)
		       {
		    	   int keyCode = e.getKeyCode();

		         if (keyCode == KeyEvent.VK_1) {
		        		 inIntro = false;
		        		 onePlayer = true;
		        		// playerOne = new SpaceInvadersLevel(1,1,PHEIGHT,PWIDTH);
		        		// whoIsPlaying = playerOne;
		        		 justSwitchedOrStarted = true;
		         }
		         else if (keyCode == KeyEvent.VK_2) {
	        		 inIntro = false;
	        		 onePlayer = false;
	        		// playerOne = new SpaceInvadersLevel(1,1,PHEIGHT,PWIDTH);
	        		 playerTwo = new SpaceInvadersLevel(1,2,PHEIGHT,PWIDTH);
	        		 whoIsntPlaying = playerTwo;
	        		 playerOne.setActive();
	        		// whoIsPlaying = playerOne;
	        		 justSwitchedOrStarted = true;
		         }
		         				        		
		       }
		     });
		  } 
	  
	  private void startOver() {
			addKeyListener( new KeyAdapter() {
		       public void keyPressed(KeyEvent e)
		       {
		    	   int keyCode = e.getKeyCode();

		         if (keyCode == KeyEvent.VK_ENTER) {
		        	 paintIntroScreen();
		         }				        		
		       }
		     });
		  } 
	  
	  private void moveShip() {
			addKeyListener( new KeyAdapter() {
				
		       public void keyPressed(KeyEvent e)
		       { int keyCode = e.getKeyCode();
		       
		       if (keyCode == KeyEvent.VK_SPACE) {
		    	   isFiring = true;
		    	//   ship.fireLaser();
		       }
		       else if (keyCode == KeyEvent.VK_LEFT) {
		    	   isMovingLeft = true;
		    	  // ship.moveLeft();

		       }
		       else if (keyCode == KeyEvent.VK_RIGHT){
		    	   //ship.moveRight();
		    	   isMovingRight = true;
		    	   
		       }

		       }		     
		       
		       
		       
		       public void keyReleased(KeyEvent e){
		    	   int keyCode = e.getKeyCode();
		    	   if (keyCode == KeyEvent.VK_SPACE) 
			    	   isFiring =false;
			       else if (keyCode == KeyEvent.VK_LEFT) 
			    	   isMovingLeft = false;
			       else if (keyCode == KeyEvent.VK_RIGHT)
			    	   isMovingRight = false;	    	   
			    	   
		       }
			
			});
			
	}
	 
	  public void addNotify()
	  // wait for the JPanel to be added to the JFrame before starting
	  { super.addNotify();   // creates the peer
	    startGame();         // start the thread
	  }


	  private void startGame()
	  // initialize and start the thread 
	  { 
	    if (animator == null || !running) {
	      animator = new Thread(this);
		  animator.start();
	    }
	  } // end of startGame()
	    

	  // ------------- game life cycle methods ------------
	  // called by the JFrame's window listener methods


	  public void resumeGame()
	  // called when the JFrame is activated / deiconified
	  {  isPaused = false;  } 


	  public void pauseGame()
	  // called when the JFrame is deactivated / iconified
	  { isPaused = true;   } 


	  public void stopGame() 
	  // called when the JFrame is closing
	  {  running = false;   }
	  

	  boolean checkWinConditions() {
		  
		  if (whoIsPlaying.getEnemies().getStillAlive() == 0)
			  return true;
		  else return false;
	  }
	  
	  boolean checkLossConditions() {
		   
		  if (whoIsPlaying.getLives() <= 0)
			  return true;
		  else if (whoIsPlaying.getEnemies().hitTheEnd())
			  return true;
		  else return false;
	  }
	  
	  boolean checkGameOverConditions() {
		  
		  if (onePlayer)
			  return playerOne.isGameOver();
		  else 
			  return playerOne.isGameOver() && playerTwo.isGameOver();
		  
		  
	  }
	  
	  private void switchPlayers() {
		  if ((whoIsPlaying==playerOne) && !playerTwo.isGameOver()) {
			  System.out.println("*** SWITCHING TO PLAYER TWO ***");
			  whoIsPlaying = playerTwo;
			  whoIsntPlaying = playerOne;
			  whoIsPlayingLives = playerTwo.getLives();
			  whoIsPlayingLevel = playerTwo.getLevel();
			  playerTwo.setActive();
		  }
		  else if ((whoIsPlaying==playerTwo) && !playerOne.isGameOver()) {
			  System.out.println("*** SWITCHING TO PLAYER ONE ***");
			  whoIsPlaying = playerOne;
			  whoIsntPlaying = playerTwo;
			  whoIsPlayingLives = playerOne.getLives();
			  whoIsPlayingLevel = playerOne.getLevel();
			  playerOne.setActive();
		  }
		  score.switchPlayers();
		  justSwitchedOrStarted = true;
		  switchedCounter = 0;
		  
	  }
	  
	  private boolean checkIfShouldSwitchPlayers() {
		  if (onePlayer)
			  return false;
		  else if (whoIsPlaying.getLevel() > whoIsPlayingLevel) {
			  return true;
		  }
		  else if (whoIsPlaying.getLives() < whoIsPlayingLives) {
			  System.out.println("***This player lost a life, switching to the other player now***");
			  return true;
		  }
		  else return false;
	  }
	  
	  
	  public void run()
	  /* The frames of the animation are drawn inside the while loop. */
	  {
	    long beforeTime, afterTime, timeDiff, sleepTime;
	    long overSleepTime = 0L;
	    int noDelays = 0;
	    long excess = 0L;

	    gameStartTime = System.nanoTime();
	    prevStatsTime = gameStartTime;
	    beforeTime = gameStartTime;

		running = true;
		
		// main loop

		inIntro = true;
		
		//playerOne.setActive();
		
		int gameOverLoop = 0;
		
		while(running) {
		//	if (loopNumber > 250)
			//	inIntro = false;
			
			
		  if (inIntro)
			  paintIntroScreen();
		  else if (gameOver) {
			  gameOverLoop++;
			  if (gameOverLoop < 200) 
		  		  gameOverMessage(dbg);
			  else {
				//  running = false;
				//  new SpaceInvader(period);
				startGame(wcTop,period);
				  //new SpaceInvaderPanel(wcTop,period);
				  //break;
			  }
			  }
			else {
		  	  gameUpdate();
			  gameRender();
		  }
			  paintScreen();
		  
			  
			  if (checkLossConditions())
				  whoIsPlaying.setGameOver();
			 
			  gameOver = checkGameOverConditions();
			  
			  if (checkIfShouldSwitchPlayers())
				  switchPlayers();
			  if (checkWinConditions()) {
				  whoIsPlaying.setLevel(whoIsPlaying.getLevel()+1);
				  //  setupLevel(level);
			  }
			  
			  whoIsPlaying.setLoopNumber(whoIsPlaying.getLoopNumber()+1);
		  
		  
	      afterTime = System.nanoTime();

	      timeDiff = afterTime - beforeTime;
	      sleepTime = (period - timeDiff) - overSleepTime;  

	      if (sleepTime > 0) {   // some time left in this cycle
	        try {
	          Thread.sleep(sleepTime/1000000L);  // nano -> ms
	        }
	        catch(InterruptedException ex){}
	        overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
	      }
	      else {    // sleepTime <= 0; the frame took longer than the period
	        excess -= sleepTime;  // store excess time value
	        overSleepTime = 0L;

	        if (++noDelays >= NO_DELAYS_PER_YIELD) {
	          Thread.yield();   // give another thread a chance to run
	          noDelays = 0;
	        }
	      }

	      beforeTime = System.nanoTime();

	      /* If frame animation is taking too long, update the game state
	         without rendering it, to get the updates/sec nearer to
	         the required FPS. */
	      int skips = 0;
	      while((excess > period) && (skips < MAX_FRAME_SKIPS)) {
	        excess -= period;
		    gameUpdate();    // update state but don't render
	        skips++;
	      }
	      framesSkipped += skips;

	      storeStats();
	    //  whoIsPlaying.setLoopNumber(whoIsPlaying.getLoopNumber()+1);
		}

		 score.writeNewHighScore(); // not working just yet.
	    printStats();

	 //   new SpaceInvaderPanel(wcTop,period);
	    //System.exit(0);   // so window disappears
	  } // end of run()

	  private void gameUpdate() { 
		  if (!isPaused && !gameOver) {
			  for (SpaceObject o: whoIsPlaying.getObjects())
				  o.move();
		  }
	       if (isFiring) {
	    	   whoIsPlaying.getShip().fireLaser();
	    	   isFiring = false;
	       }
	       if (isMovingRight)
	    	   whoIsPlaying.getShip().moveRight();
	       if (isMovingLeft)
	    	   whoIsPlaying.getShip().moveLeft();
	  }  // end of gameUpdate()

	  
	  private void paintIntroScreen() {
		  
		  if (dbImage == null){
		      dbImage = createImage(PWIDTH, PHEIGHT);
		      if (dbImage == null) {
		        System.out.println("dbImage is null");
		        return;
		      }
		      else
		        dbg = dbImage.getGraphics();
		   }

		    // clear the background
		    dbg.setColor(Color.BLACK);
		    dbg.fillRect (0, 0, PWIDTH, PHEIGHT);

			dbg.setColor(Color.WHITE);
		    dbg.setFont(font);
		    
		    drawScorePanel();
		    
		    dbg.drawString("SELECT 1 OR 2 PLAYERS (PUSH 1 OR 2 TO BEGIN)",100,200);
		    
		    dbg.drawString("PLAY",(PWIDTH/2)-20,300);
		    dbg.drawString("SPACE INVADERS",(PWIDTH/2)-100,400);
		    dbg.drawString("*SCORE ADVANCE TABLE*",(PWIDTH/2)-150,500);
		    dbg.drawString("= ? MYSTERY",PWIDTH/2,550);
		    dbg.drawString("= 30 POINTS",PWIDTH/2,600);
		    dbg.drawString("= 20 POINTS",PWIDTH/2,650);
		    dbg.drawString("= 10 POINTS",PWIDTH/2,700);
		    
		    ImageLoader imgLoader = ImageLoader.getInstance();
		    int eSizeX = Integer.parseInt(props.getProperty("ENEMY_SIZE_X"));
		    int eSizeY= Integer.parseInt(props.getProperty("ENEMY_SIZE_Y"));
		    int ufoSizeX = Integer.parseInt(props.getProperty("UFO_SIZE_X"));
		    int ufoSizeY = Integer.parseInt(props.getProperty("UFO_SIZE_Y"));
		    dbg.drawImage(imgLoader.getImage("ufo.png"),(PWIDTH/2)-100 , 520, ufoSizeX,ufoSizeY, null);
		    dbg.drawImage(imgLoader.getImage("Alien20.gif"),(PWIDTH/2)-80 , 580, eSizeX,eSizeY, null);
		    dbg.drawImage(imgLoader.getImage("Alien10.gif"),(PWIDTH/2)-80 , 630, eSizeX,eSizeY, null);
		    dbg.drawImage(imgLoader.getImage("Alien00.gif"),(PWIDTH/2)-80 , 680, eSizeX,eSizeY, null);
		    
	  }
	  
	  private void drawScorePanel() {
		  
		  	dbg.drawString("SCORE<1>", 20, 25);
			dbg.drawString(score.getPlayerOneScore()+"", 20, 45);
			
			dbg.drawString("HIGH SCORE", 350, 25);
			dbg.drawString(score.getHighScore()+"",350,45);
			
			dbg.drawString("SCORE<2>", 650, 25);
			dbg.drawString(score.getPlayerTwoScore()+"", 650, 45);
			
			dbg.drawString("CREDIT  00", 650, PHEIGHT-20);
			
	  }

	  private void gameRender(){
	    if (dbImage == null){
	      dbImage = createImage(PWIDTH, PHEIGHT);
	      if (dbImage == null) {
	        System.out.println("dbImage is null");
	        return;
	      }
	      else
	        dbg = dbImage.getGraphics();
	   }

	    // clear the background
	    dbg.setColor(Color.BLACK);
	    dbg.fillRect (0, 0, PWIDTH, PHEIGHT);

		dbg.setColor(Color.WHITE);
	    dbg.setFont(font);
    
	    drawScorePanel();
		dbg.drawString("LEVEL "+whoIsPlaying.getLevel(),350,PHEIGHT-20);
		
		if (justSwitchedOrStarted) {
			  dbg.drawString("Player "+whoIsPlaying.getPlayer(),PWIDTH/2,PHEIGHT/2);
	//		  System.out.println("***Player "+whoIsPlaying.getPlayer()+"***");
			  switchedCounter++;
			  if (switchedCounter > 300) 
				  justSwitchedOrStarted = false;
			  
		  }
		
		dbg.setColor(Color.GREEN);
		dbg.drawLine(0, (int)bottomLine, PWIDTH, (int)bottomLine);
			
		whoIsPlaying.getShip().drawLives(dbg);
		
		for (SpaceObject o: whoIsPlaying.getObjects())
			o.draw(dbg);

	    //if (gameOver)
	     // gameOverMessage(dbg);
	  }  // end of gameRender()


	  private void gameOverMessage(Graphics g)
	  // center the game-over message in the panel
	  {
		  System.out.println("game over, score="+whoIsPlaying.getScore()+"\nHigh score="+score.getHighScore());
		 
		  
	    String msg = "Game Over\n Final Score: "+ whoIsPlaying.getScore();
		int x = (PWIDTH - metrics.stringWidth(msg))/2; 
		int y = (PHEIGHT - metrics.getHeight())/2;
		g.setColor(Color.GREEN);
	    g.setFont(font);
		g.drawString(msg, x, y);
		//startOver();
		//g.drawString("Press <ENTER> to start over", x, y+100);
		
		
	  }  // end of gameOverMessage()


	  private void paintScreen()
	  // use active rendering to put the buffered image on-screen
	  { 
	    Graphics g;
	    try {
	      g = this.getGraphics();
	      if ((g != null) && (dbImage != null))
	        g.drawImage(dbImage, 0, 0, null);
	      g.dispose();
	    }
	    catch (Exception e)
	    { System.out.println("Graphics context error: " + e);  }
	  } // end of paintScreen()


	  private void storeStats() { 
	    frameCount++;
	    statsInterval += period;

	    if (statsInterval >= MAX_STATS_INTERVAL) {     // record stats every MAX_STATS_INTERVAL

	      long timeNow = System.nanoTime();

	      timeSpentInGame = (int) ((timeNow - gameStartTime)/1000000000L);  // ns --> secs
	      wcTop.setTimeSpent( timeSpentInGame );

	      long realElapsedTime = timeNow - prevStatsTime;   // time since last stats collection
	      totalElapsedTime += realElapsedTime;

	      double timingError = 
	         ((double)(realElapsedTime - statsInterval) / statsInterval) * 100.0;

	      totalFramesSkipped += framesSkipped;

	      double actualFPS = 0;     // calculate the latest FPS and UPS
	      double actualUPS = 0;
	      if (totalElapsedTime > 0) {
	        actualFPS = (((double)frameCount / totalElapsedTime) * 1000000000L);
	        actualUPS = (((double)(frameCount + totalFramesSkipped) / totalElapsedTime) 
	                                                             * 1000000000L);
	      }

	      // store the latest FPS and UPS
	      fpsStore[ (int)statsCount%NUM_FPS ] = actualFPS;
	      upsStore[ (int)statsCount%NUM_FPS ] = actualUPS;
	      statsCount = statsCount+1;

	      double totalFPS = 0.0;     // total the stored FPSs and UPSs
	      double totalUPS = 0.0;
	      for (int i=0; i < NUM_FPS; i++) {
	        totalFPS += fpsStore[i];
	        totalUPS += upsStore[i];
	      }

	      if (statsCount < NUM_FPS) { // obtain the average FPS and UPS
	        averageFPS = totalFPS/statsCount;
	        averageUPS = totalUPS/statsCount;
	      }
	      else {
	        averageFPS = totalFPS/NUM_FPS;
	        averageUPS = totalUPS/NUM_FPS;
	      }

	      framesSkipped = 0;
	      prevStatsTime = timeNow;
	      statsInterval = 0L;   // reset
	    }
	  }  // end of storeStats()


	  private void printStats()
	  {
	    System.out.println("Frame Count/Loss: " + frameCount + " / " + totalFramesSkipped);
		System.out.println("Average FPS: " + df.format(averageFPS));
		System.out.println("Average UPS: " + df.format(averageUPS));
	    System.out.println("Time Spent: " + timeSpentInGame + " secs");
	  }  // end of printStats()
}
