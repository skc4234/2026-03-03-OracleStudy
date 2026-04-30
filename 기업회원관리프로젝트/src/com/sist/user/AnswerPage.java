package com.sist.user;
// @@@@@@@@@@@@@@@ 답변하기 페이지(관리자)
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.sist.dao.SupportDAO;
import com.sist.vo.SupportVO;
public class AnswerPage extends JPanel implements ActionListener {
	ControllerPanel cp;
	JLabel noLa, no, idLa, id, nameLa, name, phoneLa, phone, dayLa, day, ansLa, titleLa, ctLa;
	JButton b1,b2;
	JTextArea ctta, ansta, title;
	SupportDAO dao = SupportDAO.newInstance();
	public AnswerPage(ControllerPanel cp) {
		this.cp = cp;
		setLayout(null);
       	
		noLa=new JLabel("번호");
        no=new JLabel();
        noLa.setBackground(Color.lightGray);
        noLa.setOpaque(true);
        noLa.setHorizontalAlignment(JLabel.CENTER);
        noLa.setBounds(30, 20, 60, 30);
        no.setBounds(95, 20, 140, 30);
        add(noLa);add(no);
		
		idLa=new JLabel("아이디");
        id=new JLabel();
        idLa.setBackground(Color.lightGray);
        idLa.setOpaque(true);
        idLa.setHorizontalAlignment(JLabel.CENTER);
        idLa.setBounds(30, 60, 60, 30);
        id.setBounds(95, 60, 140, 30);
        add(idLa);add(id);
		
        nameLa=new JLabel("이름");
        name=new JLabel();
        nameLa.setBackground(Color.lightGray);
        nameLa.setOpaque(true);
        nameLa.setHorizontalAlignment(JLabel.CENTER);
        nameLa.setBounds(30, 100, 60, 30);
        name.setBounds(95, 100, 140, 30);
        add(nameLa);add(name);
        
        // 날짜
        dayLa=new JLabel("날짜");
        day=new JLabel();
        dayLa.setBackground(Color.lightGray);
        dayLa.setOpaque(true);
        dayLa.setHorizontalAlignment(JLabel.CENTER);
        dayLa.setBounds(245, 60, 60, 30);
        day.setBounds(310, 60, 140, 30);
        add(dayLa);add(day);
        
        // 전화번호
        phoneLa=new JLabel("전화번호");
        phone=new JLabel();
        phoneLa.setBackground(Color.lightGray);
        phoneLa.setOpaque(true);
        phoneLa.setHorizontalAlignment(JLabel.CENTER);
        phoneLa.setBounds(245, 100, 60, 30);
        phone.setBounds(310, 100, 140, 30);
        add(phoneLa);add(phone);
        
        titleLa=new JLabel("제목");
        title=new JTextArea();
        titleLa.setBackground(Color.lightGray);
        titleLa.setOpaque(true);
        title.setEditable(false);
        title.setFocusable(false);
        title.setBackground(Color.white);
        JScrollPane js = new JScrollPane(title);
        titleLa.setHorizontalAlignment(JLabel.CENTER);
        titleLa.setBounds(30, 140, 60, 30);
        js.setBounds(95, 140, 385, 30);
        add(titleLa);add(js);
        
        // 내용
        ctLa=new JLabel("내용");
        ctta=new JTextArea();
        ctLa.setBackground(Color.lightGray);
        ctLa.setOpaque(true);
        ctta.setEditable(false);
        ctta.setFocusable(false);
        ctta.setBackground(Color.white);
        JScrollPane js1 = new JScrollPane(ctta);
        ctLa.setHorizontalAlignment(JLabel.CENTER);
        ctLa.setBounds(30, 180, 60, 340);
        js1.setBounds(95, 180, 385, 340);
        add(ctLa); add(js1);
        
        // 답변 쓰기
        ansLa=new JLabel("답변");
        ansta=new JTextArea();
        ansLa.setBackground(Color.lightGray);
        ansLa.setOpaque(true);
        ansLa.setHorizontalAlignment(JLabel.CENTER);
        ansLa.setBounds(490, 20, 60, 500);
        JScrollPane js2 = new JScrollPane(ansta);
        js2.setBounds(555, 20, 385, 500);
        add(ansLa); add(js2);
        
       	
        b1 = new JButton("전송");
        b2 = new JButton("취소");
        JPanel p=new JPanel();
        p.add(b1);p.add(b2);
        p.setBounds(280, 550, 435, 35);
        add(p);
        b1.addActionListener(this);
        b2.addActionListener(this);
	}
	
	public void print(int gno) {
		SupportDAO dao = SupportDAO.newInstance();
		SupportVO vo = dao.supportOneData(gno);
		no.setText(String.valueOf(vo.getNo()));
		id.setText(vo.getId());
		name.setText(vo.getName());
		phone.setText(vo.getPhone());
		day.setText(vo.getDbday());
		ctta.setText(vo.getContent());
		title.setText(vo.getTitle());
		ansta.setText(vo.getAnswer());
		ansta.requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b2) {
			cp.card.show(cp, "ADMIN");
		}
		else if(e.getSource()==b1) {
			if(ansta.getText().trim().length()<1) {
				JOptionPane.showMessageDialog(this, "답변 내용을 입력하세요!!!");
				ansta.setText("");
				ansta.requestFocus();
				return;
			}
			dao.supportUpdate(Integer.parseInt(no.getText()), ansta.getText());
			no.setText("");
			id.setText("");
			name.setText("");
			phone.setText("");
			day.setText("");
			ctta.setText("");
			title.setText("");
			ansta.setText("");
			
			cp.card.show(cp, "ADMIN");
			cp.af.slf.print();
		}
	}
}
