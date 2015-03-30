package next.database.sql;

import next.database.sql.SqlFieldNormal;

import org.junit.Test;

import uss.model.database.User;

public class SqlFieldNormalTest {

	@Test
	public void test() throws NoSuchFieldException, SecurityException {
		SqlFieldNormal sql = new SqlFieldNormal(User.class.getDeclaredField("stringId"));
		System.out.println(sql.getFieldString());
	}

}
