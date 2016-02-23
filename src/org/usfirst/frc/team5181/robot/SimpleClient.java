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
			ip = InetAddress.getByName("10.51.81.84");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			DriverStation.reportError("Error in constructor", false);
		}
		this.start();
	}
	
	public void run() {
		try {
			DriverStation.reportError("Here before connect", false);
			socket = new Socket(ip, port);
			DriverStation.reportError("Here after Connect", false);
			DataInputStream dIn = new DataInputStream(socket.getInputStream());
			while (true) {
				//byte[] packet = new byte[8];
				//dIn.read(packet);
				//ByteBuffer buffer = ByteBuffer.wrap(packet);
				//centerX = buffer.getInt(0);
				//centerY = buffer.getInt(1);
				String point = dIn.readUTF();
				//String xString = point.substring(1, point.indexOf(" ,"));
				//String yString = point.substring(point.indexOf(" ,") + 1, point.length() - 1);
				String[] coords = point.split(" ");
				centerX = Integer.parseInt(coords[0]);
				centerY = Integer.parseInt(coords[1]);
				//DriverStation.reportError("." + centerX + "..." + centerY, false);
				//DriverStation.reportError(xString + " " + yString, false);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			DriverStation.reportError("Error in run", false);
		}
	}
}
