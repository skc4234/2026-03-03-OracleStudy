package com.sist.user;
// @@@@@@@@@@@@ 관리자 페이지
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import com.sist.dao.*;
import com.sist.vo.*;
import java.util.List;
import javax.swing.table.*;
public class AdminPageForm extends JPanel {
	JTabbedPane tp = new JTabbedPane();
	ControllerPanel cp;
	MemberControlForm mcf = new MemberControlForm();
	MemberGradeForm mgf = new MemberGradeForm();
	BooksListForm blf;
	SupportListForm slf;
	public AdminPageForm(ControllerPanel cp) {
		this.cp = cp;
		// JPanel : FlowLayout
		slf = new SupportListForm(cp);
		blf = new BooksListForm(cp);
		setLayout(null);
		tp.addTab("회원관리", mcf);
		//tp.addTab("회원검색", new JPanel());
		//tp.addTab("상품관리", new JPanel());
		//tp.addTab("구매관리", new JPanel());
		tp.addTab("도서관리", blf);
		tp.addTab("등급관리", mgf);
		tp.addTab("문의목록", slf);
		//tp.setTabPlacement(tp.LEFT);
		tp.setBounds(10,15,920,480);
		add(tp);
	}

}
