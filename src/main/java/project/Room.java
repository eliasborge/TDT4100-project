package project;

public class Room {
	
	private String roomNumberString;
	private int roomNumber;
	private int floor;
	private boolean isAvailable = true;
	private String resident = "No Resident";
	private int daysRented = 0;
	private String roomType;
	private int pricePerDay;
	
	
	public Room(int floor, int roomNumber, String roomType) {
		this.floor = floor;
		this.roomNumber = roomNumber;
		this.roomNumberString = floor + "-" + roomNumber;
		
		this.roomType = roomType;
		if(roomType.equals("Single")) {
			this.pricePerDay = 100;
		}
		else {
			this.pricePerDay = 150;
		}
	}

	public String getRoomType() {
		return roomType;
	}

	public int getDaysRented() {
		return daysRented;
	}

	public void setDaysRented(int daysRented) {
		if(daysRented < 0) {
			throw new IllegalArgumentException("Days cannot be a negative number");
		}
		this.daysRented = daysRented;
	}


	
	
	public String getRoomNumberString() {
		return roomNumberString;
	}

	public int getRoomNumber() {
		return roomNumber;
	}


	public int getFloor() {
		return floor;
	}


	public boolean isAvailable() {
		return isAvailable;
	}


	public void setIsAvailable(boolean isAvailable) {
		if(this.isAvailable == isAvailable) {
			throw new IllegalArgumentException("This room is already available/booked.");
		}
		this.isAvailable = isAvailable;
	}


	public String getResident() {
		return resident;
	}


	public void setResident(String resident) {
		if(!resident.matches("^[A-ZÆØÅ][a-zæøå]*\\s[A-ZÆØÅ][a-zæøå]*$")) {
			throw new IllegalArgumentException("Must be first and last name with capital letters");
		}
		this.resident = resident;
	}
	
	public int getPricePerDay(){
		return pricePerDay;
		
	}
	
	public int getTotalPrice() {
		return getPricePerDay()*getDaysRented();
	}


	

	
}
