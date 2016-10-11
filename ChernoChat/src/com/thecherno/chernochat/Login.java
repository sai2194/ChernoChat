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

public class Login extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField txtAddress;
	private JLabel lblIpAddress;
	private JLabel lblPort;
	private JTextField textField_1;
	private JLabel lbleg;
	private JLabel lbleg_1;
   
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
		
		textField = new JTextField();
		textField.setBounds(55, 68, 165, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
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
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(55, 226, 165, 28);
		contentPane.add(textField_1);
		
		lbleg = new JLabel("(e.g. 172.16.64.159 )");
		lbleg.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbleg.setBounds(75, 166, 131, 20);
		contentPane.add(lbleg);
		
		lbleg_1 = new JLabel("(e.g. 8192 )");
		lbleg_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbleg_1.setBounds(99, 254, 131, 20);
		contentPane.add(lbleg_1);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(85, 301, 89, 23);
		contentPane.add(btnLogin);
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
