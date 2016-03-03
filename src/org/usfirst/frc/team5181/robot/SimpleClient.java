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
	public volatile boolean rockWall;
	private int trackerPort = 5805;
	private int neuralPort = 5803;
	private Socket socket;
	private InetAddress ip;
	
	public SimpleClient(boolean neuralNet) {
		try {
			ip = InetAddress.getByName("10.51.81.84");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			DriverStation.reportError("Error in constructor", false);
		}
		if (neuralNet) {
			rockWall = getNeuralNetResponse();
		}
		else {
			this.start();
		}
	}
	
	private boolean getNeuralNetResponse() {
		
		return false;
	}

	public void run() {
		try {
			DriverStation.reportError("Here before connect", false);
			socket = new Socket(ip,trackerPort);
			DriverStation.reportError("Here after Connect", false);
			DataInputStream dIn = new DataInputStream(socket.getInputStream());
			while (true) {
				String point = dIn.readUTF();
				String[] coords = point.split(" ");
				centerX = Integer.parseInt(coords[0]);
				centerY = Integer.parseInt(coords[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
			DriverStation.reportError("Error in run", false);
		}
	}
}
