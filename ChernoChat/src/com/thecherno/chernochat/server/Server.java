package com.thecherno.chernochat.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server implements Runnable{
	
	private List<ServerClient> clients = new ArrayList<ServerClient>();  // list of connected clients
	private List<Integer> clientResponse = new ArrayList<Integer>();
	
	private int port;
	private DatagramSocket socket;
	private Thread run,manage,receive,send;
	private boolean running =  false;
	
	private boolean raw = false;
	
	private final int MAX_ATTEMPTS = 5;
	
	public Server(int port){    // constructor
		
		this.port = port;
		
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		run = new Thread(this,"Server");
		run.start();
	}

	public void run() {
    
	running = true;
	System.out.println("Server started on port "+port);
	//System.out.println("harish");
    manageClients();
    receive();
    Scanner scan = new Scanner(System.in);
    while(running){
    	String text = scan.nextLine();  // grabs enter line entered by user
    	if(!text.startsWith("/")){
    		sendToAll("/m/Server :" + text + "/e/");
    		continue; 
 
    	}
    	text = text.substring(1);
    	if(text.equals("raw")){
    		raw = !raw;  // we will get to see all kind of stuff.
    	}
    	else if(text.equals("clients")){
    
    		System.out.println("Online Users :");
    		System.out.println("============");
    		for(int i=0;i<clients.size();i++){
    			
    			ServerClient c = clients.get(i);
    			System.out.println(c.name + "(" + c.getID() + "): " + c.address.toString() + ":" + c.port);
    		}
    		System.out.println("============");
    	}
    	else if(text.startsWith("kick")){
    		
    		String victim = text.split(" ")[1];
    		int victim_id = -1;
    		boolean number = true;
    		try{
    			victim_id = Integer.parseInt(victim);
    		}catch(NumberFormatException e){
    			number = false;
    		}
    		if(number){
    			boolean victim_exists = false;
    			for(int i=0;i<clients.size();i++){
    				if( victim_id == clients.get(i).getID()){
    					victim_exists = true;
    					break;
    				}
    			}
    			if(victim_exists) disconnect(victim_id,true);
    			else System.out.println("Client "+ victim_id + " doesnot exist!");
    		}
    		else{
    			
    			for(int i=0;i<clients.size();i++)
    			{
    				ServerClient c = clients.get(i);
    				if(victim.equals(c.name)){
    					disconnect(c.getID(),true);
    					break;
    				}
    			}
    		}
    	}
    }
  }
	
	private void manageClients(){
		manage = new Thread("Manage"){
			public void run(){
				while(running){
					
					sendToAll("/i/server"); // a kind of ping to all the clients
					sendStatus();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for(int i=0;i<clients.size();i++){
						ServerClient c = clients.get(i);
						if(!clientResponse.contains(c.getID())){
							if(c.attempt >= MAX_ATTEMPTS){
								disconnect(c.getID(),false); // unclean exit from user (so false)
							}
							else{
								c.attempt++;
							}
						}
						else{
							c.attempt = 0;
							clientResponse.remove(new Integer(c.getID())); // hover over remove method
						}
					}
				}
				
			}
		};
		manage.start();
	}
	
	private void sendStatus(){
		
		if(clients.size() <= 0) return;
		String users = "/u/";
		for(int i=0;i < clients.size()-1;i++){
			
			users+= clients.get(i).name + "/n/";
		}
		users+= clients.get(clients.size()-1).name + "/e/";
		sendToAll(users);
	}
	
	private void receive(){
		receive = new Thread("receive"){
			public void run(){
				while(running){
					byte data[] = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data,data.length);
					try {
						socket.receive(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
					process(packet);
					//clients.add(new ServerClient("Hari",packet.getAddress(),packet.getPort(),50));
					//System.out.println(clients.get(0).address.toString()+" : "+clients.get(0).port);
					
				}
			}
		};
		receive.start();
	}
	
	private void sendToAll(String message){
		
		if(message.startsWith("/m/")){
		 String text = message.substring(3);
		 text = text.split("/e/")[0];
		 System.out.println(text);
		}
		
		for(int i=0;i<clients.size();i++){
			
			ServerClient client = clients.get(i);
			send(message.getBytes(),client.address,client.port); // sends the message to all the clients
		}
	}
	
	private void send(final byte[] data,final InetAddress address,final int port){
		
		send = new Thread("Send"){
			public void run(){
				DatagramPacket packet = new DatagramPacket(data,data.length,address,port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		send.start();
	}
	
	private void send(String message,InetAddress address,int port){
		
		message+="/e/";  // end of the message erases the spaces(if any) after completion of message
		send(message.getBytes(),address,port);
	}
	
	private void process(DatagramPacket packet){
		
		String string = new String(packet.getData());
		if(raw){
			System.out.println(string);
		}
		if(string.startsWith("/c/")){
			
			int id = UniqueIdentifier.getIdentifier();
			String name = string.split("/c/|/e/")[1];
			System.out.println(name + " (" + id + ") connected !");
			clients.add(new ServerClient(name,packet.getAddress(),packet.getPort(),id));
			
			String ID = "/c/"+id;
			send(ID,packet.getAddress(),packet.getPort());
		}
		else if(string.startsWith("/m/")){
		
			sendToAll(string);
		}
		else if(string.startsWith("/d/")){
			String id = string.split("/d/|/e/")[1];
			disconnect(Integer.parseInt(id),true);
		}
		else if(string.startsWith("/i/")){
		//	/i/8790/e/
			clientResponse.add(Integer.parseInt(string.split("/i/|/e/")[1]));
		}
		else{
			System.out.println(string);
		}
	}
	
	private void disconnect(int id,boolean status){
		
		ServerClient c = null;
		boolean existed = false;
		for(int i=0;i<clients.size();i++){
			if(clients.get(i).getID() == id){
				c = clients.get(i);
				clients.remove(i);
				existed = true;
				break;
			}
			
		}
		if(!existed)
			return;
		String message = "";
		if(status){ // i.e., if user has done this 
			message = "Client " + c.name.trim() + " (" + c.getID() + ") @ " + c.address.toString() + ":" + c.port +" disconnected !";
		}
		else{
			message = "Client " + c.name.trim() + " (" + c.getID() + ") @ " + c.address.toString() + ":" + c.port +" timed out !";
		}
		System.out.println(message);
	}
	

}
