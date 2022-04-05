package project;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SaveHandlerTest {
	
	private Hotel hotel;
	private SaveHandler saveHandler = new SaveHandler();
	
	private void createHotel() {
		hotel = new Hotel(6,5);
		hotel.makeBooking(hotel.getRoom(1, 1), "Hans Rimmen", 3);
		hotel.makeBooking(hotel.getRoom(2, 2), "Guri Olsen", 4);
		hotel.makeBooking(hotel.getRoom(3, 3), "Sindre Nilsen", 5);
	}
	
	@BeforeEach
	public void setup() {
		createHotel();
	}
	
	@Test
	public void testLoad() {
		Hotel savedNewHotel; // Required to ignore Eclipse warning
		try {
			savedNewHotel = saveHandler.load("test-save");
		} catch (FileNotFoundException e) {
			fail("Could not load saved file");
			return;
		}
		for (int i = 0; i<hotel.getRooms().size(); i++) {
			assertEquals(hotel.getRooms().get(i).getRoomNumberString(),savedNewHotel.getRooms().get(i).getRoomNumberString());
			assertEquals(hotel.getRooms().get(i).getRoomNumber(),savedNewHotel.getRooms().get(i).getRoomNumber());
			assertEquals(hotel.getRooms().get(i).getFloor(),savedNewHotel.getRooms().get(i).getFloor());
		}
	}
	
	@Test
	public void testLoadNonExistingFIle() {
		assertThrows(
			FileNotFoundException.class,
			() -> hotel = saveHandler.load("foo")
		);
	}
	
	@Test
	public void testSave(){
		try {
			saveHandler.save("test-save-new", hotel);
		} catch (FileNotFoundException e) {
			fail("Could not save file");
		}
		
		byte[] testFile = null, newFile = null;
		try {
			testFile = Files.readAllBytes(Path.of(SaveHandler.getFilePath("test-save")));
		} catch (IOException e) {
			fail("could not load test file");
		}
		try {
			newFile = Files.readAllBytes(Path.of(SaveHandler.getFilePath("test-save-new")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(testFile);
		assertNotNull(newFile);
		assertTrue(Arrays.equals(testFile,newFile));
	}
	
	@AfterAll
	static void teardown() {
		File newTestSaveFile = new File(SaveHandler.getFilePath("test-save-new"));
		newTestSaveFile.delete();
	}
}

