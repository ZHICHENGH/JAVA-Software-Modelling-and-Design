/**
 * This class is for Workshop's exercises for SWEN30006 Software Design and Modelling subject at the University of Melbourne
 * @author 	Patanamon Thongtanunam
 * @version 1.0
 * @since 	2019-04
 * 
 */

package com.unimelb.swen30006.MonopolyExpress;
import java.util.ArrayList;
import java.util.Scanner;

import com.unimelb.swen30006.MonopolyExpress.Board.BoardGame;
import com.unimelb.swen30006.MonopolyExpress.Dice.Dies;



public class MonopolyExpress {

	public static void main(String[] args) {
		
		BoardGame board = new BoardGame();
		ArrayList<Player> players = new ArrayList<Player>();
	
		players.add(new Player("A"));
		players.add(new Player("B"));
		Scanner in = new Scanner(System.in);
		
		boolean hasWinner = false;
		
		while(!hasWinner) {
			Dies dies=new Dies();
			Player currentPlayer = players.remove(0);
			currentPlayer.newTurn();
			System.out.println("====== "+currentPlayer.getName()+"'s turn ====");
			
			boolean turnEnds = false;
			do {
				//Roll the dice and show the faces
				dies.rolldie();
				dies.pirntout();
				dies.placepolice(board);
				
				//Check PoliceDice and place on the board
				
				
				System.out.println(board.show());
				
				if(board.isAllFilled("Police")) {
					//do something
					turnEnds=true;
				}else {
					//Ask the player to pick the number dice
					int index = 0;
					int remainingDice = 0;
					
					do {
						System.out.println("------ Remaining Dice ----");
						//show dice faces
						dies.pirntout();
						remainingDice=dies.getDies().size();
						System.out.print("["+currentPlayer.getName()+"]Pick a number die (1-"+remainingDice+") or -1 (no pick):");
						index = in.nextInt();
						if(index==-1) {
							break;
						}
						if(board.placeDie(dies.getDies().get(index-1))) {
							dies.getDies().remove(index-1);
							System.out.println(board.show());
						}
					}while(index != -1);
					System.out.print("["+currentPlayer.getName()+"] Keep rolling? (y/n):");
					String answer = in.next();
					
					if(answer.toLowerCase().equals("n")) {
						turnEnds = true;
					}
				}	
	
			}while(!turnEnds);
			System.out.println("Turn ends");
			
			
			
			//Calculate score
			int turnnum=currentPlayer.getTurn();
			CompositeScoreStrategy compositeScoreStrategy=new CompositeScoreStrategy();
			compositeScoreStrategy.setScoreStrategies(ScoreFactory.instance().getCompositeScoreStrategy(turnnum));
			currentPlayer.addScore(compositeScoreStrategy.getaddScore(board));
			System.out.println("Turn :"+currentPlayer.getTurn());
			System.out.println("["+currentPlayer.getName()+"] get score: "+currentPlayer.getScore());
			
			players.add(currentPlayer);
			board.reset();
		}
		
		in.close();
		
	}

	
	
}
