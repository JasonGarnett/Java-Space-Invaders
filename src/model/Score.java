package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Score {

	private int playerOneScore;
	private int playerTwoScore;
	private int highScore;
	private boolean playerOneIsPlaying = true;
	private static Score instance = new Score();
	private int originalHighScore;
	
	private Score() {
		playerOneScore = 0;
		playerTwoScore = 0;
		highScore = readInHighScore();
		originalHighScore = highScore;
	}
	
	public void switchPlayers() { playerOneIsPlaying = !playerOneIsPlaying; }
	
	public static Score getInstance() { return instance; }
	
	public int getPlayerOneScore() { return playerOneScore; }
	
	public int getPlayerTwoScore() { return playerTwoScore; }
	
	public void writeNewHighScore() {
		if (highScore > originalHighScore)
			setNewHighScore(highScore);
	}
	
	public void addScore(int newScore) { 
	if (playerOneIsPlaying) {
		playerOneScore += newScore; 
		if (playerOneScore > highScore)
			highScore = playerOneScore;
	}
	else {
		playerTwoScore += newScore; 
		if (playerTwoScore > highScore)
			highScore = playerTwoScore;
	}
		
	}
	
	public int getHighScore() { return highScore; }
	
	private int readInHighScore() {
		 int ans;
		  try {
			  File f = new File("HighScore.txt");
			  Scanner s = new Scanner (f);
			  
			  ans = s.nextInt();
			  s.close();
			  return ans;
			  
		  } catch (FileNotFoundException e) {e.printStackTrace(); return 0; }
		  
	  }
	
	  public void setNewHighScore(int newHS){
		  System.out.println("Trying to write new highscore");
		  try {
			  File f = new File("HighScore.txt");
			  PrintWriter p = new PrintWriter(f);
			  
			  p.write(newHS+"");
			  
			  p.close();			  
			  
		  } catch(FileNotFoundException e) { e.printStackTrace();}
		  
		  
	  } 
	 
	
}
