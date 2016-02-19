package org.usfirst.frc.team5181.robot;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class SimpleClient extends Thread {
	
	public volatile double centerX;
	public volatile double centerY;
	private int port = 5805;
	private Socket socket;
	private InetAddress ip;
	
	public SimpleClient() {
		try {
			ip = InetAddress.getByName("192.168.1.174");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.start();
	}
	
	public void run() {
		try {
			socket = new Socket(ip, port);
			DataInputStream dIn = new DataInputStream(socket.getInputStream());
			while (true) {
				byte[] packet = new byte[16];
				dIn.read(packet, 0, 16);
				ByteBuffer buffer = ByteBuffer.wrap(packet);
				centerX = buffer.getDouble(0);
				centerY = buffer.getDouble(1);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
