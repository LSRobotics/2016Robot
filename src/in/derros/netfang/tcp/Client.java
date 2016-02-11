package in.derros.netfang.tcp;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Client extends Thread {
	public boolean indicator;
	public String statusListener;
	public InetAddress host;
	public int port;
	public InetAddress localAddress;
	public int localPort;
	public byte[] handin;
	public byte[] handout;
	
	public void runClient(int i, byte[] sendingPacket) {
		handin = sendingPacket;
	}
	
	private void startClient() {
		try {
			host = InetAddress.getByName("");
			Socket socket = new Socket(host, port, localAddress, localPort);
			//OutputStream out = socket.getOutputStream();
			//DataOutputStream dout = new DataOutputStream(out);
			//dout.writeByte(handout);
			
		} catch(Exception e) {
			indicator = false;
			statusListener = "[startClient] Failed to start client";
		}
	}
}
