package project;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SaveHandler implements SaveHandlerInterface{

	public final static String SAVE_FOLDER = "src/main/java/project/saves/";
	
	public void save(String filename, Hotel hotel) throws FileNotFoundException  {
		try (PrintWriter writer = new PrintWriter(getFilePath(filename))) {
			writer.println(hotel.getNumberOfRoomsPerFloor());
			writer.println(hotel.getFloors());
			
			for(Room room: hotel.getRooms()) {
				writer.println(
						room.getRoomNumber()+"-"+
						room.getFloor()+"-"+
						room.getDaysRented()+"-"+
						room.getResident()+"-"+
						room.isAvailable()
						);
			}
		}
	}

	public Hotel load(String filename) throws FileNotFoundException {
		try(Scanner scanner = new Scanner(new File(getFilePath(filename)))){
			int numberOfRoomsPerFloor = scanner.nextInt();
			int numberOfFloors = scanner.nextInt();
			
			Hotel hotel = new Hotel(numberOfRoomsPerFloor,numberOfFloors);
			scanner.nextLine();
			
			for(Room room : hotel.getRooms()) {
				String [] roomInformation = scanner.nextLine().split("-");
				if(room.getRoomNumber() != Integer.parseInt(roomInformation[0]) || room.getFloor() !=Integer.parseInt(roomInformation[1])) {
					throw new IllegalArgumentException("Something went wrong");
				}
				room.setDaysRented(Integer.parseInt(roomInformation[2]));
				room.setResident(roomInformation[3]);
				if(room.isAvailable() != Boolean.parseBoolean(roomInformation[4]))	
					room.setIsAvailable(Boolean.parseBoolean(roomInformation[4]));
					
			}
			
			return hotel;
		}
		
	}



	public static String getFilePath(String filename) {
		return SAVE_FOLDER + filename + ".txt";
	}
	
	public static void main(String[] args) {
		
	}
}
