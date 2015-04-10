package me.matching.factor;

import org.junit.Test;

public class LocationTest {

	@Test
	public void test() {
		Factor f = new Location("35.4820132,123.97799");
		Factor f2 = new Location("37.4820132,126.97799");
		Factor a = new Age(22);
		Factor b = new Age(11);
		System.out.println(a.getPoint(b));
		System.out.println(f.getPoint(f2));
	}

}
