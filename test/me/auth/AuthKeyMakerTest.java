package me.auth;

import org.junit.Test;

public class AuthKeyMakerTest {

	@Test
	public void test() {
		System.out.println(AuthKeyMaker.getKey(15));
		Boolean a = true;
		if(a)
			System.out.println(1);
	}

}
