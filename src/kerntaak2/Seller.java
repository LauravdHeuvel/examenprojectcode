package kerntaak2;

public class Seller {

	private String firstName;
	private String lastName;
	private String emailAddress;
	private int blocked;

	public Seller() {

	}

	public Seller(String firstName, String lastName, String emailAddress,
			int blocked) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.blocked = blocked;
	}

	public int getBlocked() {
		return blocked;
	}

	public void setBlocked(int blocked) {
		this.blocked = blocked;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Seller) {
			Seller seller = (Seller) object;
			if (seller.emailAddress == this.emailAddress) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "";
	}

}
