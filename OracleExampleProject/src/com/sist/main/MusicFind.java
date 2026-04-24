package com.sist.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sist.vo.*;
import com.sist.dao.*;

public class MusicFind extends JFrame implements ActionListener {
	JLabel titleLa;
    JTable table;
    DefaultTableModel model;
    TableColumn column;
    JComboBox box;
    JTextField tf;
    JButton b;
    MusicDAO dao = MusicDAO.newInstance();
    
    public MusicFind()
    {
    	
    	
    	titleLa=new JLabel("지니뮤직 목록",JLabel.CENTER);// <table>
    	titleLa.setFont(new Font("맑은 고딕",Font.BOLD,30)); //<h3></h3>
    	
    	box=new JComboBox();
    	box.addItem("제목");
    	box.addItem("가수");
    	
    	tf=new JTextField(20);
    	b=new JButton("검색");
    	
    	String[] col={"순위","등락","곡명","가수","앨범"};//<tr><th></th>....</tr>
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
    			column.setPreferredWidth(10);
    		}
    		else if(i==1)
    		{
    			column.setPreferredWidth(40);
    		}
    		else if(i==2)
    		{
    			column.setPreferredWidth(250);
    		}
    		else if(i==3)
    		{
    			column.setPreferredWidth(150);
    		}
    		else if(i==4)
    		{
    			column.setPreferredWidth(250);
    		}
    	}
    	table.getTableHeader().setReorderingAllowed(false);
    	table.setShowVerticalLines(false);
    	table.setRowHeight(30);
    	table.getTableHeader().setBackground(Color.CYAN);
    	
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
    
    public String no_idcrement(MusicVO vo) {
    	String state = "";
    	if(vo.getState().equals("하강")) {
			state = "<html>"
					+ "<body>"
					+ "<font color=blue>▼ "
					+ vo.getIdcrement()+"</font></body></html>";
		}
		else if(vo.getState().equals("상승")) {
			state = "<html>"
					+ "<body>"
					+ "<font color=red>▲ "
					+ vo.getIdcrement()+"</font></body></html>";
		}
		else state = "<html>"
				+ "<body>"
				+ "<font color=gray> - </font></body></html>";
    	return state;
    }
    
    public void print(String col, String fd) {
    	// 테이블 초기화
    	for(int i=model.getRowCount()-1; i>=0; i--) {
    		model.removeRow(i);
    	}
    	
    	// 데이터 읽기
    	List<MusicVO> list = dao.musicFindData(col, fd);
    	if(list.size()==0) JOptionPane.showMessageDialog(this, "검색결과가 없습니다.");
    	else {
    	for(MusicVO vo : list) {
    		String[] data = {
        			String.valueOf(vo.getNo()),
        			no_idcrement(vo),
        			vo.getTitle(),
        			vo.getSinger(),
        			vo.getAlbum()
        		};
    		model.addRow(data);
    	}
    	}
    }
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        new MusicFind();
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
				String[] col= {"title", "singer"};
				String fd = tf.getText();
				print(col[box.getSelectedIndex()], fd);
				tf.setText("");
			}
		}
	}
}
