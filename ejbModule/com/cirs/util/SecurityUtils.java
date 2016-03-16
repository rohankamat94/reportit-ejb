package com.cirs.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {
	public static final String hash(String plaintText) {
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-512");
			byte[] hashBytes = sha.digest(plaintText.getBytes());
			StringBuilder hashBuilder = new StringBuilder();
			for (byte b : hashBytes) {
				hashBuilder.append(Integer.toHexString((b & 0xFF) + 0x100).substring(1));
			}
			return hashBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new AssertionError("SecurityUtils#hash hash must be made");
	}
}
