package next.resource;

import next.database.DAO;
import next.database.sql.SqlSupports;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

public class Static {
	private static Reflections reflections = new Reflections("", new SubTypesScanner(), new TypeAnnotationsScanner(), new FieldAnnotationsScanner(),
			new MethodAnnotationsScanner());
	private static SqlSupports sqlSupports = new SqlSupports();
	private static DAO dao = new DAO();

	public static Reflections getReflections() {
		return reflections;
	}

	public static SqlSupports getSqlSupports() {
		return sqlSupports;
	}

	public static DAO getDAO() {
		return dao;
	}

}
