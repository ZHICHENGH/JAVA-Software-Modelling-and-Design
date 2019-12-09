package com.unimelb.swen30006.monopoly.card;
/**
 * This class is created for Workshop 9 exercises for SWEN30006 Software Design and Modelling at the University of Melbourne
 * @author Patanamon Thongtanunam
 * @version 1.1
 * @since 2019-05
 */
import com.unimelb.swen30006.monopoly.Player;
import com.unimelb.swen30006.monopoly.square.Square;

public class PayJailFeeCard extends JailCard{
	public PayJailFeeCard(int cardValue) {
		this.cardValue = (double) cardValue;
		this.cardDescription = "No need to go to Jail but you have to pay $"+cardValue+".";
	}
	
	@Override
	public void action(Player p, Square location) {
		super.action(p, location);
		int fee = (int) Math.floor(this.cardValue);
		if(p.getCash() > fee) {
			p.reduceCash(fee);
		}else {
			System.out.println("Not enough cash. Go to jail!!");
			p.setLocation(location);
		}
		
	}

}
