public class Manager {
    protected String name;
    protected String password;
    protected CommandInvoker invoker;

    public Manager(String name, String password) {
        this.name = name;
        this.password = password;
        this.invoker = new CommandInvoker();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean authenticate(String username, String password) {
        return this.name.equals(username) && this.password.equals(password);
    }
    
    public void executeCommand(ParkingCommand command) {
        invoker.executeCommand(command);
    }
}
