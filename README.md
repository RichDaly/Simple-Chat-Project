# Simple-Chat-Project
College Assignment to create a simple network based chat application using the Java Socket API.


Submitted January 2023.


## Running Application

There are two ways of running the application

1. Through the jar file by using the following command within the submitted folder:
`java -cp ./chatApp.jar ie.atu.sw.Runner`
This will launch the application through a simple menu command line interface. Can choose
to run either the Server or the Client side of the application.

2. Both ChatServer.java and ChatClient.java have main methods and can be run by compiling
the source code of both.

NB ChatClient.java will expect a command line argument of the server IP. This will be
displayed by ChatServer.java once running.
Example: `java ie.atu.sw.ChatClient 192.168.8.115`
