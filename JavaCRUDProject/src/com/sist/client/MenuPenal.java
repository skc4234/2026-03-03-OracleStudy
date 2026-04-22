package com.sist.client;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuPenal extends JPanel {
	JButton b1,b2,b3,b4,b5,b6,b7,b8;
	public MenuPenal() {
		b1=new JButton("홈");
		b2=new JButton("쇼핑몰");
		b3=new JButton("키오스크");
		b4=new JButton("사원관리");
		b5=new JButton("사내게시판");
		b6=new JButton("커뮤니티"); // 채팅
		b7=new JButton("마이페이지");
		b8=new JButton("관리자페이지");
		b8.setVisible(false);
		setLayout(new GridLayout(1,8,10,5));
		//setLayout(new GridLayout(8,1,5,10));
		add(b1);add(b2);add(b3);add(b4);add(b5);add(b6);add(b7);add(b8);
		
	}
}
