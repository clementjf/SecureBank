package Views;
import Models.*;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class OpenAccWin {
	private JFrame frame;
	private JPanel p1, p2, p3, p4, p5, p6, p7, p8, p9;
	private JButton cancelB, confirmB;
	private JLabel l1, l2, l3, l4, l5, l6, l7;
	private JTextField tf1, tf2, tf3, tf4, tf5, tf6;
	private ButtonListener l;

	private ATM atm;

	public OpenAccWin(ATM atm) {
		this.atm = atm;
		setOpenAccWin();
		initOpenAccWin();
	}

	private void initOpenAccWin() {
		frame.setLayout(new GridLayout(9, 1));
		frame.add(p1);
		frame.add(p2);
		frame.add(p3);
		frame.add(p4);
		frame.add(p5);
		frame.add(p6);
		frame.add(p7);
		frame.add(p8);
		frame.add(p9);

		p2.add(l7);
		p3.add(l1);
		p3.add(tf1);
		p4.add(l2);
		p4.add(tf2);
		p5.add(l3);
		p5.add(tf3);
		p6.add(l4);
		p6.add(tf4);
		p7.add(l5);
		p7.add(tf5);
		p8.add(l6);
		p8.add(tf6);
		p9.add(cancelB);
		p9.add(confirmB);

		cancelB.addActionListener(l);
		confirmB.addActionListener(l);
	}

	private void setOpenAccWin() {
		l = new ButtonListener();
		tf1 = new JTextField(15);
		tf2 = new JTextField(15);
		tf3 = new JTextField(15);
		tf4 = new JTextField(15);
		tf5 = new JTextField(15);
		tf6 = new JTextField(15);

		l1 = new JLabel("Username*: ");
		l2 = new JLabel("Password*: ");
		l3 = new JLabel("Name*: ");
		l4 = new JLabel("Address: ");
		l5 = new JLabel("Phone: ");
		l6 = new JLabel("Collateral: ");
		l7 = new JLabel("Open a new account");

		cancelB = new JButton("Cancel");
		confirmB = new JButton("Confirm");

		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		p7 = new JPanel();
		p8 = new JPanel();
		p9 = new JPanel();

		frame = new JFrame();

		frame.setTitle("Open an account");
		frame.setSize(500, 400);
		frame.setLocation(500, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == cancelB) {

			} else if (e.getSource() == confirmB) {
				String name = "";
				String userName = "";
				String pwd = "";
				String address = "";
				String phone = "";
				String collateral = "";
				if (tf1.getText().trim().isEmpty() || tf2.getText().trim().isEmpty()
						|| tf3.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please input all information!", "Message",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				name = tf3.getText();
				userName = tf1.getText();
				pwd = tf2.getText();
				if (tf4.getText().trim().isEmpty() == false) {
					address = tf4.getText();
				}
				if (tf5.getText().trim().isEmpty() == false) {
					phone = tf5.getText();
				}
				if (tf6.getText().trim().isEmpty() == false) {
					collateral = tf6.getText();
				}
				atm.createCustomerID(name, userName, pwd, address, phone, collateral);
				JOptionPane.showMessageDialog(null, "Your account has been created.", "Message",
						JOptionPane.INFORMATION_MESSAGE);
			}
			frame.dispose();
		}
	}
}
