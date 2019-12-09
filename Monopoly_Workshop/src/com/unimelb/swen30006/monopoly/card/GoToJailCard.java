package com.unimelb.swen30006.monopoly.card;
/**
 * This class is created for Workshop 9 exercises for SWEN30006 Software Design and Modelling at the University of Melbourne
 * @author Patanamon Thongtanunam
 * @version 1.1
 * @since 2019-05
 */
import com.unimelb.swen30006.monopoly.Player;
import com.unimelb.swen30006.monopoly.square.Square;

public class GoToJailCard extends JailCard{
	public GoToJailCard() {
		this.cardValue = 0;
		this.cardDescription = "No luck. Go to jail!!";
	}
	
	public void action(Player p, Square location) {
		super.action(p, location);
		p.setLocation(location);
	}

}
