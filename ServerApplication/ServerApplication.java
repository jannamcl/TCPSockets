/*
* ServerApplication.java
* @author - Janna McLaughlin
* 
* This is a simple application that runs a TCP Network Server.
* It accepts a connection from a Client and allows the Client to send
* the following byte data through a socket:
* Version (1 byte), Message Type (2 byte integer), User ID (4 byte integer), Payload (variable length ASCII string).
* The server then prints out this data.
* 
* I completed this code in one afternoon. In more time, this could turn into a game server.
* Things to add for a game server include, but are not limited to, the following:
* Threads 
* - multi-threading the server would allow for multiple clients to connect at once, send data, and to print that data.
* 		it could also allow the server to send/receive data at the same time, all while keeping a player engaged in a running game
* 		The server must also update game states, and should not always have to worry about receiving client data. It should be able to
* 		update without lag and then send off any updates to clients.
* - multi-threading the client would allow for sending and receiving data at the same time and client-side updates of the game state
*/

package ServerApplication;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ServerApplication {
	private ServerSocket ss;
	private int port;

	public ServerApplication(int port) {
		try {
			this.ss = new ServerSocket(port); //accepts connections from client
			this.port = port;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void acceptData() {
		Socket sock = null;
		System.out.println("\nWaiting for client to connect on port " + port);
		try {
			sock = ss.accept(); //blocking wait
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Woohoo! Someone connected from " + sock.getInetAddress());

		System.out.println("Waiting for message");
		try {
			InputStream in = sock.getInputStream();	//socket input stream
			ByteBuffer msg = ByteBuffer.allocate(7); //allocating space for msg other than payload
			byte[] msgArray = msg.array(); //good for reading in data with byte size 7
	
			in.read(msgArray, 0, 1); //read in version data
			in.read(msgArray, 1, 2); //read in message type data 
			in.read(msgArray, 3, 4); //read in userID data
			
			msg.put(msgArray); //put data into ByteBuffer - good for printing
					
			int stringLength = in.available(); //get the size of the payload
			byte[] stringBytes = new byte[stringLength];
			
			int index = 0;
			while(in.available() > 0) {	//read in bytes from payload
				in.read(stringBytes, index, 1);
				index++;
			}

			//print Message!
			System.out.println("Version: " + msg.get(0) + "\nMessage Type: " + msg.getShort(1)
					+ "\nUserID: " + msg.getInt(3) + "\nPayload: " + new String(stringBytes));
			
			in.close(); //close input stream
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			sock.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		ServerApplication serv = new ServerApplication(1337);//listens on port leet!
		while(true) {
			try {
				serv.acceptData();
				} catch (Exception e) {
            		e.printStackTrace();
       		 }
        }
	}
}