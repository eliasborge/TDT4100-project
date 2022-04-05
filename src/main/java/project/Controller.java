package project;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Controller {
	private Hotel hotel;
	private SaveHandler saveHandler = new SaveHandler();
	Alert alert = new Alert(Alert.AlertType.NONE);
	

	

	@FXML
	private HBox leftList;
	@FXML
	private HBox rightList;
	@FXML
	private TextField resident;
	@FXML
	private TextField daysRented;
	@FXML
	private ListView<String> availableListView;
	@FXML
	private ListView<String> unAvailableListView;
	@FXML
	private Text prompt;
	@FXML
	private Text confirmation;
	
	
	
	
	@FXML
	private void initialize() throws FileNotFoundException {
		setInitialHotelState();
	}
	
	private void setInitialHotelState() throws FileNotFoundException {
		try {	
			hotel = saveHandler.load("hotelState");
		}	
		catch (Exception e) {
			System.out.println(e);
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Could not load hotel.");
			alert.setHeight(400);
			alert.show();
		}
		updateListView();	
	}
	
	

@FXML
	private void updateListView() {
	
		leftList.getChildren().clear();
		rightList.getChildren().clear();
		availableListView.getItems().clear();
		unAvailableListView.getItems().clear();

		Collection<String> available = new ArrayList<>();
		Collection<String> unAvailable = new ArrayList<>();
	
		for(Room room: hotel.getAvailableRooms()) {
			available.add(
					room.getRoomNumberString() 
					+" | Resident:  "
					+ room.getResident() 
					+ " | " 
					+ room.getRoomType());
			
		}
		for(Room room: hotel.getUnAvailableRooms()) {
			unAvailable.add(
					room.getRoomNumberString() 
					+" | Resident:  "
					+ room.getResident() 
					+ " | " 
					+ room.getRoomType()
					+ " | "
					+ room.getDaysRented()
					+ " day(s)");
			
		}
		availableListView.getItems().addAll(available);
		unAvailableListView.getItems().addAll(unAvailable);
		
		leftList.getChildren().add(availableListView);
		rightList.getChildren().add(unAvailableListView);
	}
	

	@FXML
	private void removeBooking() throws FileNotFoundException {
		if(unAvailableListView.getSelectionModel().getSelectedIndex() == -1) {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Please choose a room before removing a booking.");
			alert.setHeight(400);
			alert.show();
		}
		
		else{
			Room tempRoom = hotel
			.getUnAvailableRooms()
			.get(unAvailableListView
			.getSelectionModel()
			.getSelectedIndex());
				
			Room room = hotel.getRoom(tempRoom.getFloor(), tempRoom.getRoomNumber());
			int totalPrice = room.getTotalPrice();
			hotel.removeBooking(room);
			
			alert.setAlertType(AlertType.CONFIRMATION);
			alert.setContentText("You have succesfully removed the booking for room " + room.getRoomNumberString()+ 
					". \n The total price of your stay was "+totalPrice+". We hope to see you again!");
			alert.setHeight(400);
			alert.getButtonTypes().remove(ButtonType.CANCEL);
			alert.show();
			
			
			
			updateListView();
			
		
			saveHandler.save("hotelState", hotel);
		}
	}
	
	
	@FXML
	private void makeBooking() throws FileNotFoundException {
		
		
		if(resident.getText().isEmpty() || daysRented.getText().isEmpty()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setHeight(400);
			alert.setContentText("You have to fill inn Full name and duration of your stay before booking.");
			alert.show();
		}
	
		else if(availableListView.getSelectionModel().getSelectedIndex() == -1) {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("You have to choose a room");
			alert.show();
			
		}
		
		
		else {
			Room tempRoom= hotel
					.getAvailableRooms()
					.get(availableListView
					.getSelectionModel()
					.getSelectedIndex());
			Room room = hotel.getRoom(tempRoom.getFloor(),tempRoom.getRoomNumber());
			
			
			
			
			if(!resident.getText().matches("^[A-ZÆØÅ][a-zæøå]*\\s[A-ZÆØÅ][a-zæøå]*$")) {
				
				alert.setAlertType(AlertType.ERROR);
				alert.setContentText("You need to fill inn both first and last name! Can only contain letters and must be with capital first letters");
				alert.setHeight(400);
				alert.show();
			}
		
			else if(!daysRented.getText().matches("^[1-9]\\d*$")) {
				
				alert.setAlertType(AlertType.ERROR);
				alert.setContentText("Number of days has to be a positive integer");
				alert.show();
			}
			
			else {
				hotel.makeBooking(room, resident.getText(), Integer.parseInt(daysRented.getText()));
				
				resident.setText(null);
				daysRented.setText(null);
				alert.setAlertType(AlertType.CONFIRMATION);
				alert.setContentText("You have successfully booked room "+ room.getRoomNumberString());
				alert.getButtonTypes().remove(ButtonType.CANCEL);
				alert.setHeight(400);
				alert.show();
				
				
				updateListView();
				saveHandler.save("hotelState", hotel);
			}
		}
	}
	
	
}
