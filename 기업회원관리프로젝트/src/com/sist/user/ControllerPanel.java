package com.sist.user;
// 화면 변경
import java.awt.*;
import javax.swing.*;
public class ControllerPanel extends JPanel {
	CardLayout card = new CardLayout();
	String myId;
	//UserMainFrame mf;
	// 1. HOME(메인페이지)
	HomePanel hp;
	// 2. DETAIL(상세보기 페이지)
	GoodsDetailForm df;
	// 3. JOIN(회원가입 페이지)
	JoinPanel jp;
	// 4. ADMIN(관리자 페이지)
	//AdminPage ap;
	
	// 이 2개 추가
	AdminPageForm af;
	// 5. SUPPORT(문의하기 페이지)
	SupportForm sf;
	// 6. ANSWER(답변 페이지)
	
	
	
	AnswerPage ansp;
	// 7. MYPAGE(마이페이지)
	MyPageForm mp;
	
	BooksInsertForm bif;
	BooksListForm blf;
	public ControllerPanel() {
		//this.mf = mf;
		//myId="user01";
		setLayout(card);
		//hp = new HomePanel(mf);
		hp = new HomePanel(this);
		df = new GoodsDetailForm(this);
		jp = new JoinPanel(this);
		sf = new SupportForm(this);
		//ap = new AdminPage(this);
		af = new AdminPageForm(this);
		ansp = new AnswerPage(this);
		mp = new MyPageForm(this);
		bif = new BooksInsertForm(this);
		blf = new BooksListForm(this);
		
		add("HOME", hp);
		add("DETAIL", df);
		add("JOIN", jp);
		add("SUPPORT", sf);
		//add("ADMIN", ap);
		add("ADMIN", af);
		add("ANSWER", ansp);
		add("MYPAGE", mp);
		add("booksINSERT", bif);
		//setBackground(Color.CYAN);
	}
}
