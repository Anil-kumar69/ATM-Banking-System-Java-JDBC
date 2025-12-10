package ATM_Software_System;

import java.sql.*;
import java.util.*;

public class User {
	public static String acno = null;

	Scanner sc = new Scanner(System.in);

	public void saveTransactions(String accno, double money, String type) {
		try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "root", "@Anilkumar69139");
				PreparedStatement ps = c.prepareStatement("Insert into transaction(accno,money,type) values(?,?,?)");) {

			ps.setString(1, accno);
			ps.setDouble(2, money);
			ps.setString(3, type);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void viewTransaction() {
		try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "root", "@Anilkumar69139");
				PreparedStatement ps = c.prepareStatement("Select * from transaction where accno = ?");) {
			ps.setString(1, acno);
			ResultSet rs = ps.executeQuery();
			boolean record = false;
			while (rs.next()) {
				record = true;
				System.out.println("--------------------------------------------------------------------");
				System.out.println("ACCNO: " + rs.getString("accno") + "  " + "AMOUNT: " + rs.getDouble("money") + "  "
						+ "TRANSACTION MODE: " + rs.getString("type") + "  " + "DATE: " + rs.getDate("date"));
			}
			if (!record) {
				System.out.println(ConsoleColors.BLUE + "---------------- No Transaction History ----------------" + ConsoleColors.RESET);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void Withdrawl() throws Invalid {
		System.out.print("Enter Amount To Withdrawl: ");
		double wit = sc.nextDouble();
		String debit = "Debit";
		try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "root", "@Anilkumar69139");
				PreparedStatement ps = c.prepareStatement("Update accounts set balance = balance- ? where accno=? ");
				PreparedStatement ps1 = c.prepareStatement("Select balance from accounts where accno=?");) {
			ps1.setString(1, acno);
			ResultSet rs = ps1.executeQuery();
			double balance = 0;
			while (rs.next()) {
				balance = rs.getDouble("balance");
			}
			ps.setDouble(1, wit);
			ps.setString(2, acno);
			if (balance >= wit && wit % 100 == 0) {
				ps.executeUpdate();
				saveTransactions(acno, wit, debit);
				System.out.println(ConsoleColors.GREEN + "Withdrawl Successfull" + ConsoleColors.RESET);
			} else if (balance >= wit && wit % 100 != 0) {
				throw new Invalid(ConsoleColors.YELLOW + "Enter multiples of 100" + ConsoleColors.RESET);
			} else {
				throw new Invalid(ConsoleColors.YELLOW + "Insuficient Balance" + ConsoleColors.RESET);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void Deposit() throws Invalid {
		String credit = "Credit";
		System.out.print("Enter Amount To Deposite: ");
		double dep = sc.nextDouble();
		try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "root", "@Anilkumar69139");
				PreparedStatement ps = c.prepareStatement("Update accounts set balance = balance+ ? where accno=? ");) {
			ps.setDouble(1, dep);
			ps.setString(2, acno);
			if (dep > 0) {
				ps.executeUpdate();
				saveTransactions(acno, dep, credit);
				System.out.println(ConsoleColors.GREEN + "Deposit Successfull" + ConsoleColors.RESET);
			} else {
				throw new Invalid(ConsoleColors.YELLOW + "Enter Amount More than 0" + ConsoleColors.RESET);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void Balance() {
		try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "root", "@Anilkumar69139");
				PreparedStatement ps = c.prepareStatement("select balance from accounts where accno = ?");) {
			ps.setString(1, acno);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("Your Current Balance " + rs.getDouble("balance"));
			}
			System.out.println();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void Useroperations() {

		try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "root", "@Anilkumar69139");
				PreparedStatement ps = c.prepareStatement("select * from accounts where accno = ? and pin =?");
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery("select * from accounts");) {
			if (rs.next()) {
				System.out.println("<<--------------- USER PAGE ------------------->>");
				System.out.println("Enter your Account no : ");
				String accno = sc.next();
				System.out.println("Enter Pin : ");
				String pin = sc.next();
				ps.setString(1, accno);
				ps.setString(2, pin);
				ResultSet n = ps.executeQuery();
				if (n.next()) {
					acno = accno;
					boolean val = true;
					while (val) {
						System.out.println();
						System.out.println("1) Withdrawl       		[Press 1]");
						System.out.println("2) Deposite        		[Press 2]");
						System.out.println("3) Check Balance  		[Press 3]");
						System.out.println("4) Transaction History 		[Press 4]");
						System.out.println("5) Exit            		[Press 5]");
						System.out.println();
						System.out.println("Select A Number");
						int a = sc.nextInt();

						switch (a) {
						case 1:
							try {
								Withdrawl();
							} catch (Invalid e) {
								e.printStackTrace();
							}
							break;
						case 2:
							try {
								Deposit();
							} catch (Invalid e) {
								e.printStackTrace();
							}
							break;
						case 3:
							Balance();
							break;
						case 4:
							viewTransaction();
							break;
						case 5:
							val = false;
							break;
						}

					}

				} else {
					System.out.println();
					System.out
							.println(ConsoleColors.YELLOW + "USER NOT FOUND OR INVALID DETAILS" + ConsoleColors.RESET);
					System.out.println();
				}

			} else {
				System.out.println("No Account Exist \nCreate Account First");
				System.out.println();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

}