package com.sist.user;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sist.dao.SupportDAO;
import com.sist.vo.SupportVO;
import java.util.List;
public class SupportListPage extends JPanel implements MouseListener {
    DefaultTableModel model;
    TableColumn column;
    JTable table;
    ControllerPanel cp;
    
    public void SupportListPage(ControllerPanel cp) {
    	this.cp = cp;
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
    			column.setPreferredWidth(50);
    		}
    		else if(i==1)
    		{
    			column.setPreferredWidth(100);
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
    	js.setBounds(10, 70, 950, 240);
    	add(js);
    }
	public void print() {
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
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==table) {
			if(e.getClickCount()==2) { // 더블 클릭
				// 선택된 ROW
				int row = table.getSelectedRow();
				String no = model.getValueAt(row, 0).toString();
				JOptionPane.showMessageDialog(this, "선택된 게시물 번호 : " + no);
				cp.card.show(cp, "ANSWER");
				cp.ansp.print(Integer.parseInt(no));
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
