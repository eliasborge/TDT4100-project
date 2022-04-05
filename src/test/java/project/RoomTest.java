package project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




import org.junit.jupiter.api.BeforeEach;

public class RoomTest {

	private Room room1;
	private Room room2;
	
	public void createRooms() {
		this.room1 = new Room(1, 1, "Single");
		this.room2 = new Room(3, 2, "Double");
	}
	
	@BeforeEach
	public void setup() {
		createRooms();
	}
	
	@Test
	public void testConstructor() {
		
		assertEquals("1-1",room1.getRoomNumberString());
		assertEquals("3-2",room2.getRoomNumberString());
		
		assertEquals("Single",room1.getRoomType());
		assertEquals("Double",room2.getRoomType());
		
		assertEquals(100,room1.getPricePerDay());
		assertEquals(150,room2.getPricePerDay());
		
	}
	
	@Test
	public void testTotalPrice() {
		room1.setDaysRented(2);
		room2.setDaysRented(4);
		
		assertEquals(200,room1.getTotalPrice());
		assertEquals(600,room2.getTotalPrice());
		
		
	}
	
	@Test
	public void testSetters() {
		Room room3 = new Room(4,3, "Single");
		assertThrows(
				Exception.class,
				() -> room3.setDaysRented(-1)
			);
		assertThrows(
				Exception.class,
				() -> room3.setResident("aaa")
				);
		assertThrows(
				Exception.class,
				() -> room3.setIsAvailable(true)
				);
	}
}
