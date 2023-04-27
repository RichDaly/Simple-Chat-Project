package ie.atu.sw;

import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.time.format.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is Class serves as the server the chat application. It will open on port 8888 and actively
 * listen for clients attempting to connect. This is server is multithreaded and can accept multiple
 * clients and passes messages between them all. The server is also capable of interacting in the chat.
 * 
 * Comments throughout class are as submitted during course for the purpose of explianing actions.
 *
 * @author Richard Daly
 */

public class ChatServer implements Runnable {
	private ArrayList<Client> connectedClients = new ArrayList<>(); // the multiple clients
	private static final int PORT = 8888;
	private Scanner scanner = new Scanner(System.in);
	private String serverName; // server name for chat purposes
	private boolean keepRunning = true; // keeps server in operation
	private ExecutorService pool; // instance variable so can be used throughout class
	private ServerSocket server; // instance variable so can be used throughout class

	public void run() {
		try {
			System.out.println("Please enter a Name for the Server>>");
			this.serverName = scanner.next();// sets a name for the server for chat
			server = new ServerSocket(PORT);
			System.out.println("Chat Server listening for connections.");
			System.out.println("Server Address: " + getServerAddress() + "\n"); // IP address for clients
			pool = Executors.newCachedThreadPool();// increases threads based on use.

			while (keepRunning == true) { // loop to keep server watching connections.
				Socket connection = server.accept();
				Client theNewClient = new Client(connection);
				connectedClients.add(theNewClient); // add to the array list
				pool.execute(theNewClient); // a thread for the new client
				ServerInput serverInput = new ServerInput();
				pool.execute(serverInput); // so the server can chat as well

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Had difficulty getting my own IP address due to multiple network cards. Ended
	 * up constantly getting 0.0.0.0 Found the following solution to distinguish
	 * which one was used. https://www.baeldung.com/java-get-ip-address
	 */
	private String getServerAddress() throws IOException {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress("google.com", 80));
			return socket.getLocalAddress().getHostAddress();
		}
	}

	/*
	 * Sends messages from the server to all connected clients with the server name
	 * and time sent.
	 */
	private void sendServerMessage(String message) {
		var time = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(LocalTime.now());
		for (Client client : connectedClients) {
			client.sendMessage("Server " + serverName + ", " + time + " >> " + message);
		}
	}

	/*
	 * Distributes the messages from clients to all other clients and also prints to
	 * server console. in use below
	 */
	private void sendToAll(String message) {
		System.out.println(message);
		for (Client client : connectedClients) {
			client.sendMessage(message);
		}
	}

	/*
	 * Shuts down the server and lets any connected clients know its not in
	 * operation anymore.
	 */
	private void quitServer() throws IOException {
		sendServerMessage("Server has Shut Down, please quit the application with \\q");
		keepRunning = false;
		pool.shutdownNow();
		System.exit(0);
	}

	// ---------------------------------------------------------------
	private class ServerInput implements Runnable {
		private BufferedReader serverInput;

		/*
		 * Handles the user input from the device that is acting as the server and also
		 * allows user to input command to quit
		 */
		@Override
		public void run() {
			try {
				while (keepRunning == true) { // continue loop while true
					serverInput = new BufferedReader(new InputStreamReader(System.in));
					var message = serverInput.readLine();
					if (message.equals("\\q")) { // quit command
						quitServer();
					} else {
						sendServerMessage(message);
					}
				}
			} catch (Exception e) {

			}
		}

	}

	// ----------------------------------------------------------------
	private class Client implements Runnable {
		private BufferedReader input;
		private PrintWriter output;
		private String clientName;
		private Socket socket;

		public Client(Socket s) {
			this.socket = s;
		}

		/*
		 * Handles the connection of a new client. sets up the input and output streams.
		 * Gets the client to enter a name for the purposes of the chat and informs any
		 * other clients and the server they have connected. Continues to listen for any
		 * client input and sends the message.
		 */
		@Override
		public void run() {
			try {
				output = new PrintWriter(socket.getOutputStream(), true);// auto flushing!
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				output.println("Please enter a name to start >>>");
				clientName = input.readLine(); // client name for chat purposes
				System.out.println("New Connection: " + clientName);
				sendToAll("[INFO]: " + clientName + " has joined the chat!"); // inform all they have connected.

				String inputMessage;
				while ((inputMessage = input.readLine()) != null) { // Listens for incoming client messages
					if (inputMessage.equals("\\q")) { // if the user quits and lets all know
						sendToAll("[INFO]: " + clientName + ": has left the chat!");
						System.out.println("Client: " + clientName + " has diconnected.");
						clientQuit();
					} else { // if not quitting relay messages with time received
						var time = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(LocalTime.now());
						sendToAll(clientName + ", " + time + " >> " + inputMessage);
					}
				}

			} catch (Exception e) {

			}

		}

		// sends messages to client in output stream
		private void sendMessage(String message) {
			output.println(message);
		}

		// close the streams and the socket when client quits
		private void clientQuit() throws IOException {
			input.close();
			output.close();
			socket.close();
		}

	}

	public static void main(String[] args) {
		ChatServer server = new ChatServer();
		server.run();
	}
}
