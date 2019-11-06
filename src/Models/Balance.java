package Models;
//test
public class Balance {
	private double money;
	private String currency;
	private int curID;

	public Balance(double money, Integer curID, String currency) {
		this.money = money;
		this.currency = currency;
		this.curID = curID;
	}

	public int getCurID() {
		return curID;
	}

	public void setCurID(int curID) {
		this.curID = curID;
	}

	public String toString() {
		return money + " " + currency;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
