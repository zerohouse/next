package next.database;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface DAO {

	List<Object> getRecord(String sql, int resultSize, Object... parameters);

	Map<String, Object> getRecordMap(String sql, Object... parameters);

	List<List<Object>> getRecords(String sql, int resultSize, Object... parameters);

	List<Map<String, Object>> getRecordMaps(String sql, Object... parameters);

	<T> T getObject(String sql, Class<T> cLass, Object... parameters);

	<T> T getObject(Class<T> cLass, Object... parameters);

	<T> List<T> getObjects(String sql, Class<T> cLass, Object... parameters);

	<T> List<T> getObjects(Class<T> cLass);

	Boolean execute(String sql, Object... parameters);

	void close();

	boolean fill(Object record);

	boolean insert(Object record);

	boolean insertIfExistUpdate(Object record);

	boolean update(Object record);

	boolean delete(Object record);

	BigInteger getLastKey();

}
