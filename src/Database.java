import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class Database {
	private ArrayList<Payment> allPayments;
	private ArrayList<Booking> allBookings;
	private ArrayList<Client> allClients;
	private ArrayList<Manager> allManagers;
	private ArrayList<ParkingLot> allParkingLots;
	private ArrayList<ParkingSpace> allParkingSpaces;
	
	public Database() {
		this.allPayments = new ArrayList<Payment>();
		this.allBookings = new ArrayList<Booking>();
		this.allClients = new ArrayList<Client>();
		this.allManagers = new ArrayList<Manager>();
		this.allParkingLots = new ArrayList<ParkingLot>();
		this.allParkingSpaces = new ArrayList<ParkingSpace>();
		
	}
	public ArrayList<Client> getAllClients() {
		return allClients;
	}

	public void setAllClients(ArrayList<Client> allClients) {
		this.allClients = allClients;
	}


	
	public void load(String path) throws Exception{
		CsvReader reader = new CsvReader(path); 
		reader.readHeaders();
		Client client = null;
		
		while(reader.readRecord()){ 
			String email = reader.get("email");
			String pass = reader.get("password");
			
			client = new Client(email, pass);
			System.out.println(client.getEmail());
			this.allClients.add(client);
		}
		
	}//comment
	
	public void update(String type, String path) throws Exception {
	    try {
	    	CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
	    	csvOutput.write("email");
            csvOutput.write("password");
            csvOutput.endRecord();
	        if (type.equals("Client")) {
	            for (Client c : this.allClients) {
	                csvOutput.write(c.getEmail());
	                csvOutput.write(c.getPassword());
	                csvOutput.endRecord(); // Properly ends the row
	            }
	        }
	        csvOutput.flush(); // Ensure all data is written before closing
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void main(String[] args) {
		String relativePath = Paths.get("src", "clientData.csv").toString();
	    Database db = new Database();
	    try {
	    	db.load(relativePath);
	        db.allClients.get(1).setEmail("mynameisrayyyyyyyyyy@gmail.com");
	        System.out.println(db.allClients.get(1).getEmail());
	        db.update("Client", relativePath);
	    } catch (Exception e) {
	        e.printStackTrace(); // Print exception details
	    }
	}

}
