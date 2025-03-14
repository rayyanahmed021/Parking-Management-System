package backend;
import java.security.SecureRandom;
public class SuperManager extends Manager {
    private static SuperManager singleSuperManagerInstance;

    private SuperManager(String name, String password) {
        super(name, password); // Default credentials
    }
    
    public static String generateStrongPassword(int length) {
    	String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	    String NUMBERS = "0123456789";
	    String SYMBOLS = "!@#$%^&*()-_+=<>?/";
	    String ALL_CHARACTERS = UPPERCASE + LOWERCASE + NUMBERS + SYMBOLS;
	    SecureRandom random = new SecureRandom();
        if (length < 8) {
            throw new IllegalArgumentException("Password length must be at least 8 characters.");
        }

        StringBuilder password = new StringBuilder(length);
        
        // Ensure at least one character from each required category
        password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        password.append(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));
        
        // Fill the rest with random characters
        for (int i = 4; i < length; i++) {
            password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }
        
        // Shuffle the password to ensure randomness
        return shuffleString(password.toString(), random);
    }

    private static String shuffleString(String input, SecureRandom random) {
        char[] array = input.toCharArray();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new String(array);
    }
    
    public static String generateUsername(String firstName, String lastName) {
    	SecureRandom random = new SecureRandom();
        String initials = (firstName.substring(0, 1) + lastName.substring(0, 1)).toLowerCase();
        int randomNumber = random.nextInt(10000);
        return initials + randomNumber;
    }
    
    public static String generateUniqueUsername(String firstName, String lastName, Database db) {
        String randomName;
        boolean isUnique;

        do {
            randomName = generateUsername(firstName, lastName);
            isUnique = true;
            
            for (Manager m : db.getAllManagers()) {
                if (randomName.equals(m.getName())) {
                    isUnique = false;
                    break;
                }
            }
        } while (!isUnique);

        return randomName;
    }



    public String[] createManagerAccount(String firstname, String lastname) {
    	Database db = Database.getInstance();
    	String name = generateUniqueUsername(firstname, lastname, db);
    	String password = generateStrongPassword(8);
        Manager newManager = new Manager(name, password);
        db.getAllManagers().add(newManager);
        
        String[] output = {name, password};
        return output;
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
