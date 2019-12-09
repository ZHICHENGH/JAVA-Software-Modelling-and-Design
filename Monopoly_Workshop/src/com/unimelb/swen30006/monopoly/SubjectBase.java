package com.unimelb.swen30006.monopoly;

import java.util.ArrayList;


public class SubjectBase {
	CashTransactionObserver cashTransactionObserver;
	OwnedSquaresLoggerObserver ownedSquaresLoggerObserver;
	public SubjectBase(String name) {
		this.cashTransactionObserver=new CashTransactionObserver(name);
		this.ownedSquaresLoggerObserver=new OwnedSquaresLoggerObserver(name);
	}
}
