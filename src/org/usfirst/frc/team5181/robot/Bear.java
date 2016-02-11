package org.usfirst.frc.team5181.robot;

import edu.wpi.first.wpilibj.DriverStation;

public class Bear {
	String koala; //bear
	public void Bear() {
		koala = "sssssssssssssssssssssssssssssssssyhhhhdhyysyNyssssssssssssssoooooo++++++++++++++" + "\n" +  
				"ssssssssssssssssssssssssssssyddhyssooooooooohdssssssssssssssssssssssssssssssssss" + "\n" + 
				"sssssssssssssssssssssssssssssNoooooooooooooosNsssssssyyyssssssssssssssssssssssss" + "\n" + 
				"sssssssssssssssssssssssssssssmsooooosssyhhddmNdhhyyyhddhdhysssssssssssssssssssss" + "\n" + 
				"sssssssssssssssyyysssssssssymhyhhdmmNNNmdhyyssssshNysshsymssssssssssssssssssssss" + "\n" + 
				"sssssssssssssydhhhhhdyssyhhhdddyysssoooosyhddddhddhyssddssmhssssssssssssssssssss" + "\n" + 
				"sssssssssssssmysssssshmdssoooosyhdddhhhhhyssssssssssssysssmhssssssssssssssssssss" + "\n" + 
				"ssssssssssssydsssdmsssydhddddddhysssssssssssssssssssssssssyNysssssssssssssssssss" + "\n" + 
				"sssssssssssssmyssshdssssssssssssssssssssssssssssssssssssssshmsssssssssssssssssss" + "\n" + 
				"sssssssssssssymsssssssssssssssssssssssssssssssssssssssssssssmyssssssssssssssssss" + "\n" + 
				"ssssssssssssshdsssssssssssssssssssssssssssyhhysssssssssssssshdssssssssssssssssss" + "\n" + 
				"sssssssssssssdyssssssssssssssssssssssssshmo//smysssssssssssssNysssssssssssssssss" + "\n" + 
				"sssssssssssssmssssssssssssssshdyyyhdyssyN-````:Nhssssssssssssymsssssssssssssssss" + "\n" +
				"sssssssssssssNsssssssssssssshh-.:../NyshN/:``:+mdsssssssssssssmyssssssssssssssss" + "\n" +
				"ssssssssssssymssssssssssssssmo`./`./mNNMMMNso/oNysssssssssssssdhssssssssssssssss" + "\n" + 
				"sssssssssssshdsssssssssssssshh```-ho:MMMMMN.:yNyssssssssssssssymssssssssssssssss" + "\n" +
				"ssssssssssssmysssssssssssssssmy//d+``hmNmh:``.hdsssssssssssssssdhsssssssssssssss" + "\n" + 
				"ssssssssssssNsssssssssssssssssyyN+```..--.````-NysssssssssssssssNsssssssssssssss" + "\n" + 
				"sssssssssssymsssssssssssssssssssM-`````````````dhsssssssssssssssdhssssssssssssss" + "\n" + 
				"ssssssssssshdsssssssssssssssssssN:````-/oosy:`.mhsssssssssssssssymssssssssssssss" + "\n" + 
				"sssssssssssdhsssssssssssssssssssdy```+ho:--/-`+Nsssssssssssssssssmysssssssssssss" + "\n" + 
				"sssssssssssmyssssssssssssssssssssms:.--```.-:sdysssssssssssssssssdhsssssssssssss" + "\n" + 
				"sssssssssssmssssssssssssssssssssssyhyssosyhhhyssssssssssssssssssshdsssssssssssss";

	}
	
	/**
	 * Does bear koala, or does bear bear?
	 */
	public void write() {
		DriverStation.reportError(koala, false);
	}
}
