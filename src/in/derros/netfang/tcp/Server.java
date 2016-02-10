package in.derros.netfang.tcp;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

//backlog shall be 8192
public class Server extends Thread {
	
	private ServerSocket serverSocket;
	private int backlog = 8192;
	public InetAddress ip;
	public int port;
	public boolean indicator;
	public boolean appIndicator;
	public String statusListener;
	public String handoff;
	public byte[] handin;
	public void runServer(int i) {
		
	}
		
	private void startServer() throws IOException {
		while(indicator == true) {
			try {
				Socket server = serverSocket.accept();
				serverSocket = new ServerSocket(port, backlog);
				serverSocket.setReuseAddress(true);
				serverSocket.setSoTimeout(2500);
				serverSocket.setPerformancePreferences(1, 2, 0);
				serverSocket.setReceiveBufferSize(128);
				while(appIndicator == true) {
					DataInputStream dataIn = new DataInputStream(server.getInputStream());
					handoff = dataIn.readUTF();
					BufferedReader out = new BufferedReader(server.getOutputStream());
					out.writeUTF(handin);
				}
				server.close();
				indicator = false;
			} catch(SocketTimeoutException s ) {
				indicator = false;
				statusListener = "[startServer] Socket timed out \n";
			} catch(Exception e) {
				indicator = false;
				statusListener = "[startServer] Unknown error \n";
			}
		}
	}

}
