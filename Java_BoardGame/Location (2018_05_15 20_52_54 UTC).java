import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Location {

	private ArrayList<String> coorLocation;
	
	public Location(){
		coorLocation = new ArrayList<String>();
	}
	
	
	public void addLocation(){
		
		BufferedReader read = null;
	
		try {
			//Read in the input from the file in designated folder.
			read = new BufferedReader(new FileReader(new File("coordinate.txt")));
			String line;
			//While there is still content in the file, read them, and manipulate the data as desire.
			while ((line = read.readLine()) != null) 
			{
				coorLocation.add(line);
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
	public int getLocX(String roomX) {
		for (int i = 0; i < coorLocation.size(); i++) {
			String[] breakUp = coorLocation.get(i).split(",+");
			if(roomX.equalsIgnoreCase(breakUp[0]))
			{
				return Integer.parseInt(breakUp[1]);
			}
		}
		return -1;
	}
	
	public int getLocY(String roomY) {
		for (int i = 0; i < coorLocation.size(); i++) {
			String[] breakUpY = coorLocation.get(i).split(",+");
			if(roomY.equalsIgnoreCase(breakUpY[0])) {
				return Integer.parseInt(breakUpY[2]);
			}
		}
		return -1;
	}
	
	public ArrayList<String> loc()
	{
		return coorLocation;
	}
	
	
}
