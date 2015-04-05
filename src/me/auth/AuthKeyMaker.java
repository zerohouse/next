package me.auth;

public class AuthKeyMaker {

	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKNMLOPQRSTUVWXYZ";

	public static String getKey(int size) {
		Double rd = Math.random();
		String result = "";
		for (int i = 0; i < size; i++) {
			rd = rd * 52;
			int val = rd.intValue();
			result += ALPHABET.charAt(val);
			rd -= val;
		}
		return result;
	}

}
