package Views;

import Models.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TransferWin {
	private JFrame frame;
	private JPanel p1, p2, p3, p4, p5, p6;
	private JButton confirmB, cancelB;
	private JLabel l1, l2, l3, l4;
	private JTextField accTF, amountTF;
	private ButtonListener bl;
	private ComboBoxListener cl;
	private JComboBox<String> cb;
	private CustomerID cId;
	private Account acc;
	private int balType;
	private ATM atm;
	private int curAccIndex;

	public TransferWin() {
		setTransferWin();
		initTransferWin();
	}

	public TransferWin(ATM atm, CustomerID cId, Account acc, int curAccIndex) {
		this.atm = atm;
		this.acc = acc;
		this.cId = cId;
		this.curAccIndex = curAccIndex;
		balType = Bank.USD;
		setTransferWin();
		initTransferWin();
	}

	private void setTransferWin() {
		frame = new JFrame();
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();

		cb = new JComboBox<String>(Bank.CURRENCY_LIST);

		confirmB = new JButton("Confirm");
		cancelB = new JButton("Cancel");

		l1 = new JLabel("Transfer Information");
		l2 = new JLabel("Your Account number: " + acc.getAccountNumber());
		l3 = new JLabel("Recipient 's Account Number: ");
		l4 = new JLabel("Amount: ");

		amountTF = new JTextField(10);
		accTF = new JTextField(15);

		bl = new ButtonListener();
		cl = new ComboBoxListener();
	}

	private void initTransferWin() {
		frame.setLayout(new GridLayout(6, 1));
		frame.add(p1);
		frame.add(p2);
		frame.add(p3);
		frame.add(p4);
		frame.add(p5);
		frame.add(p6);

		confirmB.addActionListener(bl);
		cancelB.addActionListener(bl);
		cb.addItemListener(cl);

		p2.add(l1);

		p3.add(l2);
		p4.add(l3);
		p4.add(accTF);

		p5.add(l4);
		p5.add(amountTF);
		p5.add(cb);

		p6.add(cancelB);
		p6.add(confirmB);

		frame.setTitle("Tranfer");
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
				if (amountTF.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a value!", "Message", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (accTF.getText().trim().isEmpty() || atm.ifAccountDB(accTF.getText()) == false) {
					JOptionPane.showMessageDialog(null, "Please enter a vaild recipient!", "Message",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				double amount;
				try {
					amount = Double.parseDouble(amountTF.getText());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Please only input number!", "Message",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (ifAvailable(acc, amount, balType) == false) {
					JOptionPane.showMessageDialog(null, "You dont have enough money!", "Message",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				atm.transfer(cId, acc, accTF.getText(), amount, balType);
				JOptionPane.showMessageDialog(null, "The money has been delivered.", "Message",
						JOptionPane.INFORMATION_MESSAGE);
			}
			new CustomerWin(atm, atm.getCustomerByDB(cId.getIndex()), true, acc.getType(), curAccIndex);
			frame.dispose();
		}
	}

	class ComboBoxListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				balType = cb.getSelectedIndex() + 1;
			}
		}
	}

	public boolean ifAvailable(Account acc, double amount, int type) {
		if (acc.getBalances().get(type).getMoney() < amount) {
			return false;
		} else {
			return true;
		}
	}
}