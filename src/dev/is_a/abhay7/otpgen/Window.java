package dev.is_a.abhay7.otpgen;

import java.awt.FlowLayout;
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
		
		return frame;
		
	}
	
	private static JTextField getSecretField() {
		if(secret != null) {
			return secret;
		}
		
		secret = new JTextField(33);
		return secret;
	}
	
	private static JSpinner getLengthField() {
		if(lengthField != null) {
			return lengthField;
		}
		var model = new SpinnerNumberModel(6, 6, 8, 1);
		lengthField = new JSpinner(model);
		return lengthField;
	}
	
	private static JComboBox<String> getAlgorithm() {
		if(algorithm != null) {
			return algorithm;
		}
		algorithm = new JComboBox<String>(new String[] {"SHA1", "SHA256", "SHA512"});
		algorithm.setSelectedIndex(0);
		return algorithm;
	}
	
	private static JButton getGenerateButton() {
		if(generate != null) {
			return generate;
		}
		
		generate = new JButton("Generate");
		generate.addActionListener((ActionEvent e) -> {
			var secret = getSecretField().getText();
			if(secret.length() < 2) {
				JOptionPane.showMessageDialog(getFrame() , "The secret is less than 2 characters long", "Secret error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			var code = OTPGen.getCode(secret, (int) getLengthField().getValue(), (String) getAlgorithm().getSelectedItem());
			getResultField().setText(code);
		});
		
		return generate;
	}
	
	private static JTextField getResultField() {
		if(result != null) {
			return result;
		}
		
		result = new JTextField(20);
		result.setEditable(false);
		return result;
	}

}
