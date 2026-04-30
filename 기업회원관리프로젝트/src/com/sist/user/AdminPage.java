package com.sist.user;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sist.dao.GoodsDAO;
import com.sist.dao.SupportDAO;
import com.sist.vo.GoodsVO;
import com.sist.vo.SupportVO;
public class AdminPage extends JPanel implements ActionListener, MouseListener {
	ControllerPanel cp;
	JLabel titleLa;
    JTable table;
    JButton b1,b2,b3,b4;
    int type=1;
    JPanel pan = new JPanel();
    
    
    DefaultTableModel model;
    TableColumn column;
//    String[] col;
//    String[][] row;
    SupportListPage slp;
    
	public AdminPage(ControllerPanel cp) {
		this.cp = cp;// 배치 
    	setLayout(null);

		JPanel p = new JPanel();
		b1 = new JButton("상품 목록");
		b2 = new JButton("회원 목록");
		b3 = new JButton("대출 목록");
		b4 = new JButton("문의사항");
		p.add(b1);p.add(b2);p.add(b3);p.add(b4);
		p.setBounds(10, 10, 400, 35);
		add(p);
		pan.setLayout(null);
		pan.setBounds(10, 50, 965, 600);
		//pan.setBackground(Color.cyan);
		
		add(pan);
		//goodsListPrint();
		
		
//		String[] col={"No","이름(아이디)","제목","전화번호","날짜","답변여부"};//<tr><th></th>....</tr>
//    	String[][] row=new String[0][6];
//    	// 한줄에 5개 데이터를 첨부 
//    	model=new DefaultTableModel(row,col) // 데이터 관리
//    	{
//
//			@Override
//			public boolean isCellEditable(int row, int column) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//    		 // 익명의 클래스 => 포함 클래스 => 상속없이 오버라이딩 => 클릭 => 편집기 => 편집방지 
//    		 
//    	};
//    	table=new JTable(model); // 테이블 모양 관리 
//    	JScrollPane js=new JScrollPane(table);
//    	for(int i=0;i<col.length;i++)
//    	{
//    		column=table.getColumnModel().getColumn(i);
//    		if(i==0)
//    		{
//    			column.setPreferredWidth(50);
//    		}
//    		else if(i==1)
//    		{
//    			column.setPreferredWidth(100);
//    		}
//    		else if(i==2)
//    		{
//    			column.setPreferredWidth(300);
//    		}
//    		else if(i==3)
//    		{
//    			column.setPreferredWidth(75);
//    		}
//    		else if(i==4)
//    		{
//    			column.setPreferredWidth(50);
//    		}
//    		else if(i==5)
//    		{
//    			column.setPreferredWidth(75);
//    		}
//    	}
//    	table.getTableHeader().setReorderingAllowed(false);
//    	table.setShowVerticalLines(false);
//    	table.setRowHeight(30);
//    	table.getTableHeader().setBackground(Color.pink);
//    	js.setBounds(10, 70, 950, 240);
//    	add(js);
		
		
    	b1.addActionListener(this);
    	b2.addActionListener(this);
    	b3.addActionListener(this);
    	b4.addActionListener(this);
    	//table.addMouseListener(this);
	}
	
	public void goodsListPrint()
    {
		type=1;
		pan.removeAll();
		String[] col={"No", "상품명","설명","가격","할인","실구매가"};//<tr><th></th>....</tr>
    	String[][] row=new String[0][6];
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
    			column.setPreferredWidth(250);
    		}
    		else if(i==2)
    		{
    			column.setPreferredWidth(300);
    		}
    		else if(i==3)
    		{
    			column.setPreferredWidth(75);
    		}
    		else if(i==4)
    		{
    			column.setPreferredWidth(50);
    		}
    		else if(i==5)
    		{
    			column.setPreferredWidth(75);
    		}
    	}
    	table.getTableHeader().setReorderingAllowed(false);
    	table.setShowVerticalLines(false);
    	table.setRowHeight(30);
    	table.getTableHeader().setBackground(Color.pink);
    	js.setBounds(10, 10, 945, 300);
    	pan.add(js);
		
    	// 테이블 지우기
    	for(int i=model.getRowCount()-1; i>=0; i--) {
    		model.removeRow(i);
    	}
    	
    	// 오라클 연동
    	GoodsDAO dao = GoodsDAO.newInstance();
    	List<GoodsVO> list = dao.goodsListDataAll(1);
    	
    	
    	// 데이터 읽기
    	for(GoodsVO vo : list) {
    		String[] data = {
    			String.valueOf(vo.getNo()),
    			vo.getGoods_name(),
    			vo.getGoods_sub(),
    			vo.getGoods_price(),
    			String.valueOf(vo.getGoods_discount()),
    			vo.getGoods_first_price()
    		};
    		model.addRow(data);
    	}
    	pan.updateUI();
    	table.addMouseListener(this);
  
    }

	public void supportListPrint()
    {
		type = 4;
		pan.removeAll();
		String[] col={"No","이름(아이디)","제목","전화번호","날짜","답변여부"};//<tr><th></th>....</tr>
    	String[][] row=new String[0][6];
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
    			column.setPreferredWidth(45);
    		}
    		else if(i==1)
    		{
    			column.setPreferredWidth(150);
    		}
    		else if(i==2)
    		{
    			column.setPreferredWidth(350);
    		}
    		else if(i==3)
    		{
    			column.setPreferredWidth(100);
    		}
    		else if(i==4)
    		{
    			column.setPreferredWidth(150);
    		}
    		else if(i==5)
    		{
    			column.setPreferredWidth(50);
    		}
    	}
    	table.getTableHeader().setReorderingAllowed(false);
    	table.setShowVerticalLines(false);
    	table.setRowHeight(30);
    	table.getTableHeader().setBackground(Color.pink);
    	js.setBounds(10, 10, 945, 300);
    	pan.add(js);
		
		
    	// 테이블 지우기
    	for(int i=model.getRowCount()-1; i>=0; i--) {
    		model.removeRow(i);
    	}
    	
    	// 오라클 연동
    	SupportDAO dao = SupportDAO.newInstance();
    	List<SupportVO> list = dao.supportAllData();
    	
    	
    	// 데이터 읽기
    	for(SupportVO vo : list) {
    		String[] data = {
    			String.valueOf(vo.getNo()),
    			vo.getName()+"("+vo.getId()+")",
    			vo.getTitle(),
    			vo.getPhone(),
    			vo.getDbday(),
    			vo.getIsAnswer()
    		};
    		model.addRow(data);
    	}
    	pan.updateUI();
    	table.addMouseListener(this);
  
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b1) {
			goodsListPrint();
		}
		else if(e.getSource()==b2) {
			
		}
		else if(e.getSource()==b3) {
			
		}
		else if(e.getSource()==b4) {
			supportListPrint();
			
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==table&&e.getClickCount()==2) {
			if(type==4) { // 더블 클릭
				// 선택된 ROW
				int row = table.getSelectedRow();
				String no = model.getValueAt(row, 0).toString();
				//JOptionPane.showMessageDialog(this, "선택된 게시물   번호 : " + no);
				cp.card.show(cp, "ANSWER");
				cp.ansp.print(Integer.parseInt(no));
			}
			else if(type==1) {
				
			}
			else if(type==2) {
				
			}
			else if(type==3) {
				
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
