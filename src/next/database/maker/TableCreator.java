package next.database.maker;

import next.database.DAO;
import next.database.annotation.Table;
import next.resource.Static;

/**
 * 
 * Table클래스들의 설정대로 테이블을 생성합니다.
 * 
 */

public class TableCreator {
	public static void createTable(boolean ifExistDrop) {
		DAO dao = new DAO();
		Static.getReflections().getTypesAnnotatedWith(Table.class).forEach(cLass -> {
			TableMaker tm = new TableMaker(cLass, dao);
			if (ifExistDrop)
				tm.dropTable();
			tm.createTable();
		});
	}
}
