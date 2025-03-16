package backend;
public class ValidateClientRegistrationCommand implements ParkingCommand {
    private String clientId;
    
    public ValidateClientRegistrationCommand(String clientId) {
    	this.clientId = clientId;
    }
    @Override
    public void execute() {
    	
    	Database database = Database.getInstance();
    	for (Client c: database.getAllClients()) {
    		if (c.getEmail().equals(this.clientId)) {
	    		if (c instanceof Student) {
					Student s = (Student) c;
					s.setAccountApproved(true);
				} else if (c instanceof Faculty) {
					Faculty s = (Faculty) c;
					s.setAccountApproved(true);
				} else if (c instanceof NonFaculty) {
					NonFaculty s = (NonFaculty) c;
					s.setAccountApproved(true);
				}
    		}
    	}
    	
    }

    private boolean isValidEmail(String email, String clientType) {
        if (clientType.equalsIgnoreCase("student") && email.endsWith("@student.yorku.ca")) {
            return true;
        } else if (clientType.equalsIgnoreCase("faculty") && email.endsWith("@faculty.yorku.ca")) {
            return true;
        } else if (clientType.equalsIgnoreCase("nonfaculty") && email.endsWith("@staff.yorku.ca")) {
            return true;
        }else if (clientType.equalsIgnoreCase("visitor") && !email.endsWith("@staff.yorku.ca") && !email.endsWith("@faculty.yorku.ca") && !email.endsWith("@student.yorku.ca")) {
        	return true;
        }return false;
    }
}
