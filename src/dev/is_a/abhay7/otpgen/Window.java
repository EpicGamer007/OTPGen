package dev.is_a.abhay7.otpgen;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class Window {
	
	private static JFrame frame;
	private static JTextField secret;
	private static JButton generate;
	private static JTextField result;
	private static JSpinner lengthField;
	private static JComboBox<String> algorithm;
	private static JButton copyButton;
	
	private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	private Window() {}
	
	public static JFrame getFrame() {
		if(frame != null) {
			return frame;
		}
		
		frame = new JFrame("OTP Generator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 150);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		frame.setLayout(new FlowLayout());
		frame.add(getSecretField());
		frame.add(getLengthField());
		frame.add(getAlgorithm());
		frame.add(getGenerateButton());
		frame.add(getResultField());
		frame.add(getCopyButton());
		
		return frame;
		
	}

	private static JTextField getSecretField() {
		if(secret == null) {
			secret = new JTextField(33);
		}
		return secret;
	}
	
	private static JSpinner getLengthField() {
		if(lengthField == null) {
			var model = new SpinnerNumberModel(6, 6, 8, 1);
			lengthField = new JSpinner(model);
		}
		return lengthField;
	}
	
	private static JComboBox<String> getAlgorithm() {
		if(algorithm == null) {
			algorithm = new JComboBox<String>(new String[] {"SHA1", "SHA256", "SHA512"});
			algorithm.setSelectedIndex(0);
		}
		return algorithm;
	}
	
	private static JButton getGenerateButton() {
		if(generate == null) {
			generate = new JButton("Generate");
			generate.addActionListener((ActionEvent e) -> {
				try {
					var secret = getSecretField().getText();
					var code = OTPGen.getCode(secret, (int) getLengthField().getValue(), (String) getAlgorithm().getSelectedItem());
					getResultField().setText(code);
				} catch(IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(getFrame() , iae.getMessage(), "Secret error", JOptionPane.ERROR_MESSAGE);
				}
			});
		}
		return generate;
	}
	
	private static JTextField getResultField() {
		if(result == null) {
			result = new JTextField(20);
			result.setEditable(false);
			result.setText("");
		}
		return result;
	}
	
	private static JButton getCopyButton() {
		if(copyButton == null) {
			copyButton = new JButton("Copy");
			copyButton.addActionListener((ActionEvent e) -> {
				if(!getResultField().getText().isEmpty()) {
					clipboard.setContents(new StringSelection(getResultField().getText()), null);
				}
			});
		}
		return copyButton;
	}
	
}
