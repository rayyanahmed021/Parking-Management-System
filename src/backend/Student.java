package backend;

public class Student extends Client{
	public static final int RATE = 5;
	private boolean accountApproved;
	
	public Student(String email, String password, boolean isApproved) {
		super(email, password);
		this.accountApproved = isApproved;
	}
	
	public boolean getAccountApproved() {
		return this.accountApproved;
	}
	public void setAccountApproved(boolean accountApproved) {
		this.accountApproved = accountApproved;
	}

	@Override
	public double calculateDepositClient() {
		return Student.RATE;
	}
	
	
}
