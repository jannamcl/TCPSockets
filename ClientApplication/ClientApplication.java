/*
* ClientApplication.java
* @author - Janna McLaughlin
* 
* A simple client application meant to connect to the server created by ServerApplication.java
*/

package ClientApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ClientApplication {
	private static Socket sock;

	private static void connect(String hostname, int port) {
		try {
			sock = new Socket(hostname, port);//hostname can be url or ip
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void closeSock() {
		try {
			sock.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void send(byte[] msg) {	//sends byte data through the socket
		connect("localhost", 1337); // blocking wait
		System.out.println("Connected to " + sock.getInetAddress());
		try{
			OutputStream osw = sock.getOutputStream(); //get the socket's output stream	
			osw.write(msg); //write and send data through socket
			osw.flush();	//clear output stream
			osw.close();	//close output stream
			System.out.println("Transmission successful. Closing connection.");
		} catch (IOException io){
			io.printStackTrace();
		}
		closeSock();
	}
	
	public static void main(String[] args) {
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			try{
			
			//Prompts the client for a simple string to send to the server. This is the payload
			System.out.println("Please input a message:");
			String out = console.readLine();
			byte[] stringMsg = out.getBytes(); //payload of variable length
			
			byte version = 1; 		//arbitrary version number as one byte
			short msgType = 144;	//arbitrary message type as two bytes
			int userID = 1337;		//arbitrary userID as four bytes

			ByteBuffer msg = ByteBuffer.allocate(7 + stringMsg.length); //allocate all bytes  
			//put data into ByteBuffer
			msg.put(version);
			msg.putShort(msgType);
			msg.putInt(userID);
			msg.put(stringMsg);

			System.out.println("Sending message to server.");
			send(msg.array());	//send raw byte data
			} catch (IOException io){
				io.printStackTrace();
			}	
		}
	}
}