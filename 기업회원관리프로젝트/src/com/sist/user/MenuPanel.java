package com.sist.user;
// 메뉴 패널
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class MenuPanel extends JPanel implements ActionListener {
	JButton b1,b2,b3,b4,b5,b6,b7,b8;
	ControllerPanel cp;
	public MenuPanel(ControllerPanel cp) {
		this.cp = cp;
		b1=new JButton("홈");
		b2=new JButton("로그인");
		b3=new JButton("회원가입");
		b4=new JButton("마이페이지");
		b5=new JButton("관리자페이지");
		b6=new JButton("나가기");
		b7=new JButton("로그아웃");
		b8=new JButton("문의하기");
		setLayout(new GridLayout(1, 5, 5, 5));
		//add(b1);add(b2);add(b3);add(b4);add(b5);add(b6);add(b7);
		init();
		b1.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b8.addActionListener(this);
	}
	
	public void init() {
		removeAll();
		add(b1);
		if(UserMainFrame.bLogin) {
			if(UserMainFrame.isAdmin=='y') {
				add(b5);
			}
			else {
				add(b4); add(b8);
			}
			add(b7);
		}
		else {
			add(b2);add(b3);
		}
		add(b6);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b6) {
			System.exit(0);
		}
		else if(e.getSource()==b1) {
			cp.card.show(cp, "HOME");
		}
		else if(e.getSource()==b5) {
			cp.card.show(cp, "ADMIN");
		}
		else if(e.getSource()==b8) {
			cp.card.show(cp, "SUPPORT");
		}
	}
}
