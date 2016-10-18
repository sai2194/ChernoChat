package com.thecherno.chernochat.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server implements Runnable{
	
	private List<ServerClient> clients = new ArrayList<ServerClient>();  // list of connected clients
	private int port;
	private DatagramSocket socket;
	private Thread run,manage,receive,send;
	private boolean running =  false;
	
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
	}
	
	private void manageClients(){
		manage = new Thread("Manage"){
			public void run(){
				while(running){
					// managing
					//System.out.println(clients.size());
				}
				
			}
		};
		manage.start();
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
		if(string.startsWith("/c/")){
			
			int id = UniqueIdentifier.getIdentifier();
			System.out.println("Identifier : "+id);
			clients.add(new ServerClient(string.substring(3, string.length()),packet.getAddress(),packet.getPort(),id));
			System.out.println(string.substring(3, string.length()));
			//System.out.println(string.length());
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
		else{
			System.out.println(string);
		}
	}
	
	private void disconnect(int id,boolean status){
		
		ServerClient c = null;
		for(int i=0;i<clients.size();i++){
			if(clients.get(i).getID() == id){
				c = clients.get(i);
				clients.remove(i);
				break;
			}
			
		}
		String message = "";
		if(status){ // i.e., if user has done this 
			message = "Client " + c.name.trim() + " (" + c.getID() + ") @ " + c.address.toString() + ":" + c.port +" disconnected !";
		}
		else{
			message = "Client " + c.name.toString() + " (" + c.getID() + ") @ " + c.address.toString() + ":" + c.port +" timed out !";
		}
		System.out.println(message);
	}
	

}
