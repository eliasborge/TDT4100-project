package project;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class Hotel {
	
	private ArrayList<Room> rooms = new ArrayList<>();
	private int numberOfRoomsPerFloor;
	private int floors;
	private int numberOfRooms;
	private boolean roomTurnSwitch = true;
	
	public Hotel(int numberOfRoomsPerFloor, int floors) {
		
	
		try {
			validateConstructor(floors,numberOfRoomsPerFloor);
			for(int i = 1; i < floors+1; i++) {
				for (int j = 1; j< numberOfRoomsPerFloor+1; j++) {
					if(roomTurnSwitch) {
						this.rooms.add(new Room(i,j,"Single"));
						roomTurnSwitch = false;
					}
					else {
						this.rooms.add(new Room(i,j,"Double"));
						roomTurnSwitch = true;
					}
				}
			}
		}
		catch (Exception e) {
				System.out.println(e);
		}
		
		finally {
			
			this.numberOfRoomsPerFloor = numberOfRoomsPerFloor;
			this.floors = floors;
			this.numberOfRooms = floors*numberOfRoomsPerFloor;
		}
		
	}
	
	
	
	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	
	public ArrayList<Room> getRooms() {
		return rooms;
	}



	public Room getRoom(int floor, int roomNumber) {
		return rooms.get((floor-1) * this.getNumberOfRoomsPerFloor() + roomNumber-1);
	}


	public int getNumberOfRoomsPerFloor() {
		return numberOfRoomsPerFloor;
	}


	public int getFloors() {
		return floors;
	}

	
	public void makeBooking(Room room, String resident, int daysRented) {
		try {	
			validateBooking(room,resident,daysRented);
			Room roomInHotel = getRoom(room.getFloor(),room.getRoomNumber());
			roomInHotel.setResident(resident);
			roomInHotel.setIsAvailable(false);
			roomInHotel.setDaysRented(daysRented);
		}
		catch (Exception e) {
			System.out.println(e);
		}
	
	}
	public void removeBooking(Room room) {
		
		try {
			validateRemoveBooking(room);
			
			getRoom(room.getFloor(),room.getRoomNumber()).setResident("No Resident");
			getRoom(room.getFloor(),room.getRoomNumber()).setIsAvailable(true);
			getRoom(room.getFloor(),room.getRoomNumber()).setDaysRented(0);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	
	public List<Room> getAvailableRooms(){
		return rooms.stream().filter(r -> r.isAvailable()).collect(Collectors.toList());
		
	}
	
	public List<Room> getUnAvailableRooms(){
		return rooms.stream().filter(r -> !r.isAvailable()).collect(Collectors.toList());
		
	}
	
	public void validateBooking(Room room, String resident, int daysRented) {
		if(!room.isAvailable() || !room.getResident().equals("No Resident") || (room.getDaysRented() !=0)) {
			throw new IllegalArgumentException("This room is not valid for booking");
		}
	}

	public void validateRemoveBooking(Room room) {
	
		if(room.isAvailable() || room.getResident().equals("No Resident") || (room.getDaysRented() == 0)) {
			throw new IllegalArgumentException("This room is not valid for removal");
		}
			
	}
	
	public void validateConstructor(int floors, int numberOfRoomsPerFloor) {
		if(floors <1 || numberOfRoomsPerFloor <1) {
			throw new IllegalArgumentException("This is not valid arguments");
		}
	}
	

}
