package next.database;

import me.model.database.Letter;

import org.junit.Test;

public class DAOTest {

	@Test
	public void test() {
			Letter e = new Letter();
			e.setEmail("man4@uss.com");
			e.setHead("a");
			e.setBody("B");
			e.setId(1);
			
			DAO dao = new DAO();
			dao.update(e);
			dao.commitAndClose();
	}

}
