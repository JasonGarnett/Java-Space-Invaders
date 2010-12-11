package model;

import java.awt.Image;
import java.util.HashMap;

import utilities.*;

public class ImageLoader {

	private static ImageLoader instance = new ImageLoader();
	private HashMap<String,Image> images;
	private LevelFactory factory = new LevelFactory();
	
	private ImageLoader(){
		images = factory.loadImages();
		
	}
	
	public static ImageLoader getInstance() { return instance; }
	
	public Image getImage(String imageName) {
		return images.get(imageName); 
	}
	
	
}
