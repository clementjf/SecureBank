package Models;

public class SecurityAccount {
	
	private String savingAccountNum;
	
	public SecurityAccount(String savingAccountNum) {
		this.savingAccountNum = savingAccountNum;
	}
	
	public String getBoundSavingNum() {
		return savingAccountNum;
	}
	
	public void setBoundSavingNum(String num) {
		savingAccountNum = num;
	}
}
