package org.usfirst.frc.team5181.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * 
 * @author Connor Devitt | Mark Bense | Alex Fang
 *
 **/

public class Bear extends Thread {
	ArrayList<String> koala = new ArrayList<String>(); //bear
	public void Bear() {
			koala.add("sssssssssssssssssssssssssssssssssyhhhhdhyysyNyssssssssssssssoooooo++++++++++++++");  
			koala.add("ssssssssssssssssssssssssssssyddhyssooooooooohdssssssssssssssssssssssssssssssssss");
			koala.add("sssssssssssssssssssssssssssssNoooooooooooooosNsssssssyyyssssssssssssssssssssssss");
			koala.add("sssssssssssssssssssssssssssssmsooooosssyhhddmNdhhyyyhddhdhysssssssssssssssssssss"); 
			koala.add("sssssssssssssssyyysssssssssymhyhhdmmNNNmdhyyssssshNysshsymssssssssssssssssssssss"); 
			koala.add("sssssssssssssydhhhhhdyssyhhhdddyysssoooosyhddddhddhyssddssmhssssssssssssssssssss"); 
			koala.add("sssssssssssssmysssssshmdssoooosyhdddhhhhhyssssssssssssysssmhssssssssssssssssssss"); 
			koala.add("ssssssssssssydsssdmsssydhddddddhysssssssssssssssssssssssssyNysssssssssssssssssss"); 
			koala.add("sssssssssssssmyssshdssssssssssssssssssssssssssssssssssssssshmsssssssssssssssssss"); 
			koala.add("sssssssssssssymsssssssssssssssssssssssssssssssssssssssssssssmyssssssssssssssssss"); 
			koala.add("ssssssssssssshdsssssssssssssssssssssssssssyhhysssssssssssssshdssssssssssssssssss"); 
			koala.add("sssssssssssssdyssssssssssssssssssssssssshmo//smysssssssssssssNysssssssssssssssss"); 
			koala.add("sssssssssssssmssssssssssssssshdyyyhdyssyN-````:Nhssssssssssssymsssssssssssssssss");
			koala.add("sssssssssssssNsssssssssssssshh-.:../NyshN/:``:+mdsssssssssssssmyssssssssssssssss");
			koala.add("ssssssssssssymssssssssssssssmo`./`./mNNMMMNso/oNysssssssssssssdhssssssssssssssss"); 
			koala.add("sssssssssssshdsssssssssssssshh```-ho:MMMMMN.:yNyssssssssssssssymssssssssssssssss");
			koala.add("ssssssssssssmysssssssssssssssmy//d+``hmNmh:``.hdsssssssssssssssdhsssssssssssssss"); 
			koala.add("ssssssssssssNsssssssssssssssssyyN+```..--.````-NysssssssssssssssNsssssssssssssss"); 
			koala.add("sssssssssssymsssssssssssssssssssM-`````````````dhsssssssssssssssdhssssssssssssss"); 
			koala.add("ssssssssssshdsssssssssssssssssssN:```+ho:--/-.mhsssssssssssssssymsssssssssssssss"); 
			koala.add("sssssssssssdhsssssssssssssssssssdy`````-/oosy:`+Nsssssssssssssssssmyssssssssssss"); 
			koala.add("sssssssssssmyssssssssssssssssssssms:.--```.-:sdysssssssssssssssssdhsssssssssssss"); 
			koala.add("sssssssssssmssssssssssssssssssssssyhyssosyhhhyssssssssssssssssssshdsssssssssssss");
	}
	
	/**
	 * Does bear koala, or does bear bear?
	 */
	public void run() {
		for(String s: koala) {
			DriverStation.reportError(s + "\n", false);
		}
		DriverStation.reportError("3.1415926535 8979323846 2643383279 5028841971 6939937510\n", false);
		DriverStation.reportError("  5820974944 5923078164 0628620899 8628034825 3421170679\n", false);
	}
	public void start() {
		this.start();
	}
}
