package project;

import java.io.FileNotFoundException;

public interface SaveHandlerInterface {
	
	public void save(String fileName, Hotel hotel) throws FileNotFoundException;
	
	public Hotel load(String fileName) throws FileNotFoundException;
}

