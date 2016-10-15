package com.thecherno.chernochat.server;

import java.net.InetAddress;

public class ServerClient {

	public String name;
	public int port;
	public InetAddress address;
    private final int ID;   // unique client
	public int attempt = 0;
	
	public ServerClient(String name,InetAddress address,int port,final int ID){
		
		this.name = name;
		this.port = port;
		this.ID = ID;
		this.address = address;
	}
	public int getID(){
		return ID;
	}
	
}
