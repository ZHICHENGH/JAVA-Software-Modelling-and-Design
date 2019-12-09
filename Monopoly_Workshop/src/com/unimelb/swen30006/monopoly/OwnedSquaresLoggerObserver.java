package com.unimelb.swen30006.monopoly;


import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
public class OwnedSquaresLoggerObserver{
	public OwnedSquaresLoggerObserver(String playername) {
		init(playername);
	}
	
	public void init(String playername) {
		try {
			FileWriter outStream = new FileWriter("OwnedSquares_"+playername);
			outStream.close();
			} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
			}
		
	}

		public void writeFile(ArrayList<String> list,String playername) {
			try {
			FileWriter outStream = new FileWriter("OwnedSquares_"+playername);
			Map<Object, Long> counts = list.stream().collect(Collectors.groupingBy(e -> e,
					Collectors.counting()));
			for(Entry<Object, Long> entry: counts.entrySet()) {
				outStream.write(entry.getKey()+" x "+entry.getValue()+"\n");
				}
					outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
				}
		
	}

}
