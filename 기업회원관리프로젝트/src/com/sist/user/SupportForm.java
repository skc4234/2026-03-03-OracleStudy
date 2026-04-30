package com.sist.user;
// @@@@@@@@@ 문의하기 페이지
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.sist.dao.SupportDAO;
import com.sist.vo.*;
public class SupportForm extends JPanel implements ActionListener {
	JLabel idLa, pwdLa, nameLa, phLa, ctLa, titleLa;
	JTextField idtf, nametf, phtf, titletf;
	JTextArea ctta;
	JPasswordField pf;
	JButton b1,b2;
	ControllerPanel cp;
	SupportDAO dao = SupportDAO.newInstance();
	public SupportForm(ControllerPanel cp) {
		this.cp=cp;
		setLayout(null);
		idLa = new JLabel("아이디");
		//idLa.setBounds(150,20,90,30);
        idLa.setBackground(Color.lightGray);
        idLa.setOpaque(true);
        idLa.setHorizontalAlignment(JLabel.CENTER);
        idLa.setBounds(140, 30, 60, 30);
		add(idLa);
		
		idtf = new JTextField();
		//idtf.setEditable(false);
		idtf.setEnabled(false);
		idtf.setBounds(205, 30, 140, 30);
		add(idtf);
		
		pwdLa = new JLabel("비밀번호");
		pwdLa.setBounds(375, 30, 60, 30);
        pwdLa.setBackground(Color.lightGray);
        pwdLa.setOpaque(true);
        pwdLa.setHorizontalAlignment(JLabel.CENTER);
		add(pwdLa);
		
		pf = new JPasswordField();
		pf.setBounds(440, 30, 140, 30);
		add(pf);
		
		nameLa = new JLabel("성함");
		nameLa.setBounds(140, 70, 60, 30);
        nameLa.setBackground(Color.lightGray);
        nameLa.setOpaque(true);
        nameLa.setHorizontalAlignment(JLabel.CENTER);
		add(nameLa);
		
		nametf = new JTextField();
		nametf.setEnabled(false);
		nametf.setBounds(205, 70, 140, 30);
		add(nametf);
		
		phLa = new JLabel("전화번호");
		phLa.setBounds(375, 70, 60, 30);
        phLa.setBackground(Color.lightGray);
        phLa.setOpaque(true);
        phLa.setHorizontalAlignment(JLabel.CENTER);
		add(phLa);
		
		phtf = new JTextField();
		phtf.setBounds(440, 70, 140, 30);
		add(phtf);
		
		titleLa = new JLabel("제목");
		titleLa.setBounds(140, 110, 60, 30);
        titleLa.setBackground(Color.lightGray);
        titleLa.setOpaque(true);
        titleLa.setHorizontalAlignment(JLabel.CENTER);
		add(titleLa);
		
		titletf = new JTextField();
		titletf.setBounds(205, 110, 600, 30);
		add(titletf);
		
		ctLa = new JLabel("문의내용");
		ctLa.setBounds(140, 150, 60, 300);
        ctLa.setBackground(Color.lightGray);
        ctLa.setOpaque(true);
        ctLa.setHorizontalAlignment(JLabel.CENTER);
		add(ctLa);
		
		ctta = new JTextArea();
		JScrollPane js = new JScrollPane(ctta);
		js.setBounds(205, 150, 600, 300);
		add(js);
		JPanel p = new JPanel();
		b1 = new JButton("접수");
		b2 = new JButton("취소");
		p.add(b1);p.add(b2);
		p.setBounds(300, 500, 300, 50);
		add(p);
		b1.addActionListener(this);
		b2.addActionListener(this);
	}
	
	public void print() {
		String id = cp.myId;
		MemberVO vo = dao.memberOneData(id);
		idtf.setText(id);
		nametf.setText(vo.getName());
		phtf.setText(vo.getPhone());
		pf.setText("");
		ctta.setText("");
		titletf.setText("");
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
			String title = titletf.getText();
			if(pwd.trim().length()<1||phone.trim().length()<1||content.trim().length()<1 || title.trim().length()<1) {
				JOptionPane.showMessageDialog(this, "비어있습니다");
				return;
			}
			else {
				if(dao.supportPwdCheck(id, pwd)) {
					SupportVO vo = new SupportVO();
					vo.setId(id);
					vo.setPwd(pwd);
					vo.setName(name);
					vo.setPhone(phone);
					vo.setContent(content);
					vo.setTitle(title);
					int check = dao.supportInsert(vo);
					if(check>0) {
						JOptionPane.showMessageDialog(this, "문의하기가 들어갔습니다");
						print();
					}
					else {
						JOptionPane.showMessageDialog(this, "실패하였습니다");
						
					}
				}
				else {
					JOptionPane.showMessageDialog(this, "비밀번호가 틀렸습니다");
					pf.setText("");
					pf.requestFocus();
					return;
				}
			}
		}
		else if(e.getSource()==b2) {
			cp.card.show(cp, "HOME");
		}
	}
}
