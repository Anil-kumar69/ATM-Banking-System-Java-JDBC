package ATM_Software_System;

import java.sql.*;
import java.util.*;

public class Admin {

	private static String Admin_pin = "1234";

	Scanner sc = new Scanner(System.in);

	String getadminpin() {
		return Admin_pin;
	}

	public static int count = 0;

	public String getAccno() {
		Random r = new Random();
		String acc = "";
		int n = 8 + r.nextInt(5);
		for (int i = 0; i < n; i++) {
			acc = acc + r.nextInt(n);
		}
		return acc;
	}

	public void CreateAccount() {

		System.out.println("---------------- Enter Details ----------------");
		System.out.println();
		System.out.print("Enter Username: ");
		String username = sc.next();
		System.out.print("Enter location: ");
		String location = sc.next();
		System.out.print("Create pin: ");
		String pin = sc.next();
		System.out.print("Enter Deposit Money: ");
		double balance = sc.nextDouble();
		String accno = "SBI"+getAccno();
		count++;

		try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "root", "@Anilkumar69139");
				PreparedStatement ps = c.prepareStatement(
						"insert into Accounts (accno,username,location,pin,balance) values (?,?,?,?,?)");) {

			ps.setString(1, accno);
			ps.setString(2, username);
			ps.setString(3, location);
			ps.setString(4, pin);
			ps.setDouble(5, balance);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("-------------------- ACCOUNT CREATED SUCCESSFULLY ---------------");
		System.out.println();

	}

	public void ViewAccount() {

		try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "root", "@Anilkumar69139");
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery("Select * from Accounts");) {
			boolean val = rs.next();
			if (val) {
				System.out.println("----------------- All Accounts -----------------");
				System.out.println();
				System.out.println("------------------------------------------------------");
				while (val) {
					System.out.println("Account No: " + rs.getNString("accno"));
					System.out.println("Name: " + rs.getNString("username"));
					System.out.println("Location: " + rs.getNString("location"));
					System.out.println("Pin: " + rs.getNString("pin"));
					System.out.println("Balance: " + rs.getDouble("balance"));
					System.out.println("------------------------------------------------------");
					System.out.println();
					if (!rs.next()) {
						val = false;
					}
				}

			} else {
				System.out.println("No Accounts Exist");
				System.out.println();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteacc() {
		try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "root", "@Anilkumar69139");
				PreparedStatement ps = c.prepareStatement("delete from Accounts where accno = ?");
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery("Select * from accounts");) {
			if (rs.next()) {
				System.out.print("ENTER ACCOUNT NO. TO DELETE: ");
				String acc = sc.next();

				System.out.print("Enter Admin pin to Delete: ");
				String pin = sc.next();
				if (getadminpin().equals(pin)) {
					ps.setNString(1, acc);
					int val = ps.executeUpdate();
					if (val == 1) {
						System.out.println("         " + ConsoleColors.GREEN + "ACCOUNT DELETED SUCCESSFULLY !"
								+ ConsoleColors.RESET + "         ");
						System.out.println();

					} else {
						System.out.println("         " + ConsoleColors.YELLOW + "ACCOUNT NOT FOUND !"
								+ ConsoleColors.RESET + "         ");
						System.out.println();
					}

				} else {
					System.out.println("         " + ConsoleColors.RED + "Admin pin Incorrect!" + ConsoleColors.RESET
							+ "         ");
				}

			} else {
				System.out.println(
						"         " + ConsoleColors.YELLOW + "No Accounts Exist" + ConsoleColors.RESET + "         ");
				System.out.println();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void admin_operations() throws Invalid {
		System.out.print("Enter Admin pin: ");
		String pin = sc.next();
		System.out.println();
		int count = 1;
		boolean chance = true;
		if (!Admin_pin.equals(pin)) {
			while (chance) {
				System.out.print(ConsoleColors.YELLOW + "Admin Pin Incorrect You have " + (3 - count) + " chances "
						+ ConsoleColors.RESET);
				String pin1 = sc.next();
				System.out.println();
				if (count == 2 || pin1.equals(Admin_pin)) {
					pin = pin1;
					chance = false;
					break;
				}
				count++;

			}
		}

		if (Admin_pin.equals(pin)) {
			System.out.println();
			System.out.println(
					"         " + ConsoleColors.GREEN + "<<--------------- Admin Login Successful ----------------->>"
							+ ConsoleColors.RESET + "         ");
			System.out.println();
			boolean val = true;
			while (val) {
				System.out.println("1) Create Account  [Press 1]");
				System.out.println("2) View Account    [Press 2]");
				System.out.println("3) Delete Account  [Press 3]");
				System.out.println("4) Exit            [Press 4]");
				System.out.println();
				System.out.print("Select a number: ");
				int a = sc.nextInt();
				System.out.println();
				switch (a) {
				case 1:
					CreateAccount();
					break;
				case 2:
					ViewAccount();
					break;

				case 3:
					deleteacc();
					break;
				case 4:
					val = false;
					break;
				}
			}
		} else {
			throw new Invalid(ConsoleColors.RED + "Invalid Admin Login Pin Multiple Incorrect Inputs"
					+ ConsoleColors.RESET);

		}

	}

}