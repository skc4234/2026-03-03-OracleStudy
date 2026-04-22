package com.sist.client;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class UserMainForm extends JFrame implements ActionListener{
	MenuPenal mp = new MenuPenal();
	ControllerPenal cp;
	public UserMainForm() {
		setLayout(null);
		cp = new ControllerPenal(this);
		mp.setBounds(150,20,840,35);
		//mp.setBounds(10,150,120,360);
		add(mp);
		cp.setBounds(20, 80, 984, 640);
		add(cp);
		setSize(1024, 768);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		mp.b1.addActionListener(this);
		mp.b5.addActionListener(this);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		} catch (Exception e) {
			// TODO: handle exception
		}
		new UserMainForm();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==mp.b5) { // 사내 게시판
			cp.card.show(cp, "BLIST");
			cp.bList.print();
		}
		else if(e.getSource()==mp.b1) { // 홈
			cp.card.show(cp, "HOME");
		}
	}

}
