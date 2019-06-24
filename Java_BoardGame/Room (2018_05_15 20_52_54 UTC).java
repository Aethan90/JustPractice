

import java.io.*;
import java.util.*;

public class Room {
	private ArrayList<String> paths;
	
	public Room(){
		paths = new ArrayList<String>();
	}
	
	
	public void addList(String input){
		
		BufferedReader read = null;
	
		try {
			//Read in the input from the file in designated folder.
			read = new BufferedReader(new FileReader(new File(input)));
			String line;
			//While there is still content in the file, read them, and manipulate the data as desire.
			while ((line = read.readLine()) != null) 
			{
				paths.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace(); //If there's an error in opening the file, print the stack trace.
		} finally
		{
			try {
				read.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getLength() {
		return paths.size();
	}
	
	public String getList(int index) {
		return paths.get(index);
	}
	
	public int compareMap(String compareRoom) {
		for(int i = 0; i < paths.size(); i++) {
			String [] tokenBreak = paths.get(i).split("\\|+");
			if (compareRoom.equalsIgnoreCase(tokenBreak[0])) {
				return i;
			}
		}
		return -1;
	}
	
	
}
