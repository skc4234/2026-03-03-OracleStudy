package com.sist.user;
// 메인 화면
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.util.*;
import java.util.List;

import com.sist.commons.ImageChange;
import com.sist.dao.*;
import com.sist.vo.*;
import java.awt.event.*;
import java.lang.ModuleLayer.Controller;
import java.net.URI;
import java.net.URL;

import com.sist.dao.*;
import com.sist.vo.*;
public class HomePanel extends JPanel implements ActionListener, MouseListener {
	//UserMainFrame mf;
	ControllerPanel cp;
	JPanel pan = new JPanel(); // 3*4 목록
	JButton b1,b2,b3,b4,b5,b6;
	JLabel la = new JLabel("0 page / 0 pages");
	JLabel[] imgs = new JLabel[12];
	int curPage=1;
	int totalPages=0;
	int type=1;
	GoodsDAO dao = GoodsDAO.newInstance();
	public HomePanel(ControllerPanel cp) {
		//this.mf = mf;
		this.cp=cp;
		setLayout(null);
		JPanel p = new JPanel();
		b1 = new JButton("전체 상품");
		b2 = new JButton("베스트 상품");
		b3 = new JButton("신상품");
		b4 = new JButton("특가 상품");
		p.add(b1);p.add(b2);p.add(b3);p.add(b4);
		p.setBounds(10, 20, 400, 35);
		pan.setLayout(new GridLayout(3,4,8,8));
		pan.setBounds(10,60,920,480);
		JPanel p2 = new JPanel();
		b5 = new JButton("이전");
		b6 = new JButton("다음");
		p2.add(b5);p2.add(la);p2.add(b6);
		p2.setBounds(10, 550, 920, 30);
		//pan.setBackground(Color.PINK);
		add(p);
		add(pan);
		add(p2);
		init();
		print();
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
	}
	
	public void init() {
		for(int i=0; i<imgs.length; i++) {
			imgs[i] = new JLabel("");
		}
		pan.removeAll(); // JLabel 지우기
		//pan.validate(); // Panel 재배치
		//pan.revalidate();
		//pan.updateUI();
	}
	
	// 화면 출력
	public void print() {
		totalPages=dao.totalPages(type);
		la.setText(curPage + " page / " + totalPages + " pages");
		List<GoodsVO> list = dao.goodsListData(type, curPage);
		for(int i=0; i<list.size(); i++) {
			GoodsVO vo = list.get(i);
			try {
				//URL url = new URL(vo.getGoods_poster());
				URI uri = new URI(vo.getGoods_poster());
				URL url = uri.toURL();
				Image image = ImageChange.getImage(new ImageIcon(url), 250, 180);
				imgs[i]=new JLabel(new ImageIcon(image));
				imgs[i].setToolTipText(vo.getGoods_name()+"^"+vo.getNo());
				pan.add(imgs[i]);
				imgs[i].addMouseListener(this);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		pan.updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b1) {
			init();
			type=1;
			curPage=1;
			print();
		}
		else if(e.getSource()==b2) {
			init();
			type=2;
			curPage=1;
			print();
		}
		else if(e.getSource()==b3) {
			init();
			type=3;
			curPage=1;
			print();
		}
		else if(e.getSource()==b4) {
			init();
			type=4;
			curPage=1;
			print();
		}
		else if(e.getSource()==b5) {
			if(curPage>1) {
				curPage--;
				init();
				print();
			}
		}
		else if(e.getSource()==b6) {
			if(curPage<totalPages) {
				curPage++;
				init();
				print();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		for(int i=0; i<imgs.length; i++) {
			if(e.getSource()==imgs[i]&&e.getClickCount()==2) {
				String gno = imgs[i].getToolTipText();
				gno = gno.substring(gno.indexOf("^")+1);
				
				//JOptionPane.showMessageDialog(this, "선택번호 : " + gno);
				cp.card.show(cp, "DETAIL");
				cp.df.print(type, Integer.parseInt(gno));
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		for(int i=0; i<imgs.length; i++) {
			if(e.getSource()==imgs[i]) {
				imgs[i].setBorder(new LineBorder(Color.red, 3));
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		for(int i=0; i<imgs.length; i++) {
			if(e.getSource()==imgs[i]) {
				imgs[i].setBorder(null);
			}
		}
	}
}
