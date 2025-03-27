package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;

public class GenerateClientFactoryTest {

    private GenerateClientFactory clientFactory;

    @Before
    public void setUp() throws Exception {
        clientFactory = new GenerateClientFactory();
    }

    @Test
    public void test_1() {
        Client student = clientFactory.getClientInstance("student", "student@example.com", "password123");
        assertNotNull("Student client should not be null", student);
        assertTrue("Client should be of type Student", student instanceof Student);
    }

    @Test
    public void test_2() {
        Client faculty = clientFactory.getClientInstance("faculty", "faculty@example.com", "password123");
        assertNotNull("Faculty client should not be null", faculty);
        assertTrue("Client should be of type Faculty", faculty instanceof Faculty);
    }

    @Test
    public void test_3() {
        Client nonFaculty = clientFactory.getClientInstance("nonfaculty", "nonfaculty@example.com", "password123");
        assertNotNull("NonFaculty client should not be null", nonFaculty);
        assertTrue("Client should be of type NonFaculty", nonFaculty instanceof NonFaculty);
    }

    @Test
    public void test_4() {
        Client visitor = clientFactory.getClientInstance("visitor", "visitor@example.com", "password123");
        assertNotNull("Visitor client should not be null", visitor);
        assertTrue("Client should be of type Visitor", visitor instanceof Visitor);
    }

    @Test
    public void test_5() {
        Client invalidClient = clientFactory.getClientInstance("invalid", "invalid@example.com", "password123");
        assertNull("Client should be null for an invalid client type", invalidClient);
    }

    @Test
    public void test_6() {
        Client student = clientFactory.getClientInstance("student", "student@example.com", "password123");
        assertEquals("student@example.com", student.getEmail());
        assertEquals("password123", student.getPassword());
    }

    @Test
    public void test_7() {
        Client faculty = clientFactory.getClientInstance("faculty", "faculty@example.com", "password123");
        assertEquals("faculty@example.com", faculty.getEmail());
        assertEquals("password123", faculty.getPassword());
    }

    @Test
    public void test_8() {
        Client nonFaculty = clientFactory.getClientInstance("nonfaculty", "nonfaculty@example.com", "password123");
        assertEquals("nonfaculty@example.com", nonFaculty.getEmail());
        assertEquals("password123", nonFaculty.getPassword());
    }

    @Test
    public void test_9() {
        Client visitor = clientFactory.getClientInstance("visitor", "visitor@example.com", "password123");
        assertEquals("visitor@example.com", visitor.getEmail());
        assertEquals("password123", visitor.getPassword());
    }

    @Test
    public void test_10() {
        Client student = clientFactory.getClientInstance("student", "student@example.com", "password123");
        Client faculty = clientFactory.getClientInstance("faculty", "faculty@example.com", "password123");
        Client nonFaculty = clientFactory.getClientInstance("nonfaculty", "nonfaculty@example.com", "password123");
        Client visitor = clientFactory.getClientInstance("visitor", "visitor@example.com", "password123");

        assertTrue("Client should be of type Student", student instanceof Student);
        assertTrue("Client should be of type Faculty", faculty instanceof Faculty);
        assertTrue("Client should be of type NonFaculty", nonFaculty instanceof NonFaculty);
        assertTrue("Client should be of type Visitor", visitor instanceof Visitor);
    }
}
