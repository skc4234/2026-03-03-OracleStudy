package com.sist.commons;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageChange {
	// 이미지 확대/축소
	public static Image getImage(ImageIcon icon, int width, int height) {
		return icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
}
