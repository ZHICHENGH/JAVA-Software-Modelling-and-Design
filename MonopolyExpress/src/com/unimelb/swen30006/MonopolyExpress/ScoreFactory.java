package com.unimelb.swen30006.MonopolyExpress;

import java.util.ArrayList;

public class ScoreFactory {
	private static ScoreFactory _instance;
	private ScoreFactory() {};
	public static ScoreFactory instance() {
		if(_instance==null) _instance=new ScoreFactory();
		return _instance;
	}
	
	public ArrayList<IScoreStrategy> getCompositeScoreStrategy(int turnnum) {
		ArrayList<IScoreStrategy> applicableStrategies = new ArrayList<IScoreStrategy>();
		applicableStrategies.add(new Rule1ScoreStrategy());
		if(turnnum%2==0) {
			applicableStrategies.add(new Rule2ScoreStrategy());
		}
		if(turnnum%3==0) {
			applicableStrategies.add(new Rule3ScoreStrategy());
		}
		return applicableStrategies;
	}
}
