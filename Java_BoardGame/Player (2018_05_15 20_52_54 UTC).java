import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Player{
	private JLabel player;
	private Location coor = new Location();
	
	public Player() {
		player = new JLabel();
		coor.addLocation();
		//System.out.println(coor.loc());
	}
	
	public void setPlayer(int index) {
		String[] name = new String[] {"Matt", "Sophia", "Steve"};
		player.setForeground(new Color(255, 51, 0));
		player.setFont(new Font("Tahoma", 1, 27));
		player.setText(name[index]);
	}
	public String getName() {
		return player.getText();
	}
	
	public JLabel getPlayer() {
		return player;
	}

	public void setPlayerLoc(int x, int y) {
		player.setBounds(x, y, 100, 50);
	}
	
	public void movePlayer(String roomLocation, int addY) {
		player.setLocation(coor.getLocX(roomLocation), coor.getLocY(roomLocation)+ addY);
	}
}
