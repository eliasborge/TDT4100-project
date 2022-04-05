package project;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

public class HotelTest {
	
	private Hotel hotel;
	
	public void createHotel() {
		this.hotel = new Hotel(5,5);
		
	}
	
	@BeforeEach
	public void setup() {
		createHotel();
	}
	
	@Test
	public void testConstructor() {
		assertEquals(25,hotel.getNumberOfRooms());
		int counter = 0;
		for(Room room:hotel.getRooms()) {
			
			if(counter%2 == 0) {
				assertEquals("Single",room.getRoomType());
			}
			else {
				assertEquals("Double",room.getRoomType());
			}
			
			
			counter++;
		}
		counter = 0;
		
		
	}
	
	@Test
	public void testGetRoom() {
		Room room = hotel.getRooms().get(0);
		Room room2 = hotel.getRoom(1, 1);
		
		assertEquals(room,room2);
	}
	
	@Test
	public void testGetAvailableRooms() {
		List<Room> newList = new ArrayList<>();
		for(Room room:hotel.getRooms()) {
			if(room.isAvailable()) {
				newList.add(room);
			}
		}
		
		assertEquals(newList,hotel.getAvailableRooms());
	}
	
	@Test
	public void testGetUnAvailableRooms() {
		List<Room> newList = new ArrayList<>();
		for(Room room:hotel.getRooms()) {
			if(!room.isAvailable()) {
				newList.add(room);
			}
		} 
		
		assertEquals(newList,hotel.getUnAvailableRooms());
	}
	
	@Test
	public void testBooking() {
		if(!hotel.getRoom(1, 1).isAvailable()) {
			hotel.removeBooking(hotel.getRoom(1, 1));
		}
		
		hotel.makeBooking(hotel.getRoom(1, 1), "Hans Gimsar", 3);
		assertFalse(hotel.getRoom(1, 1).isAvailable());
		assertEquals("Hans Gimsar",hotel.getRoom(1, 1).getResident());
		assertEquals(3,hotel.getRoom(1, 1).getDaysRented());
	}
	
	@Test
	public void testRemoveBooking() {
		hotel.makeBooking(hotel.getRoom(1, 2), "Guri Jansen", 3);
		
		hotel.removeBooking(hotel.getRoom(1, 2));
		
		assertTrue(hotel.getRoom(1, 2).isAvailable());
		assertEquals("No Resident",hotel.getRoom(1, 2).getResident());
		assertEquals(0,hotel.getRoom(1, 2).getDaysRented());
		
	}
	@Test
	public void testThrows() {
		
		Room room4 = new Room(1,4,"Single");
		Room room5 = new Room(3,5,"Single");
		room4.setDaysRented(10);
		assertThrows(
				Exception.class,
				() -> hotel.validateConstructor(-1, -2)
			);
		assertThrows(
				Exception.class,
				() ->hotel.validateBooking(room4, "Guri Jansen", 3)
				);
		assertThrows(
				Exception.class,
				() -> hotel.validateRemoveBooking(room5)
				);
	}
}
