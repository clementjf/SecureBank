package Models;

public abstract class ID {
	protected String userName;
	protected String password;
	protected int index;

	public ID(String userName, String password, int index) {
		this.userName = userName;
		this.password = password;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return "Manager " + userName;
	}
}
