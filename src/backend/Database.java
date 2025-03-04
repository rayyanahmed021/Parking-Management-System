package backend;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class Database implements ParkingObserver{
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
			boolean isApproved = Boolean.parseBoolean(reader.get("isApproved"));

			switch (type) {
			case "student":
				client = new Student(email, pass, isApproved);
				break;
			case "visitor":
				client = new Visitor(email, pass);
				break;
			case "faculty":
				client = new Faculty(email, pass, isApproved);
				break;
			case "nonfaculty":
				client = new NonFaculty(email, pass, isApproved);
				break;
			}
			this.allClients.add(client);
		}
	}
	
	
	public ArrayList<Payment> getAllPayments() {
		return allPayments;
	}

	public void setAllPayments(ArrayList<Payment> allPayments) {
		this.allPayments = allPayments;
	}

	public ArrayList<Booking> getAllBookings() {
		return allBookings;
	}

	public void setAllBookings(ArrayList<Booking> allBookings) {
		this.allBookings = allBookings;
	}

	public ArrayList<Manager> getAllManagers() {
		return allManagers;
	}

	public void setAllManagers(ArrayList<Manager> allManagers) {
		this.allManagers = allManagers;
	}

	public ArrayList<ParkingLot> getAllParkingLots() {
		return allParkingLots;
	}

	public void setAllParkingLots(ArrayList<ParkingLot> allParkingLots) {
		this.allParkingLots = allParkingLots;
	}

	public ArrayList<ParkingSpace> getAllParkingSpaces() {
		return allParkingSpaces;
	}

	public void setAllParkingSpaces(ArrayList<ParkingSpace> allParkingSpaces) {
		this.allParkingSpaces = allParkingSpaces;
	}

	public void loadManagers(String path) throws Exception {
		CsvReader reader = new CsvReader(path);
        reader.readHeaders();

        while (reader.readRecord()) {
            String name = reader.get("name");
            String password = reader.get("password");
            boolean isSuperManager = Boolean.parseBoolean(reader.get("isSuperManager"));
            if (isSuperManager) {
            	SuperManager superManager = SuperManager.getSuperManagerInstance(name, password);
            	this.allManagers.add(superManager);
            }
            else {
              Manager manager = new Manager(name, password);
              this.allManagers.add(manager);
            }

        }
        reader.close();
	}
	
	public void loadParkingSpaces(String path) throws Exception {
		CsvReader reader = new CsvReader(path);
		String parkingLotId;
		int parkingSpaceId;
        reader.readHeaders();

        while (reader.readRecord()) {
            ParkingSpace parkingSpace = new ParkingSpace();
            parkingSpaceId = Integer.parseInt(reader.get("id"));
            parkingSpace.setId(parkingSpaceId);
//            parkingSpace.setParkingLot(new ParkingLot(Integer.parseInt(reader.get("lot"))))); //change this
            parkingLotId = reader.get("lot");
            for (ParkingLot lot: this.allParkingLots) {
            	if (lot.getId().equals(parkingLotId)) {
            		parkingSpace.setParkingLot(lot);
            		ParkingSpace[] parkingSpaces = lot.getParkingSpaces();
            		parkingSpaces[parkingSpaceId] = parkingSpace;
            		lot.setParkingSpaces(parkingSpaces);
            	}
            }
//            parkingSpace.setParkingLot(new ParkingLot());
            parkingSpace.setOccupied(Boolean.parseBoolean(reader.get("occupied")));
            parkingSpace.setLocation(reader.get("location"));
            parkingSpace.setEnabled(Boolean.parseBoolean(reader.get("isEnabled")));
            this.allParkingSpaces.add(parkingSpace);
        }
	}
	
	
	public void loadParkingLot(String path) throws Exception {
		CsvReader reader = new CsvReader(path);
        reader.readHeaders();

        while (reader.readRecord()) {
//            ParkingLot parkingLot = new ParkingLot(
//                reader.get("id"),
//                reader.get("name"),
//                ParkingLotState.valueOf(reader.get("state")) // Assuming ParkingLotState is an enum
//            );
        	ParkingLot parkingLot = new ParkingLot(reader.get("id"),reader.get("name"),new ParkingSpace[6]);
            this.allParkingLots.add(parkingLot);
        }
        reader.close();
	}
	
	public void loadBookings(String path) throws Exception {
		CsvReader reader = new CsvReader(path);
		reader.readHeaders();
		Client client = null;

		while (reader.readRecord()) {
			Booking booking = new Booking();
			
			int bookingId = Integer.valueOf(reader.get("id"));
			Booking.nextBookingId = Math.max(bookingId, Booking.nextBookingId);
			booking.setID(bookingId);
			String clientEmail = reader.get("client");
			
			
			
			for(Client c: this.allClients) {
				if(c.getEmail().equals(clientEmail)) {
					c.bookings.add(booking);
					booking.setClient(c);
				}
			}
			
			booking.setTotalPrice(Double.parseDouble(reader.get("totalPrice")));
			booking.setLicensePlate(reader.get("licensePlate"));
			LocalDateTime startTime = LocalDateTime.parse(reader.get("startTime"),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			LocalDateTime endTime = LocalDateTime.parse(reader.get("endTime"),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			booking.setStartTime(startTime);
			booking.setEndTime(endTime);
			int parkingSpaceId = Integer.parseInt(reader.get("parkingSpace"));
			String parkingLotId = reader.get("parkingLot");
			ParkingLot parkingLot = null;
			
			
			for (ParkingLot lot: this.allParkingLots) {
				if(lot.getId().equals(parkingLotId)) {
					booking.setParkingLot(lot);
				}
			}
			
			
			System.out.println(parkingSpaceId);
			ParkingSpace space = booking.getParkingLot().getParkingSpaces()[parkingSpaceId];
			System.out.println(space.getId());
			
			booking.setParkingSpace(space);
			
			int paymentId = Integer.valueOf(reader.get("payment"));
			Payment payment = null;
			
			for (Payment p: this.allPayments) {
				if(p.getId() == paymentId) {
					booking.setPayment(p);
				}
			}
			
			this.allBookings.add(booking);
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
			Payment payment = new Payment(paymentId, total, refund, paymentStrategy);
			this.allPayments.add(payment);
		}
	}
	
	
	public void updateParkingSpaces(String path) throws Exception {
        CsvWriter writer = new CsvWriter(path);
        writer.write("id");
        writer.write("lot");
        writer.write("occupied");
        writer.write("location");
        writer.endRecord();

        for (ParkingSpace space : this.allParkingSpaces) {
            writer.write(String.valueOf(space.getId()));
//            writer.write(String.valueOf(space.getParkingLot().getId())); // make changes here
            writer.write(String.valueOf(space.getId()));
            writer.write(String.valueOf(space.isOccupied()));
            writer.write(space.getLocation());
            writer.endRecord();
        }
        writer.close();
    }
	
	public void updateBookings(String path) throws Exception {
        CsvWriter writer = new CsvWriter(path);
        writer.write("id");
        writer.write("client");
        writer.write("totalPrice");
        writer.write("licensePlate");
        writer.write("startTime");
        writer.write("endTime");
        writer.write("parkingSpace");
        writer.write("parkingLot");
        writer.write("payment");
        writer.endRecord();

        for (Booking booking : this.allBookings) {
            writer.write(String.valueOf(booking.getID()));
            writer.write(booking.getClient().getEmail());
            writer.write(String.valueOf(booking.getTotalPrice()));
            writer.write(booking.getLicensePlate());
            writer.write(String.valueOf(booking.getStartTime()).replace('T', ' '));
            writer.write(String.valueOf(booking.getEndTime()).replace('T', ' '));
            writer.write(String.valueOf(booking.getParkingSpace().getId()));
            writer.write(booking.getParkingLot().getId());
            writer.write(String.valueOf(booking.getPayment().getId()));
            writer.endRecord();
        }
        writer.close();
    }
	
	public void updateManagers(String path) throws Exception {
        CsvWriter writer = new CsvWriter(path);
        
        writer.write("name");
        writer.write("password");
        writer.write("isSuperManager");
        writer.endRecord();

        for (Manager manager : this.allManagers) {
//            writer.write(manager.getName());
//            writer.write(manager.getPassword());
//            writer.write(String.valueOf(manager.isSuperManager()));
            writer.endRecord();
        }
        writer.close();
    }
	
	public void updateParkingLot(String path) throws Exception {
        CsvWriter writer = new CsvWriter(path);
        
        writer.write("id");
        writer.write("name");
        writer.write("state");
        writer.endRecord();

        for (ParkingLot lot : this.allParkingLots) {
            writer.write(String.valueOf(lot.getId()));
            writer.write(String.valueOf(lot.getName()));
//            writer.write(String.valueOf(lot.getState));
            writer.write("enabled");
            writer.endRecord();
        }
        writer.close();
    }

	public void updatePayments(String path) throws Exception {
		try {
			CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
			csvOutput.write("id");
			csvOutput.write("total");
			csvOutput.write("refund");
			csvOutput.write("method");
			csvOutput.write("card number");
			csvOutput.write("card name");
			csvOutput.write("cvv");
			csvOutput.write("expiry date");
			csvOutput.write("username");
			csvOutput.write("password");
			csvOutput.write("mobile number");
			csvOutput.write("provider");
			csvOutput.endRecord();

			for (Payment payment : this.allPayments) {
				csvOutput.write(String.valueOf(payment.getId()));
				csvOutput.write(String.valueOf(payment.getTotal()));
				csvOutput.write(String.valueOf(payment.getIsRefunded()));
				String paymentMethod = payment.getPaymentMethod();
				csvOutput.write(paymentMethod);
				
				if (paymentMethod.equals("Credit Card")) {
					CreditCardStrategy creditCardStrategy = (CreditCardStrategy) payment.getPaymentStrategy();
					csvOutput.write(String.valueOf(creditCardStrategy.getCardNumber()));
					csvOutput.write(creditCardStrategy.getCardHolderName());
					csvOutput.write(creditCardStrategy.getCVV());
					csvOutput.write(creditCardStrategy.getExpiryDate());
					csvOutput.write("");
					csvOutput.write("");
					csvOutput.write("");
					csvOutput.write("");
				}
				else if (paymentMethod.equals("Debit Card")) {
					DebitCardStrategy debitCardStrategy = (DebitCardStrategy) payment.getPaymentStrategy();
					csvOutput.write(String.valueOf(debitCardStrategy.getCardNumber()));
					csvOutput.write(debitCardStrategy.getCardHolderName());
					csvOutput.write(debitCardStrategy.getCVV());
					csvOutput.write(debitCardStrategy.getExpiryDate());
					csvOutput.write("");
					csvOutput.write("");
					csvOutput.write("");
					csvOutput.write("");
				}
				else if (paymentMethod.equals("PayPal")) {
					PayPalStrategy payPalStrategy = (PayPalStrategy) payment.getPaymentStrategy();
					csvOutput.write("");
					csvOutput.write("");
					csvOutput.write("");
					csvOutput.write("");
					csvOutput.write(payPalStrategy.getUsername());
					csvOutput.write(payPalStrategy.getPassword());
					csvOutput.write("");
					csvOutput.write("");
				}
				else {
					MobilePaymentStrategy mobilePaymentStrategy = (MobilePaymentStrategy) payment.getPaymentStrategy();
					csvOutput.write("");
					csvOutput.write("");
					csvOutput.write("");
					csvOutput.write("");
					csvOutput.write("");
					csvOutput.write("");
					csvOutput.write(mobilePaymentStrategy.getMobileNumber());
					csvOutput.write(mobilePaymentStrategy.getProvider());
				}
				csvOutput.endRecord(); // Ends the row properly
			}
			csvOutput.flush(); // Ensure data is written before closing
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateClients(String path) throws Exception {
		try {
			CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
			csvOutput.write("email");
			csvOutput.write("password");
			csvOutput.write("type");
			csvOutput.endRecord();

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
			csvOutput.flush(); // Ensure data is written before closing
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadEverything() throws Exception {
		String clientDataPath = Paths.get("src", "clientData.csv").toString();
		String managerDataPath = Paths.get("src", "managerData.csv").toString();
		String bookingDataPath = Paths.get("src", "bookingData.csv").toString();
		String paymentDataPath = Paths.get("src", "paymentData.csv").toString();
		String parkingSpaceDataPath = Paths.get("src", "parkingSpaceData.csv").toString();
		String parkingLotDataPath = Paths.get("src", "parkinglotData.csv").toString();
		Database db = Database.getInstance();
		
		
		
		try {
			db.loadClients(clientDataPath);
			db.loadManagers(managerDataPath);
			db.loadPayments(paymentDataPath);
			db.loadParkingLot(parkingLotDataPath);
			db.loadParkingSpaces(parkingSpaceDataPath);
			db.loadBookings(bookingDataPath);
			
			NonFaculty n = (NonFaculty) db.allClients.get(0);
			System.out.println(n.getAccountApproved());
			Student newClient = (Student) Client.registerUser("Student", "test@gmail.com", "321");
			System.out.println(newClient.getAccountApproved());
			SuperManager superM = SuperManager.getSuperManagerInstance("admin", "admin");
//			superM.executeCommand(new UpdateParkingSpaceCommand("disable",3,"1"));
			
			System.out.println(db.getAllParkingLots().get(0).getParkingSpaces()[3].isEnabled());
			superM.executeCommand(new AddParkingLotCommand(ParkingLot.randomIdGenerator(),"Jordan"));
			System.out.println("asdada");
//			System.out.println(db.getAllParkingLots().get(0));
			System.out.println(db.getAllParkingLots().get(3).getName());
//			System.out.println(SuperManager.getSuperManagerInstance("", "").getSuperManagerData());
			
//			System.out.println(Manager.authenticate("justin", "67823123"));
//			Student s = new Student("jordan","123");
//			LocalDateTime startTime = LocalDateTime.of(2025, 3, 1, 10, 0);
//			LocalDateTime endTime = LocalDateTime.of(2025, 3, 1, 12, 0);
//			Payment p = new Payment();
//			p.setPaymentMethod(new PayPalStrategy("email", "password"));
//			p.payAmount(35.0);
//			
//			System.out.println(s.selectSpace("1",3,"ABC-123",100,35.0,startTime, endTime, p));
//			
//			s.updateParking("Extend", null, s.bookings.get(0));
//			System.out.println(s.bookings.size());
			
			//System.out.println(s.isValidLicensePlate("ABC-123"));
//			System.out.println(s.bookings.get(0).getEndTime());
//			db.updateBookings(bookingDataPath);
			
//			db.updateParkingSpaces(parkingSpaceDataPath);
//			db.updateParkingLot(parkingLotDataPath);
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
//			PaymentStrategy paymentStrategy = new MobilePaymentStrategy("asdasd","google pay");
//			Payment p = new Payment(++Payment.nextPaymentId,31,false,paymentStrategy);
//			db.allPayments.add(p);
//			db.updatePayments(paymentDataPath);
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

	@Override
	public void update(int spaceId,String lotId, boolean isOccupied) {
		for (ParkingLot lot: this.allParkingLots) {
			if(lot.getId().equals(lotId)) {
				lot.getParkingSpaces()[spaceId].setOccupied(isOccupied);
			}
		}
		
		
	}

}