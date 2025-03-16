package backend;

public class Visitor extends Client {
	public static final int RATE = 15;
	
	public Visitor(String email, String password) {
		super(email, password);
	}

	@Override
	public double calculateDepositClient() {
		return Visitor.RATE;
	}

	@Override
	public String getClientType() {
		return "visitor";
	}
}
