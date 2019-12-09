package com.unimelb.swen30006.monopoly;

import java.io.FileWriter;
import java.io.IOException;


public class CashTransactionObserver{
	public CashTransactionObserver(String name) {
		init(name);
	}
	public void init(String playername) {
		try {
		FileWriter outStream = new FileWriter("CashTransaction_"+playername);
		outStream.write("Amount, Balance\n");
		outStream.write("Init, 1000.0\n");
		outStream.close();
		} catch (IOException e) {
		e.printStackTrace();
		System.exit(1);
		}
		}
	public void addline(String playername, int amount,int balance) {
		try {
		FileWriter outStream = new FileWriter("CashTransaction_"+playername);
		outStream.write(amount+", "+balance+"\n");
		outStream.close();
		} catch (IOException e) {
		e.printStackTrace();
		System.exit(1);
		}
		}



}
