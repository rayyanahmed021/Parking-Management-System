
public class Visitor extends Client {
	public static final int RATE = 15;
	
	public Visitor(String email, String password) {
		super(email, password);
	}

	@Override
	public double calculateDepositClient() {
		// TODO Auto-generated method stub
		return Visitor.RATE;
	}
}
