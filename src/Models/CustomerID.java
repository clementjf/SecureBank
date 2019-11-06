package Models;
import java.util.ArrayList;

public class CustomerID extends ID {

	private ArrayList<Account> accounts;
	private String collateral = "";
	private int currentCheAccount;
	private int currentSavAccount;
	private int currentLoaAccount;
	private String name = "";
	private String address = "";
	private String phone = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCurrentCheAccount() {
		return currentCheAccount;
	}

	public void setCurrentCheAccount(int currentCheAccount) {
		this.currentCheAccount = currentCheAccount;
	}

	public int getCurrentSavAccount() {
		return currentSavAccount;
	}

	public void setCurrentSavAccount(int currentSavAccount) {
		this.currentSavAccount = currentSavAccount;
	}

	public int getCurrentLoaAccount() {
		return currentLoaAccount;
	}

	public void setCurrentLoaAccount(int currentLoaAccount) {
		this.currentLoaAccount = currentLoaAccount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public CustomerID(String name, String userName, String password, int index) {
		super(userName, password, index);
		this.name = name;
		accounts = new ArrayList<Account>();
	//	Bank.createCheckingAccount(this);
		setCurrentCheAccount(0);
		setCurrentSavAccount(-1);
		setCurrentLoaAccount(-1);
	}


	
	

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}

	public String getCollateral() {
		return collateral;
	}

	public void setCollateral(String collateral) {
		this.collateral = collateral;
	}


	public ArrayList<Account> getOneAccounts(int accountType) {
		ArrayList<Account> asAccounts = new ArrayList<Account>();
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getType() == accountType && accounts.get(i).getCondition() == 1) {
				 asAccounts.add(accounts.get(i));
			}
		}
		return asAccounts;
	}

	public String toString() {
		// TODO
		return name;
	}

	

}
