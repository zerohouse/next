package me.auth;

import static org.junit.Assert.*;

import org.junit.Test;

public class AuthKeyMakerTest {

	@Test
	public void test() {
		System.out.println(AuthKeyMaker.getKey(15));
	}

}
