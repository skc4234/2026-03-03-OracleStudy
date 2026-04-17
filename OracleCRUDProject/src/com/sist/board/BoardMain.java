package com.sist.board;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class BoardMain extends JFrame {
	private CardLayout card = new CardLayout();
	BoardList bList = new BoardList();
	BoardDetail dList = new BoardDetail();
	BoardInsert iList = new BoardInsert();
	public BoardMain() {
		setLayout(card);
		//add("bList", bList);
		add("iList", iList);
		setBounds(500,200, 640, 560);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BoardMain();
		
	}

}
