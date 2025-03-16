package backend;
public class CommandInvoker {
    public void executeCommand(ParkingCommand command) {
        command.execute();
    }
}
