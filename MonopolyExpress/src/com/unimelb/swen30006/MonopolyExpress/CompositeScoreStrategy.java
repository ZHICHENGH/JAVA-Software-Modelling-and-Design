package com.unimelb.swen30006.MonopolyExpress;

import java.util.ArrayList;
import com.unimelb.swen30006.MonopolyExpress.Board.BoardGame;
import com.unimelb.swen30006.MonopolyExpress.Board.SquareSet;

public class CompositeScoreStrategy implements IScoreStrategy {
	protected ArrayList<IScoreStrategy> ScoreStrategies;
	public CompositeScoreStrategy() {
		this.ScoreStrategies=new ArrayList<IScoreStrategy>();
	}
	public ArrayList<IScoreStrategy> getScoreStrategies() {
		return ScoreStrategies;
	}
	public void setScoreStrategies(ArrayList<IScoreStrategy> scoreStrategies) {
		ScoreStrategies = scoreStrategies;
	}
	public void add(IScoreStrategy strategy) {
		ScoreStrategies.add(strategy);
	}
	public void addall(ArrayList<IScoreStrategy> strategies) {
		for(IScoreStrategy strategy:strategies) {
		ScoreStrategies.add(strategy);
		}
	}
	@Override
	public int getaddScore(BoardGame board) {
		// TODO Auto-generated method stub
		int highestScore=0;
		int tmp;
		for(IScoreStrategy strat:this.ScoreStrategies) {
			tmp=strat.getaddScore(board);
			if(tmp>highestScore) {
				highestScore=tmp;
			}
		}
		return highestScore;
	}

}
