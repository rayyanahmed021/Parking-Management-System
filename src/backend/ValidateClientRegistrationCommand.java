package backend;
public class ValidateClientRegistrationCommand implements ParkingCommand {
    private String clientId;
//    private String email;
//    private String password;
//    private Database database;
//
//    public ValidateClientRegistrationCommand(String clientType, String email, String password) {
//        this.clientType = clientType;
//        this.email = email;
//        this.password = password;
//        this.database = Database.getInstance();
//    }
    
    public ValidateClientRegistrationCommand(String clientId) {
    	this.clientId = clientId;
    }
    @Override
    public void execute() {
//        if (!isValidEmail(email, clientType)) {
//            //System.out.println("Registration failed");
//            return;
//        }
//
//        Client newClient;
//        switch (clientType.toLowerCase()) {
//            case "student":
//                newClient = new Student(email, password);
//                break;
//            case "faculty":
//                newClient = new Faculty(email, password);
//                break;
//            case "nonfaculty":
//                newClient = new NonFaculty(email, password);
//                break;
//            case "visitor":
//                newClient = new NonFaculty(email, password);
//                break;
//            default:
//                //System.out.println("Invalid");
//                return;
//        }

//        database.getAllClients().add(newClient);
        //System.out.println("registered and validated.");
    	
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
