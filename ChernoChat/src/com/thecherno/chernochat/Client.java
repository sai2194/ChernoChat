package com.thecherno.chernochat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import java.awt.GridBagLayout;

import javax.swing.JTextArea;

import java.awt.GridBagConstraints;

import javax.swing.JButton;

import java.awt.Insets;

import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private String name,address;
	private int port;
	private JTextField txtMessage;
	private JTextArea history;
	private DefaultCaret caret;
    
	private DatagramSocket socket;
	private InetAddress ip;
	private Thread send;
	
	public Client(String name,String address,int port) {
		
		setTitle("Cherno Chat Client");
		
		this.name = name;
		this.address = address;
		this.port = port;
		boolean connect = openConnection(address);
		if(!connect)
		{
			System.err.println("Connection Failed !!!");
			console("Connection Failed !!!");
		}
		createWindow();
		console("Successfully Connected!!");
		String connection = name + " connected from address "+address+" : "+port;
		send(connection.getBytes());
	}
	private boolean openConnection(String address)
	{
		try{
			socket = new DatagramSocket();
		ip = InetAddress.getByName(address);
		}
		catch(UnknownHostException e){
			e.printStackTrace();
			return false;
		}
		catch(SocketException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private String receive()
	{
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data,data.length);
		try{
			socket.receive(packet);
		}catch(IOException e){
			e.printStackTrace();
		}
		String message = new String(packet.getData());
		return message;  
	}
	
	private void send(final byte[] data)
	{
		send = new Thread("send"){
			public void run(){
				DatagramPacket packet = new DatagramPacket(data,data.length,ip,port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		send.start();
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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{35,812,30,3}; //sum = 880
		gbl_contentPane.rowHeights = new int[]{60,450,45};  // sum = 550
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
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
		scrollconstraints.gridwidth = 3;
		scrollconstraints.gridheight = 2;
		//scrollconstraints.insets = new Insets(0,5,0,0);
		contentPane.add(scroll, scrollconstraints);
		
		txtMessage = new JTextField();
		txtMessage.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					send(txtMessage.getText());
				}
			}
		});
		
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.insets = new Insets(0, 0, 0, 5);
		gbc_txtMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMessage.gridx = 0;
		gbc_txtMessage.gridy = 2;
		gbc_txtMessage.gridwidth = 2;
		contentPane.add(txtMessage, gbc_txtMessage);
		txtMessage.setColumns(10);
		
		JButton btnSend = new JButton("send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send(txtMessage.getText());
			}
		});
		
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.gridx = 2;
		gbc_btnSend.gridy = 2;
		contentPane.add(btnSend, gbc_btnSend);
		
		setVisible(true);
		txtMessage.requestFocusInWindow();
	}
	
	public void send(String message)
	{
		if(message.equals(""))return;
		message = name + " : " + message;
		console(message);
		send(message.getBytes()); // sends the server the message
		txtMessage.setText("");
	}
    public void console(String message)
    {
    	history.append(message + "\n\r");
    }
}
