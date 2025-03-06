package backend;
public class SuperManager extends Manager {
    private static SuperManager singleSuperManagerInstance;

    private SuperManager(String name, String password) {
        super(name, password); // Default credentials
    }

    public boolean createManagerAccount(String name, String password) {
    	Database db = Database.getInstance();
    	for(Manager m: db.getAllManagers()) {
    		if (name.equals(m.getName())) {
    			return false;
    		}
    	}
        Manager newManager = new Manager(name, password);
        db.getAllManagers().add(newManager);
        
        return true;
    }
    
    public static boolean authenticate(String name, String password) {

		boolean isLoggedIn = false;
		Database database = Database.getInstance();
		
		for (Manager manager : database.getAllManagers()) {
			if (manager.getName().equals(name) && manager.getPassword().equals(password)) {
				if (manager instanceof SuperManager) {
					isLoggedIn = true;
					return isLoggedIn;
				}
				else {
					return false;
				}
			}
		}
		return isLoggedIn;
	}
    
    public static SuperManager getSuperManagerInstance(String name, String password) {
        if (singleSuperManagerInstance == null) {
            singleSuperManagerInstance = new SuperManager(name,password);
        }
        return singleSuperManagerInstance;
    }

    public String getSuperManagerData() {
        return "SuperManager Name: " + this.name;
    }
}
