package com.sist.user;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.sist.dao.SupportDAO;
import com.sist.vo.*;
public class SupportForm extends JPanel implements ActionListener {
	JLabel idLa, pwdLa, nameLa, phLa, ctLa;
	JTextField idtf, nametf, phtf;
	JTextArea ctta;
	JPasswordField pf;
	JButton b1,b2;
	ControllerPanel cp;
	SupportDAO dao = SupportDAO.newInstance();
	public SupportForm(ControllerPanel cp) {
		this.cp=cp;
		setLayout(null);
		idLa = new JLabel("아이디");
		idLa.setBounds(150,20,90,30);
		//idLa.setBackground(Color.lightGray);
		//idLa.setOpaque(true);
		add(idLa);
		
		idtf = new JTextField();
		idtf.setBounds(250, 20,200,30);
		add(idtf);
		
		pwdLa = new JLabel("비밀번호");
		pwdLa.setBounds(150, 60, 90, 30);
		//pwdLa.setBackground(Color.lightGray);
		//pwdLa.setOpaque(true);
		add(pwdLa);
		
		pf = new JPasswordField();
		pf.setBounds(250, 60, 200, 30);
		add(pf);
		
		nameLa = new JLabel("성함");
		nameLa.setBounds(150, 100, 90, 30);
		add(nameLa);
		
		nametf = new JTextField();
		nametf.setBounds(250,100,200,30);
		add(nametf);
		
		phLa = new JLabel("전화번호");
		phLa.setBounds(150,140,90,30);
		add(phLa);
		
		phtf = new JTextField();
		phtf.setBounds(250,140,200,30);
		add(phtf);
		
		ctLa = new JLabel("문의내용");
		ctLa.setBounds(150, 180, 90,30);
		add(ctLa);
		
		ctta = new JTextArea();
		JScrollPane js = new JScrollPane(ctta);
		js.setBounds(250,190,500, 200);
		add(js);
		JPanel p = new JPanel();
		b1 = new JButton("접수");
		b2 = new JButton("취소");
		p.add(b1);p.add(b2);
		p.setBounds(300, 450, 300, 50);
		add(p);
		b1.addActionListener(this);
		b2.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()==b1) {
			String id = idtf.getText();
			String pwd = String.valueOf(pf.getPassword());
			String name = nametf.getText();
			String phone = phtf.getText();
			String content = ctta.getText();
			if(id.trim().length()<1||pwd.trim().length()<1||name.trim().length()<1||phone.trim().length()<1||content.trim().length()<1) {
				JOptionPane.showMessageDialog(this, "비어있습니다");
				return;
			}
			else {
				if(dao.supportIdCheck(id)) {
					SupportVO vo = new SupportVO();
					vo.setId(id);
					vo.setPwd(pwd);
					vo.setName(name);
					vo.setPhone(phone);
					vo.setContent(content);
					int check = dao.supportInsert(vo);
					if(check>0) {
						JOptionPane.showMessageDialog(this, "문의하기가 들어갔습니다");
						idtf.setText("");
						pf.setText("");
						nametf.setText("");
						phtf.setText("");
						ctta.setText("");
					}
					else {
						JOptionPane.showMessageDialog(this, "실패하였습니다");
						
					}
				}
				else {
					JOptionPane.showMessageDialog(this, "아이디가 잘못되었습니다");
					idtf.setText("");
					idtf.requestFocus();
					return;
				}
			}
			//dao.supportInsert(null);
		}
		else if(e.getSource()==b2) {
			cp.card.show(cp, "HOME");
		}
	}
}
