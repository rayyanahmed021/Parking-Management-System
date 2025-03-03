
public class NonFaculty extends Client{
	public static final int RATE = 10;
	private boolean accountApproved;
	
	public NonFaculty(String email, String password) {
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
		return NonFaculty.RATE;
	}
}
