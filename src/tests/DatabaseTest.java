package tests;

import backend.*;

import static org.junit.Assert.*;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.csvreader.CsvWriter;

public class DatabaseTest {
    private Database db;
    private String testDataDir = "test_data";
    
    @Before
    public void setUp() throws Exception {
        // Get singleton instance
        db = Database.getInstance();
        
        // Clear existing data
        clearDatabase();
        
        // Create test directory
        new File(testDataDir).mkdirs();
        
        // Create test CSV files
        createTestFiles();
    }
    
    @After
    public void tearDown() throws Exception {
        // Clean up test files
        deleteTestFiles();
    }
    
    private void clearDatabase() {
        db.setAllClients(new ArrayList<>());
        db.setAllManagers(new ArrayList<>());
        db.setAllParkingLots(new ArrayList<>());
        db.setAllParkingSpaces(new ArrayList<>());
        db.setAllPayments(new ArrayList<>());
        db.setAllBookings(new ArrayList<>());
    }
    
    private void createTestFiles() throws Exception {
        createClientTestFile();
        createManagerTestFile();
        createParkingLotTestFile();
        createParkingSpaceTestFile();
        createPaymentTestFile();
        createBookingTestFile();
    }
    
    private void createClientTestFile() throws Exception {
        CsvWriter writer = new CsvWriter(testDataDir + "/clientData.csv");
        writer.writeRecord(new String[]{"email", "password", "type", "isApproved"});
        writer.writeRecord(new String[]{"student@test.com", "pass123", "student", "true"});
        writer.writeRecord(new String[]{"faculty@test.com", "pass456", "faculty", "true"});
        writer.writeRecord(new String[]{"visitor@test.com", "pass789", "visitor", ""});
        writer.writeRecord(new String[]{"nonfaculty@test.com", "pass000", "nonfaculty", "false"});
        writer.close();
    }
    
    private void createManagerTestFile() throws Exception {
        CsvWriter writer = new CsvWriter(testDataDir + "/managerData.csv");
        writer.writeRecord(new String[]{"name", "password", "isSuperManager"});
        writer.writeRecord(new String[]{"admin", "admin123", "true"});
        writer.writeRecord(new String[]{"manager1", "mgr123", "false"});
        writer.close();
    }
    
    private void createParkingLotTestFile() throws Exception {
        CsvWriter writer = new CsvWriter(testDataDir + "/parkinglotData.csv");
        writer.writeRecord(new String[]{"id", "name", "state", "location"});
        writer.writeRecord(new String[]{"L1", "North Lot", "enabled", "North Campus"});
        writer.writeRecord(new String[]{"L2", "South Lot", "disabled", "South Campus"});
        writer.close();
    }
    
    private void createParkingSpaceTestFile() throws Exception {
        CsvWriter writer = new CsvWriter(testDataDir + "/parkingSpaceData.csv");
        writer.writeRecord(new String[]{"id", "lot", "occupied", "isEnabled"});
        writer.writeRecord(new String[]{"1", "L1", "false", "true"});
        writer.writeRecord(new String[]{"2", "L1", "true", "true"});
        writer.writeRecord(new String[]{"1", "L2", "false", "false"});
        writer.close();
    }
    
    private void createPaymentTestFile() throws Exception {
        CsvWriter writer = new CsvWriter(testDataDir + "/paymentData.csv");
        writer.writeRecord(new String[]{"id", "total", "refund", "method", "card number", "card name", "cvv", "expiry date", 
            "username", "password", "mobile number", "provider"});
        writer.writeRecord(new String[]{"1", "10.50", "false", "Credit Card", "1234567812345678", "John Doe", "123", "12/25", 
            "", "", "", ""});
        writer.writeRecord(new String[]{"2", "15.75", "true", "PayPal", "", "", "", "", "user@paypal", "paypal123", "", ""});
        writer.writeRecord(new String[]{"3", "5.00", "false", "Mobile", "", "", "", "", "", "", "5551234567", "Verizon"});
        writer.close();
    }
    
    private void createBookingTestFile() throws Exception {
        CsvWriter writer = new CsvWriter(testDataDir + "/bookingData.csv");
        writer.writeRecord(new String[]{"id", "client", "totalPrice", "licensePlate", "startTime", "endTime", "parkingSpace", "parkingLot", "payment"});
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String later = LocalDateTime.now().plusHours(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        writer.writeRecord(new String[]{"1", "student@test.com", "10.50", "ABC123", now, later, "1", "L1", "1"});
        writer.writeRecord(new String[]{"2", "faculty@test.com", "15.75", "XYZ789", now, later, "2", "L1", "2"});
        writer.close();
    }
    
    private void deleteTestFiles() {
        new File(testDataDir + "/clientData.csv").delete();
        new File(testDataDir + "/managerData.csv").delete();
        new File(testDataDir + "/parkinglotData.csv").delete();
        new File(testDataDir + "/parkingSpaceData.csv").delete();
        new File(testDataDir + "/paymentData.csv").delete();
        new File(testDataDir + "/bookingData.csv").delete();
        new File(testDataDir).delete();
    }

    @Test
    public void test1() throws Exception {
        db.loadClients(testDataDir + "/clientData.csv");
        
        assertEquals(4, db.getAllClients().size());
        
        // Verify student
        Client student = db.getAllClients().get(0);
        assertTrue(student instanceof Student);
        assertEquals("student@test.com", student.getEmail());
        assertTrue(((Student)student).getAccountApproved());
        
        // Verify faculty
        Client faculty = db.getAllClients().get(1);
        assertTrue(faculty instanceof Faculty);
        assertEquals("faculty@test.com", faculty.getEmail());
        
        // Verify visitor
        Client visitor = db.getAllClients().get(2);
        assertTrue(visitor instanceof Visitor);
        assertEquals("visitor@test.com", visitor.getEmail());
        
        // Verify non-faculty
        Client nonFaculty = db.getAllClients().get(3);
        assertTrue(nonFaculty instanceof NonFaculty);
        assertFalse(((NonFaculty)nonFaculty).getAccountApproved());
    }
    
    @Test
    public void test2() throws Exception {
        // Load initial data
        db.loadClients(testDataDir + "/clientData.csv");
        
        // Modify a client
        Student student = (Student) db.getAllClients().get(0);
        student.setPassword("newpassword123");
        ((Student)student).setAccountApproved(false);
        
        // Add a new client
        Faculty newFaculty = new Faculty("newfaculty@test.com", "faculty123", false);
        db.getAllClients().add(newFaculty);
        
        // Update the file
        db.updateClients(testDataDir + "/clientData_updated.csv");
        
        // Verify the update
        Database newDb = Database.getInstance();
        newDb.loadClients(testDataDir + "/clientData_updated.csv");
        
        assertEquals(10, newDb.getAllClients().size());
        
        // Verify modified student
        Student updatedStudent = (Student) newDb.getAllClients().get(0);
        assertEquals("newpassword123", updatedStudent.getPassword());
        assertFalse(updatedStudent.getAccountApproved());
        
        // Verify new faculty
        Faculty addedFaculty = (Faculty) newDb.getAllClients().get(4);
        assertEquals("newfaculty@test.com", addedFaculty.getEmail());
        assertFalse(addedFaculty.getAccountApproved());
    }
    
    @Test
    public void test3() throws Exception {
        db.loadManagers(testDataDir + "/managerData.csv");
        
        assertEquals(2, db.getAllManagers().size());
        
        // Verify super manager
        Manager admin = db.getAllManagers().get(0);
        assertTrue(admin instanceof SuperManager);
        
    }
    
    @Test
    public void test4() throws Exception {
        // Load initial data
        db.loadManagers(testDataDir + "/managerData.csv");
        
        // Modify a manager
        Manager manager = db.getAllManagers().get(1);
        manager.setPassword("newmgrpassword");
        
        // Add a new manager
        Manager newManager = new Manager("manager2", "mgr456");
        db.getAllManagers().add(newManager);
        
        // Update the file
        db.updateManagers(testDataDir + "/managerData_updated.csv");
        
        // Verify the update
        Database newDb = Database.getInstance();
        newDb.loadManagers(testDataDir + "/managerData_updated.csv");
        
        assertEquals(6, newDb.getAllManagers().size());
        assertEquals("newmgrpassword", newDb.getAllManagers().get(1).getPassword());
        assertEquals("manager2", newDb.getAllManagers().get(2).getName());
    }
    
    @Test
    public void test5() throws Exception {
        db.loadParkingLot(testDataDir + "/parkinglotData.csv");
        
        assertEquals(2, db.getAllParkingLots().size());
        
        // Verify enabled lot
        ParkingLot lot1 = db.getAllParkingLots().get(0);
        assertEquals("L1", lot1.getId());
        assertTrue(lot1.getState() instanceof EnabledState);
        assertEquals("North Lot", lot1.getName());
        
        // Verify disabled lot
        ParkingLot lot2 = db.getAllParkingLots().get(1);
        assertEquals("L2", lot2.getId());
        assertTrue(lot2.getState() instanceof DisabledState);
        assertEquals("South Campus", lot2.getLocation());
    }
    
    @Test
    public void test6() throws Exception {
        // Load initial data
        db.loadParkingLot(testDataDir + "/parkinglotData.csv");
        
        // Modify a lot
        ParkingLot lot = db.getAllParkingLots().get(0);
        lot.setState(new DisabledState());
        lot.setName("Updated North Lot");
        
        // Add a new lot
        ParkingLot newLot = new ParkingLot("L3", "East Lot", new EnabledState(), new ParkingSpace[20], "East Campus");
        db.getAllParkingLots().add(newLot);
        
        // Update the file
        db.updateParkingLot(testDataDir + "/parkinglotData_updated.csv");
        
        // Verify the update
        Database newDb = Database.getInstance();
        newDb.loadParkingLot(testDataDir + "/parkinglotData_updated.csv");
        
        assertEquals(6, newDb.getAllParkingLots().size());
        
        // Verify modified lot
        ParkingLot updatedLot = newDb.getAllParkingLots().get(0);
        assertTrue(updatedLot.getState() instanceof DisabledState);
        assertEquals("Updated North Lot", updatedLot.getName());
        
        // Verify new lot
        ParkingLot addedLot = newDb.getAllParkingLots().get(2);
        assertEquals("L3", addedLot.getId());
        assertTrue(addedLot.getState() instanceof EnabledState);
    }
    
    @Test
    public void test7() throws Exception {
        // Need to load parking lots first
        db.loadParkingLot(testDataDir + "/parkinglotData.csv");
        db.loadParkingSpaces(testDataDir + "/parkingSpaceData.csv");
        
        assertEquals(3, db.getAllParkingSpaces().size());
        
        // Verify space in L1
        ParkingSpace space1 = db.getAllParkingSpaces().get(0);
        assertEquals(1, space1.getId());
        assertEquals("L1", space1.getParkingLot().getId());
        assertFalse(space1.isOccupied());
        assertTrue(space1.isEnabled());
        
        // Verify occupied space
        ParkingSpace space2 = db.getAllParkingSpaces().get(1);
        assertEquals(2, space2.getId());
        assertTrue(space2.isOccupied());
        
        // Verify disabled space
        ParkingSpace space3 = db.getAllParkingSpaces().get(2);
        assertEquals(1, space3.getId());
        assertEquals("L2", space3.getParkingLot().getId());
        assertFalse(space3.isEnabled());
    }
    
    @Test
    public void test8() throws Exception {
        // Load dependencies
        db.loadParkingLot(testDataDir + "/parkinglotData.csv");
        db.loadParkingSpaces(testDataDir + "/parkingSpaceData.csv");
        
        // Modify a space
        ParkingSpace space = db.getAllParkingSpaces().get(0);
        space.setOccupied(true);
        space.setEnabled(false);
        
        // Add a new space
        ParkingLot lot = db.getAllParkingLots().get(0);
        ParkingSpace newSpace = new ParkingSpace();
        newSpace.setId(3);
        newSpace.setParkingLot(lot);
        newSpace.setOccupied(false);
        newSpace.setEnabled(true);
        db.getAllParkingSpaces().add(newSpace);
        lot.getParkingSpaces()[3] = newSpace;
        
        // Update the file
        db.updateParkingSpaces(testDataDir + "/parkingSpaceData_updated.csv");
        
        // Verify the update
        Database newDb = Database.getInstance();
        newDb.loadParkingLot(testDataDir + "/parkinglotData.csv");
        newDb.loadParkingSpaces(testDataDir + "/parkingSpaceData_updated.csv");
        
        assertEquals(8, newDb.getAllParkingSpaces().size());
        
        // Verify modified space
        ParkingSpace updatedSpace = newDb.getAllParkingSpaces().get(0);
        assertTrue(updatedSpace.isOccupied());
        assertFalse(updatedSpace.isEnabled());
        
        // Verify new space
        ParkingSpace addedSpace = newDb.getAllParkingSpaces().get(3);
        assertEquals(3, addedSpace.getId());
        assertTrue(addedSpace.isEnabled());
    }
    
    @Test
    public void test9() throws Exception {
        db.loadPayments(testDataDir + "/paymentData.csv");
        
        assertEquals(3, db.getAllPayments().size());
        
        // Verify credit card payment
        Payment payment1 = db.getAllPayments().get(0);
        assertEquals(1, payment1.getId());
        assertEquals(10.50, payment1.getTotal(), 0.001);
        assertFalse(payment1.getIsRefunded());
        assertTrue(payment1.getPaymentStrategy() instanceof CreditCardStrategy);
        
        // Verify PayPal payment
        Payment payment2 = db.getAllPayments().get(1);
        assertEquals(2, payment2.getId());
        assertTrue(payment2.getIsRefunded());
        assertTrue(payment2.getPaymentStrategy() instanceof PayPalStrategy);
        
        // Verify mobile payment
        Payment payment3 = db.getAllPayments().get(2);
        assertEquals(3, payment3.getId());
        assertTrue(payment3.getPaymentStrategy() instanceof MobilePaymentStrategy);
    }
    
    @Test
    public void test10() throws Exception {
        // Load initial data
        db.loadPayments(testDataDir + "/paymentData.csv");
        
        // Modify a payment
        Payment payment = db.getAllPayments().get(0);
        payment.setIsRefunded(true);
        payment.setTotal(12.50);
        
        // Add a new payment
        DebitCardStrategy debitCard = new DebitCardStrategy(9876543210987654L, "Jane Smith", "789", "06/26");
        Payment newPayment = new Payment(4, 8.25, false, debitCard);
        db.getAllPayments().add(newPayment);
        
        // Update the file
        db.updatePayments(testDataDir + "/paymentData_updated.csv");
        
        // Verify the update
        Database newDb = Database.getInstance();
        newDb.loadPayments(testDataDir + "/paymentData_updated.csv");
        
        assertEquals(8, newDb.getAllPayments().size());
        
        // Verify modified payment
        Payment updatedPayment = newDb.getAllPayments().get(0);
        assertTrue(updatedPayment.getIsRefunded());
        assertEquals(12.50, updatedPayment.getTotal(), 0.001);
        
        // Verify new payment
        Payment addedPayment = newDb.getAllPayments().get(3);
        assertEquals(4, addedPayment.getId());
        assertTrue(addedPayment.getPaymentStrategy() instanceof DebitCardStrategy);
    }
    
    @Test
    public void test11() throws Exception {
        // Load dependencies
        db.loadClients(testDataDir + "/clientData.csv");
        db.loadParkingLot(testDataDir + "/parkinglotData.csv");
        db.loadParkingSpaces(testDataDir + "/parkingSpaceData.csv");
        db.loadPayments(testDataDir + "/paymentData.csv");
        
        // Load bookings
        db.loadBookings(testDataDir + "/bookingData.csv");
        
        assertEquals(2, db.getAllBookings().size());
        
        // Verify first booking
        Booking booking1 = db.getAllBookings().get(0);
        assertEquals(1, booking1.getID());
        assertEquals("student@test.com", booking1.getClient().getEmail());
        assertEquals("ABC123", booking1.getLicensePlate());
        assertEquals(1, booking1.getParkingSpace().getId());
        assertEquals("L1", booking1.getParkingLot().getId());
        assertEquals(1, booking1.getPayment().getId());
        
        // Verify second booking
        Booking booking2 = db.getAllBookings().get(1);
        assertEquals(2, booking2.getID());
        assertEquals("faculty@test.com", booking2.getClient().getEmail());
        assertTrue(booking2.getPayment().getIsRefunded());
    }
    
    @Test
    public void test12() throws Exception {
        // Test the integrated load function
        Database.loadEverything();
        
        // Verify counts
        assertTrue(db.getAllClients().size() > 0);
        assertTrue(db.getAllManagers().size() > 0);
        assertTrue(db.getAllParkingLots().size() > 0);
        assertTrue(db.getAllParkingSpaces().size() > 0);
        assertTrue(db.getAllPayments().size() > 0);
        assertTrue(db.getAllBookings().size() > 0);
    }
    
    @Test
    public void test13() throws Exception {
        // First load some data
        Database.loadEverything();
        
        // Make some modifications
        if (!db.getAllClients().isEmpty()) {
            Client client = db.getAllClients().get(0);
            client.setPassword("updatedpass");
        }
        
        if (!db.getAllParkingLots().isEmpty()) {
            ParkingLot lot = db.getAllParkingLots().get(0);
            lot.setName("Updated Lot Name");
        }
        
        // Test the integrated update function
        Database.updateEverything();
        
        // This test mainly verifies no exceptions are thrown
        assertTrue(true);
    }
    
    @Test
    public void test14() {
        // Setup test parking lot and space
        ParkingLot lot = new ParkingLot("TEST", "Test Lot", new EnabledState(), new ParkingSpace[5], "Test Location");
        db.getAllParkingLots().add(lot);
        
        ParkingSpace space = new ParkingSpace();
        space.setId(1);
        space.setParkingLot(lot);
        space.setOccupied(false);
        db.getAllParkingSpaces().add(space);
        lot.getParkingSpaces()[1] = space;
        
        // Test observer update
        db.update(1, "TEST", true);
        
        // Verify the space was updated
        assertTrue(lot.getParkingSpaces()[1].isOccupied());
    }
}