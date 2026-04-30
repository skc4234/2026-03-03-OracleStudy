package com.sist.user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sist.books.BooksDAO;
import com.sist.books.BooksVO;
import com.sist.dao.SupportDAO;
import com.sist.vo.SupportVO;

public class BooksInsertForm extends JPanel implements ActionListener {
	ControllerPanel cp;
	//       bookname,author  price  pubdate,   isbn,   content,   tag
	JLabel nameLa, authorLa, priceLa, dayLa, isbnLa, ctLa, tagLa ;
	JTextField name, author, price, day, isbn, tag;
	JButton b1,b2;
	JTextArea ctta;
	private static final String POSTER = "https://wikibook.co.kr/images/cover/s/9791158396756.jpg";
	//SupportDAO dao = SupportDAO.newInstance();
	public BooksInsertForm(ControllerPanel cp) {
		this.cp = cp;
		setLayout(null);
		
		// bookname
        nameLa=new JLabel("도서명");
        name=new JTextField();
        nameLa.setBackground(Color.lightGray);
        nameLa.setOpaque(true);
        nameLa.setHorizontalAlignment(JLabel.CENTER);
        nameLa.setBounds(30, 20, 60, 30);
        name.setBounds(95, 20, 140, 30);
        add(nameLa);add(name);
        
		authorLa=new JLabel("저자");
		author=new JTextField();
		authorLa.setBackground(Color.lightGray);
		authorLa.setOpaque(true);
		authorLa.setHorizontalAlignment(JLabel.CENTER);
		authorLa.setBounds(245, 20, 60, 30);
		author.setBounds(310, 20, 140, 30);
		add(authorLa);add(author);
        
        // price
        priceLa=new JLabel("가격");
        price=new JTextField();
        priceLa.setBackground(Color.lightGray);
        priceLa.setOpaque(true);
        priceLa.setHorizontalAlignment(JLabel.CENTER);
        priceLa.setBounds(30, 60, 60, 30);
        price.setBounds(95, 60, 140, 30);
        add(priceLa);add(price);
		
        // 날짜
        dayLa=new JLabel("출판일");
        day=new JTextField();
        dayLa.setBackground(Color.lightGray);
        dayLa.setOpaque(true);
        dayLa.setHorizontalAlignment(JLabel.CENTER);
        dayLa.setBounds(245, 60, 60, 30);
        day.setBounds(310, 60, 140, 30);
        add(dayLa);add(day);
        
        // isbn
        isbnLa=new JLabel("ISBN");
        isbn=new JTextField();
        isbnLa.setBackground(Color.lightGray);
        isbnLa.setOpaque(true);
        isbnLa.setHorizontalAlignment(JLabel.CENTER);
        isbnLa.setBounds(460, 60, 60, 30);
        isbn.setBounds(525, 60, 140, 30);
        add(isbnLa);add(isbn);

        
        // 내용
        ctLa=new JLabel("설명");
        ctta=new JTextArea();
        ctLa.setBackground(Color.lightGray);
        ctLa.setOpaque(true);
        JScrollPane js1 = new JScrollPane(ctta);
        ctLa.setHorizontalAlignment(JLabel.CENTER);
        ctLa.setBounds(30, 100, 60, 340);
        js1.setBounds(95, 100, 570, 340);
        add(ctLa); add(js1);
        
        // 태그
        tagLa=new JLabel("태그");
        tag=new JTextField();
        tagLa.setBackground(Color.lightGray);
        tagLa.setOpaque(true);
        tagLa.setHorizontalAlignment(JLabel.CENTER);
        tagLa.setBounds(30, 450, 60, 30);
        tag.setBounds(95, 450, 570, 30);
        add(tagLa);add(tag);
        
       	
        b1 = new JButton("전송");
        b2 = new JButton("취소");
        JPanel p=new JPanel();
        p.add(b1);p.add(b2);
        p.setBounds(280, 550, 435, 35);
        add(p);
        b1.addActionListener(this);
        b2.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b1) {
			// 등록
			if(name.getText().trim().length()<1||author.getText().trim().length()<1||price.getText().trim().length()<1||day.getText().trim().length()<1||isbn.getText().trim().length()<1||tag.getText().trim().length()<1||ctta.getText().trim().length()<1) {
				JOptionPane.showMessageDialog(this, "입력해주세요");
				return;
			}
			BooksVO vo = new BooksVO();
			// name, author, price, day, isbn, tag, ctta
			vo.setBookname(name.getText());
			vo.setAuthor(author.getText());
			vo.setPrice(price.getText());
			vo.setPubdate(day.getText());
			vo.setIsbn(isbn.getText());
			vo.setTag(tag.getText());
			vo.setContent(ctta.getText());
			vo.setPoster(POSTER);
			BooksDAO dao = BooksDAO.newInstance();
			dao.insertBooks(vo);
			//dao.booksInsertData(vo);
			JOptionPane.showMessageDialog(this, "성공");
			cp.card.show(cp, "ADMIN");
			cp.blf.print();
			
		}
		else if(e.getSource()==b2) {
			// 취소(돌아가기)
			cp.card.show(cp, "ADMIN");
			cp.blf.print();
			
		}
	}
	
	public void print() {
		name.setText("");
		day.setText("");
		ctta.setText("");
	}
	
//	public void print(int bno) {
//		BooksDAO dao = BooksDAO.newInstance();
//		//SupportVO vo = dao.supportOneData(gno);
//		BooksVO vo = dao.booksOneData(bno);
//		System.out.println(vo.getBookname());
//		no.setText(String.valueOf(vo.getNo()));
//		id.setText(vo.getBookname());
//		name.setText(String.valueOf(vo.getPrice()));
//		phone.setText(vo.getIsbn());
//		day.setText(vo.getPubdate());
//		ctta.setText(vo.getContent());
//		//title.setText(vo.getTitle());
//		//ansta.setText(vo.getAnswer());
//		ansta.requestFocus();
//	}
}
