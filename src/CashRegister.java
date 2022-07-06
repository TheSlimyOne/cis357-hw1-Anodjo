/* 
 * Homework 1: Sales Register Program
 * Course: CIS357
 * Due date: 7/5/2022
 * Name: Jordan Anodjo
 * GitHub: https://github.com/TheSlimyOne/cis357-hw1-Anodjo.git
 * Instructor: Il-Hyung Cho
 * Program description: 
 * 		In this program, a user will interact with an emulated cash register.
 * 		The user will be able to purchase items from a predetermined list.
 * 		The cash register will respond appropriately from the user. 
 * 		Always producing the correct results/response from the user.
*/

import java.util.Scanner;

/**
 * The main class, the driver of program.
 */
public class CashRegister {

	public static void main(String[] args) throws java.io.FileNotFoundException {

		// Filling up the stock of the store with a file
		Receptacle stock = new Receptacle(
				"C:\\Users\\xcree\\OneDrive\\Desktop\\School Folder\\CIS 357\\cis357-hw1-Anodjo\\input.txt");

		// Declaration of Scanner
		Scanner input = new Scanner(System.in);

		// Variable used to record all transactions;
		float grandTotal = 0;

		// Greet the user.
		System.out.println("Welcome to Anodjo cash register system!");

		// The main session loop to continuously prompt the user.
		session: while (true) {

			// Loop the selection to start shopping or end program now.
			if (!isShopping(input)) {
				input.close();
				System.out.printf("The total sale for the day is%3s%7.2f\n", "$", grandTotal);
				System.out.println("Thanks for using POST system. Goodbye.");
				break session;
			}

			// Create the user's shopping cart
			Receptacle shoppingCart = new Receptacle();

			// Continuously prompt the user to select which items and
			// they want to purchase and how many of said items.
			shopping: while (true) {

				System.out.print("Enter product code: ");
				String userInput = input.next(); // Record user's input for later use.

				// Check if the user's input is valid
				if (isInt(userInput)) {
					int userInt = Integer.parseInt(userInput);

					// Also check if the input falls within the range of items available.
					if (userInt > 0 && userInt <= stock.getLength()) {

						// Copy the them from the stock
						Item item = stock.copyItem(userInt - 1);

						// If the item doesnt already exist
						// Then add it into the shopping cart
						if (!shoppingCart.exist(item))
							shoppingCart.addItem(item);
						else
							// If the item already exists in the shopping cart
							// reference that item so user can increase amount they have.
							item = shoppingCart.getItem(shoppingCart.getIndex(item));

						System.out.printf("%20s%s\n", "Item name: ", item.getName());

						// Prompt the amount of items the user wants to purchase.
						promptQuantity(item, shoppingCart, input);

					} else if (userInt == -1) {
						// Negative 1 is the key to continue to the next phase of the program.
						// Prompt the user with what they will be paying.
						grandTotal += promtEndTransaction(shoppingCart, input);
						break shopping;

					} else
						System.out.println("!!! Invalid product code\n");

				} else
					System.out.println("!!! Invalid product code\n");
			}
		}

		// Exit program.
		System.exit(0);
	}

	/**
	 * Description: Checks if the input can be converted to an Interger.
	 *
	 * @param input Desired string to see if it can be converted to an Integer.
	 * @return A boolean value that is determined by if the String can be converted
	 *         to an Integer.
	 */
	public static boolean isInt(String input) {

		try {
			Integer.parseInt(input);

		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	/**
	 * Description: Checks if the input can be converted to an Double.
	 *
	 * @param input Desired string to see if it can be converted to an Integer.
	 * @return A boolean value that is determined by if the String can be converted
	 *         to a Double.
	 */
	public static boolean isDouble(String input) {
		try {
			Double.parseDouble(input);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	/**
	 * Description: Starts a session in where the user must select an apporiate
	 * response. In this session the user must select Y (case insenstive) or N (case
	 * insenstive). If the user sends in an invalid response, the user will be
	 * prompted to select an apporiate response until they have selected such.
	 * 
	 * 
	 * @param input The users input.
	 * @return boolean value if the user selects Y (Yes) or N (no)
	 */
	public static boolean isShopping(Scanner input) {

		// Using an infinite loop to keep the user in the session.
		while (true) {
			System.out.print("Beginning a new sale (Y/N) "); // Prompt user to select (Y/N) Prompt A

			switch (input.next().toUpperCase()) {
				case "Y":
					System.out.println("--------------------");
					return true;
				case "N":
					System.out.println("--------------------");
					return false;
				default:
					// Default: invalid input, prompt user to start again.
					System.out.println("!!! Invalid input");
					break;
			}
		}
	}

	/**
	 * Description: Starts a session in where the user must select an apporiate
	 * response. In this session the user must select a number greater than 0 to add
	 * to the amount of the item they have in their shopping cart.
	 * 
	 * @param item         The Item that will have its quanity amount raised.
	 * @param shoppingCart The Receptacle the item is in.
	 * @param input        The users input.
	 */
	public static void promptQuantity(Item item, Receptacle shoppingCart, Scanner input) {
		// Looping session
		while (true) {
			System.out.printf("%-20s", "Enter quantity: ");
			String userInput = input.next(); // Store the user's response.

			if (isInt(userInput)) {
				int userInt = Integer.parseInt(userInput); // Store the number.
				if (userInt > 0) {
					// Increase the amount of this Item.
					item.addAmount(userInt);
					System.out.printf("%22s%6.2f\n\n", "Item total: $ ", item.getPrice() * userInt);
					break;
				} else
					// 0 or negative number
					System.out.println("!!! Invalid quantity\n");
			} else
				// Input was not a number
				System.out.println("!!! Invalid quantity\n");
		}
	}

	/**
	 * Description: Starts a session in where the user must select an apporiate
	 * response. In this session the user must pay out what they have in the
	 * shopping cart.
	 * 
	 * @param input        The user input.
	 * @param shoppingCart The Receptacle that holds items that is being purchased.
	 * @return subTotal full cost of the current session.
	 */
	public static float promtEndTransaction(Receptacle shoppingCart, Scanner input) {

		// Variable used to store the tax.
		double tax = 0.06;

		// Variable used to store the amount of money needed for this transaction.
		float subTotal = shoppingCart.getTotalCost();

		// Sort list from longest name to sortest name.
		shoppingCart.sort();

		System.out.println("----------------------------");
		System.out.println("Item list:");

		// Print each Item in the shopping cart
		System.out.println(shoppingCart);

		// Session used to keep user in until an appropriate transaction is made.
		while (true) {

			// Calcuate the total cost with tax applied.
			double priceTax = subTotal + (subTotal * tax);

			System.out.printf("Subtotal %12s %6.2f\n", "$", subTotal);

			System.out.printf("Total with Tax (%.0f%%) %s %6.2f\n", tax * 100, "$", priceTax);

			// Format the string the user will be inputting on.
			correctDecimal(priceTax);


			String userInput = input.next(); // Store the user's input.

			// If the user's input is valid.
			if (isDouble(userInput)) {

				// Variable used to store the balance after the user has paid.
				double remainingBalance = Double.parseDouble(userInput) - priceTax;

				// If remaining balance is greater than 0 that means the user has paid in full.
				if (remainingBalance >= 0)
					System.out.printf("Change%15s%7.2f\n", "$", remainingBalance);
				else
					promptRemainingBalance(remainingBalance, input); // Remaning balance the user needs to pay.
				break;

			} else
				// User input was not valid.
				System.out.println("!!! Invalid input\n");
			System.out.println("----------------------------\n");
		}
		System.out.println("----------------------------\n");

		return subTotal;
	}

	public static void correctDecimal(double price){
		
		if (price > 9.99)
			System.out.printf("Tendered amount%6s%2s", "$", " ");
		else
			System.out.printf("Tendered amount%6s%3s", "$", " ");
	}

	/**
	 * Description: Starts a session in where the user must select an apporiate
	 * response. In this session the user must continue to pay till the remaining
	 * balance is less or equal to 0.
	 * 
	 * @param remainingBalance The balance that needs to be paid.
	 * @param input            The user input.
	 */
	public static void promptRemainingBalance(double remainingBalance, Scanner input) {
		while (remainingBalance < 0) {
			System.out.printf("Remaining balance%4s%7.2f\n", "$", Math.abs(remainingBalance));
			correctDecimal(Math.abs(remainingBalance));

			String userInput = input.next();
			if (isDouble(userInput)) {
				remainingBalance -= -Double.parseDouble(userInput);

			} else
				System.out.println("!!! Invalid input");
		}
		System.out.printf("Change %14s%7.2f\n", "$", remainingBalance);

	}
}