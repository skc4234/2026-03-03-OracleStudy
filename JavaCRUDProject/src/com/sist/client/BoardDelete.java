package com.sist.client;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
public class BoardDelete extends JPanel implements ActionListener {
	JLabel titleLa,la;
	JPasswordField pf;
	JButton b1,b2;
	UserMainForm mf; 
	
	public BoardDelete(UserMainForm mf)
	{
		this.mf = mf;
		 titleLa=new JLabel("삭제하기",JLabel.CENTER);// <table>
    	 titleLa.setFont(new Font("맑은 고딕",Font.BOLD,30)); //<h3></h3>
    	 setLayout(null);
   	     titleLa.setBounds(10, 15, 620, 50);
   	     add(titleLa);
   	     
   	     la=new JLabel("비밀번호");
   	     pf=new JPasswordField();
   	     b1=new JButton("삭제");
   	     b2=new JButton("취소");
   	     
   	     // 배치 
   	     la.setBounds(230, 75, 80, 30);
   	     pf.setBounds(315, 75, 150, 30);
   	     
   	     JPanel p=new JPanel();
   	     p.add(b1);p.add(b2);
   	     
   	     p.setBounds(230, 115, 235, 35);
   	     add(p);
   	     
   	     add(la);
   	     add(pf);
   	     b2.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b2) {
			mf.cp.card.show(mf.cp, "BLIST");
			mf.cp.bList.print();
		}
	}
}
