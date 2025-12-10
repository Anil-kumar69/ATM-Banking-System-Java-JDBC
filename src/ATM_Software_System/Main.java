package ATM_Software_System;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Admin admin = new Admin();
		User user = new User();
		boolean val = true;
		System.out.println("                  "+ ConsoleColors.BLUE + "Welcome to the ATM System!" + ConsoleColors.RESET +"         ");
		System.out.println();
		while (val) {
			System.out.println(ConsoleColors.BLUE + "<< ---------------        HOME PAGE      ------------------- >>" + ConsoleColors.RESET);
			System.out.println();
			System.out.println("1) Admin Login     [Press 1]");
			System.out.println("2) User Login      [Press 2]");
			System.out.println("3) Exit            [Press 3]");
			System.out.println();
			System.out.print("Select a number: ");
			int a = sc.nextInt(); 
			System.out.println();
			switch (a) {
			case 1:
				try {
					admin.admin_operations();
				}catch(Invalid e) {
					e.printStackTrace();
				}
				break;
			case 2:
				user.Useroperations();
				break;
			case 3:
				System.out.println(ConsoleColors.GREEN + "<< ---------------        Thank You      ------------------- >>" + ConsoleColors.RESET);
				val = false;
				break;
			}
		}
		sc.close();
	}

}
