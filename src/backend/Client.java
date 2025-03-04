<<<<<<< Updated upstream:src/Client.java
import java.time.LocalDateTime;
=======
package backend;
>>>>>>> Stashed changes:src/backend/Client.java
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Client {
	protected String email;
	protected String password;
	protected ArrayList<Booking> bookings;
	
	
	public Client(String email, String password) {
		this.email = email;
		this.password = password;
		this.bookings = new ArrayList<Booking>();
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return this.email;
	}
	public String getPassword() {
		return this.password;
	}
	
	public ArrayList<Booking> getBookings() {
		return bookings;
	}
	public void setBookings(ArrayList<Booking> bookings) {
		this.bookings = bookings;
	}
	public static Client registerUser(String clientType, String email, String password) throws Exception {
	    Database database = Database.getInstance();

	    // Validate email format
	    if (!isValidEmail(email)) {
	        throw new Exception("Invalid email format.");
	    }

	    // Check if email is already registered
	    for (Client client : database.getAllClients()) {
	        if (client.getEmail().equals(email)) {
	            throw new Exception("Email is already registered.");
	        }
	    }

	    // Create client using Factory pattern
	    GenerateClientFactory factory = new GenerateClientFactory();
	    Client registeredClient = factory.getClientInstance(clientType, email, password);
	    database.getAllClients().add(registeredClient);

	    return registeredClient;
	}

	// Email validation function
	private static boolean isValidEmail(String email) {
	    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	    Pattern pattern = Pattern.compile(emailRegex);
	    Matcher matcher = pattern.matcher(email);
	    return matcher.matches();
	}
	
	// License plate validation function
		public static boolean isValidLicensePlate(String licensePlate) {
			String plateRegex = "^[A-Z0-9]{1,3}-?[A-Z0-9]{1,4}$";
		    Pattern pattern = Pattern.compile(plateRegex);
		    Matcher matcher = pattern.matcher(licensePlate);
		    return matcher.matches();
		}
	
	public static boolean authenticate(String email, String password) {

		boolean isLoggedIn = false;
		Database database = Database.getInstance();
		
		for (Client client : database.getAllClients()) {
			if (client.getEmail().equals(email) && client.getPassword().equals(password)) {
				isLoggedIn = true;
				return isLoggedIn;
			}
		}
		return isLoggedIn;
	}
	

	public abstract double calculateDepositClient();

	// Assume payment is being passed from the front end
	public boolean selectSpace(String lot, int space, String licensePlate, int id, 
		double totalPrice, LocalDateTime startTime, LocalDateTime endTime,
		Payment payment) {
		
		Database db = Database.getInstance();
		ParkingLot parkingLot = null;
		ParkingSpace parkingSpace;
		
		// Check if licensePlate is valid
		if (isValidLicensePlate(licensePlate)) {
			// Check if lot state is enabled
			for (ParkingLot lots : db.getAllParkingLots()) {
				if (lots.getId().equals(lot)) {
					parkingLot = lots;
					if (!(lots.getState() instanceof EnabledState)) {
						return false;
					}
					else {
						break;
					}
				}
			}
			
			// Check if space state is enabled and not occupied
				if (parkingLot != null && parkingLot.getParkingSpaces()[space] != null) {
					parkingSpace = parkingLot.getParkingSpaces()[space];
					// W.I.P: Understand how parking space states are handled
					if (parkingSpace.isEnabled() && !parkingSpace.isOccupied()) {
						// TODO: Add all booking parameters to selectSpace method as well
						Booking booking = new Booking(id, this, totalPrice, licensePlate,
						startTime, endTime, payment, parkingSpace, parkingLot);
						this.bookings.add(booking);
						parkingSpace.setOccupied(true);
					}
					else {
						return false;
					}
				}
		}
		else {
			return false;
		}
		return true;
	}
	
	// Changed change string to LocalDateTime array for simplicity
	public boolean updateParking(String changeType, LocalDateTime[] change, Booking booking) {
		if (changeType.equals("Cancel")) {
			//cannot cancel at the current
			//provide refund
			this.bookings.remove(booking);
		}
		else if (changeType.equals("Extend")) {
			//recalcualte the total
			for (Booking bookings : this.bookings) {
				if (bookings == booking) {
					bookings.setEndTime(change[1]);
				}
			}
		}
		else if (changeType.equals("Edit")) {
			//update payment
			for (Booking bookings : this.bookings) {
				if (bookings == booking) {
					bookings.setStartTime(change[0]);
					bookings.setEndTime(change[1]);
				}
			}
		}
		else {
			return false;
		}
		return true;
	}
	
}
