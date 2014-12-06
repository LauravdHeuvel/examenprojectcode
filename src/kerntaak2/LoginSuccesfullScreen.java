package kerntaak2;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginSuccesfullScreen extends JFrame implements ActionListener {
	public JLabel label;
	public JButton logoutButton;
	public JPanel p;

	public LoginSuccesfullScreen() {
		JPanel hulp = new JPanel();
		add(hulp);
		setLayout(new FlowLayout());
		p = new JPanel();
		add(p);
		setSize(200, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		label = new JLabel("Je bent succesvol ingelogd.");
		add(label);

		logoutButton = new JButton("Log uit.");
		add(logoutButton);
		logoutButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		setVisible(false);
		new LoginScreen();

		// Uitloggen om weer op de inlogpagina te komen

	}

}
