package com.unimelb.swen30006.monopoly.square;

import com.unimelb.swen30006.monopoly.Player;
import com.unimelb.swen30006.monopoly.card.GoToJailCard;
import com.unimelb.swen30006.monopoly.card.JailCard;
import com.unimelb.swen30006.monopoly.card.JailExcemptionCard;
import com.unimelb.swen30006.monopoly.card.PayJailFeeCard;
import java.util.ArrayList;
public class GoToJailFacade {
	
	ArrayList<JailCard> playcard;
	public GoToJailFacade() {
		this.playcard=new ArrayList<JailCard>();
		playcard.add(new JailExcemptionCard());
		playcard.add(new GoToJailCard());
		playcard.add(new GoToJailCard());
		playcard.add(new PayJailFeeCard(500));
		playcard.add(new PayJailFeeCard(500));
	}
	public void judge(Player player,Square location) {
		int cardnum = (int)((Math.random()*4));
		JailCard tmpcard=playcard.get(cardnum);
		tmpcard.action(player, location);
	}
}
