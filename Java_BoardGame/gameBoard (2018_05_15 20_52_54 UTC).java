package boardGame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.*;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.Random;


public class gameBoard extends JFrame{
	private ImageIcon image;
	private JLabel label;
	private Player name1;
	private Player name2;
	private Player human;
	private JPanel panel1;
	private JPanel panel2;
	private JScrollPane scroll1;
	private JScrollPane scroll2;
	private JScrollPane scroll3;
	private JTextField textField;
	private JList list1;
	private JList list2;
	private JList humanList;
	private JTextArea textArea;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private Room mapList;
	
	public gameBoard() {
		
		boardComponent();
		
	}
	
	private void boardComponent() {
	    label = new JLabel();
	    name1 = new Player();
	    name2 = new Player();
	    human = new Player();
	    panel1 = new JPanel();
	    panel2 = new JPanel();
		scroll1 = new JScrollPane();
		scroll2 = new JScrollPane();
		scroll3 = new JScrollPane();
	    button1 = new JButton();
	    button2 = new JButton();
	    button3 = new JButton();
	    list1 = new JList<>();
	    list2 = new JList<>();
	    humanList = new JList<>();
	    textField = new JTextField();
	    textArea = new JTextArea();
	       
	    Random rand = new Random();
		int number = rand.nextInt(3);
		System.out.println(number);
		if (number == 0)
		{
			name1.setPlayer(number + 1);
		    name2.setPlayer(number + 2);
		    human.setPlayer(number);
		}
		else if(number == 1)
		{
			name1.setPlayer(number - 1);
		    name2.setPlayer(number + 1);
		    human.setPlayer(number);
		}
		else
		{
			name1.setPlayer(number - 1);
		    name2.setPlayer(number - 2);
		    human.setPlayer(number);
		}
	    
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setResizable(false);

	    scroll1.setPreferredSize(new Dimension(1900, 1500));
	    
        label.add(name1.getPlayer());
        name1.getPlayer().setBounds(1000, 1000, 100, 50); //(location x, location y, width, height)
        
        label.add(name2.getPlayer());
        name2.getPlayer().setBounds(1000, 1040, 100, 50);//(location x, location y, width, height)
        
        label.add(human.getPlayer());
        human.getPlayer().setBounds(1000, 1080, 100, 50); //(location x, location y, width, height)
	    
	    label.setBounds(20, 30, 2000, 1500);
	    label.setMaximumSize(new Dimension(1900, 1500));
        label.setMinimumSize(new Dimension(1900, 1500));
        label.setPreferredSize(new Dimension(2000, 1500));

	    image = new ImageIcon("CSULBMap5_1200x1437.png"); // NOI18N
	    Image image2 = image.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
	    ImageIcon scaledImage = new ImageIcon(image2);  
	    label.setIcon(scaledImage);
	   
	    scroll1.setViewportView(label);

	    panel1.setPreferredSize(new Dimension(2000, 453));

	    button1.setText("Draw Card");	        
	    button1.setPreferredSize(new Dimension(157, 41));
	    button1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
		    	jButton1ActionPerformed(evt);
		    	}
		    });
       
	    button2.setText("Move");
	    button2.setPreferredSize(new Dimension(157, 41));
	    button2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
		    	jButton2ActionPerformed(evt);
		    	}
		    });

	    button3.setText("Play Card");
	    button3.setPreferredSize(new Dimension(157, 41));
	    button3.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {
	    	jButton3ActionPerformed(evt);
	    	}
	    });

	    textField.setText("TextField");
	    
	    mapList = new Room();
    	mapList.addList("pathways.txt");
    	updateList(mapList, 0, humanList);
    	updateList(mapList, 0, list1);
    	updateList(mapList, 0, list2);
    	scroll2.setViewportView(humanList);
    	
	   
	    textArea.setColumns(20);
	    textArea.setRows(5);
	    scroll3.setViewportView(textArea);

	    GroupLayout panel2Layout = new GroupLayout(panel2);
	    panel2.setLayout(panel2Layout);
	    panel2Layout.setHorizontalGroup(
	    		panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGap(0, 100, Short.MAX_VALUE)
	    		);
	    panel2Layout.setVerticalGroup(
	    		panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGap(0, 100, Short.MAX_VALUE)
	    		);

	    GroupLayout panel1Layout = new GroupLayout(panel1);
	    panel1.setLayout(panel1Layout);
	    panel1Layout.setHorizontalGroup(
	    		panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(panel1Layout.createSequentialGroup().addContainerGap()
	            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addComponent(button1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	            .addComponent(button3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	            .addGroup(panel1Layout.createSequentialGroup()
	            .addComponent(scroll2, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
	            .addGap(108, 108, 108)
	            .addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	            .addComponent(button2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 436, Short.MAX_VALUE)
	            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
	            .addComponent(scroll3, GroupLayout.DEFAULT_SIZE, 1111, Short.MAX_VALUE).addComponent(textField))
	            .addContainerGap())
	    		);
	        
	    panel1Layout.setVerticalGroup(
	            panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(panel1Layout.createSequentialGroup()
	            .addComponent(textField).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
	            .addComponent(scroll3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	            .addContainerGap()).addGroup(panel1Layout.createSequentialGroup()
	            .addComponent(button1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	            .addComponent(button2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	            .addComponent(button3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addComponent(scroll2, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)
	            .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
	            .addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	            .addGap(155, 155, 155))))
	    		);

	    GroupLayout layout = new GroupLayout(getContentPane());
	    getContentPane().setLayout(layout);
	    layout.setHorizontalGroup(
	    	layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup()
	    	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
	        .addComponent(scroll1, GroupLayout.DEFAULT_SIZE, 2025, Short.MAX_VALUE)
	        .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	        .addGap(0, 25, Short.MAX_VALUE)));
	    layout.setVerticalGroup(
	        layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup()
	        .addContainerGap().addComponent(scroll1, GroupLayout.DEFAULT_SIZE, 1491, Short.MAX_VALUE)
	        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
	        .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	        );

	    pack();
		
	}
	
	 private void jButton1ActionPerformed(ActionEvent evt) {                                         
	        // TODO add your handling code here:
	    }  
	 
	 private void jButton2ActionPerformed(ActionEvent evt) {                                         
	        // TODO add your handling code here:
		 if (humanList.getSelectedValue() != null) {
			 //human.setLocation(human.getX(), human.getY() - 150);
			 //System.out.print(list.getSelectedValue());
			 String humanRoom = (String)humanList.getSelectedValue();
			 int labelAt = mapList.compareMap(humanRoom);
			 human.movePlayer(humanRoom, 80);
			 updateList(mapList, labelAt, humanList);
			 scroll2.setViewportView(humanList);
		 }
	    }  
	
	 private void jButton3ActionPerformed(ActionEvent evt) {                                         
	        // TODO add your handling code here:
		 Random rand1 = new Random();
		 int number1 = rand1.nextInt(list1.getModel().getSize()-1);
		 String comp1Room = ((String)list1.getModel().getElementAt(number1));
		 int comp1At = mapList.compareMap(comp1Room);
		 name1.movePlayer(comp1Room, 0);
		 updateList(mapList, comp1At, list1);
		 
		 Random rand2 = new Random();
		 int number2 = rand2.nextInt(list2.getModel().getSize()-1);
		 String comp2Room = ((String)list2.getModel().getElementAt(number2));
		 int comp2At = mapList.compareMap(comp2Room);
		 name2.movePlayer(comp2Room, 40);
		 updateList(mapList, comp2At, list2);
		 
	    }  
	 
	 private void updateList(Room mapList, int index, JList usrList) {
		 String[] in = mapList.getList(index).split("\\|+");
	 	    String[] strings = new String[in.length];
	 	    
	 	    for (int i = 1; i < in.length; i++) {
	 	    	strings[i - 1] = in[i];
	 	    }
		    usrList.setModel(new AbstractListModel<String>() {
		    
		    public int getSize(){return strings.length;}
		    public String getElementAt(int i) {return strings[i];}
		    });
		    
	 }

}