package Views;

import Models.*;

import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

public class LoansWin {
	private JFrame frame;
	private JPanel p1, p2, p3, p4, p5, p6;
	private JButton confirmB, cancelB;
	private JLabel l1, l2, l3;
	private JTextField amountTF;
	private JRadioButton rb1, rb2;
	private ButtonGroup group1;
	private ButtonListener bl;
	private JComboBox<String> cb;
	private CustomerID cId;
	private Account acc;
	private int inOrOut;
	private ATM atm;
	private int curAccIndex;

	public LoansWin(ATM atm, int inOrOut, CustomerID cId, Account acc, int curAccIndex) {
		this.acc = acc;
		this.cId = cId;
		this.atm = atm;
		this.curAccIndex = curAccIndex;
		setLoansWin();
		initLoansWin(inOrOut);

	}

	private void setLoansWin() {
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

		l2 = new JLabel("Transaction Type: ");
		l3 = new JLabel("Input amount:");
		l1 = new JLabel();
		l1.setText("Your account number: " + acc.getAccountNumber());

		amountTF = new JTextField(10);

		rb1 = new JRadioButton("Make loans");
		rb2 = new JRadioButton("Return loans");

		group1 = new ButtonGroup();

		bl = new ButtonListener();

	}

	private void initLoansWin(int inOrOut) {
		frame.setLayout(new GridLayout(5, 1));
		frame.add(p6);
		frame.add(p5);
		frame.add(p1);
		frame.add(p3);
		frame.add(p4);
		confirmB.addActionListener(bl);
		cancelB.addActionListener(bl);

		p5.add(l1);

		p1.add(l2);
		p1.add(rb1);
		p1.add(rb2);

		p3.add(l3);
		p3.add(amountTF);
		p3.add(cb);

		p4.add(cancelB);
		p4.add(confirmB);

		group1.add(rb1);
		group1.add(rb2);

		frame.setTitle("Transaction");
		frame.setSize(500, 400);
		frame.setLocation(500, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

		switch (inOrOut) {
		case 1:
			rb1.setSelected(true);
			break;
		case 2:
			rb2.setSelected(true);
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

					if (rb2.isSelected()) {// TODO return loans
						if (ifAvailable(cId, amount, type) == false) {
							JOptionPane.showMessageDialog(null, "You dont have enough money!", "Message",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						inOrOut = 2;
						JOptionPane.showMessageDialog(null, "The loans has been returned.", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					} else if (rb1.isSelected()) { // TODO make loans
						inOrOut = 1;
						JOptionPane.showMessageDialog(null,
								"Now the money is available. Remember to return as soom as possible...", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					}
					atm.loans(cId, acc, inOrOut, amount, type);
					// atm.updateCustomerInfo(cId);
					atm.updateCustomerAccDB(cId);
				}
			}
			new CustomerWin(atm, atm.getCustomerByDB(cId.getIndex()), true, acc.getType(), curAccIndex);
			frame.dispose();
		}

	}

	public boolean ifAvailable(CustomerID cId, double amount, int type) {
		if (cId.getAccounts().get(0).getBalances().get(type).getMoney() < amount) {
			return false;
		} else {
			return true;
		}
	}
}
