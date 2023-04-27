# Simple-Chat-Project
College Assignment to create a simple network based chat application using the Java Socket API.

Submitted January 2023.

## Project Requirements

Your task is to demonstrate your understanding of networking by designing and
implementing a network-based chat application in Java, using the Java Socket API.
Chat Application Functionality:
- Server starts up and waits for socket connections on a specific port. `8888`
- Client starts up and attempts to create a socket connection to the server.
- If a connection is successfully formed, then the client and server should be able
to facilitate a text-based chat session between users at the client and server
side, i.e.
1. Client and server should allow the user to enter text at the console
(command line).
2. Messages entered by the user should be sent across the socket
connection to the other application and displayed at the console.
- Users should be able to gracefully end the chat session and close the
connection by entering “\q”.
- The client program – ChatClient – that implements the client side
functionality. [ChatClient.java](https://github.com/RichDaly/Simple_Chat_Project/blob/main/src/ie/atu/sw/ChatClient.java)
- The Server program – ChatServer – that facilitates the server-side
functionality. [ChatServer.java](https://github.com/RichDaly/Simple_Chat_Project/blob/main/src/ie/atu/sw/ChatServer.java)
- A brief (1-2 page max.) design document that outlines the design / rationale for
your programs, and references to any external sources consulted. [DesignDocument.pdf](https://github.com/RichDaly/Simple_Chat_Project/blob/main/DesignDocument.pdf)

## Running Application

There are two ways of running the application through the CLI. Once the ChatServer is running, it will be able to receive multiple ChatClient connections and facilitate a text-based chat session between all users.

1. Through the jar file by using the following command within the stored directory:
`java -cp ./chatApp.jar ie.atu.sw.Runner`
This will launch the application through a simple menu command line interface. Can choose
to run either the Server or the Client side of the application.

2. Both ChatServer.java and ChatClient.java have main methods and can be run by compiling
the source code of both. From within the src folder use the following `javac ie.atu.sw.*.java` and then `java ie/atu/sw/ChatClient` or `java ie/atu/sw/ChatServer` depending on the part of the application you wish to run.

**NB** ChatClient.java will expect a command line argument of the server IP. This will be
displayed by ChatServer.java once running.
Example: `java ie.atu.sw.ChatClient 193.178.8.125`
