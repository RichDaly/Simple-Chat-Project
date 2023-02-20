package ie.atu.sw;

import java.util.Scanner;

public class Menu {
	private boolean keepRunning = true; // keeps menu running until correctly used.
	private ChatServer server; // instance of ChatServer.
	private ChatClient client; // instance of ChatClient.
	private Scanner scanner;

	public Menu() {
		this.scanner = new Scanner(System.in); // Scanner that takes system input.
	}

	/*
	 * Only public method, uses a while loop and a boolean to keep things running
	 * and a switch statement to act upon user choices.
	 */
	public void start() {
		while (keepRunning) {
			try {
				showOptions();
				int choice = Integer.parseInt(scanner.next());
				switch (choice) {
				case 1 -> startServer();
				case 2 -> startClient();
				case 3 -> quit();
				default -> invalidChoice();
				}
			} catch (Exception e) {
				invalidInput();
			}
		}
	}

	/*
	 * Closes the menu and launches ChatServer
	 */
	private void startServer() {
		this.keepRunning = false;
		this.server = new ChatServer();
		server.run();
	}

	/*
	 * Closes the menu and launches ChatClient, asks for IP address required.
	 */
	private void startClient() {
		this.keepRunning = false;
		System.out.println("Enter Server IP Address>>");
		String address = scanner.next();
		this.client = new ChatClient(address);
		client.run();
	}

	/*
	 * Simple User Interface
	 */
	private void showOptions() {
		System.out.println("************************************************************");
		System.out.println("*       ATU - Dept. Computer Science & Applied Physics     *");
		System.out.println("*                                                          *");
		System.out.println("*               Simple Server & Client Chat                *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("Allows Multiple chat clients to send messages over one chat");
		System.out.println("server. Chat Server is a host and a Chat Client can connect");
		System.out.println("to an established server via IP Address.\n");
		System.out.println("(1) Launch as Chat Server");
		System.out.println("(2) Join as a Chat Client");
		System.out.println("(3) Quit\n");
		System.out.print("Select Option [1-3]>");
		System.out.println();
	}

	/*
	 * Closes the menu
	 */
	private void quit() {
		System.out.println("[INFO] Shutting down");
		keepRunning = false;
	}

	/*
	 * Catches option number inputs from user
	 */
	private void invalidChoice() {
		System.out.println("[ERROR] Invalid Option: try again");
		System.out.println();
	}

	/*
	 * Catches invalid input from user
	 */
	private void invalidInput() {
		System.out.println("[ERROR] Invalid Input, try again");
		System.out.println();
	}
}
