package Views;
import Models.*;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class ViewTransactionWin {
	private JFrame frame;
	private GridBagLayout gbLayout;
	private GridBagConstraints gbc;

	private JLabel headlineL1;
	private JList<String> list;
	private JPanel p;

	private ArrayList<Transaction> transactions;

	private ATM atm;

	public ViewTransactionWin(ATM atm, int index) {
		this.atm = atm;
		transactions = atm.getTransactions(index);
		setViewTransactionWin();
		initViewTransactionWin();
	}

	private void initViewTransactionWin() {
		frame.setLayout(gbLayout);
		frame.setTitle("ATM - Transaction");
		frame.setSize(500, 500);
		frame.setLocation(500, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

		frame.add(headlineL1);
		frame.add(p);
		frame.add(list);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(headlineL1, gbc);

		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbLayout.setConstraints(p, gbc);

		gbc.gridwidth = 0;
		gbc.gridheight = 4;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbLayout.setConstraints(list, gbc);

	}

	private void setViewTransactionWin() {
		frame = new JFrame();
		gbLayout = new GridBagLayout();
		gbc = new GridBagConstraints();

		headlineL1 = new JLabel(atm.getDate() + " transactions:");

		String[] traStr = new String[transactions.size()];

		for (int j = 0; j < transactions.size(); j++) {
			traStr[j] = transactions.get(j).toString();
		}

		list = new JList<String>(traStr);

		p = new JPanel();
	}

}
