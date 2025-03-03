public class SuperManager extends Manager {
    private static SuperManager singleSuperManagerInstance;

    private SuperManager() {
        super("Admin", "SecurePassword"); // Default credentials
    }

    public String createManagerAccount(String name, String password) {
        Manager newManager = new Manager(name, password);
        Database.getInstance().getAllManagers().add(newManager);
        return "Manager account created for: " + name;
    }
    
    public static SuperManager getSuperManagerInstance() {
        if (singleSuperManagerInstance == null) {
            singleSuperManagerInstance = new SuperManager();
        }
        return singleSuperManagerInstance;
    }

    public String getSuperManagerData() {
        return "SuperManager Name: " + this.name;
    }
}
