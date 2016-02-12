package org.usfirst.frc.team5181.robot;

import edu.wpi.first.wpilibj.DriverStation;

public class Bear extends Thread {
	String[] koala = new String[23]; //bear
	public void Bear() {
		String[] temp = {"sssssssssssssssssssssssssssssssssyhhhhdhyysyNyssssssssssssssoooooo++++++++++++++" + "\n",  
				"ssssssssssssssssssssssssssssyddhyssooooooooohdssssssssssssssssssssssssssssssssss" + "\n" , 
				"sssssssssssssssssssssssssssssNoooooooooooooosNsssssssyyyssssssssssssssssssssssss" + "\n" , 
				"sssssssssssssssssssssssssssssmsooooosssyhhddmNdhhyyyhddhdhysssssssssssssssssssss" + "\n" , 
				"sssssssssssssssyyysssssssssymhyhhdmmNNNmdhyyssssshNysshsymssssssssssssssssssssss" + "\n" , 
				"sssssssssssssydhhhhhdyssyhhhdddyysssoooosyhddddhddhyssddssmhssssssssssssssssssss" + "\n" , 
				"sssssssssssssmysssssshmdssoooosyhdddhhhhhyssssssssssssysssmhssssssssssssssssssss" + "\n" , 
				"ssssssssssssydsssdmsssydhddddddhysssssssssssssssssssssssssyNysssssssssssssssssss" + "\n" , 
				"sssssssssssssmyssshdssssssssssssssssssssssssssssssssssssssshmsssssssssssssssssss" + "\n" , 
				"sssssssssssssymsssssssssssssssssssssssssssssssssssssssssssssmyssssssssssssssssss" + "\n" , 
				"ssssssssssssshdsssssssssssssssssssssssssssyhhysssssssssssssshdssssssssssssssssss" + "\n" , 
				"sssssssssssssdyssssssssssssssssssssssssshmo//smysssssssssssssNysssssssssssssssss" + "\n" , 
				"sssssssssssssmssssssssssssssshdyyyhdyssyN-````:Nhssssssssssssymsssssssssssssssss" + "\n" ,
				"sssssssssssssNsssssssssssssshh-.:../NyshN/:``:+mdsssssssssssssmyssssssssssssssss" + "\n" ,
				"ssssssssssssymssssssssssssssmo`./`./mNNMMMNso/oNysssssssssssssdhssssssssssssssss" + "\n" , 
				"sssssssssssshdsssssssssssssshh```-ho:MMMMMN.:yNyssssssssssssssymssssssssssssssss" + "\n" ,
				"ssssssssssssmysssssssssssssssmy//d+``hmNmh:``.hdsssssssssssssssdhsssssssssssssss" + "\n" , 
				"ssssssssssssNsssssssssssssssssyyN+```..--.````-NysssssssssssssssNsssssssssssssss" + "\n" , 
				"sssssssssssymsssssssssssssssssssM-`````````````dhsssssssssssssssdhssssssssssssss" + "\n" , 
				"ssssssssssshdsssssssssssssssssssN:````-/oosy:`.mhsssssssssssssssymssssssssssssss" + "\n" , 
				"sssssssssssdhsssssssssssssssssssdy```+ho:--/-`+Nsssssssssssssssssmysssssssssssss" + "\n" , 
				"sssssssssssmyssssssssssssssssssssms:.--```.-:sdysssssssssssssssssdhsssssssssssss" + "\n" , 
				"sssssssssssmssssssssssssssssssssssyhyssosyhhhyssssssssssssssssssshdsssssssssssss" };
		koala = temp;
	}
	
	/**
	 * Does bear koala, or does bear bear?
	 */
	public void run() {
		for(int i = 0; i < 1/*koala.length*/; i++) {
			DriverStation.reportError(koala[12], false);
		}
	}
	public void start() {
		this.start();
	}
}
