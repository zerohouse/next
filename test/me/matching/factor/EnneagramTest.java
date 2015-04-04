package me.matching.factor;

import org.junit.Test;

public class EnneagramTest {

	@Test
	public void test() {
		Enneagram a = new Enneagram("1");
		Enneagram b = new Enneagram("5");
		System.out.println(a.getPoint(b));
	}

}
