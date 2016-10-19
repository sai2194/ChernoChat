package com.thecherno.chernochat;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JList;

import java.awt.GridBagConstraints;
import java.awt.Color;

public class OnlineUsers extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JList list;

	public OnlineUsers() {
		
		setTitle("Whoz Online!");
		
		setType(Type.UTILITY);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(200,350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		list = new JList();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 0;
		JScrollPane p = new JScrollPane();
		p.setViewportView(list);
		contentPane.add(p, gbc_list);
		list.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 12));
	}
	
	public void update(String[] users){
		list.setListData(users);
	}
   
}
