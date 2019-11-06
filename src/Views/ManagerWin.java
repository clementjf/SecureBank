package Views;

import Models.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

public class ManagerWin {
	private JFrame frame;
	private GridBagLayout gbLayout;
	private GridBagConstraints gbc;

	private JButton findInfo, viewTrans, viewTransByID, bt1, bt2;
	private JLabel headlineL1, headlineL2, lb, lb1, lb2, lb3, lb4;
	private JTextField customerTF, tf1, tf2, tf3, tf4;
	private JList<String> cList;
	private JList<String> sList;
	private JPanel p, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12;
	private JTabbedPane tabbedPane;

	private ArrayList<CustomerID> customers;
	private String welcomeInfo;

	private SharedListSelectionHandler listener1;
	private FindCustomerListener listener2;
	private FindTransactionListener listener3;

	private ATM atm;

	public ManagerWin(ATM atm, int i) {
		this.atm = atm;
		welcomeInfo = "Hello " + this.atm.getManagerID(i) + "!";
		customers = atm.getCustomerIDs();
		setManagerWin();
		initManagerWin();
	}

	public ManagerWin(ATM atm, ManagerID mId) {
		this.atm = atm;
		welcomeInfo = "Hello " + mId.getUserName() + "!";
		customers = atm.getCustomersByDB();
		setManagerWin();
		initManagerWin();
	}

	private void setManagerWin() {
		frame = new JFrame();
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		gbLayout = new GridBagLayout();
		gbc = new GridBagConstraints();

		listener1 = new SharedListSelectionHandler();
		listener2 = new FindCustomerListener();
		listener3 = new FindTransactionListener();

		headlineL1 = new JLabel(welcomeInfo);
		headlineL2 = new JLabel("All customers: ");

		findInfo = new JButton("Find ");
		viewTransByID = new JButton("View his transaction");
		viewTrans = new JButton("Today's transaction");

		customerTF = new JTextField(15);

		String[] customerStr = new String[customers.size()];

		for (int j = 0; j < customers.size(); j++) {
			CustomerID cId = customers.get(j);
			customerStr[j] = cId.getUserName() + "    " + cId.getName() + "    " + cId.getIndex();
		}

		cList = new JList<String>(customerStr);

		sList = new JList<String>();

		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		p8 = new JPanel();
		p7 = new JPanel();
		p9 = new JPanel();
		p10 = new JPanel();
		p11 = new JPanel();
		p12 = new JPanel();

		p = (JPanel) frame.getContentPane();
	}

	private void initManagerWin() {
		frame.setTitle("ATM - Manager");
		frame.setSize(500, 600);
		frame.setLocation(500, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

		p.add(tabbedPane);

		initPanel1();
		initPanel2();

		tabbedPane.addTab("Transaction & Info", p4);
		tabbedPane.addTab("Stocks", p5);

	}

	private void initPanel2() {

		bt1 = new JButton("Delete this Stock");
		bt2 = new JButton("Add this Stock");
		tf1 = new JTextField(25);
		tf2 = new JTextField(15);
		tf3 = new JTextField(15);
		tf4 = new JTextField(15);
		lb = new JLabel("Stocks: ");
		lb1 = new JLabel("Create a stock:  ");
		lb2 = new JLabel("Stock 's ID: ");
		lb3 = new JLabel("Company/Name: ");
		lb4 = new JLabel("Price/per: ");

		p5.setLayout(new GridLayout(10, 1));
		p5.add(p6);
		p5.add(lb);
		p5.add(p12);
		p5.add(p7);
		p5.add(lb1);
		p5.add(p8);
		p5.add(p9);
		p5.add(p10);
		p5.add(p11);
		p5.add(bt2);

		p7.add(tf1);
		p7.add(bt1);

		p9.add(lb2);
		p9.add(tf2);

		p10.add(lb3);
		p10.add(tf3);

		p11.add(lb4);
		p11.add(tf4);

	}

	private void initPanel1() {
		p4.add(headlineL1);
		p4.add(p1);
		p4.add(customerTF);
		p4.add(findInfo);
		p4.add(viewTransByID);
		p4.add(viewTrans);
		p4.add(p2);
		p4.add(headlineL2);
		p4.add(p3);
		p4.add(cList);

		p4.setLayout(gbLayout);

		cList.addListSelectionListener(listener1);
		findInfo.addActionListener(listener2);
		viewTrans.addActionListener(listener3);
		viewTransByID.addActionListener(listener3);

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
		gbLayout.setConstraints(p1, gbc);

		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbLayout.setConstraints(customerTF, gbc);

		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(findInfo, gbc);

		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(viewTransByID, gbc);

		gbc.gridwidth = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(viewTrans, gbc);

		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbLayout.setConstraints(p2, gbc);

		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbLayout.setConstraints(headlineL2, gbc);

		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbLayout.setConstraints(p3, gbc);

		gbc.gridwidth = 0;
		gbc.gridheight = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbLayout.setConstraints(cList, gbc);

	}

	class FindCustomerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean flag = false;
			for (int i = 0; i < customers.size(); i++) {
				CustomerID cId = customers.get(i);
				if (cId.getName().equals(customerTF.getText())) {
					flag = true;
					new CustomerWin(atm, atm.getCustomerByDB(cId.getIndex()), false, 0, 0);
				}
			}
			if (flag == false) {
				JOptionPane.showMessageDialog(null, "No such a customer!", "Message", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class FindTransactionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object object = e.getSource();
			if (object == viewTrans) {
				new ViewTransactionWin(atm, -1);
			} else if (object == viewTransByID) {
				int index = cList.getSelectedIndex();
				new ViewTransactionWin(atm, index + 1);
			}
		}
	}

	class SharedListSelectionHandler implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int j = cList.getSelectedIndex();
			customerTF.setText(customers.get(j).getName());
		}
	}

}
