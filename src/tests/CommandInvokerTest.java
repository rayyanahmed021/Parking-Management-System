package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import backend.*;

public class CommandInvokerTest {

    private CommandInvoker commandInvoker;
    
    @Before
    public void test1() {
        commandInvoker = new CommandInvoker();
    }

    @Test
    public void test2() {
        ParkingCommand command = new ParkingCommand() {
            @Override
            public void execute() {
            	
            }
        };

        commandInvoker.executeCommand(command);
    }

    @Test
    public void test3() {
        boolean[] executed = {false, false};

        ParkingCommand command1 = new ParkingCommand() {
            @Override
            public void execute() {
                executed[0] = true;
            }
        };

        ParkingCommand command2 = new ParkingCommand() {
            @Override
            public void execute() {
                executed[1] = true;
            }
        };

        commandInvoker.executeCommand(command1);
        commandInvoker.executeCommand(command2);

        assertTrue(executed[0]);
        assertTrue(executed[1]);
    }

    @Test
    public void test4() {
        ParkingCommand noArgCommand = new ParkingCommand() {
            @Override
            public void execute() {
                System.out.println("Executing command with no arguments");
            }
        };

        commandInvoker.executeCommand(noArgCommand);
    }

    @Test
    public void test5() {
        ParkingLot parkingLot = new ParkingLot("PL123", "Test Lot", new EnabledState(), new ParkingSpace[10], "123 Test St.");
        ParkingSpace parkingSpace = new ParkingSpace(1, parkingLot, true);
        ParkingCommand bookSpotCommand = new ParkingCommand() {
            @Override
            public void execute() {
                parkingSpace.setOccupied(true);
            }
        };

        commandInvoker.executeCommand(bookSpotCommand);
        assertTrue(parkingSpace.isOccupied());
    }

    @Test
    public void test6() {
        PaymentStrategy paymentStrategy = new CreditCardStrategy(411111111, "John Doe", "12/25", "123");
        Payment payment = new Payment(50.0, false, paymentStrategy);
        ParkingCommand paymentCommand = new ParkingCommand() {
            @Override
            public void execute() {
                payment.payAmount(50.0);
            }
        };

        commandInvoker.executeCommand(paymentCommand);
        assertEquals(50.0, payment.getTotal(), 0.01);
    }

    @Test
    public void test7() {
        ParkingCommand invalidCommand = new ParkingCommand() {
            @Override
            public void execute() {
                throw new RuntimeException("Command failed");
            }
        };

        try {
            commandInvoker.executeCommand(invalidCommand);
            fail("Expected exception was not thrown");
        } catch (RuntimeException e) {
            assertEquals("Command failed", e.getMessage());
        }
    }

    @Test
    public void test8() {
        boolean[] executed = {false, false};

        ParkingCommand command1 = new ParkingCommand() {
            @Override
            public void execute() {
                executed[0] = true;
            }
        };

        ParkingCommand command2 = new ParkingCommand() {
            @Override
            public void execute() {
                executed[1] = true;
            }
        };

        commandInvoker.executeCommand(command1);
        commandInvoker.executeCommand(command2);

        assertTrue(executed[0]);
        assertTrue(executed[1]);
    }

    @Test
    public void test9() {
        StringBuilder order = new StringBuilder();

        ParkingCommand command1 = new ParkingCommand() {
            @Override
            public void execute() {
                order.append("Command 1 executed; ");
            }
        };

        ParkingCommand command2 = new ParkingCommand() {
            @Override
            public void execute() {
                order.append("Command 2 executed;");
            }
        };

        commandInvoker.executeCommand(command1);
        commandInvoker.executeCommand(command2);

        assertEquals("Command 1 executed; Command 2 executed;", order.toString());
    }

    @Test
    public void test10() {
        ParkingLot parkingLot = new ParkingLot("PL123", "Test Lot", new EnabledState(), new ParkingSpace[10], "123 Test St.");
        ParkingSpace parkingSpace = new ParkingSpace(1, parkingLot, false); // Initially unoccupied
        ParkingCommand parkCommand = new ParkingCommand() {
            @Override
            public void execute() {
                parkingSpace.setOccupied(true);
            }
        };

        commandInvoker.executeCommand(parkCommand);
        assertTrue(parkingSpace.isOccupied());
    }

    @Test
    public void test11() {
        ParkingLot parkingLot = new ParkingLot("PL123", "Test Lot", new EnabledState(), new ParkingSpace[10], "123 Test St.");
        ParkingSpace parkingSpace = new ParkingSpace(1, parkingLot, true);
        ParkingCommand occupySpotCommand = new ParkingCommand() {
            @Override
            public void execute() {
                parkingSpace.setOccupied(true);
            }
        };

        commandInvoker.executeCommand(occupySpotCommand);
        assertTrue(parkingSpace.isOccupied());
    }
}
