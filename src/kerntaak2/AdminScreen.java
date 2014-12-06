package kerntaak2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminScreen extends JFrame implements ActionListener {

	public JLabel label;
	public JComboBox<String> box;
	public JButton logoutButton, editButton, logButton;
	public List<Seller> emailList;
	public List<Login> loginList;
	public Connection c;
	private JPanel p;

	public AdminScreen() {
		JPanel hulp = new JPanel();
		add(hulp);
		setLayout(new FlowLayout());
		p = new JPanel();
		add(p);
		setSize(300, 300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		label = new JLabel("Selecteer een geblokkeerd email adres.");
		add(label);

		SellerDAO SD = new SellerDAO();

		emailList = SD.findAllBlockedSellers();
		// Combobox aanmaken en de lijst van de geblokkeerde sellers er in
		// zetten
		box = new JComboBox();
		for (Seller seller : emailList) {
			box.addItem(seller.getEmailAddress());
		}
		add(box);
		logoutButton = new JButton("Log uit.");
		add(logoutButton);
		logoutButton.addActionListener(this);
		editButton = new JButton("Deblokkeer");
		add(editButton);
		editButton.addActionListener(this);
		logButton = new JButton("Show logfile");
		add(logButton);
		logButton.addActionListener(this);

	}

	public void writeLog() throws IOException {
		FileWriter writer = new FileWriter("verkoper.log");
		LoginDAO LD = new LoginDAO();
		loginList = LD.allLogins();
		for (Login login : loginList) {
			String listItem = login.toString();
			writer.write(listItem + "\n");
		}
		writer.close();
		// Filewriter aanmaken, alle logingegevens in een list zetten en die
		// list per regel wegschrijven
	}

	public void updateBlockedSeller(String sellerEmail) {
		SellerDAO SD = new SellerDAO();
		Seller blockedSeller = SD.findSeller(sellerEmail);
		blockedSeller.setBlocked(0);
		SD.updateBlocked(blockedSeller);
		// setBlocked van de seller naar 0 zetten en updaten in de database
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == editButton) {
			String emailAddress = String.valueOf(box.getSelectedItem());
			updateBlockedSeller(emailAddress);
			this.repaint();
		} else if (evt.getSource() == logoutButton) {
			setVisible(false);
			new LoginScreen();
		} else if (evt.getSource() == logButton) {

			try {
				writeLog();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
