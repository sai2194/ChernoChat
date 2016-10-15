package com.thecherno.chernochat.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server implements Runnable{
	
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
					String string = new String(packet.getData());
					System.out.println(string);
				}
			}
		};
		receive.start();
	}
	

}
