package com.thecherno.chernochat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtAddress;
	private JLabel lblIpAddress;
	private JLabel lblPort;
	private JTextField txtPort;
	private JLabel lblAddressDesc;
	private JLabel lblPortDesc;
   
	/*Create the frame.*/
	 
	public Login() {
		
		/*try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,380);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		txtName = new JTextField();
		txtName.setBounds(55, 68, 165, 28);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblName.setBounds(110, 39, 49, 20);
		contentPane.add(lblName);
		
		txtAddress = new JTextField();
		txtAddress.setBounds(55, 138, 165, 28);
		contentPane.add(txtAddress);
		txtAddress.setColumns(10);
		
		lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIpAddress.setBounds(99, 107, 86, 20);
		contentPane.add(lblIpAddress);
		
		lblPort = new JLabel("Port");
		lblPort.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPort.setBounds(110, 195, 86, 20);
		contentPane.add(lblPort);
		
		txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setBounds(55, 226, 165, 28);
		contentPane.add(txtPort);
		
		lblAddressDesc = new JLabel("(e.g. 172.16.64.159 )");
		lblAddressDesc.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAddressDesc.setBounds(75, 166, 131, 20);
		contentPane.add(lblAddressDesc);
		
		lblPortDesc = new JLabel("(e.g. 8192 )");
		lblPortDesc.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPortDesc.setBounds(99, 254, 131, 20);
		contentPane.add(lblPortDesc);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				
				String name = txtName.getText();
				String address = txtAddress.getText();
				int port = Integer.parseInt(txtPort.getText());
				login(name,address,port);
			}

			
		});
		btnLogin.setBounds(85, 301, 89, 23);
		contentPane.add(btnLogin);
	}
	
	/* Login Stuff here !!! */
	private void login(String name,String address,int port) {
		dispose();
		System.out.println(name+" "+address+" "+port);
		
	}
	
	 /* Launch the application. */
	   
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
