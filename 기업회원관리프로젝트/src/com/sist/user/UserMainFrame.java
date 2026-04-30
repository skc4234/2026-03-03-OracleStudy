package com.sist.user;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.sist.dao.MemberDAO;
import com.sist.vo.MemberVO;
public class UserMainFrame extends JFrame implements ActionListener {
	MenuPanel mp;
	ControllerPanel cp;
	Login login;
	MemberDAO dao = MemberDAO.newInstance();
	public static boolean bLogin = false;
	public static char isAdmin = 'n';
	public UserMainFrame() {
		cp=new ControllerPanel();
		mp=new MenuPanel(cp);
		login=new Login();
		setLayout(null);
		mp.setBounds(340, 15, 650, 40);
		cp.setBounds(14, 65, 980, 580);
		add(mp);
		add(cp);
		setBounds(450,150,1024,700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		mp.b1.addActionListener(this);
		mp.b2.addActionListener(this);
		mp.b3.addActionListener(this);
		login.b1.addActionListener(this); // 로그인
		login.b2.addActionListener(this); // 취소
		mp.b7.addActionListener(this); // 로그아웃
		

		
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
		// 연습용
		//cp.myId = "user01";
		bLogin = true;
		isAdmin = 'y';
		
		
		new UserMainFrame();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==mp.b3) {
			cp.card.show(cp, "JOIN");
		}
		else if(e.getSource()==mp.b1) {
			cp.card.show(cp, "HOME");
		}
		else if(e.getSource()==mp.b2) {
			login.tf.setText("");
			login.pf.setText("");
			login.setVisible(true);
		}
		// 로그인 처리
		else if(e.getSource()==login.b1) {
			String id = login.tf.getText();
			String pwd = String.valueOf(login.pf.getPassword());
			if(id.trim().length()<1) {
				login.tf.setText("");
				login.tf.requestFocus();
				JOptionPane.showMessageDialog(login, "아이디를 입력하세요");
				return;
			}
			if(pwd.trim().length()<1) {
				login.pf.setText("");
				login.pf.requestFocus();
				JOptionPane.showMessageDialog(login, "비밀번호를 입력하세요");
				return;
			}
			MemberVO vo = dao.isLogin(id, pwd);
			String result = vo.getMsg();
			if(result.equals("NOID")) {
				JOptionPane.showMessageDialog(login, "아이디가 존재하지 않습니다.");
				login.tf.setText("");
				login.pf.setText("");
				login.tf.requestFocus();
			}
			else if(result.equals("NOPWD")) {
				JOptionPane.showMessageDialog(login, "비밀번호가 틀립니다.");
				login.pf.setText("");
				login.pf.requestFocus();
			}
			else {
				String s = vo.getIsAdmin().equals("y")? "관리자" : "사용자";
				String title = vo.getId()+"(" + s + ")님 환영합니다";
				setTitle(title);
				cp.myId = vo.getId();
				//cp.sf.idtf.setText(cp.myId);
				UserMainFrame.bLogin = true;
				UserMainFrame.isAdmin = vo.getIsAdmin().charAt(0);
				mp.init();
				login.setVisible(false);
				if(vo.getIsAdmin().equals("y")) {
					cp.card.show(cp, "ADMIN");
				}
			}
		}
		else if(e.getSource()==login.b2) {
			login.setVisible(false);
		}
		else if(e.getSource()==mp.b7) {
			UserMainFrame.bLogin = false;
			UserMainFrame.isAdmin = 'n';
			setTitle("");
			JOptionPane.showMessageDialog(this, "로그아웃 되었습니다");
			mp.init();
			cp.card.show(cp, "HOME");
		}
	}

}
