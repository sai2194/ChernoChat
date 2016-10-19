package com.thecherno.chernochat;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Button;

public class ClientWindow extends JFrame implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	private JTextField txtMessage;
	private JTextArea history;
	private JPanel contentPane;
	private DefaultCaret caret;
	private Thread listen,run;
	
	private boolean running = false;
	
	private Client client;
	private JMenuBar File;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmOnlineUsers;
	private JMenuItem mntmExit;
	
	private OnlineUsers users;
	private JButton btnOnline;
	private Button button;

     public ClientWindow(String name,String address,int port) {
	
		setTitle("Cherno Chat Client");
		client = new Client(name,address,port);
		//setBackground(Color.PINK);
		//setForeground(Color.PINK);
		boolean connect = client.openConnection(address);
		if(!connect)
		{
			System.err.println("Connection Failed !!!");
			console("Connection Failed !!!");
		}
		createWindow();
		console("Successfully Connected!!");
		String connection = "/c/" + name + "/e/";
		client.send(connection.getBytes());
		users = new OnlineUsers();
		running = true;
		run = new Thread(this,"Running");
		run.start();
		
	   }

     private void createWindow()
     {
	
	/*try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
     } 
	catch (Exception e) {
	e.printStackTrace();
     } */
	
	
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(880,550);
	setLocationRelativeTo(null);
	
	File = new JMenuBar(); 
	setJMenuBar(File);
	
	
	btnOnline = new JButton("Online Users");
	btnOnline.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			users.setVisible(true);
		}
	});
	File.add(btnOnline);
	
	
	//mntmExit = new JMenuItem("Exit");
	//mnFile.add(mntmExit);
	
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	
	GridBagLayout gbl_contentPane = new GridBagLayout();
	gbl_contentPane.columnWidths = new int[]{28,815,30,7}; //sum = 880
	gbl_contentPane.rowHeights = new int[]{35,485,40};  // sum = 550
	contentPane.setLayout(gbl_contentPane);
	
	history = new JTextArea();
	history.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 13));
	history.setEditable(false);
	caret = (DefaultCaret)history.getCaret();
	caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); 
	JScrollPane scroll = new JScrollPane(history);
	GridBagConstraints scrollconstraints = new GridBagConstraints();
	scrollconstraints.insets = new Insets(0, 0, 5, 5);
	scrollconstraints.fill = GridBagConstraints.BOTH;
	scrollconstraints.gridx = 0;
	scrollconstraints.gridy = 0;
	scrollconstraints.weightx = 1;
	scrollconstraints.weighty = 1;
	scrollconstraints.gridwidth = 3;
	scrollconstraints.gridheight = 2;
	scrollconstraints.insets = new Insets(0,5,0,0);
	contentPane.add(scroll, scrollconstraints);
	
	txtMessage = new JTextField();
	txtMessage.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				send(txtMessage.getText(),true);
			}
		}
	});
	
	GridBagConstraints gbc_txtMessage = new GridBagConstraints();
	gbc_txtMessage.insets = new Insets(0, 0, 0, 5);
	gbc_txtMessage.fill = GridBagConstraints.HORIZONTAL;
	gbc_txtMessage.gridx = 0;
	gbc_txtMessage.gridy = 2;
	gbc_txtMessage.weightx = 1;
	gbc_txtMessage.weighty = 0; // default is 0
	gbc_txtMessage.gridwidth = 2;
	contentPane.add(txtMessage, gbc_txtMessage);
	txtMessage.setColumns(10);
	
	JButton btnSend = new JButton("send");
	btnSend.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			send(txtMessage.getText(),true);
		}
	});
	
	GridBagConstraints gbc_btnSend = new GridBagConstraints();
	gbc_btnSend.insets = new Insets(0, 0, 0, 5);
	gbc_btnSend.gridx = 2;
	gbc_btnSend.gridy = 2;
	gbc_btnSend.weightx = 0;
	gbc_btnSend.weighty = 0;
	contentPane.add(btnSend, gbc_btnSend);
	
	addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent e){
			//System.out.println("closed !!!");
			String disconnect = "/d/" + client.getID() + "/e/";
			send(disconnect,false);
			running = false;
			client.close();
		}
	});
	
	setVisible(true);
	txtMessage.requestFocusInWindow();
    }
     
     public void run(){
    	 listen();
     }
     
    public void send(String message,boolean text){
    	
 		if(message.equals(""))return;
 		if(text){
 		message = client.getName() + " : " + message;
        message = "/m/" + message;
        txtMessage.setText("");
 		}
 		client.send(message.getBytes()); // sends the server the message
 		
 	}
    
    public void listen(){
    	
    	listen = new Thread("Listen"){
    	  public void run(){
    		while(running){
    	          String message =  client.receive();
    	          if(message.startsWith("/c/")){
    	        	  
    	        	  client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));  //  "/c/85468/e/       "
    	        	  console("Successfully connnected to server ! ID : " + client.getID());
    	          }else if(message.startsWith("/m/")){
    	        		  String text = message.substring(3);
    	        		  text = text.split("/e/")[0];
    	        		  console(text); 
    	          }else if(message.startsWith("/i/")){
    	        	      String text = "/i/" + client.getID() + "/e/";
    	        	      send(text,false);
    	          }else if(message.startsWith("/u/")){  // server sends the clients list of names whoz online
    	        	  String[] u = message.split("/u/|/n/|/e/");
    	        	  users.update(Arrays.copyOfRange(u, 1, u.length-1)); // will trim the array
    	          }
    	          
    		  }
    	  }
    	};
    	listen.start();
    }
    
    public void console(String message){
    	 
     	history.append(message + "\n\r");
     	history.setCaretPosition(history.getDocument().getLength());
     }

}