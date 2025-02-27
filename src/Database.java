import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class Database {
	private ArrayList<Payment> allPayments;
	private ArrayList<Booking> allBookings;
	private ArrayList<Client> allClients;
	private ArrayList<Manager> allManagers;
	private ArrayList<ParkingLot> allParkingLots;
	
	public ArrayList<Client> getAllClients() {
		return allClients;
	}

	public void setAllClients(ArrayList<Client> allClients) {
		this.allClients = allClients;
	}

	private ArrayList<ParkingSpace> allParkingSpace;
	
	public void load(String path) throws Exception{
		CsvReader reader = new CsvReader(path); 
		reader.readHeaders();
		Client client = null;
		
		while(reader.readRecord()){ 
			String email = reader.get("email");
			String pass = reader.get("password");
			
			client = new Client(email, pass);
			this.allClients.add(client);
		}
		
	}
	
	public void update(String type, String path) throws Exception{
		try {		
				CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
				
				if (type.equals("Client")) {
					for(Client c: this.allClients) {
						csvOutput.write(c.getEmail());
						csvOutput.write(c.getPassword());
					}
				}
				csvOutput.close();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "/Users/rayyanahmed/eclipse-workspace/3311-deliverable-2/src/clientData.csv";
		Database db = new Database();
		try {
			db.load(path);
			db.allClients.get(1).setEmail("mynameisrayy@gmail.com");
			System.out.println(db.allClients.get(1).getEmail());
			db.update("Client", path);
			System.out.println("HI THERE");
		} catch (Exception e) {
		}
		
	}

}
