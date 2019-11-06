package Models;

import java.util.HashMap;

public class Account {
	protected int type;
	protected String accountNumber;
	protected HashMap<Integer, Balance> balances;
	protected int condition;

	public Account() {
		balances = new HashMap<Integer, Balance>();
		balances.put(Bank.USD, new Balance(0, Bank.USD, "USD"));
		setAccountNumber(SCBank.getCounts("Account") + 1);
		Bank.setAccountCount();
		condition = 1;
	}

	public Account(String accNumber, int type, int con) {
		accountNumber = accNumber;
		this.type = type;
		condition = con;
	}

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public HashMap<Integer, Balance> getBalances() {
		return balances;
	}

	public void setBalances(HashMap<Integer, Balance> balances) {
		this.balances = balances;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int an) {
		String num = String.format("%8d", an).replace(" ", "0");
		this.accountNumber = num;
	}
}
