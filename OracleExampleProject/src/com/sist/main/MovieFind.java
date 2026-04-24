package com.sist.main;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.util.List;

import com.sist.vo.*;
import com.sist.dao.*;
public class MovieFind extends JFrame implements ActionListener {
    JLabel titleLa;
    JTable table;
    DefaultTableModel model;
    TableColumn column;
    JComboBox box;
    JTextField tf;
    JButton b;
    MovieDAO dao = MovieDAO.newInstance();
    
    public MovieFind()
    {
    	
    	
    	titleLa=new JLabel("영화 목록",JLabel.CENTER);// <table>
    	titleLa.setFont(new Font("맑은 고딕",Font.BOLD,30)); //<h3></h3>
    	
    	box=new JComboBox();
    	box.addItem("제목");
    	box.addItem("장르");
    	box.addItem("출연");
    	
    	tf=new JTextField(20);
    	b=new JButton("검색");
    	
    	String[] col={"번호","영화명","출연","개봉일","장르"};//<tr><th></th>....</tr>
    	String[][] row=new String[0][5];
    	// 한줄에 5개 데이터를 첨부 
    	model=new DefaultTableModel(row,col) // 데이터 관리
    	{

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
    		 // 익명의 클래스 => 포함 클래스 => 상속없이 오버라이딩 => 클릭 => 편집기 => 편집방지 
    		 
    	};
    	table=new JTable(model); // 테이블 모양 관리 
    	JScrollPane js=new JScrollPane(table);
    	for(int i=0;i<col.length;i++)
    	{
    		column=table.getColumnModel().getColumn(i);
    		if(i==0)
    		{
    			column.setPreferredWidth(50);
    		}
    		else if(i==1)
    		{
    			column.setPreferredWidth(350);
    		}
    		else if(i==2)
    		{
    			column.setPreferredWidth(100);
    		}
    		else if(i==3)
    		{
    			column.setPreferredWidth(150);
    		}
    		else if(i==4)
    		{
    			column.setPreferredWidth(50);
    		}
    	}
    	table.getTableHeader().setReorderingAllowed(false);
    	table.setShowVerticalLines(false);
    	table.setRowHeight(30);
    	table.getTableHeader().setBackground(Color.pink);
    	
    	// 배치 
    	setLayout(null);
    	titleLa.setBounds(10, 15, 820, 50);
    	add(titleLa);
    	
    	js.setBounds(10, 110, 800, 450);
    	add(js);
    	
    	JPanel p=new JPanel();
    	p.add(box);
    	p.add(tf);
    	p.add(b);
    	
    	p.setBounds(10, 70, 350, 35);
    	add(p);
    	
    	setSize(850, 700);
    	setVisible(true);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	b.addActionListener(this);
    	tf.addActionListener(this);
    }
    
    public void print(String col, String fd) {
    	// 테이블 초기화
    	for(int i=model.getRowCount()-1; i>=0; i--) {
    		model.removeRow(i);
    	}
    	
    	// 데이터 읽기
    	List<MovieVO> list = dao.movieFindData(col, fd);
    	for(MovieVO vo : list) {
    		String[] data = {
    			String.valueOf(vo.getMno()),
    			vo.getTitle(),
    			vo.getActor(),
    			vo.getRegdate(),
    			vo.getGenre()
    		};
    		model.addRow(data);
    	}
    }
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        new MovieFind();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b || e.getSource()==tf) {
			if(tf.getText().trim().length()<1) {
				tf.requestFocus();
				return;
			}
			else {
				String[] col = {"title", "genre", "actor"};
				int item = box.getSelectedIndex();
				String fd = tf.getText();
//				switch(item) {
//				case "제목" -> col="title";
//				case "장르" -> col="genre";
//				case "출연" -> col="actor";
//				}
				print(col[item], fd);
				tf.setText("");
			}
		}
	}

}
