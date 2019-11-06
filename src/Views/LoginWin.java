package Views;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

import Models.*;

public class LoginWin {
	private JFrame frame;
	private JPanel p1, p2, p3, p4, p5;
	private JButton resetB, loginB, openB, closeB;
	private JLabel welcomeL1, welcomeL2, pwdL, usernameL;
	private JTextField userNameTF;
	private JPasswordField pwdTF;
	private LoginListener lListener;
	private JRadioButton rb1;
	private JRadioButton rb2;
	private ButtonGroup group;

	private ATM atm;

	public LoginWin(ATM atm) {
		this.atm = atm;
		setLoginWin();
		initLoginWin();
	}

	private void setLoginWin() {

		frame = new JFrame();
		lListener = new LoginListener();
		group = new ButtonGroup();
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();

		usernameL = new JLabel("Username:");
		pwdL = new JLabel("Password:");
		welcomeL1 = new JLabel("Welcome to use atm!");
		welcomeL2 = new JLabel("You can get access to SC Bank by logining in!");

		resetB = new JButton("Reset");
		loginB = new JButton("Log in");
		openB = new JButton("Open an Account");
		closeB = new JButton("Close this Account");

		rb1 = new JRadioButton("Customer");
		rb2 = new JRadioButton("Manager");

		userNameTF = new JTextField(16);
		pwdTF = new JPasswordField(16);
	}

	private void initLoginWin() {
		resetB.addActionListener(lListener);
		loginB.addActionListener(lListener);
		closeB.addActionListener(lListener);
		openB.addActionListener(lListener);

		frame.setLayout(new GridLayout(5, 1));
		frame.add(p1);
		frame.add(p2);
		frame.add(p3);
		frame.add(p4);
		frame.add(p5);

		p1.add(welcomeL1);
		p1.add(welcomeL2);
		p1.setBounds(20, 20, 20, 20);

		p2.add(usernameL);
		p2.add(userNameTF);

		p3.add(pwdL);
		p3.add(pwdTF);

		group.add(rb1);
		group.add(rb2);
		p4.add(rb1);
		p4.add(rb2);

		p5.add(resetB);
		p5.add(loginB);
		p5.add(openB);
		p5.add(closeB);

		frame.setTitle("atm");
		frame.setSize(500, 320);
		frame.setLocation(500, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			Object object = e.getSource();
			if (object == resetB) {
				userNameTF.setText("");
				pwdTF.setText("");
			} else if (object == openB) {
				new OpenAccWin(atm);
			} else {
				if (userNameTF.getText().trim().isEmpty() || pwdTF.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter your username and passward!", "Message",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (object == loginB) {

					if (rb1.isSelected()) {
						int index = atm.loginToBank(userNameTF.getText(), pwdTF.getText(), 2);
						if (index == 0) {
							JOptionPane.showMessageDialog(null, "Dear customer, username and password are not matched.",
									"Message", JOptionPane.ERROR_MESSAGE);
						} else {
							new CustomerWin(atm, atm.getCustomerByDB(index), true, 0, 0);
						}
					} else if (rb2.isSelected()) {
						int index = atm.loginToBank(userNameTF.getText(), pwdTF.getText(), 1);
						if (index == 0) {
							JOptionPane.showMessageDialog(null, "Dear manager, username and password are not matched.",
									"Message", JOptionPane.ERROR_MESSAGE);
						} else {
							new ManagerWin(atm, atm.getManagerByDB(index));
						}
					}
				} else if (object == closeB) {
					if (rb1.isSelected()) {
						int index = atm.loginToBank(userNameTF.getText(), pwdTF.getText(), 2);
						if (index == 0) {
							JOptionPane.showMessageDialog(null, "Dear customer, username and password are not matched.",
									"Message", JOptionPane.ERROR_MESSAGE);
						} else {
							atm.closeAccount(index);
							JOptionPane.showMessageDialog(null, "All your information are removed. Here is your money.",
									"Message", JOptionPane.INFORMATION_MESSAGE);
						}
					} else if (rb2.isSelected()) {
						JOptionPane.showMessageDialog(null, "You CAN NOT close or stop a manager account.", "Message",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		}
	}
}
