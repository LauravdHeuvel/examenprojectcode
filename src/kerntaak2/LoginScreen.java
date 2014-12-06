package kerntaak2;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginScreen extends JFrame implements ActionListener {

	private JButton loginButton;
	private JLabel emailLabel, passwordLabel, errorLabel;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JPanel p;
	private int teller = 0;

	public LoginScreen() {
		JPanel hulp = new JPanel();
		add(hulp);
		setLayout(new FlowLayout());
		p = new JPanel();
		add(p);
		setSize(200, 200);

		emailLabel = new JLabel("Email:");
		add(emailLabel);
		emailField = new JTextField(10);
		add(emailField);
		passwordLabel = new JLabel("Password:");
		add(passwordLabel);
		passwordField = new JPasswordField(10);
		add(passwordField);
		loginButton = new JButton("Login");
		add(loginButton);
		loginButton.addActionListener(this);
		errorLabel = new JLabel("");
		add(errorLabel);

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public void updateBlockedSeller(String sellerEmail) {
		SellerDAO SD = new SellerDAO();
		Seller blockedSeller = SD.findSeller(sellerEmail);
		blockedSeller.setBlocked(1);
		SD.updateBlocked(blockedSeller);
		// seller blokkeren en updaten in de database
	}

	@SuppressWarnings("deprecation")
	public void fillLoginValues(Seller seller) {
		LoginDAO LD = new LoginDAO();
		Login loginData = new Login();
		// Date aa = Calendar.getInstance().getTime();
		java.util.Date date = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		loginData.setDate(sqlDate);
		loginData.setFirstName(seller.getFirstName());
		loginData.setLastName(seller.getLastName());
		loginData.setEmailAddress(seller.getEmailAddress());

		// LoginData vullen met de gegevens van de seller die in wilt loggen en
		// de tijd/datum en deze opslaan in de database.

		LD.addLogin(loginData);

	}

	public void login(String password) {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String email = emailField.getText();
		String Password = String.valueOf(passwordField.getPassword());

		PasswordDAO PD = new PasswordDAO();
		SellerDAO SD = new SellerDAO();
		PersonDAO PeD = new PersonDAO();

		Password loginDetails = PD.findPassword(email);
		String rightPassword = loginDetails.getPassword();

		Seller seller = SD.findSeller(email);
		Person person = PeD.findPerson(email);

		if (seller != null) {
			int blocked = seller.getBlocked();
			if (blocked == 1) {
				errorLabel.setText("Geblokkeerd email adres.");
				// Error geven als de seller geblokkeerd is
			} else {
				fillLoginValues(seller);
				if (rightPassword != null && Password.equals(rightPassword)) {

					setVisible(false);
					teller = 0;

					new LoginSuccesfullScreen();

				} else {
					errorLabel
							.setText("Verkeerde gebruikersnaam en/of wachtwoord.");
					teller++;
					if (teller >= 3) {

						updateBlockedSeller(seller.getEmailAddress());
						errorLabel.setText("Account is nu geblokkeerd.");
						teller = 0;

						// Seller blokkeren als wachtwoord 3x fout is
					}
				}

			}
		}

		else if (person != null) {
			int blocked = person.getBlocked();
			int adminornot = person.getAdmin();
			if (blocked == 1) {
				errorLabel.setText("Geblokkeerd email adres.");

			} else {
				if (adminornot == 0) {
					// Controleren of de person een admin is, zo niet doorsturen
					// naar de "gewone" inlogpagina
					if (rightPassword != null && Password.equals(rightPassword)) {

						setVisible(false);

						new LoginSuccesfullScreen();
						teller = 0;
					} else {
						errorLabel
								.setText("Verkeerde gebruikersnaam en/of wachtwoord.");
						teller++;
						if (teller >= 3) {
							person.setBlocked(1);
							PeD.updateBlocked(person);
							errorLabel.setText("Account is nu geblokkeerd.");
							teller = 0;

							// Account blokkeren bij 3 verkeerde pogingen
						}
					}
				}
				if (adminornot == 1) {

					if (rightPassword != null && Password.equals(rightPassword)) {

						// Doorsturen naar de adminpagina als de person een
						// admin is

						setVisible(false);

						new AdminScreen();
						teller = 0;
					}

					else {
						errorLabel
								.setText("Verkeerde gebruikersnaam en/of wachtwoord.");
						teller++;
						if (teller >= 3) {
							person.setBlocked(1);
							PeD.updateBlocked(person);
							errorLabel.setText("Account is nu geblokkeerd.");
							teller = 0;
							// Account blokkeren bij 3 verkeerde pogingen
						}
					}
				}

			}
		}

	}

}
