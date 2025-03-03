public class GenerateClientFactory {
	public Client getClientInstance(String clientType, String email, String password) {
		String givenType = clientType.toLowerCase();
		Client client = null;
		
		if (givenType.equals("student")) {
			client = new Student(email, password, false);
		}
		else if(givenType.equals("faculty")) {
			client = new Faculty(email, password, false);
		}
		else if (givenType.equals("nonfaculty")) {
			client = new NonFaculty(email, password, false);
		}
		else if(givenType.equals("visitor")) {
			client = new Visitor(email, password);
		}
		else {
			client = null;
		}
		
		return client;
	}
}
