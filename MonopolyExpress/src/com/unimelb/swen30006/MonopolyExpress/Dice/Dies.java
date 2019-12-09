package com.unimelb.swen30006.MonopolyExpress.Dice;

import java.util.ArrayList;

import com.unimelb.swen30006.MonopolyExpress.Board.BoardGame;

public class Dies {
	ArrayList<Die> dies;
	public ArrayList<Die> getDies() {
		return dies;
	}
	public void setDies(ArrayList<Die> dies) {
		this.dies = dies;
	}
	public Dies() {
		dies=new ArrayList<Die>();
		dies.add(new Die1());
		dies.add(new Die2());
		dies.add(new Die34());
		dies.add(new Die34());
		dies.add(new Die5());
		dies.add(new DieUtility());
		dies.add(new DieUtility());
		dies.add(new DiePolice());
		dies.add(new DiePolice());
		dies.add(new DiePolice());
		
	}
	public void rolldie() {
		for (Die die:dies) {
			die.roll();
		}
	}
	public void pirntout() {
		int i=1;
		for (Die die:dies) {
			System.out.println(i+"   : "+die.getCurrentFaceName());
			i++;
		}
	}
	public void placepolice(BoardGame board) {
		for (Die die:dies) {
			if(die.getCurrentFaceName().equals("Police")) {
				board.getGroups().get(0).place(die);
			}
		}
		for (int i=0;i<dies.size();i++) {
			if(dies.get(i).getCurrentFaceName().equals("Police")) {
				dies.remove(i);
				i--;
			}

		}
	}
}
