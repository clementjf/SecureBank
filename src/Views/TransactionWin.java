package Views;

import Models.*;

import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

public class TransactionWin {
	private JFrame frame;
	private JPanel p1, p2, p3, p4, p5, p6;
	private JButton confirmB, cancelB;
	private JLabel l1, l2, l3, l4;
	private JTextField amountTF;
	private JRadioButton rb1, rb2, rb3, rb4;
	private ButtonGroup group1, group2;
	private ButtonListener bl;
	private JComboBox<String> cb;
	private CustomerID cId;
	private Account acc;
	private int inOrOut;
	private ATM atm;
	private int curAccIndex;

	public TransactionWin(ATM atm, int inOrOut, CustomerID cId, Account acc, int curAccIndex) {
		this.acc = acc;
		this.cId = cId;
		this.atm = atm;
		this.curAccIndex = curAccIndex;
		setTransactionWin();
		initTransactionWin(acc.getType(), inOrOut);

	}

	private void setTransactionWin() {
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

		l1 = new JLabel("Account Type:");
		l2 = new JLabel("Transaction Type: ");
		l3 = new JLabel("Input amount:");
		l4 = new JLabel();
		l4.setText("Your account number: " + acc.getAccountNumber());

		amountTF = new JTextField(10);

		rb1 = new JRadioButton("Checking Account");
		rb2 = new JRadioButton("Saving Account");
		rb3 = new JRadioButton("Withdrawal");
		rb4 = new JRadioButton("Deposit");

		group1 = new ButtonGroup();
		group2 = new ButtonGroup();

		bl = new ButtonListener();

	}

	private void initTransactionWin(int accountType, int inOrOut) {
		frame.setLayout(new GridLayout(5, 1));
		frame.add(p6);
		frame.add(p5);
		// frame.add(p1);
		frame.add(p2);
		frame.add(p3);
		frame.add(p4);

		confirmB.addActionListener(bl);
		cancelB.addActionListener(bl);

		p5.add(l4);

		p1.add(l1);
		p1.add(rb1);
		p1.add(rb2);

		p2.add(l2);
		p2.add(rb3);
		p2.add(rb4);

		p3.add(l3);
		p3.add(amountTF);
		p3.add(cb);

		p4.add(cancelB);
		p4.add(confirmB);

		group1.add(rb1);
		group1.add(rb2);
		group2.add(rb3);
		group2.add(rb4);

		frame.setTitle("Transaction");
		frame.setSize(500, 400);
		frame.setLocation(500, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		switch (accountType) {
		case Bank.CHECKING_ACCOUNT:
			rb1.setSelected(true);
			break;
		case Bank.SAVING_ACCOUNT:
			rb2.setSelected(true);
			break;
		default:
			break;
		}

		switch (inOrOut) {
		case 1:
			rb4.setSelected(true);
			break;
		case 2:
			rb3.setSelected(true);
			break;
		default:
			break;
		}
	}

	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int type = cb.getSelectedIndex() + 1;
			if (e.getSource() == confirmB) {
				if (amountTF.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please input a value!", "Message", JOptionPane.ERROR_MESSAGE);
				} else {
					double amount;
					try {
						amount = Double.parseDouble(amountTF.getText());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Please only input number!", "Message",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					if (rb3.isSelected()) {
						if (ifAvailable(acc, amount, type) == false) {
							JOptionPane.showMessageDialog(null, "You dont have enough money!", "Message",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						inOrOut = 2;
						JOptionPane.showMessageDialog(null, "Now get the money.", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					} else if (rb4.isSelected()) {
						inOrOut = 1;
						JOptionPane.showMessageDialog(null, "Now put the money in the ATM...", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					}
					atm.transaction(cId, acc, inOrOut, amount, type);
					atm.updateCustomerAccDB(cId);
				}
			}
			new CustomerWin(atm, atm.getCustomerByDB(cId.getIndex()), true, acc.getType(), curAccIndex);
			frame.dispose();
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
