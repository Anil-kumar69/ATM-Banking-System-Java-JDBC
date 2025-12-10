package ATM_Software_System;

import java.util.*;

public class Account {

	private String username;
	private String location;
	private String accno;
	private String pin;
	double balance;

	Scanner sc = new Scanner(System.in);

	Account(String username, String location, String accno, String pin, double balance) {
		this.username = username;
		this.location = location;
		this.accno = accno;
		this.pin = pin;
		setbalance(balance);
	}

	public String getusername() {
		return username;
	}

	public String getlocation() {
		return location;
	}

	public String getaccno() {
		return accno;
	}

	public String getpin() {
		return pin;
	}

	public double getbalance() {
		return balance;
	}

	public void setbalance(double balance) {
		if (balance >= 0) {
			this.balance = balance;
		}
	}

	public String toString() {
		return "name: " + username + "\nLocation:" + location + "\nAccount Number: " + accno + "\npin: " + pin
				+ "\nBalance: " + balance;
	}

}
