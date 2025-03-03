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
	
	public boolean authenticate(String email, String password) {
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
	
}
