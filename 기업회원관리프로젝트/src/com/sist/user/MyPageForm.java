package com.sist.user;

import java.util.*;
import java.util.List;

import javax.swing.*;

import com.sist.commons.ImageChange;
import com.sist.dao.*;
import com.sist.vo.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.net.URL;

import javax.swing.table.*;
public class MyPageForm extends JPanel implements ActionListener, MouseListener {
	JTable table;
	DefaultTableModel model;
	JButton btn;
	ControllerPanel cp;
	GoodsDAO dao = GoodsDAO.newInstance();
	
	public MyPageForm(ControllerPanel cp) {
		this.cp = cp;
		String[] col = {"번호","","상품명","수량","가격","구매일"};
		Object[][] row = new Object[0][6];
		model = new DefaultTableModel(row, col) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false; // 편집 불가
			}

			// 테이블에 이미지 출력
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				return getValueAt(0, columnIndex).getClass();
			}
			
			
			
			// 익명 클래스(상속 없이 오버라이딩만)
			// 인터페이스 : 의존성 낮게 만든다 => 스프링
			// POJO : 일반 클래스 => 독립적으로 사용
			// 내부 클래스 - 멤버클래스 : 쓰레드, 네트워크 => 다른 클래스에서 멤버변수로 사용
			//           - 익명클래스 : 윈도우에서 사용, 웹에서는 사용빈도 낮음
		};
		// table => 외부 관리 / 데이터 관리 => model
		// MVC : model, view 나눠서 처리
		// Spring, SpringBoot, Redux, Pinia, Vuex(MVVM)
		table = new JTable(model);
		/*
		 *   React     TanStackQuery
		 *     |             |
		 *    JSP           DAO
		 */
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(35);
		
		JScrollPane js = new JScrollPane(table);
		btn = new JButton("목록");
		
		setLayout(null);
		btn.setBounds(50, 15, 100, 30);
		js.setBounds(50, 50, 800, 450);
		add(btn);
		add(js);
		
		btn.addActionListener(this);
		table.addMouseListener(this);
	}

	public void print() {
		// 데이터 지우기
		for(int i=model.getRowCount()-1; i>=0; i--) {
			model.removeRow(i);
		}
		
		// 데이터 추가
		List<BuyVO> list = dao.buyListData(cp.myId);
		for(BuyVO vo : list) {
			try {
				URI uri = new URI(vo.getBvo().getGoods_poster());
				URL url = uri.toURL();
				Image image = ImageChange.getImage(new ImageIcon(url), 30, 30);
				Object[] data = {
					String.valueOf(vo.getNo()),
					new ImageIcon(image),
					vo.getBvo().getGoods_name(),
					String.valueOf(vo.getAccount()),
					String.valueOf(vo.getPrice()),
					vo.getDbday()
				};
				model.addRow(data);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btn) {
			cp.card.show(cp, "HOME");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==table&&e.getClickCount()==2) {
			int row = table.getSelectedRow();
			String no = model.getValueAt(row, 0).toString();
			//JOptionPane.showMessageDialog(this, no);
			int a = JOptionPane.showConfirmDialog(this, "취소하시겠습니까?", "취소", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(a==JOptionPane.YES_OPTION) { // yes 버튼 눌렀을떄
				// 데이터베이스 연동 => delete
				dao.buyDelete(Integer.parseInt(no));
				print();
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
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
