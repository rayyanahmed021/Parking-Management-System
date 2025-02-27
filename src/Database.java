import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
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

	public void load(String path) throws Exception {
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
		String relativePath = Paths.get("src", "clientData.csv").toString();

		Database db = Database.getInstance();
		
		
		
		try {
			db.load(relativePath);
			Client c = Client.registerUser("student", "ugly@gmail.com","123");
			//System.out.println(c.authenticate("ra@gmail.com", "123"));
			db.update("Client", relativePath);
		} catch (Exception e) {
			e.printStackTrace(); // Print exception details
		}
	  
	}

}
