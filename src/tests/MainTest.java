package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    AddParkingLotCommandTest.class,
    BookingTest.class,
    CarTest.class,
    ClientTest.class,
    CommandInvokerTest.class,
    CreditCardStrategyTest.class,
    DatabaseTest.class,
    DebitCardStrategyTest.class,
    DisabledStateTest.class,
    EnabledStateTest.class,
    FacultyTest.class,
    GenerateClientFactoryTest.class,
    ManagerTest.class,
    MobilePaymentStrategyTest.class,
    NonFacultyTest.class,
    ParkingLotTest.class,
    ParkingSensorTest.class,
    ParkingSpaceTest.class,
    PaymentTest.class,
    PayPalStrategyTest.class,
    StudentTest.class,
    SuperManagerTest.class,
    UpdateParkingLotCommandTest.class,
    UpdateParkingSpaceCommandTest.class,
    ValidateClientRegistrationCommandTest.class,
    VisitorTest.class
})
public class MainTest {
    
}