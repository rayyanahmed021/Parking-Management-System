package backend;
import java.time.Duration;
import java.time.LocalDateTime;
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
	
	public abstract String getClientType();

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
//						payment.
						// Booking Deposit: **(Confused whether to assign deposit or full total)**
						booking.setTotalPrice(booking.getTotalPrice() + this.calculateDepositClient());
						// Full total:
//						long hoursDifference = Duration.between(startTime, endTime).toHours();
//						booking.setTotalPrice(booking.getTotalPrice() + (hoursDifference*this.calculateDepositClient()));
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
		Database db = Database.getInstance();
		
		if (changeType.toLowerCase().equals("cancel")) {
			//cannot cancel at the current
			//provide refund
			if (this.bookings.contains(booking)) {
				// Check if current time is before the start time of the booking
				if (LocalDateTime.now().isBefore(change[0])) {
					booking.setTotalPrice(0);
					booking.getPayment().setIsRefunded(true);
				}
				this.bookings.remove(booking);
			}
			else {
				return false;
			}
			this.bookings.remove(booking);
		}
		else if (changeType.toLowerCase().equals("extend")) {
			//Recalculate the total
			long hoursDifference = Duration.between(booking.getEndTime(), change[1]).toHours();
			booking.setTotalPrice(booking.getTotalPrice() + (hoursDifference*this.calculateDepositClient()));
			boolean overlap = false;
			// Add # of hrs extended * rate of client type
			for (Booking bookings : db.getAllBookings()) {
				// If there is an overlap in time
				if (booking.getStartTime().isBefore(bookings.getEndTime()) && bookings.getStartTime().isBefore(booking.getEndTime())) {
					overlap = true;
				}
			}
			if (!overlap) {
				for (Booking bookings2 : this.bookings) {
					if (bookings2 == booking) {
						bookings2.setEndTime(change[1]);
						return true;
					}
				}
			}
			return false;
		}
		else if (changeType.toLowerCase().equals("edit")) {
			//update payment
			long hoursDifference = Duration.between(change[0], change[1]).toHours();
			booking.setTotalPrice(booking.getTotalPrice() + (hoursDifference*this.calculateDepositClient()));
			boolean overlap = false;
			
			for (Booking bookings : db.getAllBookings()) {
				// If there is an overlap in time
				if (booking.getStartTime().isBefore(bookings.getEndTime()) && bookings.getStartTime().isBefore(booking.getEndTime())) {
					overlap = true;
				}
			}
			if (!overlap) {
				for (Booking bookings2 : this.bookings) {
					if (bookings2 == booking) {
						bookings2.setStartTime(change[0]);
						bookings2.setEndTime(change[1]);
						return true;
					}
				}
			}
			
		}
		else {
			return false;
		}
		return true;
	}
	
}
