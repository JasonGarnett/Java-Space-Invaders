package utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SIProps {

	private static SIProps instance = new SIProps();
	private Properties props;
	
	private SIProps() {
		props = new Properties();
		try {
			props.load(new FileReader("SpaceInvaders.props"));
		} catch (IOException e){ e.printStackTrace(); }
	}
	
	public static SIProps getInstance() {
		return instance;
	}
	
	public String getProperty(String p) {
		return props.getProperty(p);
	}
	
}