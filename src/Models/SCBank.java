package Models;

import java.util.ArrayList;
import java.util.HashMap;

import JDBC.DB;

public class SCBank extends Bank {
	public static DB db;
	public static final float SC_CHECKING_ACCOUNT_OPEN_FEE = 10;
	public static final float SC_CHECKING_ACCOUNT_CLOSE_FEE = 10;
	public static final float SC_SAVING_ACCOUNT_OPEN_FEE = 10;
	public static final float SC_SAVING_ACCOUNT_CLOSE_FEE = 10;
	public static final float SC_LOANS_ACCOUNT_OPEN_FEE = 10;
	public static final float SC_LOANS_ACCOUNT_CLOSE_FEE = 10;

	public static final float SC_TRANSACTION_FEE = 10;
	public static final float SC_WITHDRAWAL_FEE = 10;

	public static final double SC_LOANS_INTEREST = 0.001;
	public static final double SC_SAVING_INTEREST = 0.002;

	public static final double SC_SAVING_INTEREST_THRESOLD = 5000;

	public SCBank(String name) {
		super(name);
		CHECKING_ACCOUNT_OPEN_FEE = SC_CHECKING_ACCOUNT_OPEN_FEE;
		CHECKING_ACCOUNT_CLOSE_FEE = SC_CHECKING_ACCOUNT_CLOSE_FEE;
		SAVING_ACCOUNT_OPEN_FEE = SC_SAVING_ACCOUNT_OPEN_FEE;
		SAVING_ACCOUNT_CLOSE_FEE = SC_SAVING_ACCOUNT_CLOSE_FEE;
		LOANS_ACCOUNT_OPEN_FEE = SC_LOANS_ACCOUNT_OPEN_FEE;
		LOANS_ACCOUNT_CLOSE_FEE = SC_LOANS_ACCOUNT_CLOSE_FEE;

		TRANSACTION_FEE = SC_TRANSACTION_FEE;

		LOANS_INTEREST = SC_LOANS_INTEREST;
		SAVING_INTEREST = SC_SAVING_INTEREST;

		SAVING_INTEREST_THRESOLD = SC_SAVING_INTEREST_THRESOLD;

		// createCustomers();
		// createManagers();
	}

	public SCBank(String name, DB db) {
		super(name);
		this.db = db;
		CHECKING_ACCOUNT_OPEN_FEE = SC_CHECKING_ACCOUNT_OPEN_FEE;
		CHECKING_ACCOUNT_CLOSE_FEE = SC_CHECKING_ACCOUNT_CLOSE_FEE;
		SAVING_ACCOUNT_OPEN_FEE = SC_SAVING_ACCOUNT_OPEN_FEE;
		SAVING_ACCOUNT_CLOSE_FEE = SC_SAVING_ACCOUNT_CLOSE_FEE;
		LOANS_ACCOUNT_OPEN_FEE = SC_LOANS_ACCOUNT_OPEN_FEE;
		LOANS_ACCOUNT_CLOSE_FEE = SC_LOANS_ACCOUNT_CLOSE_FEE;

		TRANSACTION_FEE = SC_TRANSACTION_FEE;

		LOANS_INTEREST = SC_LOANS_INTEREST;
		SAVING_INTEREST = SC_SAVING_INTEREST;

		SAVING_INTEREST_THRESOLD = SC_SAVING_INTEREST_THRESOLD;
	}

	public static int getAccountCount() {
		return accountCount;
	}

	public static void setAccountCount(int accountCount) {
		SCBank.accountCount = accountCount;
	}

	public void createCustomer(String name, String userName, String pwd, String address, String phone,
			String collateral) {
		CustomerID cId = new CustomerID(name, userName, pwd, getCounts("Customer") + 1);
		cId.setAddress(address);
		cId.setPhone(phone);
		cId.setCollateral(collateral);
		createCheckingAccount(cId);
		Account acc = cId.getAccounts().get(0);
		HashMap<Integer, Balance> bs = acc.getBalances();
		for (Balance b : bs.values()) {
			db.insertAccount(cId.getIndex(), acc, b);
		}
	}

	@Override
	public int ifManager(String username, String pwd) { // By database
		return db.ifManagerID(username, pwd);
	}

	@Override
	public int ifCustomer(String username, String pwd) { // By database
		return db.ifCustomerID(username, pwd);
	}

	@Override
	public ManagerID getManager(int id) { // By database
		return db.getManagerID(id);
	}

	@Override
	public CustomerID getCustomer(int id) { // By database
		CustomerID cId = db.getCustomerID(id);
		ArrayList<Account> accounts = db.getAccounts(id);
		cId.setAccounts(accounts);
		return cId;
	}

	@Override
	public ArrayList<CustomerID> getCustomers() { // By database
		return db.getCustomersID();
	}

	@Override
	public void updateCustomer(CustomerID cId) { // By database
		db.updateCustomerInfo(cId);
	}

	@Override
	public void updateCustomerAccs(int cIdIndex, ArrayList<Account> accs) { // By database
		for (int i = 0; i < accs.size(); i++) {
			Account acc = accs.get(i);
			Account accQuery = db.getAccount(acc.getAccountNumber());
			HashMap<Integer, Balance> bs = acc.getBalances();
			if (accQuery == null) {
				for (Balance b : bs.values()) {
					db.insertAccount(cIdIndex, acc, b);
				}
			} else {
				for (Balance b : bs.values()) {
					db.updateAccount(acc, b);
				}
			}
		}

	}

	public static int getCounts(String classstr) { // By database
		int c = 0;
		if (classstr.equals("Account")) {
			c = db.getCountsAcc();
		}
		return c;
	}

	@Override
	public void deleteCustomer(int index) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ifAccount(String accn) {
		boolean flag = false;
		Account account = null;
		account = db.getAccount(accn);
		if (account != null && account.getType() != Bank.LOANS_ACCOUNT) {
			flag = true;
		}
		return flag;
	}
	
	@Override
	public boolean ifSecurity(Account cur) {
		// TODO Auto-generated method stub
		return db.hasSecure(cur.getAccountNumber());
//		return false;
	}
}
