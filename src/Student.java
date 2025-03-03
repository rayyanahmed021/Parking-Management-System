
public class Student extends Client{
	public static final int RATE = 5;
	private boolean accountApproved;
	
	public Student(String email, String password) {
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
		return Student.RATE;
	}
	
	
}
