package next.database.maker;

import next.database.DAO;
import next.database.annotation.Table;
import next.resource.Static;

public class PackageCreator {
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
