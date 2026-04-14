package com.sist.user;
import java.util.*;
import java.util.List;

import com.sist.dao.*;
import com.sist.vo.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.*;
public class FoodForm extends JFrame implements ActionListener {
	JTable table;
	DefaultTableModel model;
	JButton b1,b2,b3,b4;
	public FoodForm() {
		b1 = new JButton("한식");
		b2 = new JButton("중식");
		b3 = new JButton("양식");
		b4 = new JButton("일식");
		JPanel p = new JPanel();
		add("North", p);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		p.add(b1); p.add(b2); p.add(b3); p.add(b4);
		String[] col = {"번호", "업체명", "종류", "주소", "전화번호"};
		String[][] row = new String[0][5];
		model = new DefaultTableModel(row, col);
		table = new JTable(model);
		JScrollPane js = new JScrollPane(table);
		
		add("Center", js);
		setBounds(580, 180, 800, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		print("한식");
		
	}
	
	public void print(String type) {
		// 데이터 삭제
		for(int i=model.getRowCount()-1; i>=0; i--) {
			model.removeRow(i);
		}
		FoodDAO dao = FoodDAO.newInstance();
		List<FoodVO> list = dao.foodFindData(type);
		for(FoodVO vo : list) {
			String[] data = {
				String.valueOf(vo.getNo()),
				vo.getName(),
				vo.getType(),
				vo.getAddress(),
				vo.getPhone()
			};
			model.addRow(data);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new FoodForm();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b1) {
			print("한식");
		}
		else if(e.getSource()==b2) {
			print("중식");
		}
		else if(e.getSource()==b3) {
			print("일식");
		}
		else if(e.getSource()==b4) {
			print("양식");
		}
	}

}
