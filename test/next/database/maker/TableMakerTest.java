package next.database.maker;

import next.database.maker.TableMaker;

import org.junit.Test;

import uss.model.database.User;

public class TableMakerTest {

	TableMaker tm = new TableMaker(User.class);
	
	@Test
	public void print(){
		System.out.println(tm);
	}
	
	
	@Test
	public void dropTest() {
		tm.dropTable();
	}
	
	@Test
	public void createTest() {
		tm.createTable();
	}
	
	@Test
	public void resetTest() {
		tm.reset();
	}

}
