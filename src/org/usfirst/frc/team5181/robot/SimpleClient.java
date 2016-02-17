package org.usfirst.frc.team5181.robot;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class SimpleClient extends Thread {
	
	public volatile double centerX;
	public volatile double centerY;
	private int port;
	private Socket socket;
	private SocketAddress ip;
	
	public SimpleClient() {
		socket = new Socket();
		this.start();
	}
	
	public void run() {
		try {
			socket.connect(ip, 12000);
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
