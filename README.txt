
/*
* ServerApplication.java, ClientApplication.java
* @author - Janna McLaughlin
* 
* This is a simple application that runs a TCP Network Server.
* It accepts a connection from a Client and allows the Client to send
* the following byte data through a socket:
* Version (1 byte), Message Type (2 byte integer), User ID (4 byte integer), Payload (variable length ASCII string).
* The server then prints out this data.
* 
* If given more time than 24 hours, this could turn into a better game server.
* Things to add include:
* Threads 
* - multi-threading the server would allow for multiple clients to connect at once, send data, and to print that data.
* 		it could also allow the server to send/receive data at the same time, all while keeping a player engaged in a running game
* 		The server must also update game states, and should not always have to worry about receiving client data. It should be able to
* 		update without lag and then send off any updates to clients.
* - multi-threading the client would allow for sending and receiving data at the same time and client-side updates of the game state
* I would also include an elegant way to stop the server.
*/

To run the program:

Run ServerApplication.java first, then run ClientApplication.
The client application should connect to the server.
In ClientApplication, you will be prompted for an ASCII String. 
After entering a string, the client application connects to the server,
sending data through the socket. The server then reads the data and prints.
The server then continues to prompt.



//Janna McLaughlin