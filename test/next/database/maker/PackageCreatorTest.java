package next.database.maker;


import next.database.maker.PackageCreator;

import org.junit.Test;

public class PackageCreatorTest {

	@Test
	public void test() {
		PackageCreator.createTable(true, "uss.model.database");
	}

}
