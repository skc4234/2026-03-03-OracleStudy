package com.sist.user;
//@@@@@@@@@@@@@@@ 관리자 페이지에서 도서목록 출력 => 도서 추가/삭제 가능
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sist.books.BooksDAO;
import com.sist.books.BooksVO;


public class BooksListForm extends JPanel implements MouseListener, ActionListener {
	JTable table;
	DefaultTableModel model;
    TableColumn column;
    JButton b1, b2;
    ControllerPanel cp;
    
	public BooksListForm(ControllerPanel cp) {
		this.cp=cp;
		String[] col={"No","도서명","가격","출판일","ISBN","책소개"};//<tr><th></th>....</tr>
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
    			column.setPreferredWidth(200);
    		}
    		else if(i==2)
    		{
    			column.setPreferredWidth(200);
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
    			column.setPreferredWidth(150);
    		}
    	}
    	table.getTableHeader().setReorderingAllowed(false);
    	//table.setShowVerticalLines(false);
    	table.setRowHeight(30);
    	//table.getTableHeader().setBackground(Color.cyan);
    	
    	b1 = new JButton("도서추가");
    	b2 = new JButton("새로고침");
    	JPanel p = new JPanel();
    	//p.setLayout(new GridLayout(1,2,5,5));
    	p.add(b1);
    	p.add(b2);
    	
    	
    	setLayout(new BorderLayout());
    	add("North", p);
    	add("Center", js);
    	table.addMouseListener(this);
    	print();
    	
    	b1.addActionListener(this);
    	b2.addActionListener(this);
	}
	
	public void print() {
		// 테이블 지우기
    	for(int i=model.getRowCount()-1; i>=0; i--) {
    		model.removeRow(i);
    	}
    	
    	// 오라클 연동
    	BooksDAO dao = BooksDAO.newInstance();
    	List<BooksVO> list = dao.booksAllData();
    	
    	
    	// 데이터 읽기
    	for(BooksVO vo : list) {
    		String[] data = {
    			String.valueOf(vo.getNo()),
    			vo.getBookname(),
    			vo.getPrice(),
    			vo.getPubdate(),
    			vo.getIsbn(),
    			vo.getContent()
    		};
    		model.addRow(data);
    	}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==table&&e.getClickCount()==2) {
			// 선택된 ROW
			int row = table.getSelectedRow();
			String no = model.getValueAt(row, 0).toString();
			//System.out.println("선택된 row : " + row + ", no : " + no);
			
			//cp.card.show(cp, "booksINSERT");
			//cp.bif.print(Integer.parseInt(no));
			int a = JOptionPane.showConfirmDialog(this, "삭제하시겠습니까?", "회원 삭제", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(a==JOptionPane.YES_OPTION) {
				// DB 연동 => 회원 삭제
				BooksDAO dao = BooksDAO.newInstance();
				dao.booksDeleteData(Integer.parseInt(no));
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// 도서추가
		if(e.getSource()==b1) {
			cp.card.show(cp, "booksINSERT");
			//cp.bif.print(Integer.parseInt(no));
			cp.bif.print();
		}
		// 새로고침
		else if(e.getSource()==b2) {
			print();
		}
	}
}
