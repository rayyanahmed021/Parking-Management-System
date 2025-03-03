public class ValidateClientRegistrationCommand implements ParkingCommand {
    private String clientType;
    private String email;
    private String password;
    private Database database;

    public ValidateClientRegistrationCommand(String clientType, String email, String password) {
        this.clientType = clientType;
        this.email = email;
        this.password = password;
        this.database = Database.getInstance();
    }

    @Override
    public void execute() {
        if (!isValidEmail(email, clientType)) {
            //System.out.println("Registration failed");
            return;
        }

        Client newClient;
        switch (clientType.toLowerCase()) {
            case "student":
                newClient = new Student(email, password);
                break;
            case "faculty":
                newClient = new Faculty(email, password);
                break;
            case "nonfaculty":
                newClient = new NonFaculty(email, password);
                break;
            case "visitor":
                newClient = new NonFaculty(email, password);
                break;
            default:
                //System.out.println("Invalid");
                return;
        }

        database.getAllClients().add(newClient);
        //System.out.println("registered and validated.");
    }

    private boolean isValidEmail(String email, String clientType) {
        if (clientType.equalsIgnoreCase("student") && email.endsWith("@student.university.edu")) {
            return true;
        } else if (clientType.equalsIgnoreCase("faculty") && email.endsWith("@faculty.university.edu")) {
            return true;
        } else if (clientType.equalsIgnoreCase("nonfaculty") && email.endsWith("@staff.university.edu")) {
            return true;
        }else if (clientType.equalsIgnoreCase("visitor") && !email.endsWith("@staff.university.edu") && !email.endsWith("@faculty.university.edu") && !email.endsWith("@student.university.edu")) {
        	return true;
        }return false;
    }
}
