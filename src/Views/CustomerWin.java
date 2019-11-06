package Views;
import Models.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

public class CustomerWin {
	private String welcomeInfo;
	private CustomerID cId;

	private GridBagLayout gbLayout1;
	private GridBagConstraints gbc1;

	private ProfileButtonListener pbListener;
	private CheSavAccButtonListener casaListener;
	private TransferButtonListener tsfListener;
	private AccOnOffButtonListener onoffListener;
	private LoaButtonListener loaListener;
	private ComboBoxListener listener;
	
	private OpenSecurityButtonListener osbListener;

	private JFrame frame;
	private JTabbedPane tabbedPane;
	private JPanel p, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15;
	private JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14;
	private JTextField tf1, tf2, tf3, tf4, tf5, tf6;
	private JPasswordField pf1;
	private JButton bt1, bt2, bt3, bt4, bt5, bt6;
	private JButton bt7, bt8, bt9, bt10, bt11, bt12, bt13, bt14, bt15, bt16, bt17, bt18, bt19, bt20, bt21, bt22;
	private JButton bt23, bt24, bt25, bt26;
	private JList<String> bList, bList2, bList3;
	private JComboBox<String> cb1, cb2, cb3;

	private ArrayList<Account> cheAccounts;
	private ArrayList<Account> savAccounts;
	private ArrayList<Account> loaAccounts;
	private Account curAccount;

	private ATM atm;
	
	private int secureThreshold = 500;

	public CustomerWin(ATM atm, int index, boolean flag, int win) {
		this.atm = atm;
		cId = this.atm.getCustomerID(index);
		setCustomerWin(flag);
		initComboBoxes();
		initCustomerWin(win);
		switch (win) {
		case 0:
			curAccount = cheAccounts.get(0);
			break;
		case 1:
			curAccount = cheAccounts.get(0);
			break;
		case 2:
			if (cb2 != null) {
				curAccount = savAccounts.get(0);
			}
			break;
		case 3:
			if (cb3 != null) {
				curAccount = loaAccounts.get(0);
			}
			break;
		default:
			break;
		}
		setUserRight(flag);
	}

	public CustomerWin(ATM atm, CustomerID cId, boolean flag, int win, int curAccIndex) {
		this.atm = atm;
		this.cId = cId;
		setCustomerWin(flag);
		initComboBoxes();
		initCustomerWin(win);
		switch (win) {
		case 0:
			curAccount = cheAccounts.get(0);
			break;
		case 1:
			curAccount = cheAccounts.get(0);
			cb1.setSelectedIndex(curAccIndex);
			break;
		case 2:
			if (cb2 != null) {
				curAccount = savAccounts.get(0);
				cb2.setSelectedIndex(curAccIndex);
			}
			break;
		case 3:
			if (cb3 != null) {
				curAccount = loaAccounts.get(0);
				cb3.setSelectedIndex(curAccIndex);
			}
			break;
		default:
			break;
		}
		setUserRight(flag);
	}

	private void setCustomerWin(boolean flag) {
		if (flag == false) {
			welcomeInfo = "Information";
		} else {
			welcomeInfo = "Hello " + cId.toString() + "!";
		}

		frame = new JFrame();
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		pbListener = new ProfileButtonListener();
		casaListener = new CheSavAccButtonListener();
		onoffListener = new AccOnOffButtonListener();
		tsfListener = new TransferButtonListener();
		loaListener = new LoaButtonListener();
		listener = new ComboBoxListener();
		osbListener = new OpenSecurityButtonListener();

		p = (JPanel) frame.getContentPane();

		tabbedPane.addChangeListener(new ChangeListener() {

			@Override

			public void stateChanged(ChangeEvent e) {

				int accountType = tabbedPane.getSelectedIndex();

				switch (accountType) {
				case Bank.CHECKING_ACCOUNT:
					curAccount = cheAccounts.get(0);
					cb1.setSelectedIndex(0);
					break;
				case Bank.SAVING_ACCOUNT:
					if (cb2 != null) {
						curAccount = savAccounts.get(0);
						cb2.setSelectedIndex(0);
					}
					break;
				case Bank.LOANS_ACCOUNT:
					if (cb3 != null) {
						curAccount = loaAccounts.get(0);
						cb3.setSelectedIndex(0);
					}
					break;

				default:
					break;
				}

			}

		});

	}

	private void initComboBoxes() {

		cheAccounts = cId.getOneAccounts(Bank.CHECKING_ACCOUNT);

		int curCheAcc = cheAccounts.size() - 1;
		System.out.println("cheA:" + cheAccounts.size());
		if (curCheAcc != -1) {
			String[] cheAccStr = getAccountStr(cheAccounts);
			cb1 = new JComboBox<String>(cheAccStr);
			cb1.addActionListener(listener);
		}

		savAccounts = cId.getOneAccounts(Bank.SAVING_ACCOUNT);
		int curSavAcc = savAccounts.size() - 1;
		System.out.println("savA:" + savAccounts.size());
		if (curSavAcc != -1) {
			String[] savAccStr = getAccountStr(savAccounts);
			cb2 = new JComboBox<String>(savAccStr);
			cb2.addActionListener(listener);
		}

		loaAccounts = cId.getOneAccounts(Bank.LOANS_ACCOUNT);

		int curLoanAcc = loaAccounts.size() - 1;
		System.out.println("loaA:" + loaAccounts.size());
		if (curLoanAcc != -1) {
			String[] loaAccStr = getAccountStr(loaAccounts);
			cb3 = new JComboBox<String>(loaAccStr);
			cb3.addActionListener(listener);
		}

	}

	private String[] getAccountStr(ArrayList<Account> a) {
		String[] str = new String[a.size()];
		for (int i = 0; i < a.size(); i++) {
			String s = a.get(i).getAccountNumber();
			String info = "****" + s.substring(s.length() - 4, s.length());
			str[i] = info;
		}
		return str;
	}

	private void setUserRight(boolean flag) {
		if (flag == false) {
			bt1.setEnabled(false);
			bt2.setEnabled(false);
			bt3.setEnabled(false);
			bt4.setEnabled(false);
			bt5.setEnabled(false);
			bt6.setEnabled(false);
			bt21.setEnabled(false);
			bt22.setEnabled(false);

			bt17.setEnabled(false);
			bt18.setEnabled(false);
			bt10.setEnabled(false);
			bt19.setEnabled(false);
			bt11.setEnabled(false);
			bt20.setEnabled(false);

			p6.setVisible(false);
			p10.setVisible(false);
			p13.setVisible(false);
		}
	}

	private void initCustomerWin(int win) {
		frame.setTitle("ATM - Customer");
		frame.setSize(650, 300);
		frame.setLocation(500, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

		p.add(tabbedPane);

		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();

		setPanel1();
		setPanel2();
		setPanel3();
		setPanel4();

		initPanel1();
		initPanel2();
		initPanel3();
		initPanel4();

		tabbedPane.addTab("Profile", p1);
		tabbedPane.addTab("Checking Account", p2);
		tabbedPane.addTab("Saving Account", p3);
		tabbedPane.addTab("Loans Account", p4);
		tabbedPane.setSelectedIndex(win);
	}

	private void setPanel1() {
		p5 = new JPanel();
		l1 = new JLabel(welcomeInfo);
		l2 = new JLabel("Username: ");
		l3 = new JLabel("Name: ");
		l4 = new JLabel("Account Number: ");
		l5 = new JLabel("Phone: ");
		l6 = new JLabel("Address: ");
		l7 = new JLabel("Password: ");
		l14 = new JLabel("Collateral: ");

		gbLayout1 = new GridBagLayout();
		gbc1 = new GridBagConstraints();

		tf1 = new JTextField(15);
		tf2 = new JTextField(15);
		tf3 = new JTextField(15);
		tf4 = new JTextField(15);
		tf5 = new JTextField(15);
		pf1 = new JPasswordField(15);
		tf6 = new JTextField(15);

		bt1 = new JButton("Edit");
		bt2 = new JButton("Edit");
		bt3 = new JButton("Edit");
		bt4 = new JButton("Save");
		bt5 = new JButton("Save");
		bt6 = new JButton("Save");
		bt21 = new JButton("Edit");
		bt22 = new JButton("Save");

		tf1.setText(cId.getUserName());
		tf2.setText(cId.getName());
		tf3.setText(cId.getIndex() + "");
		tf4.setText(cId.getPhone());
		tf5.setText(cId.getAddress());
		pf1.setText(cId.getPassword());
		tf6.setText(cId.getCollateral());

		tf1.setEditable(false);
		tf2.setEditable(false);
		tf3.setEditable(false);
		tf4.setEditable(false);
		tf5.setEditable(false);
		pf1.setEditable(false);
		tf6.setEditable(false);

		bt1.addActionListener(pbListener);
		bt2.addActionListener(pbListener);
		bt3.addActionListener(pbListener);
		bt4.addActionListener(pbListener);
		bt5.addActionListener(pbListener);
		bt6.addActionListener(pbListener);
		bt21.addActionListener(pbListener);
		bt22.addActionListener(pbListener);
	}

	private void setPanel2() {
		p6 = new JPanel();
		p7 = new JPanel();
		p8 = new JPanel();

		bList = new JList<String>();

		l8 = new JLabel("Checking Account");
		l9 = new JLabel("Operations");

		bt7 = new JButton("Withdrawal");
		bt8 = new JButton("Deposit");
		bt9 = new JButton("Transfer");
		bt16 = new JButton("View Transactions");
		bt17 = new JButton("Create a Checking Account");
		bt18 = new JButton("Stop this Account");

		bt7.addActionListener(casaListener);
		bt8.addActionListener(casaListener);
		bt9.addActionListener(tsfListener);

		bt16.addActionListener(casaListener);
		bt17.addActionListener(onoffListener);
		bt18.addActionListener(onoffListener);
	}

	private void setPanel3() {
		p10 = new JPanel();
		p11 = new JPanel();
		p12 = new JPanel();

		bList2 = new JList<String>();

		l10 = new JLabel("Operations ");
		l11 = new JLabel("Saving Account ");

		bt10 = new JButton("Create a Saving Account");
		bt12 = new JButton("Withdrawal");
		bt13 = new JButton("Deposit");
		bt19 = new JButton("Stop this Account");
		
		
		bt23 = new JButton("Open Security Account");
		
		
		bt24 = new JButton("Get Interests");
//		bt23.setEnabled(false);
		
		bt23.addActionListener(osbListener);

		bt12.addActionListener(casaListener);
		bt13.addActionListener(casaListener);
		bt24.addActionListener(casaListener);
		bt10.addActionListener(onoffListener);
		bt19.addActionListener(onoffListener);
	}

	private void setPanel4() {
		p13 = new JPanel();
		p14 = new JPanel();
		p15 = new JPanel();

		bList3 = new JList<String>();

		l12 = new JLabel("Loans Account ");
		l13 = new JLabel("Operations  ");

		bt11 = new JButton("Create a Loans Account");
		bt20 = new JButton("Stop this Account");

		bt25 = new JButton("Notification");
		bt26 = new JButton("Interest: 0.1%");

		bt14 = new JButton("Loans");
		bt15 = new JButton("Return loan");
		bt26.setEnabled(false);

		bt15.addActionListener(loaListener);
		bt14.addActionListener(loaListener);
		bt25.addActionListener(loaListener);
		bt11.addActionListener(onoffListener);
		bt20.addActionListener(onoffListener);
	}

	private void initPanel4() {
		ArrayList<JPanel> ps = new ArrayList<JPanel>();
		ps.add(p4);
		ps.add(p13);
		ps.add(p14);
		ps.add(p15);

		ArrayList<JLabel> ls = new ArrayList<JLabel>();
		ls.add(l12);
		ls.add(l13);

		ArrayList<JButton> bs = new ArrayList<JButton>();
		bs.add(bt20);
		bs.add(bt11);
		bs.add(bt14);
		bs.add(bt15);
		bs.add(bt25);
		bs.add(bt26);

		ArrayList<GridBagLayout> gbs = new ArrayList<GridBagLayout>();
		gbs.add(new GridBagLayout());
		gbs.add(new GridBagLayout());
		ArrayList<GridBagConstraints> gbcs = new ArrayList<GridBagConstraints>();
		gbcs.add(new GridBagConstraints());
		gbcs.add(new GridBagConstraints());

		settingComponents(Bank.LOANS_ACCOUNT, cb3, bList3, ps, ls, bs, gbs, gbcs);
	}

	private void settingComponents(int accountType, JComboBox<String> cb, JList<String> bList, ArrayList<JPanel> ps,
			ArrayList<JLabel> ls, ArrayList<JButton> bs, ArrayList<GridBagLayout> gbs,
			ArrayList<GridBagConstraints> gbcs) {
		if (cb != null) {
			System.out.println(cb.getSelectedIndex());
			String[] balancesStr = getBalalceList(0, accountType);
			bList.setListData(balancesStr);
		}

		JPanel upperP = ps.get(0);
		JPanel lowerP = ps.get(1);

		int pIndex = 2;
		int lIndex = 0, bIndex = 0;

		GridBagLayout gb1 = gbs.get(0);
		GridBagLayout gb2 = gbs.get(1);

		GridBagConstraints gbc1 = gbcs.get(0);
		GridBagConstraints gbc2 = gbcs.get(1);

		upperP.setLayout(gb1);
		upperP.add(ls.get(lIndex++));
		if (cb != null) {
			upperP.add(cb);
			upperP.add(bs.get(bIndex++));
		} else {
			bIndex++;
		}
		upperP.add(bs.get(bIndex++));
		upperP.add(ps.get(pIndex++));
		if (cb != null) {
			upperP.add(bList);
		}

		lowerP.setLayout(gb2);

		lowerP.add(ls.get(lIndex++));
		lowerP.add(ps.get(pIndex++));
		lowerP.add(bs.get(bIndex++));
		lowerP.add(bs.get(bIndex++));
		lowerP.add(bs.get(bIndex++));
		lowerP.add(bs.get(bIndex++));

		upperP.add(lowerP);

		gbc1.fill = GridBagConstraints.BOTH;
		gbc2.fill = GridBagConstraints.BOTH;
		lIndex = 0;
		bIndex = 0;
		pIndex = 2;

		add(gb1, ls.get(lIndex++), gbc1, 2, 1, 1, 0);

		if (cb != null) {
			add(gb1, cb, gbc1, 2, 1, 1, 0);
			add(gb1, bs.get(bIndex++), gbc1, 2, 1, 1, 0);
		} else {
			bIndex++;
		}

		add(gb1, bs.get(bIndex++), gbc1, 0, 1, 0, 0);
		add(gb1, ps.get(pIndex++), gbc1, 0, 1, 1, 0);

		if (cb != null) {
			add(gb1, bList, gbc1, 0, 3, 1, 1);
		}

		add(gb1, lowerP, gbc1, 0, 1, 1, 1);

		add(gb2, ls.get(lIndex++), gbc2, 0, 1, 1, 0);
		add(gb2, ps.get(pIndex++), gbc2, 0, 1, 1, 0);
		add(gb2, bs.get(bIndex++), gbc2, 2, 1, 1, 0);
		add(gb2, bs.get(bIndex++), gbc2, 2, 1, 1, 0);
		add(gb2, bs.get(bIndex++), gbc2, 2, 1, 1, 0);
		add(gb2, bs.get(bIndex++), gbc2, 0, 1, 1, 0);

	}

	private void initPanel3() {

		ArrayList<JPanel> ps = new ArrayList<JPanel>();
		ps.add(p3);
		ps.add(p10);
		ps.add(p11);
		ps.add(p12);

		ArrayList<JLabel> ls = new ArrayList<JLabel>();
		ls.add(l11);
		ls.add(l10);

		ArrayList<JButton> bs = new ArrayList<JButton>();
		bs.add(bt19);
		bs.add(bt10);
		bs.add(bt12);
		bs.add(bt13);
		bs.add(bt23);
		bs.add(bt24);

		ArrayList<GridBagLayout> gbs = new ArrayList<GridBagLayout>();
		gbs.add(new GridBagLayout());
		gbs.add(new GridBagLayout());
		ArrayList<GridBagConstraints> gbcs = new ArrayList<GridBagConstraints>();
		gbcs.add(new GridBagConstraints());
		gbcs.add(new GridBagConstraints());

		settingComponents(Bank.SAVING_ACCOUNT, cb2, bList2, ps, ls, bs, gbs, gbcs);
	}

	private void initPanel2() {

		ArrayList<JPanel> ps = new ArrayList<JPanel>();
		ps.add(p2);
		ps.add(p6);
		ps.add(p7);
		ps.add(p8);

		ArrayList<JLabel> ls = new ArrayList<JLabel>();
		ls.add(l8);
		ls.add(l9);

		ArrayList<JButton> bs = new ArrayList<JButton>();
		bs.add(bt18);
		bs.add(bt17);
		bs.add(bt7);
		bs.add(bt8);
		bs.add(bt9);
		bs.add(bt16);

		ArrayList<GridBagLayout> gbs = new ArrayList<GridBagLayout>();
		gbs.add(new GridBagLayout());
		gbs.add(new GridBagLayout());
		ArrayList<GridBagConstraints> gbcs = new ArrayList<GridBagConstraints>();
		gbcs.add(new GridBagConstraints());
		gbcs.add(new GridBagConstraints());

		settingComponents(Bank.CHECKING_ACCOUNT, cb1, bList, ps, ls, bs, gbs, gbcs);
	}

	private String[] getBalalceList(int i, int accountType) {
		Account ac = null;
		switch (accountType) {
		case Bank.CHECKING_ACCOUNT:
			ac = cheAccounts.get(i);
			break;
		case Bank.SAVING_ACCOUNT:
			ac = savAccounts.get(i);
			break;
		case Bank.LOANS_ACCOUNT:
			ac = loaAccounts.get(i);
			break;
		default:
			break;
		}
		HashMap<Integer, Balance> caBalances = ac.getBalances();
		String[] balancesStr = new String[caBalances.size()];
		Iterator<Integer> iter = caBalances.keySet().iterator();
		int j = 0;
		while (iter.hasNext()) {
			Integer key = iter.next();
			Balance value = caBalances.get(key);
			balancesStr[j] = value.toString();
			j++;
		}
		return balancesStr;
	}

	private void initPanel1() {
		p1.setLayout(gbLayout1);

		p1.add(l1);
		p1.add(p5);

		p1.add(l2);
		p1.add(tf1);
		p1.add(p5);

		p1.add(l3);
		p1.add(tf2);
		p1.add(p5);

		p1.add(l4);
		p1.add(tf3);
		p1.add(p5);

		p1.add(l5);
		p1.add(tf4);
		p1.add(bt1);
		p1.add(bt4);
		p1.add(p5);

		p1.add(l6);
		p1.add(tf5);
		p1.add(bt2);
		p1.add(bt5);
		p1.add(p5);

		p1.add(l7);
		p1.add(pf1);
		p1.add(bt3);
		p1.add(bt6);
		p1.add(p5);

		p1.add(l14);
		p1.add(tf6);
		p1.add(bt21);
		p1.add(bt22);
		p1.add(p5);

		gbc1.fill = GridBagConstraints.BOTH;

		add(gbLayout1, l1, gbc1, 0, 1, 0, 0);
		add(gbLayout1, p5, gbc1, 0, 1, 1, 0);

		add(gbLayout1, l2, gbc1, 2, 1, 1, 0);
		add(gbLayout1, tf1, gbc1, 0, 1, 1, 0);
		add(gbLayout1, p5, gbc1, 0, 1, 1, 0);

		add(gbLayout1, l3, gbc1, 2, 1, 1, 0);
		add(gbLayout1, tf2, gbc1, 0, 1, 1, 0);
		add(gbLayout1, p5, gbc1, 0, 1, 1, 0);

		add(gbLayout1, l4, gbc1, 2, 1, 1, 0);
		add(gbLayout1, tf3, gbc1, 0, 1, 1, 0);
		add(gbLayout1, p5, gbc1, 0, 1, 1, 0);

		add(gbLayout1, l5, gbc1, 2, 1, 1, 0);
		add(gbLayout1, tf4, gbc1, 2, 1, 1, 0);
		add(gbLayout1, bt1, gbc1, 1, 1, 0, 0);
		add(gbLayout1, bt4, gbc1, 0, 1, 0, 0);
		add(gbLayout1, p5, gbc1, 0, 1, 1, 0);

		add(gbLayout1, l6, gbc1, 2, 1, 1, 0);
		add(gbLayout1, tf5, gbc1, 2, 1, 1, 0);
		add(gbLayout1, bt2, gbc1, 1, 1, 0, 0);
		add(gbLayout1, bt5, gbc1, 0, 1, 0, 0);
		add(gbLayout1, p5, gbc1, 0, 1, 1, 0);

		add(gbLayout1, l7, gbc1, 2, 1, 1, 0);
		add(gbLayout1, pf1, gbc1, 2, 1, 1, 0);
		add(gbLayout1, bt3, gbc1, 1, 1, 0, 0);
		add(gbLayout1, bt6, gbc1, 0, 1, 0, 0);
		add(gbLayout1, p5, gbc1, 0, 1, 1, 0);

		add(gbLayout1, l14, gbc1, 2, 1, 1, 0);
		add(gbLayout1, tf6, gbc1, 2, 1, 1, 0);
		add(gbLayout1, bt21, gbc1, 1, 1, 0, 0);
		add(gbLayout1, bt22, gbc1, 0, 1, 0, 0);
		add(gbLayout1, p5, gbc1, 0, 1, 1, 0);
	}

	private void add(GridBagLayout gb, Component c, GridBagConstraints gbc, int gridwidth, int gridheight, int weightx,
			int weighty) {
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gb.setConstraints(c, gbc);
	}

	class ProfileButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object object = e.getSource();
			if (object == bt1) {
				tf4.setEditable(true);
			} else if (object == bt2) {
				tf5.setEditable(true);
			} else if (object == bt3) {
				pf1.setEditable(true);
			} else if (object == bt21) {
				tf6.setEditable(true);
			} else {
				if (object == bt4) { // phone
					cId.setPhone(tf4.getText());
					// atm.updateCustomerInfo(cId);
					atm.updateCustomerInfoDB(cId);
				} else if (object == bt5) { // address
					cId.setAddress(tf5.getText());
					atm.updateCustomerInfoDB(cId);
					// atm.updateCustomerInfo(cId);
				} else if (object == bt6) { // pwd
					cId.setPassword(pf1.getText());
					// atm.updateCustomerInfo(cId);
					atm.updateCustomerInfoDB(cId);
				} else if (object == bt22) {
					cId.setCollateral(tf6.getText());
					// atm.updateCustomerInfo(cId);
					atm.updateCustomerInfoDB(cId);
				}
				JOptionPane.showMessageDialog(null, "Information Updated", "Message", JOptionPane.INFORMATION_MESSAGE);
				new CustomerWin(atm, atm.getCustomerByDB(cId.getIndex()), true, 0, 0);
				frame.dispose();
			}
		}
	}

	class CheSavAccButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int inOrOut = 0;
			int curAccIndex = 0;
			Object object = e.getSource();
			if (object == bt24) {
				atm.getInterest(cId, curAccount);
				atm.updateCustomerAccDB(cId);
				JOptionPane.showMessageDialog(null, "Balance Updated", "Message", JOptionPane.INFORMATION_MESSAGE);
				new CustomerWin(atm, atm.getCustomerByDB(cId.getIndex()), true, 2, cb2.getSelectedIndex());
				frame.dispose();
				return;
			} else if (object == bt16) {
				new ViewTransactionWin(atm, cId.getIndex());
				return;
			} else if (object == bt13 || object == bt8) {
				inOrOut = 1;
			} else if (object == bt12 || object == bt7) {
				inOrOut = 2;
			}
			if (object == bt13 || object == bt12) {
				curAccIndex = cb2.getSelectedIndex();
			} else if (object == bt7 || object == bt8) {
				curAccIndex = cb1.getSelectedIndex();
			}
			new TransactionWin(atm, inOrOut, cId, curAccount, curAccIndex);
			frame.dispose();
		}
	}

	
	class OpenSecurityButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object object = e.getSource();
			if(object == bt23) {
				//check balance > threshold
				//or
				//check if the account already has a security account
				
				
				System.out.println("button click");
				if(canOpen(curAccount) ) {//|| hasSecure((SavingAccount)curAccount) ) {
					new SecurityWin();
					frame.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Your do not have enough money to open a security account! Your balance should larger than 500!");
				}
				
				
			}
		}
		
	}
	
	class AccOnOffButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object object = e.getSource();
			if (object == bt10 || object == bt11 || object == bt17) {
				if (object == bt10) {
					atm.createAccount(cId, Bank.SAVING_ACCOUNT);
				} else if (object == bt11) {
					if (cId.getCollateral().trim().isEmpty() == true) {
						JOptionPane.showMessageDialog(null,
								"Please find a collateral before you create a loans account!", "Message",
								JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						atm.createAccount(cId, Bank.LOANS_ACCOUNT);
					}
				} else if (object == bt17) {
					atm.createAccount(cId, Bank.CHECKING_ACCOUNT);
				}
				// atm.updateCustomerInfo(cId);
				atm.updateCustomerAccDB(cId);
			} else {
				if (object == bt18) {
					if (cb1.getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(null, "This is a default account, you cannot stop it!", "Message",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (object == bt19) {
					curAccount = savAccounts.get(cb2.getSelectedIndex());

				} else if (object == bt20) {
					curAccount = loaAccounts.get(cb3.getSelectedIndex());
					if (ifAvailable(curAccount) == false) {
						JOptionPane.showMessageDialog(null, "Please return the money before you stop a loans account! ",
								"Message", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				atm.stopAccount(cId, curAccount);
				// atm.updateCustomerInfo(cId);
				atm.updateCustomerAccDB(cId);
			}

			atm.newCustomerWin(atm, cId, true, tabbedPane.getSelectedIndex());
			frame.dispose();
		}
	}

	class TransferButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new TransferWin(atm, cId, curAccount, cb1.getSelectedIndex());
			frame.dispose();
		}
	}

	class LoaButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int inOrOut = 0;
			Object object = e.getSource();
			if (object == bt25) {
				JOptionPane.showMessageDialog(null,
						"When you return loans, we will get money from your default checking account.", "Message",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			} else if (object == bt14) {
				if (cb3 == null) {
					JOptionPane.showMessageDialog(null, "Please open a loans account! You dont have one! ", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
				inOrOut = 1;
			} else if (object == bt15) {
				if (cb3 == null) {
					JOptionPane.showMessageDialog(null, "Please open a loans account! You dont have one! ", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
				inOrOut = 2;
			}
			new LoansWin(atm, inOrOut, cId, curAccount, cb3.getSelectedIndex());
			frame.dispose();
		}

	}

	class ComboBoxListener implements ActionListener {

		int index;
		int accountType;
		String[] balancesStr;

		@Override
		public void actionPerformed(ActionEvent e) {
			accountType = tabbedPane.getSelectedIndex();
			index = ((JComboBox) e.getSource()).getSelectedIndex();

			switch (accountType) {
			case Bank.CHECKING_ACCOUNT:
				curAccount = cheAccounts.get(index);
				balancesStr = getBalalceList(cb1.getSelectedIndex(), Bank.CHECKING_ACCOUNT);
				bList.setListData(balancesStr);
				break;
			case Bank.SAVING_ACCOUNT:
				curAccount = savAccounts.get(index);
				balancesStr = getBalalceList(cb2.getSelectedIndex(), Bank.SAVING_ACCOUNT);
				bList2.setListData(balancesStr);
				break;
			case Bank.LOANS_ACCOUNT:
				curAccount = loaAccounts.get(index);
				balancesStr = getBalalceList(cb3.getSelectedIndex(), Bank.LOANS_ACCOUNT);
				bList3.setListData(balancesStr);
				break;
			default:
				break;
			}
		}
	}

	public boolean ifAvailable(Account cur) {
		boolean flag = true;
		for (int i = 0; i < cur.getBalances().size(); i++) {
			if (cur.getBalances().get(i + 1).getMoney() > 0) {
				flag = false;
			}
		}
		return flag;
	}
	
	//check if this saving account can open a secuiry account
		public boolean canOpen(Account cur) {
			if(cur.getBalances().get(1).getMoney() > secureThreshold) {
				return true;
			}
			return false;
		}
		
		public boolean hasSecure(Account cur) {
			return true;
			
			
			
//			if(cur.hasSecureAccount()) {
//				return true;
//			}
//			return false;
		}
	
}
