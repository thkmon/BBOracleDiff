package com.bb.dbdiff.form;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * BBPatchHelper의 공통 Swing 폼을 BBOracleDiff에 맞게 단순화한 클래스.
 */
public class BasicForm extends JFrame {

	private static final long serialVersionUID = 1L;

	private final Container container;
	private final Font font;

	public BasicForm(int width, int height, String title) {
		container = getContentPane();
		container.setLayout(null);

		setBounds(200, 200, width, height);
		setTitle(title);

		font = new Font("Malgun Gothic", Font.PLAIN, 13);
	}

	public void open() {
		if (getState() == Frame.ICONIFIED) {
			setState(Frame.NORMAL);
		}

		setVisible(true);
		if (getFocusableWindowState()) {
			requestFocus();
		}
	}

	public JLabel addLabel(int left, int top, int width, int height, String value) {
		JLabel label = new JLabel(value);
		label.setBounds(left, top, width, height);
		label.setFont(font);
		addComponent(label);
		return label;
	}

	public JTextField addTextInput(int left, int top, int width, int height) {
		JTextField textField = new JTextField();
		textField.setBounds(left, top, width, height);
		textField.setFont(font);
		addComponent(textField);
		return textField;
	}

	public JPasswordField addPasswordInput(int left, int top, int width, int height) {
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(left, top, width, height);
		passwordField.setFont(font);
		addComponent(passwordField);
		return passwordField;
	}

	public JButton addButton(int left, int top, int width, int height, String value) {
		JButton button = new JButton(value);
		button.setBounds(left, top, width, height);
		button.setFont(font);
		addComponent(button);
		return button;
	}

	private void addComponent(Component component) {
		container.add(component);
	}
}
