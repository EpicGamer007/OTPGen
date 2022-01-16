package dev.is_a.abhay7.otpgen;

import com.bastiaanjansen.otp.HMACAlgorithm;
import com.bastiaanjansen.otp.TOTP;

public class OTPGen {

	private OTPGen() {}
	
	public static String getCode(String secret) {
		return getCode(secret, 6);
	}
	
	public static String getCode(String secret, int length) {
		return getCode(secret, length, "SHA1");
	}
	
	public static String getCode(String secret, int length, String algorithm) {
		
		HMACAlgorithm alg = HMACAlgorithm.SHA1;
		
		if(algorithm.equalsIgnoreCase("SHA256")) {
			alg = HMACAlgorithm.SHA256;
		} else if(algorithm.equalsIgnoreCase("SHA512")) {
			alg = HMACAlgorithm.SHA512;
		} else if(!algorithm.equalsIgnoreCase("SHA1")) {
			throw new IllegalArgumentException("Illegal algorithm \"" + algorithm + "\" provided. Accepted values include SHA1, SHA256, and SHA512");
		}
		
		if(secret.length() < 2) {
			throw new IllegalArgumentException("Length of Secret is less than 2 characters");
		}
		
		if(length < 6 || length > 8) {
			throw new IllegalArgumentException("Provided code length is less than 6 or greater than 8");
		}
				
		TOTP.Builder builder = new TOTP.Builder(secret.getBytes());
		builder.withPasswordLength(length).withAlgorithm(alg);
		TOTP totp = builder.build();
		
		return totp.now();
	}
	
}