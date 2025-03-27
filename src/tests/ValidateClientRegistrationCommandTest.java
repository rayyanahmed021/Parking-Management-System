package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;
import java.lang.reflect.Method;

public class ValidateClientRegistrationCommandTest {

    private ValidateClientRegistrationCommand validateCommand;
    private Database database;

    @Before
    public void setUp() throws Exception {
        database = Database.getInstance();
        Client studentClient = new Student("student1@student.yorku.ca", "password123", false);
        Client studentClient1 = new Student("rah@student.yorku.ca", "password123", false);
        Client facultyClient = new Faculty("faculty1@faculty.yorku.ca", "password123", false);
        Client facultyClient1 = new Faculty("faculty2@faculty.yorku.ca", "password123", false);
        Client nonFacultyClient = new NonFaculty("nonfaculty1@staff.yorku.ca", "password123", false);
        Client nonFacultyClient1 = new NonFaculty("nonfaculty2@staff.yorku.ca", "password", false);
        Client visitorClient = new Visitor("visitor1@visitors.yorku.ca", "password123");
        Client visitorClient1 = new Visitor("visitor@gmail.com", "password123");

        database.getAllClients().add(studentClient);
        database.getAllClients().add(studentClient1);
        database.getAllClients().add(facultyClient);
        database.getAllClients().add(facultyClient1);
        database.getAllClients().add(nonFacultyClient);
        database.getAllClients().add(nonFacultyClient1);
        database.getAllClients().add(visitorClient);
        database.getAllClients().add(visitorClient1);
        
        validateCommand = new ValidateClientRegistrationCommand("test@example.com");
    }

    @Test
    public void test_1() {
        validateCommand = new ValidateClientRegistrationCommand("student1@student.yorku.ca");
        validateCommand.execute();

        Client client = database.getAllClients().stream()
            .filter(c -> c.getEmail().equals("student1@student.yorku.ca"))
            .findFirst()
            .orElse(null);

        assertNotNull("Student client should exist", client);
        assertTrue("Student client account should be approved", client instanceof Student);
        assertTrue("Student client account should be approved", ((Student) client).getAccountApproved());
    }

    @Test
    public void test_2() {
    	validateCommand = new ValidateClientRegistrationCommand("rah@student.yorku.ca");
        validateCommand.execute();

        Client client = database.getAllClients().stream()
            .filter(c -> c.getEmail().equals("rah@student.yorku.ca"))
            .findFirst()
            .orElse(null);

        assertNotNull("Student client should exist", client);
        assertTrue("Student client account should be approved", client instanceof Student);
        assertTrue("Student client account should be approved", ((Student) client).getAccountApproved());
    }

    @Test
    public void test_3() {
        validateCommand = new ValidateClientRegistrationCommand("faculty1@faculty.yorku.ca");
        validateCommand.execute();

        Client client = database.getAllClients().stream()
            .filter(c -> c.getEmail().equals("faculty1@faculty.yorku.ca"))
            .findFirst()
            .orElse(null);

        assertNotNull("Faculty client should exist", client);
        assertTrue("Faculty client account should be approved", client instanceof Faculty);
        assertTrue("Faculty client account should be approved", ((Faculty) client).getAccountApproved());
    }

    @Test
    public void test_4() {
        validateCommand = new ValidateClientRegistrationCommand("faculty2@faculty.yorku.ca");
        validateCommand.execute();

        Client client = database.getAllClients().stream()
            .filter(c -> c.getEmail().equals("faculty2@faculty.yorku.ca"))
            .findFirst()
            .orElse(null);

        assertNotNull("Faculty client should exist", client);
        assertTrue("Faculty client account should be approved", client instanceof Faculty);
        assertTrue("Faculty client account should be approved", ((Faculty) client).getAccountApproved());
    }

    @Test
    public void test_5() {
        validateCommand = new ValidateClientRegistrationCommand("nonfaculty1@staff.yorku.ca");
        validateCommand.execute();

        Client client = database.getAllClients().stream()
            .filter(c -> c.getEmail().equals("nonfaculty1@staff.yorku.ca"))
            .findFirst()
            .orElse(null);

        assertNotNull("Non-faculty client should exist", client);
        assertTrue("Non-faculty client account should be approved", client instanceof NonFaculty);
        assertTrue("Non-faculty client account should be approved", ((NonFaculty) client).getAccountApproved());
    }

    @Test
    public void test_6() {
        validateCommand = new ValidateClientRegistrationCommand("nonfaculty2@staff.yorku.ca");
        validateCommand.execute();

        Client client = database.getAllClients().stream()
            .filter(c -> c.getEmail().equals("nonfaculty2@staff.yorku.ca"))
            .findFirst()
            .orElse(null);

        assertNotNull("Non-faculty client should exist", client);
        assertTrue("Non-faculty client account should be approved", client instanceof NonFaculty);
        assertTrue("Non-faculty client account should be approved", ((NonFaculty) client).getAccountApproved());
    }

    @Test
    public void test_7() {
        validateCommand = new ValidateClientRegistrationCommand("visitor1@visitors.yorku.ca");
        validateCommand.execute();

        Client client = database.getAllClients().stream()
            .filter(c -> c.getEmail().equals("visitor1@visitors.yorku.ca"))
            .findFirst()
            .orElse(null);

        assertNotNull("Visitor client should exist", client);
        assertTrue("Visitor client account should be approved", client instanceof Visitor);
    }

    @Test
    public void test_8() {
        validateCommand = new ValidateClientRegistrationCommand("visitor@gmail.com");
        validateCommand.execute();

        Client client = database.getAllClients().stream()
            .filter(c -> c.getEmail().equals("visitor@gmail.com"))
            .findFirst()
            .orElse(null);

        assertNotNull("Visitor client should exist", client);
        assertTrue("Visitor client account should be approved", client instanceof Visitor);
    }

    @Test
    public void test_9() {
        validateCommand = new ValidateClientRegistrationCommand("student1@student.yorku.ca");
        validateCommand.execute();

        Client client = database.getAllClients().stream()
            .filter(c -> c.getEmail().equals("student1@student.yorku.ca"))
            .findFirst()
            .orElse(null);

        assertNotNull("Client should exist", client);
        assertTrue("Client account should be approved only once", client instanceof Student && ((Student) client).getAccountApproved());
    }
    
    @Test
    public void test_10() {
        validateCommand = new ValidateClientRegistrationCommand("invalidemail");
        validateCommand.execute();
        
        Client client = database.getAllClients().stream()
            .filter(c -> c.getEmail().equals("invalidemail"))
            .findFirst()
            .orElse(null);

        assertNull("Invalid email should not pass validation", client);
    }
    
    @Test
    public void test_11() throws Exception {
        Method method = ValidateClientRegistrationCommand.class.getDeclaredMethod("isValidEmail", String.class, String.class);
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(validateCommand, "student1@student.yorku.ca", "student");
        
        assertTrue("The email should be valid for student", result);
    }
    
    @Test
    public void test_12() throws Exception {
        Method method = ValidateClientRegistrationCommand.class.getDeclaredMethod("isValidEmail", String.class, String.class);
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(validateCommand, "faculty1@faculty.yorku.ca", "faculty");
        
        assertTrue("The email should be valid for faculty", result);
    }
    
    @Test
    public void test_13() throws Exception {
        Method method = ValidateClientRegistrationCommand.class.getDeclaredMethod("isValidEmail", String.class, String.class);
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(validateCommand, "nonfaculty1@staff.yorku.ca", "nonfaculty");
        
        assertTrue("The email should be valid for non-faculty", result);
    }
    
    @Test
    public void test_14() throws Exception {
        Method method = ValidateClientRegistrationCommand.class.getDeclaredMethod("isValidEmail", String.class, String.class);
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(validateCommand, "visitor1@visitors.yorku.ca", "visitor");
        
        assertTrue("The email should be valid for visitor", result);
    }
}
