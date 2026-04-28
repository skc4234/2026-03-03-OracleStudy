package com.sist.user;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.sist.dao.SupportDAO;
import com.sist.vo.SupportVO;
public class AnswerPage extends JPanel implements ActionListener {
	ControllerPanel cp;
	JLabel noLa, no, idLa, id, nameLa, name;
	JButton b1,b2;
	public AnswerPage(ControllerPanel cp) {
		this.cp = cp;
		setLayout(null);
       	
		noLa=new JLabel("번호");
        no=new JLabel();
        noLa.setBackground(Color.lightGray);
        noLa.setOpaque(true);
        noLa.setBounds(330, 20, 80, 30);
        no.setBounds(415, 20, 250, 30);
        add(noLa);add(no);
		
		idLa=new JLabel("아이디");
        id=new JLabel();
        idLa.setBackground(Color.lightGray);
        idLa.setOpaque(true);
        idLa.setBounds(330, 60, 80, 30);
        id.setBounds(415, 60, 250, 30);
        add(idLa);add(id);
		
        nameLa=new JLabel("이름");
        name=new JLabel();
        nameLa.setBackground(Color.lightGray);
        nameLa.setOpaque(true);
        nameLa.setBounds(330, 100, 80, 30);
        name.setBounds(415, 100, 250, 30);
        add(nameLa);add(name);
       	
        b1 = new JButton("전송");
        b2 = new JButton("취소");
        JPanel p=new JPanel();
        p.add(b1);p.add(b2);
        p.setBounds(330, 200, 435, 35);
        add(p);
        b2.addActionListener(this);
	}
	
	public void print(int gno) {
		SupportDAO dao = SupportDAO.newInstance();
		SupportVO vo = dao.supportOneData(gno);
		no.setText(String.valueOf(vo.getNo()));
		id.setText(vo.getId());
		name.setText(vo.getName());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b2) {
			cp.card.show(cp, "ADMIN");
		}
	}
}
