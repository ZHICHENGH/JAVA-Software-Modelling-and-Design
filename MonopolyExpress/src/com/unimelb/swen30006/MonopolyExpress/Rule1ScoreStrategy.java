package com.unimelb.swen30006.MonopolyExpress;

import java.util.ArrayList;

import com.unimelb.swen30006.MonopolyExpress.Board.SquareSet;
import com.unimelb.swen30006.MonopolyExpress.Board.BoardGame;
public class Rule1ScoreStrategy implements IScoreStrategy {

	@Override
	public int getaddScore(BoardGame board) {
		// TODO Auto-generated method stub
		int result=0;
		ArrayList<SquareSet> sets;
		sets=board.getCompleteGroup();
		for(SquareSet group:sets) {
			if(group.getGroupName().equals("50")) {
				result+=600;
			}
			else if(group.getGroupName().equals("100")) {
				result+=1000;
			}
			else if(group.getGroupName().equals("150")) {
				result+=1500;
			}
			else if(group.getGroupName().equals("200")) {
				result+=1800;
			}
			else if(group.getGroupName().equals("250")) {
				result+=2200;
			}
			else if(group.getGroupName().equals("300")) {
				result+=2700;
			}
			else if(group.getGroupName().equals("400")) {
				result+=3000;
			}
			else if(group.getGroupName().equals("500")) {
				result+=3500;	
			}
			else if(group.getGroupName().equals("Railroad")) {
				result+=2500;
			}
			else if(group.getGroupName().equals("Utility")) {
				result+=800;
			}
		}
		return result;
	}
}
