
public class Faculty extends Client{
	public static final int RATE = 8;
	private boolean accountApproved;
	
	public Faculty(String email, String password) {
		super(email, password);
	}
	
	public boolean getAccountApproved() {
		return this.accountApproved;
	}
	public void setAccountApproved(boolean accountApproved) {
		this.accountApproved = accountApproved;
	}

	@Override
	public double calculateDepositClient() {
		return Faculty.RATE;
	}
}
