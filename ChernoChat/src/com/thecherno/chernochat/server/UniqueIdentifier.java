package com.thecherno.chernochat.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UniqueIdentifier {

	private static List<Integer> ids = new ArrayList<Integer>();
	private static final int RANGE = 10000; //10000 unique clients
	private static int index=0;
	
	static{
		
	 for(int i=0;i<RANGE;i++){
		 ids.add(i);
	 }
	 Collections.shuffle(ids); 
	}
	private UniqueIdentifier(){
		
	}
	public static int getIdentifier(){
		
		if(index >= ids.size())
			index=0;
		return ids.get(index++);
	}
	
}
