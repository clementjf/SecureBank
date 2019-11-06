package Models;

public class SavingAccount extends Account{
	
	private SecurityAccount secureAccount = null;
	
	public SavingAccount() {
		super();
		this.type = Bank.SAVING_ACCOUNT;

	}
	
	public void setNewSecurityAccount() {
		String savingNum = this.accountNumber;
		secureAccount = new SecurityAccount(savingNum);
	}
	
	public boolean hasSecureAccount() {
		if(secureAccount == null) {
			return false;
		}
		return true;
	}
}
