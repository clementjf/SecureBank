package JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import Models.*;

public class DB {
	public static MySQLHelper mHelper;

	public DB() {
		mHelper = new MySQLHelper();
	}

	public Account getAccount(String acnumber) {
		// get accounts ID By accountnumber
		Account acc = null;
		String sql = "select * from accountinfo where ID=" + acnumber;
		ResultSet pResultSet = null;
		try {
			pResultSet = mHelper.query(sql);

			while (pResultSet.next()) {

				String accNumber = pResultSet.getString(1);
				int custID = pResultSet.getInt(2);
				int accType = pResultSet.getInt(3);
				int condition = pResultSet.getInt(6);
				acc = new Account(accNumber, accType, condition);
				HashMap<Integer, Balance> balances = getBalances(accNumber, custID);
				acc.setBalances(balances);

			}
			pResultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return acc;
	}

	public void close() {
		mHelper.close();
	}

	public ArrayList<Account> getAccounts(int id) {

		// get accounts ID By customer id(index)

		String sql = "select * from accountinfo where custID=" + id;
		ResultSet pResultSet = null;
		ArrayList<Account> accs = new ArrayList<Account>();
		try {
			pResultSet = mHelper.query(sql);

			while (pResultSet.next()) {

				String accNumber = pResultSet.getString(1);
				int accType = pResultSet.getInt(3);
				int condition = pResultSet.getInt(6);
				if (condition == 1) {
					Account acc = new Account(accNumber, accType, 1);
					HashMap<Integer, Balance> balances = getBalances(accNumber, id);
					acc.setBalances(balances);
					accs.add(acc);
				}
			}
			pResultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accs;

	}

	private HashMap<Integer, Balance> getBalances(String accNum, int cId) {

		HashMap<Integer, Balance> balances = new HashMap<Integer, Balance>();
		String sql = "select CurrencyID,money from accountinfo where custID=" + cId + " and id='" + accNum + "'";
		ResultSet pResultSet = null;

		try {
			pResultSet = mHelper.query(sql);

			while (pResultSet.next()) {

				int currencyID = pResultSet.getInt(1);
				double balance = pResultSet.getDouble(2);
				String currType = getCurrencyType(currencyID);
				balances.put(currencyID, new Balance(balance, currencyID, currType));
			}
			pResultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balances;
	}

	private String getCurrencyType(int currencyID) {
		String sql = "select name from currencytype where ID=" + currencyID;
		ResultSet pResultSet = null;
		String currType = null;
		try {
			pResultSet = mHelper.query(sql);

			while (pResultSet.next()) {

				currType = pResultSet.getString(1);
			}
			pResultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currType;
	}

	public CustomerID getCustomerID(int id) {

		// getCustomerID By index

		String sql = "select * from customerinfo where id=" + id;
		ResultSet pResultSet = null;
		CustomerID cID = null;
		try {
			pResultSet = mHelper.query(sql);

			while (pResultSet.next()) {
				int index = pResultSet.getInt(1);
				String name = pResultSet.getString(2);
				String add = pResultSet.getString(3);
				String password = pResultSet.getString(4);
				String userName = pResultSet.getString(5);
				String phone = pResultSet.getString(6);
				String collateral = pResultSet.getString(7);
				cID = new CustomerID(name, userName, password, index);
				cID.setAddress(add);
				cID.setCollateral(collateral);
				cID.setPhone(phone);
			}
			pResultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cID;
	}

	public ManagerID getManagerID(int id) {

		// getManagerID By index

		String sql = "select * from managerinfo where id=" + id;
		ResultSet pResultSet = null;
		ManagerID mID = null;
		try {
			pResultSet = mHelper.query(sql);

			while (pResultSet.next()) {
				int index = pResultSet.getInt(1);
				String password = pResultSet.getString(2);
				String userName = pResultSet.getString(3);
				mID = new ManagerID(userName, password, index);
			}
			pResultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mID;
	}

	public Stock getStocks() { // getCustomerID By index
		return null;

	}

	public int ifCustomerID(String username, String pwd) {

		// check db if this username and pwd could match a customer ID

		String sql = "select ID from customerinfo where username='" + username + "' and password='" + pwd + "'";
		ResultSet pResultSet = null;
		int id = 0;
		try {
			pResultSet = mHelper.query(sql);

			while (pResultSet.next()) {
				id = Integer.parseInt(pResultSet.getString(1));

			}
			pResultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public int ifManagerID(String username, String pwd) {

		// check db if this username and pwd could match a manager ID

		String sql = "select ID from managerinfo where username='" + username + "' and password='" + pwd + "'";
		ResultSet pResultSet = null;
		int id = 0;
		try {
			pResultSet = mHelper.query(sql);

			while (pResultSet.next()) {
				id = pResultSet.getInt(1);
				System.out.println(id);
			}
			pResultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public ArrayList<CustomerID> getCustomersID() {
		// getCustomerIDs

		ArrayList<CustomerID> cIDs = new ArrayList<CustomerID>();
		String sql = "select * from customerinfo";
		ResultSet pResultSet = null;
		CustomerID cID = null;
		try {
			pResultSet = mHelper.query(sql);

			while (pResultSet.next()) {
				int index = pResultSet.getInt(1);
				String name = pResultSet.getString(2);
				String add = pResultSet.getString(3);
				String password = pResultSet.getString(4);
				String userName = pResultSet.getString(5);
				String phone = pResultSet.getString(6);
				String collateral = pResultSet.getString(7);
				cID = new CustomerID(name, userName, password, index);
				cID.setAddress(add);
				cID.setCollateral(collateral);
				cID.setPhone(phone);
				cIDs.add(cID);
			}
			pResultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cIDs;
	}

	public void updateCustomerInfo(CustomerID cId) {
		String add = cId.getAddress();
		String password = cId.getPassword();
		String phone = cId.getPhone();
		String collateral = cId.getCollateral();
		int index = cId.getIndex();
		String sql = "Update customerinfo set address='" + add + "',password='" + password + "',phoneno='" + phone
				+ "',collateral='" + collateral + "' where id=" + index;
		try {
			mHelper.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertAccount(int cIdIndex, Account acc, Balance b) {

		String id = acc.getAccountNumber();
		int custId = cIdIndex;
		int accountType = acc.getType();
		int currencyID = b.getCurID();
		double money = b.getMoney();
		int condition = 1;
		String sql = "Insert into accountinfo values ('" + id + "'," + custId + "," + accountType + "," + currencyID
				+ "," + money + "," + condition + ")";
		try {
			mHelper.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateAccount(Account acc, Balance b) {
		String id = acc.getAccountNumber();
		int currencyID = b.getCurID();
		double money = b.getMoney();
		int condition = acc.getCondition();
		String sql = "Update accountinfo set money=" + money + ",Con=" + condition + " where id='" + id
				+ "' and currencyID=" + currencyID;
		try {
			mHelper.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getCountsAcc() {
		String sql = "Select * from accountinfo";
		return mHelper.getCount(sql);
	}
	
public boolean hasSecure(String savId) {
		
//		String sql = "select ID from managerinfo where username='" + username + "' and password='" + pwd + "'";
//		ResultSet pResultSet = null;
//		int id = 0;
//		try {
//			pResultSet = mHelper.query(sql);
//
//			while (pResultSet.next()) {
//				id = pResultSet.getInt(1);
//				System.out.println(id);
//			}
//			pResultSet.close();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return id;
		
		return false;
	}

}
