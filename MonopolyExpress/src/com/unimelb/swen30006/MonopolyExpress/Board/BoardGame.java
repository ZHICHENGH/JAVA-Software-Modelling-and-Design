/**
 * This class is for Workshop's exercises for SWEN30006 Software Design and Modelling subject at the University of Melbourne
 * @author 	Patanamon Thongtanunam
 * @version 1.0
 * @since 	2019-04
 * 
 */

package com.unimelb.swen30006.MonopolyExpress.Board;

import java.util.ArrayList;

import com.unimelb.swen30006.MonopolyExpress.Dice.Die;

public class BoardGame {
	private ArrayList<SquareSet> groups;
	public ArrayList<SquareSet> getGroups() {
		return groups;
	}
	public void setGroups(ArrayList<SquareSet> groups) {
		this.groups = groups;
	}
	public BoardGame() {
		this.groups = new ArrayList<SquareSet>();
		reset();
	}
	public void reset() {
		if(this.groups.size() > 0) {
			this.groups.clear();
		}
		groups.add(new SquareSet("Police", 3));
		groups.add(new SquareSet("Utility", 2));
		groups.add(new SquareSet("Railroad", 4));
		groups.add(new SquareSet("50", 2));
		groups.add(new SquareSet("100", 3));
		groups.add(new SquareSet("150", 3));
		groups.add(new SquareSet("200", 3));
		groups.add(new SquareSet("250", 3));
		groups.add(new SquareSet("300", 3));
		groups.add(new SquareSet("400", 3));
		groups.add(new SquareSet("500", 2));
	}
	public boolean placeDie(Die d) {
		String selectedGroup = d.getCurrentFaceName();
		if(d.getCurrentFaceName().equals("<blank>")) {
			System.out.println("Can not place blank");
			return false;
		}
			for(int i = 0; i < groups.size(); i++) {
				if(groups.get(i).getGroupName().equals(selectedGroup)) {
					groups.get(i).place(d);
				}
			}
			return true;
	}
	
	public String show() {
		String output = "======= BOARD =====\n";
		for(SquareSet ss : groups) {
			output += ss.showSquares()+"\n";
		}
		output += "=============";
		return output;
	}
	
	public ArrayList<SquareSet> getCompleteGroup(){
		ArrayList<SquareSet> complete = new ArrayList<SquareSet>();
		for(SquareSet ss: groups) {
			if(ss.isAllFilled()) {
				complete.add(ss);
			}
		}
		return(complete);
	}
	
	public ArrayList<SquareSet> getInCompleteGroup(){
		ArrayList<SquareSet> incomplete = new ArrayList<SquareSet>();
		for(SquareSet ss: groups) {
			if(!ss.isAllFilled()) {
				incomplete.add(ss);
			}
		}
		return(incomplete);
	}

	public boolean isAllFilled(String face) {
		boolean allFilled = false;
		for(SquareSet ss: groups) {
			if(ss.getGroupName().equals(face)) {
				allFilled = ss.isAllFilled();
			}
		}
		
		return allFilled;
	}
	public ArrayList<Integer> getsingleScore(ArrayList<SquareSet> Group){
		ArrayList<Integer> score = new ArrayList<Integer>();
		for(SquareSet group:Group) {
			if(group.getGroupName().equals("50")) {
				if(group.isAllFilled())
				score.add(600);
				else {
					score.add(50*group.countFilled());
				}	
			}
			else if(group.getGroupName().equals("100")) {
				if(group.isAllFilled())
				score.add(1000);
				else {
					score.add(100*group.countFilled());
				}	
			}
			else if(group.getGroupName().equals("150")) {
				if(group.isAllFilled())
				score.add(1500);
				else {
					score.add(150*group.countFilled());
				}	
			}
			else if(group.getGroupName().equals("200")) {
				if(group.isAllFilled())
				score.add(1800);
				else {
					score.add(200*group.countFilled());
				}	
			}
			else if(group.getGroupName().equals("250")) {
				if(group.isAllFilled())
				score.add(2200);
				else {
					score.add(250*group.countFilled());
				}	
			}
			else if(group.getGroupName().equals("300")) {
				if(group.isAllFilled())
				score.add(2700);
				else {
					score.add(300*group.countFilled());
				}	
			}
			else if(group.getGroupName().equals("400")) {
				if(group.isAllFilled())
				score.add(3000);
				else {
					score.add(400*group.countFilled());
				}	
			}
			else if(group.getGroupName().equals("500")) {
				if(group.isAllFilled())
				score.add(3500);
				else {
					score.add(500*group.countFilled());
				}	
			}
			else if(group.getGroupName().equals("Railroad")&&group.isAllFilled()) {
				score.add(2500);
			}
			else if(group.getGroupName().equals("Utility")&&group.isAllFilled()) {
				score.add(800);
			}
		}
		return score;
	}	
}
