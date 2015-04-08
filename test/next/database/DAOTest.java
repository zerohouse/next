package next.database;

import me.model.database.Essay;

import org.junit.Test;

public class DAOTest {

	@Test
	public void test() {
			Essay e = new Essay();
			e.setEmail("man4@uss.com");
			e.setHead("a");
			e.setBody("B");
			e.setId(1);
			
			DAO dao = new DAO();
			dao.update(e);
			dao.commitAndClose();
	}

}
