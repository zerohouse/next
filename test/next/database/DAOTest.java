package next.database;

import me.model.database.TestResult;

import org.junit.Test;

public class DAOTest {

	@Test
	public void test() {
			TestResult t = new TestResult();
			t.setUserId("zerohouse");
			t.setName("MBTI");
			t.setResult("ISTP");
			DAO dao = new DAO();
			dao.insertIfExistUpdate(t);
			dao.commitAndReturn();
	}

}
