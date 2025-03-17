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
	
	public static Client getClientByEmail(String email) {
	    Database db = Database.getInstance();
	    for (Client c : db.getAllClients()) {
	        if (c.getEmail().equals(email)) {
	            return c;
	        }
	    }
	    return null;
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

	    if (!isValidEmail(email)) {
	        throw new Exception("Invalid email format.");
	    }

	    for (Client client : database.getAllClients()) {
	        if (client.getEmail().equals(email)) {
	            throw new Exception("Email is already registered.");
	        }
	    }

	    GenerateClientFactory factory = new GenerateClientFactory();
	    Client registeredClient = factory.getClientInstance(clientType, email, password);
	    database.getAllClients().add(registeredClient);

	    return registeredClient;
	}
	
	public abstract String getClientType();

	public static boolean isValidEmail(String email) {
	    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	    Pattern pattern = Pattern.compile(emailRegex);
	    Matcher matcher = pattern.matcher(email);
	    return matcher.matches();
	}
	
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

	public boolean selectSpace(Booking booking) {
			
			Database db = Database.getInstance();
			ParkingLot parkingLot = null;
			ParkingSpace parkingSpace;
			
			if (isValidLicensePlate(booking.getLicensePlate())) {
				for (ParkingLot lots : db.getAllParkingLots()) {
					if (lots.getId().equals(booking.getParkingLot().getId())) {
						parkingLot = lots;
						if (!(lots.getState() instanceof EnabledState)) {
							return false;
						}
						else {
							break;
						}
					}
				}
				
				if (parkingLot != null && parkingLot.getParkingSpaces()[booking.getParkingSpace().getId()] != null) {
					parkingSpace = parkingLot.getParkingSpaces()[booking.getParkingSpace().getId()];
					if (parkingSpace.isEnabled() && !parkingSpace.isOccupied()) {
						booking.getParkingSpace().setOccupied(true);
						booking.setTotalPrice(this.calculateDepositClient());
						this.bookings.add(booking);
					}
					else {
						System.out.println("bad!");
						return false;
					}
				}
		}
		else {
			return false;
		}
		return true;
	}
	 public ArrayList<Booking> activeBookings() {
	    	ArrayList<Booking> active = new ArrayList<Booking>();
	    	
	    	for (Booking b : this.bookings) {
	    		if (b.getEndTime().isAfter(LocalDateTime.now()) && b.getStartTime().isAfter(LocalDateTime.now())) {
	    			if(b.getPayment() != null && !(b.getPayment().getIsRefunded())) {
	    				active.add(b);
	    			}
	    			
	    		}
	    	}
	    	return active;
	    }


	public boolean updateParking(String changeType, LocalDateTime[] change, Booking booking) {
		Database db = Database.getInstance();
		
		if (change[0].isBefore(LocalDateTime.now()) || change[1].isBefore(LocalDateTime.now())
				|| (change[1].isBefore(change[0]))) {
			return false;
		}
		
		if (changeType.toLowerCase().equals("cancel")) {
			if (this.bookings.contains(booking)) {
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
			
			if (change[0].isBefore(LocalDateTime.now()) || change[1].isBefore(LocalDateTime.now())) {
				return false;
			}
			
			boolean overlap = false;
			for (Booking bookings : db.getAllBookings()) {
				if (booking.getEndTime().isBefore(bookings.getEndTime()) && 
					bookings.getStartTime().isBefore(change[1])) {
					overlap = true;
				}
			}
			if (!overlap) {
				booking.setEndTime(change[1]);
				booking.setTotalPrice(booking.calculateCheckout());
				return true;
			}
			return false;
		}
		else if (changeType.toLowerCase().equals("edit")) {
			boolean overlap = false;
			
			if (change[0].isBefore(LocalDateTime.now()) || change[1].isBefore(LocalDateTime.now())) {
				return false;
			}
			
	        for (Booking existingBooking : db.getAllBookings()) {
	            if (booking.getParkingLot().equals(existingBooking.getParkingLot())) {
	                if (!(change[1].isBefore(existingBooking.getStartTime()) ||
	                      existingBooking.getEndTime().isBefore(change[0]))) {
	                    overlap = true;
	                    break;
	                }
	            }
	        }
			
			if (!overlap) {
				booking.setStartTime(change[0]);
				booking.setEndTime(change[1]);
				booking.setTotalPrice(booking.calculateCheckout());
			}
			else {
				return false;
			}
			
		}
		else {
			return false;
		}
		return true;
	}
	
	public static boolean isStrongPassword(String password) {
		String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	    String NUMBERS = "0123456789";
	    String SYMBOLS = "!@#$%^&*()-_+=<>?/";
	    
	    boolean hasUpper = false, hasLower = false, hasNumber = false, hasSymbol = false;
	    
	    for (int i = 0; i < UPPERCASE.length();	i++) {
	    	if (password.contains(UPPERCASE.charAt(i)+"")) hasUpper = true;
	    }
	    for (int i = 0; i < LOWERCASE.length();	i++) {
	    	if (password.contains(LOWERCASE.charAt(i)+"")) hasLower = true;
	    }
	    for (int i = 0; i < NUMBERS.length();	i++) {
	    	if (password.contains(NUMBERS.charAt(i)+"")) hasNumber = true;
	    }
	    for (int i = 0; i < SYMBOLS.length();	i++) {
	    	if (password.contains(SYMBOLS.charAt(i)+"")) hasSymbol = true;
	    }
	    return hasUpper && hasLower && hasNumber && hasSymbol;
	}
	
}
