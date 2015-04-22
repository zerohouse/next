package next.database.maker;

import next.database.ConnectionPool;
import next.database.DAO;
import next.database.MySql;
import next.database.annotation.Table;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

public class PackageCreator {
	public static void createTable(boolean ifExistDrop, String packagePath) {
		ConnectionPool connectionPool = new ConnectionPool();
		DAO dao = new MySql(connectionPool.getConnection(true));
		Reflections ref = new Reflections(packagePath, new SubTypesScanner(), new TypeAnnotationsScanner());
		ref.getTypesAnnotatedWith(Table.class).forEach(cLass -> {
			TableMaker tm = new TableMaker(cLass, dao);
			if (ifExistDrop)
				tm.dropTable();
			tm.createTable();
		});
		dao.close();
	}
}
