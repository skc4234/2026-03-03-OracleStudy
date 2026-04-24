package com.sist.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sist.dao.*;
import com.sist.vo.*;

public class MusicMain extends JFrame implements ActionListener, MouseListener{
	JButton prevBtn,nextBtn;
    JLabel pageLa,titleLa;
    JTable table;
    DefaultTableModel model;
    TableColumn column;
    MusicDAO dao = MusicDAO.newInstance();
    int curPage = 1;
    int totalPages = 0;
    
    public MusicMain()
    {
    	
    	prevBtn=new JButton("이전");
    	nextBtn=new JButton("다음");
    	pageLa=new JLabel("0 page / 0 pages"); //<label>0 page / 0 pages</label>
    	titleLa=new JLabel("음악 목록",JLabel.CENTER);// <table>
    	titleLa.setFont(new Font("맑은 고딕",Font.BOLD,30)); //<h3></h3>
    	
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
    	p.add(prevBtn);
    	p.add(pageLa);
    	p.add(nextBtn);
    	
    	p.setBounds(10, 570, 800, 35);
    	add(p);
    	
    	setSize(850, 730);
    	setVisible(true);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	print();
    	prevBtn.addActionListener(this);
    	nextBtn.addActionListener(this);
    	table.addMouseListener(this);
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
    
    
    public void print() {
    	// 테이블 전체 지우기
    	for(int i=model.getRowCount()-1; i>=0; i--) {
    		model.removeRow(i);
    	}
    	// 데이터 읽기
    	List<MusicVO> list = dao.musicListData(curPage);
    	totalPages = dao.musicTotalPages();
    	pageLa.setText(curPage + " page / " + totalPages + " pages");
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
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        new MusicMain();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==prevBtn) {
			if(curPage>1) {
				curPage--;
				print();
			}
		}
		else if(e.getSource()==nextBtn) {
			if(curPage<totalPages) {
				curPage++;
				print();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==table) {
			if(e.getClickCount()==2) { // 더블클릭
				// 1. 선택된 ROW 위치 가져오기
				int row = table.getSelectedRow();
				// 2. ROW의 0번째 값
				String no = model.getValueAt(row, 0).toString();
				MusicVO vo = dao.musicDetailData(Integer.parseInt(no));
				String msg="순위 : " + vo.getNo() + no_idcrement(vo) + "\n"
						+ "cno : " + vo.getCno() + "\n"
						+ "제목 : " + vo.getTitle() + "\n"
						+ "가수 : " + vo.getSinger() + "\n"
						+ "앨범 : " + vo.getAlbum();
				JOptionPane.showMessageDialog(this, msg);
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
