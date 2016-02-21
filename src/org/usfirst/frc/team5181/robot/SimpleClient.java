package org.usfirst.frc.team5181.robot;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.DriverStation;

public class SimpleClient extends Thread {
	
	public volatile int centerX;
	public volatile int centerY;
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
				//byte[] packet = new byte[8];
				//dIn.read(packet);
				//ByteBuffer buffer = ByteBuffer.wrap(packet);
				//centerX = buffer.getInt(0);
				//centerY = buffer.getInt(1);
				String point = dIn.readUTF();
				String xString = point.substring(1, point.indexOf(','));
				String yString = point.substring(point.indexOf(',') + 1, point.length() - 1);
				centerX = Integer.parseInt(xString);
				centerY = Integer.parseInt(yString);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
