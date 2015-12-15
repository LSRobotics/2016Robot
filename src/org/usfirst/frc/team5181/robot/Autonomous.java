package org.usfirst.frc.team5181.robot;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.io.*;
import edu.wpi.first.wpilibj.Timer;

public class Autonomous {
	private static Timer t;
	private String recordingFile; //for actionPlayback
	
	public Autonomous(String autonName) {
		t = new Timer();
		
		//choose auton
		if(autonName.equalsIgnoreCase("ActionPlayback")) {
			recordingFile = "";
			actionPlayback();
		}
	}
	
	private void actionPlayback() {
		try {
			//read commands from file
			List<String> commands = new ArrayList<String>();
			BufferedReader fileReader = new BufferedReader(new FileReader(new File(recordingFile)));
			
			String line = "";
			while((line = fileReader.readLine()) != null) {
				commands.add(line);
			}
			
			//parse commands and execute
			StringTokenizer tokenizer;
			String button = "";
			String magnitude = "";
			int time = 0;
			
			//Start timer 
			t.start();
			
			Iterator<String> i = commands.iterator();
			while(i.hasNext()) {
				tokenizer = new StringTokenizer(i.next(), ":");
				button = tokenizer.nextToken().trim();
				magnitude = tokenizer.nextToken().trim();
				time = Integer.parseInt(tokenizer.nextToken());
				
				//check time
				//TODO
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
