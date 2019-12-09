package com.unimelb.swen30006.MonopolyExpress;

import java.util.ArrayList;

import com.unimelb.swen30006.MonopolyExpress.Board.SquareSet;
import com.unimelb.swen30006.MonopolyExpress.Board.BoardGame;
public class Rule2ScoreStrategy implements IScoreStrategy {

	@Override
	public int getaddScore(BoardGame board) {
		int result=0;
		ArrayList<SquareSet> sets;
		sets=board.getInCompleteGroup();
		int tmp=0;
		for(SquareSet group:sets) {
			if(group.getGroupName().equals("50")) {
					tmp=50*group.countFilled();
			}
			else if(group.getGroupName().equals("100")||group.getGroupName().equals("Utility")) {
					tmp=100*group.countFilled();}	
			else if(group.getGroupName().equals("150")) {
					tmp=(150*group.countFilled());
			}
			else if(group.getGroupName().equals("200")||group.getGroupName().equals("Railroad")) {
					tmp=(200*group.countFilled());
			}	
			else if(group.getGroupName().equals("250")) {
					tmp=(250*group.countFilled());
			}
			else if(group.getGroupName().equals("300")) {
					tmp=(300*group.countFilled());
			}	
			else if(group.getGroupName().equals("400")) {
					tmp=(400*group.countFilled());
			}
			else if(group.getGroupName().equals("500")) {
					tmp=(500*group.countFilled());
			}
			if(result<tmp)
				result=tmp;
		}
		return result;
	}
}
