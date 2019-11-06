package Models;

public class Transaction {
	private String name;
	private String info;
	private Balance balance;
	private String accountNumber;
	private int index;

	public Transaction(int index, String name, String accNum, String info, Balance balance) {
		this.index = index;
		this.name = name;
		this.accountNumber = accNum;
		this.info = info;
		this.balance = balance;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Balance getBalance() {
		return balance;
	}

	public void setBalance(Balance balance) {
		this.balance = balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String toString() {
		return name + "     " + accountNumber + "     " + info + "     " + balance;
	}
}
