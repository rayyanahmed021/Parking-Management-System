import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class Database {
	private static Database instance = null; // Singleton instance

	private ArrayList<Payment> allPayments;
	private ArrayList<Booking> allBookings;
	private ArrayList<Client> allClients;
	private ArrayList<Manager> allManagers;
	private ArrayList<ParkingLot> allParkingLots;
	private ArrayList<ParkingSpace> allParkingSpaces;

	// Private constructor to prevent direct instantiation
	private Database() {
		this.allPayments = new ArrayList<>();
		this.allBookings = new ArrayList<>();
		this.allClients = new ArrayList<>();
		this.allManagers = new ArrayList<>();
		this.allParkingLots = new ArrayList<>();
		this.allParkingSpaces = new ArrayList<>();
	}

	// Singleton getInstance method
	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}

	public ArrayList<Client> getAllClients() {
		return allClients;
	}

	public void setAllClients(ArrayList<Client> allClients) {
		this.allClients = allClients;
	}

	public void loadClients(String path) throws Exception {
		CsvReader reader = new CsvReader(path);
		reader.readHeaders();
		Client client = null;

		while (reader.readRecord()) {
			String email = reader.get("email");
			String pass = reader.get("password");
			String type = reader.get("type");

			switch (type) {
			case "student":
				client = new Student(email, pass);
				System.out.println("student");
				break;
			case "visitor":
				client = new Visitor(email, pass);
				System.out.println("visitor");
				break;
			case "faculty":
				client = new Faculty(email, pass);
				System.out.println("faculty");
				break;
			case "nonfaculty":
				client = new NonFaculty(email, pass);
				System.out.println("nonfaculty");
				break;
			}
			this.allClients.add(client);
		}
	}
	
	public void loadBookings(String path) throws Exception {
		CsvReader reader = new CsvReader(path);
		reader.readHeaders();
		Client client = null;

		while (reader.readRecord()) {
			String bookingId = reader.get("id");
			String clientEmail = reader.get("client");
			double totalPrice = Double.parseDouble(reader.get("totalPrice"));
			String licensePlate = reader.get("licensePlate");
			LocalDateTime startTime = LocalDateTime.parse(reader.get("startTime"),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			LocalDateTime endTime = LocalDateTime.parse(reader.get("endTime"),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			int parkingSpaceId = Integer.parseInt(reader.get("parkingSpace"));
			String parkingLotId = reader.get("parkingLot");
			ParkingLot parkingLot = null;
			
			for (ParkingLot p: this.allParkingLots) {
				//add logic here
			}
			//ParkingSpace space = parkingLot.parkingSpaces[parkingSpaceId];
			String paymentId = reader.get("payment");
			Payment payment = null;
			for (Payment p: this.allPayments) {
				//add logic here
			}
			//Booking booking = new Booking()
//			this.allBookings.add(booking);
		}
	}
	
	public void loadPayments(String path) throws Exception {
		PaymentStrategy paymentStrategy = null;
		int paymentId;
		double total;
		boolean refund;
		String paymentMethod = "";
		CsvReader reader = new CsvReader(path);
		reader.readHeaders();

		while (reader.readRecord()) {
			paymentId = Integer.parseInt(reader.get("id"));
			Payment.nextPaymentId = Math.max(paymentId,Payment.nextPaymentId);
			total = Double.parseDouble(reader.get("total"));
			refund = Boolean.parseBoolean(reader.get("refund"));
			paymentMethod = reader.get("method");
			
			if (paymentMethod.equals("Credit Card")) {
				long cardNumber = Long.parseLong(reader.get("card number"));
				String cardName = reader.get("card name");
				String cvv = reader.get("cvv");
				String expiryDate = reader.get("expiry date");
				paymentStrategy = new CreditCardStrategy(cardNumber, cardName,cvv,expiryDate);
				
			} else if (paymentMethod.equals("Debit Card")) {
				long cardNumber = Long.parseLong(reader.get("card number"));
				String cardName = reader.get("card name");
				String cvv = reader.get("cvv");
				String expiryDate = reader.get("expiry date");
				paymentStrategy = new DebitCardStrategy(cardNumber, cardName,cvv,expiryDate);
			}
			else if (paymentMethod.equals("PayPal")) {
				String username = reader.get("username");
				String password = reader.get("password");
				paymentStrategy = new PayPalStrategy(username,password);
			}
			else if (paymentMethod.equals("Mobile")) {
				String mobileNumber = reader.get("mobile number");
				String provider = reader.get("provider");
				paymentStrategy = new MobilePaymentStrategy(mobileNumber,provider);
			}
			Payment payment = paymentStrategy.processPayment(total);
			System.out.println(payment.getId());
			this.allPayments.add(payment);
		}
	}

	public void update(String type, String path) throws Exception {
		try {
			CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
			csvOutput.write("email");
			csvOutput.write("password");
			csvOutput.write("type");
			csvOutput.endRecord();

			if (type.equals("Client")) {
				for (Client c : this.allClients) {
					csvOutput.write(c.getEmail());
					csvOutput.write(c.getPassword());

					if (c instanceof Student) {
						csvOutput.write("student");
					} else if (c instanceof Faculty) {
						csvOutput.write("faculty");
					} else if (c instanceof NonFaculty) {
						csvOutput.write("nonfaculty");
					} else if (c instanceof Visitor) {
						csvOutput.write("visitor");
					}

					csvOutput.endRecord(); // Ends the row properly
				}
			}
			csvOutput.flush(); // Ensure data is written before closing
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		String clientDataPath = Paths.get("src", "clientData.csv").toString();
		String bookingDataPath = Paths.get("src", "bookingData.csv").toString();
		String paymentDataPath = Paths.get("src", "paymentData.csv").toString();
		Database db = Database.getInstance();
		
		
		
		try {
			db.loadClients(clientDataPath);
			db.loadBookings(bookingDataPath);
//			Client client = new Student("ugly@gmail.com", "123");
//
//	        // Creating a payment strategy (Credit Card)
//	        PaymentStrategy payPalStrategy = new PayPalStrategy("user@example.com", "securepassword");
//
//	        // Processing the payment
//	        Payment payment = new Payment();
//	        payment.setPaymentMethod(payPalStrategy);
//	        payment = payment.payAmount(100.0);
//
//	        // Creating a parking space
//	        ParkingSpace parkingSpace = new ParkingSpace();
//
//	        // Creating start and end times for the booking
//	        LocalDateTime startTime = LocalDateTime.of(2025, 3, 1, 10, 0); // March 1, 2025, 10:00 AM
//	        LocalDateTime endTime = LocalDateTime.of(2025, 3, 1, 12, 0);   // March 1, 2025, 12:00 PM
//	        
//	        ParkingLot parkingLot = null;
//	        
//
//	        // Creating a booking with the processed payment
//	        Booking booking = new Booking(1, client, 100.0, "ABC-123", startTime, endTime, payment, parkingSpace, parkingLot);
//
//			//System.out.println(c.authenticate("ra@gmail.com", "123"));
//	        printBookingDetails(booking);
////			db.update("Client", clientDataPath);

//			db.loadPayments(paymentDataPath);
//			PaymentStrategy paymentStrategy = new MobilePaymentStrategy("asdasd","apple pay");
//			Payment p = paymentStrategy.processPayment(100);
//			System.out.println(p.getId());
//			db.update("Client", clientDataPath);

			
		} catch (Exception e) {
			e.printStackTrace(); // Print exception details
		}
		
	  
	}
	
	private static void printBookingDetails(Booking booking) {
        System.out.println("\n--- Booking Details ---");
        System.out.println("Booking ID: " + booking.getID());
//        System.out.println("Client: " + booking.getClient().getEmail());
        System.out.println("Email: " + booking.getClient().getEmail());
        System.out.println("License Plate: " + booking.getLicensePlate());
        System.out.println("Start Time: " + booking.getStartTime());
        System.out.println("End Time: " + booking.getEndTime());
//        System.out.println("Parking Space ID: " + booking.getParkingSpace().getSpaceID());

        System.out.println("\n--- Payment Details ---");
        System.out.println("Amount Paid: $" + booking.getPayment().getTotal());
        System.out.println("Refund Status: " + (booking.getPayment().getIsRefunded() ? "Refunded" : "Not Refunded"));
        System.out.println("Payment Method: " + booking.getPayment().getPaymentMethod().getClass().getSimpleName());
    }

}
