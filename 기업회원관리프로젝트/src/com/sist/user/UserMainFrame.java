package com.sist.user;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class UserMainFrame extends JFrame implements ActionListener {
	MenuPanel mp;
	ControllerPanel cp;
	public UserMainFrame() {
		cp=new ControllerPanel();
		mp=new MenuPanel(cp);
		setLayout(null);
		mp.setBounds(340, 15, 650, 40);
		cp.setBounds(14, 65, 980, 580);
		add(mp);
		add(cp);
		setBounds(450,150,1024,700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		mp.b3.addActionListener(this);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			//UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		new UserMainFrame();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==mp.b3) {
			cp.card.show(cp, "JOIN");
		}
	}

}
