package kerntaak2;

import java.sql.Date;
import java.sql.Time;

public class Login {

	private Date date;

	private String firstName;
	private String lastName;
	private String emailAddress;
	private int loginNumber;

	public Login() {

	}

	public Login(Date date, int loginNumber, String firstName, String lastName,
			String emailAddress) {
		this.date = date;

		this.loginNumber = loginNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getLoginNumber() {
		return ++loginNumber;
	}

	public void setLoginNumber(int loginNumber) {
		this.loginNumber = loginNumber;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName + " " + emailAddress + " " + date;
	}

}
