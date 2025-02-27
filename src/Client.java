import java.util.*;

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
	
	
	public boolean authenticate(String email, String password) {
		boolean isLoggedIn = false;
		Database database = new Database();
		for (Client client : database.getAllClients()) {
			if (client.getEmail().equals(email) && client.getPassword().equals(password)) {
				isLoggedIn = true;
				return isLoggedIn;
			}
		}
		return isLoggedIn;
	}
	
}
