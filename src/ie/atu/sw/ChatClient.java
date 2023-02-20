package ie.atu.sw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient implements Runnable {
	private static final int PORT = 8888;
	private boolean keepRunning = true;
	private BufferedReader serverInput;
	private PrintWriter output;
	private String serverAddress;
	private Socket socket;

	public ChatClient(String address) { // IP address of the Server
		this.serverAddress = address;
	}

	@Override
	public void run() {
		try {
			socket = new Socket(serverAddress, PORT); // attempt to connect to server
			output = new PrintWriter(socket.getOutputStream(), true);// auto flushing!
			serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ClientInput input = new ClientInput();
			Thread thread = new Thread(input); // thread to handles client input
			thread.start();

			String message; // listen and read messages coming from server.
			while ((message = serverInput.readLine()) != null) {
				System.out.println(message);
			}

		} catch (Exception e) { // can't connect or server quits
			System.out.println("No Connection");
		}
	}

	/*
	 * Quitting the chat. finish loops, close streams and close socket.
	 */
	private void quitChat() {
		try {
			keepRunning = false;
			serverInput.close();
			output.close();
			socket.close();
		} catch (Exception e) {

		}
	}

	// --------------------------------------------------------------------------------
	private class ClientInput implements Runnable {

		/*
		 * Handles the user input from the device that is acting as the client and also
		 * allows user to input command to quit
		 */
		@Override
		public void run() {
			try (BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in))) {
				while (keepRunning == true) { // continue loop while true
					var message = clientInput.readLine();
					if (message.equals("\\q")) { // quit command
						output.println(message);
						clientInput.close();
						quitChat();
					} else {
						output.println(message);
					}
				}
			} catch (Exception e) {

			}
		}

	}

	public static void main(String[] args) {
		String address = args[0];
		ChatClient client = new ChatClient(address);
		client.run();
	}
}
