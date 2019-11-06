package Models;

import java.util.ArrayList;


import JDBC.DB;

public abstract class Bank {
	public Bank(String name) {
		this.name = name;
		transactions = new ArrayList<Transaction>();
		accountCount = 0;
	}

	public static int accountCount;

	public static final int CHECKING_ACCOUNT = 1;
	public static final int SAVING_ACCOUNT = 2;
	public static final int LOANS_ACCOUNT = 3;

	public static final String[] CURRENCY_LIST = { "USD", "CNY", "JPY" };

	public static final int USD = 1;
	public static final int CNY = 2;
	public static final int JPY = 3;

	public static final String TR_SAVE_TO_CHECKACC = "Saving Money to Checking Account";
	public static final String TR_SAVE_TO_SAVEACC = "Saving Money to Saving Account";

	public static final String TR_OPEN_LOANSACC = "Opening a loans account";
	public static final String TR_OPEN_SAVEACC = "Opening a saving account";
	public static final String TR_OPEN_CHECKACC = "Opening a checking account";

	public static final String TR_CLOSE_LOANSACC = "Closing a loans account";
	public static final String TR_CLOSE_SAVEACC = "Closing a saving account";
	public static final String TR_CLOSE_CHECKACC = "Closing a checking account";

	public static final String TR_REQUEST_LOANS = "Requesting loans";
	public static final String TR_RETURN_LOANS = "Returning loans";

	public static final String TR_TRANSFER = "Transfer Money";
	public static final String TR_WITHDRAWAL_CHEACC = "Withdrawing cash from checking account";
	public static final String TR_WITHDRAWAL_SAVACC = "Withdrawing cash from saving account";

	protected String name;
	protected DB db;
//	protected ArrayList<ManagerID> managerIDs;
//	protected ArrayList<CustomerID> customerIDs;
	protected ArrayList<Transaction> transactions;

	protected static float CHECKING_ACCOUNT_OPEN_FEE;
	protected static float CHECKING_ACCOUNT_CLOSE_FEE;
	protected static float SAVING_ACCOUNT_OPEN_FEE;
	protected static float SAVING_ACCOUNT_CLOSE_FEE;
	protected static float LOANS_ACCOUNT_OPEN_FEE;
	protected static float LOANS_ACCOUNT_CLOSE_FEE;

	protected static float TRANSACTION_FEE;
	protected static float WITHDRAWAL_FEE;

	protected static double LOANS_INTEREST;
	protected static double SAVING_INTEREST;

	protected static double SAVING_INTEREST_THRESOLD;

	public static int getAccountCount() {
		return accountCount;
	}

	public static void setAccountCount() {
		Bank.accountCount++;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}


	public abstract void createCustomer(String name, String username, String pwd, String address, String phone,
			String collateral);

	public static void createLoansAccount(CustomerID cId) {
		LoansAccount lAccount = new LoansAccount();
		cId.getAccounts().add(lAccount);
		if (cId.getCurrentLoaAccount() == -1) {
			cId.setCurrentLoaAccount(0);
		}
		charge(cId, LOANS_ACCOUNT_OPEN_FEE);
	}

	public static void createSavingAccount(CustomerID cId) {
		SavingAccount sAccount = new SavingAccount();
		cId.getAccounts().add(sAccount);
		if (cId.getCurrentSavAccount() == -1) {
			cId.setCurrentSavAccount(0);
		}
		charge(cId, SAVING_ACCOUNT_OPEN_FEE);
	}

	public static void createCheckingAccount(CustomerID cId) {
		CheckingAccount cAccount = new CheckingAccount();
		cId.getAccounts().add(cAccount);
		charge(cId, CHECKING_ACCOUNT_OPEN_FEE);

	}

	public static void charge(CustomerID cId, double price) {
		int defaultAcc = cId.getCurrentCheAccount();
		Balance b = cId.getAccounts().get(defaultAcc).getBalances().get(Bank.USD);
		b.setMoney(b.getMoney() - price);
	}

	public void addTransaction(int index, String name, String accNum, String info, Balance b) {
		Transaction t = new Transaction(index, name, accNum, info, b);
		transactions.add(t);
	}

	public void deposit(CustomerID cId, Account acc, double amount, int type) {
		String info = "";

		int accType = acc.getType();

		switch (accType) {
		case CHECKING_ACCOUNT:
			info = TR_SAVE_TO_CHECKACC;
			break;
		case SAVING_ACCOUNT:
			info = TR_SAVE_TO_SAVEACC;
			break;
		default:
			break;
		}

		for (int i = 0; i < cId.getAccounts().size(); i++) {

			Account a = cId.getAccounts().get(i);

			if (a.getAccountNumber().equals(acc.getAccountNumber())) {

				double money = a.getBalances().get(type).getMoney();
				money = money + amount;
				a.getBalances().get(type).setMoney(money);

				cId.getAccounts().set(i, a);

				Balance b = new Balance(amount, type, Bank.CURRENCY_LIST[type]);
				addTransaction(cId.getIndex(), cId.getName(), acc.getAccountNumber(), info, b);
				charge(cId, TRANSACTION_FEE);
				return;
			}
		}

	}

	public void withdrawal(CustomerID cId, Account acc, double amount, int type) {
		String info = "";

		int accType = acc.getType();

		switch (accType) {
		case CHECKING_ACCOUNT:
			info = TR_WITHDRAWAL_CHEACC;
			break;
		case SAVING_ACCOUNT:
			info = TR_WITHDRAWAL_SAVACC;
			break;
		default:
			break;
		}

		for (int i = 0; i < cId.getAccounts().size(); i++) {

			Account a = cId.getAccounts().get(i);

			if (a.getAccountNumber().equals(acc.getAccountNumber())) {

				double money = a.getBalances().get(type).getMoney();
				money = money - amount;
				a.getBalances().get(type).setMoney(money);

				cId.getAccounts().set(i, a);

				Balance b = new Balance(amount, type, Bank.CURRENCY_LIST[type]);
				addTransaction(cId.getIndex(), cId.getName(), acc.getAccountNumber(), info, b);
				charge(cId, WITHDRAWAL_FEE);
				return;
			}
		}

	}

	public void borrowLoans(CustomerID cId, Account acc, double amount, int type) {
		String info = TR_REQUEST_LOANS;
		for (int i = 0; i < cId.getAccounts().size(); i++) {

			Account a = cId.getAccounts().get(i);

			if (a.getAccountNumber().equals(acc.getAccountNumber())) {

				double money = a.getBalances().get(type).getMoney();
				money = money + amount;
				a.getBalances().get(type).setMoney(money);

				cId.getAccounts().set(i, a);

				Balance b = new Balance(amount, type, Bank.CURRENCY_LIST[type]);
				addTransaction(cId.getIndex(), cId.getName(), acc.getAccountNumber(), info, b);
				charge(cId, TRANSACTION_FEE);
				return;
			}
		}

	}

	public void returnLoans(CustomerID cId, Account acc, double amount, int type) {
		String info = TR_RETURN_LOANS;

		int cur = cId.getCurrentCheAccount();
		Account cAccount = cId.getAccounts().get(cur);
		double money = cAccount.getBalances().get(type).getMoney();
		money = (float) (money - amount * (1 + LOANS_INTEREST));
		cAccount.getBalances().get(type).setMoney(money);

		cId.getAccounts().set(cur, cAccount);

		for (int i = 0; i < cId.getAccounts().size(); i++) {
			String s1 = cId.getAccounts().get(i).getAccountNumber();
			String s2 = acc.getAccountNumber();
			if (s1.equals(s2)) {
				double m = acc.getBalances().get(type).getMoney();
				cId.getAccounts().get(i).getBalances().get(type).setMoney(m - amount);
				cId.getAccounts().set(i, acc);
				break;
			}
		}
		Balance b = new Balance(-amount, type, Bank.CURRENCY_LIST[type]);
		addTransaction(cId.getIndex(), cId.getName(), cAccount.getAccountNumber(), info, b);
		charge(cId, TRANSACTION_FEE);
		return;

	}

	public void stopAccount(CustomerID cId, Account curAccount) {
		for (int i = 0; i < cId.getAccounts().size(); i++) {
			String s1 = cId.getAccounts().get(i).getAccountNumber();
			String s2 = curAccount.getAccountNumber();
			if (s1.equals(s2)) {
				cId.getAccounts().get(i).setCondition(0);
				break;
			}
		}
		int type = curAccount.getType();
		boolean flag = false;
		for (int i = 0; i < cId.getAccounts().size(); i++) {
			if (cId.getAccounts().get(i).getType() == type && cId.getAccounts().get(i).getCondition() == 1) {
				flag = true;
				break;
			}
		}
		if (flag == false) {
			if (type == Bank.SAVING_ACCOUNT) {
				cId.setCurrentSavAccount(-1);
			} else if (type == Bank.LOANS_ACCOUNT) {
				cId.setCurrentLoaAccount(-1);
			}
		}
		switch (type) {
		case Bank.CHECKING_ACCOUNT:
			charge(cId, CHECKING_ACCOUNT_CLOSE_FEE);
			break;
		case Bank.SAVING_ACCOUNT:
			charge(cId, SAVING_ACCOUNT_CLOSE_FEE);
			break;
		case Bank.LOANS_ACCOUNT:
			charge(cId, LOANS_ACCOUNT_CLOSE_FEE);
			break;
		default:
			break;
		}

	}

	public void closeAccount(int index) {
		deleteCustomer(index);
	}



	public void getInterest(CustomerID cId, Account acc) {
		
		for (int i = 0; i < cId.getAccounts().size(); i++) {
			Account a = cId.getAccounts().get(i);
			if (a.getAccountNumber().equals(acc.getAccountNumber())) {
				for (int j = 0; j < a.getBalances().size(); j++) {
					double money = a.getBalances().get(j + 1).getMoney();
					if (money >= SAVING_INTEREST_THRESOLD) {
						money = (double) (money * (1 + SAVING_INTEREST));
						a.getBalances().get(j + 1).setMoney(money);
					}
				}
			}
		}
	}

	public abstract int ifManager(String username, String pwd); // By database

	public abstract int ifCustomer(String username, String pwd); // By database

	public abstract ManagerID getManager(int id); // By database

	public abstract ArrayList<CustomerID> getCustomers(); // By database

	public abstract CustomerID getCustomer(int id); // By database

	public abstract void updateCustomer(CustomerID cId); // By database

	public abstract void updateCustomerAccs(int cIdIndex, ArrayList<Account> accs); // By database
	
	public abstract void deleteCustomer(int index);   // By database

	public abstract boolean ifAccount(String accn);  // By database
	
	public abstract boolean ifSecurity(Account cur); // By database

}
