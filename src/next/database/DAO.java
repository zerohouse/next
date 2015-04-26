package next.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import next.database.sql.KeyParams;
import next.database.sql.NullableParams;
import next.resource.Static;
import next.util.LoggerUtil;
import next.util.Parser;

import org.slf4j.Logger;

public class DAO {

	private static final Logger logger = LoggerUtil.getLogger(DAO.class);

	public DAO() {
	}

	private PreparedStatement getPSTMT(Connection conn, String sql, Object... parameters) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if (parameters != null)
				for (int j = 0; j < parameters.length; j++) {
					pstmt.setObject(j + 1, parameters[j]);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		logger.debug(pstmt.toString());
		return pstmt;
	}

	/**
	 * 하나의 레코드(Row)에 해당하는 데이터를 LinkedHashMap으로 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param parameters
	 *            ?에 해당하는 파라미터
	 * @return LinkedHashMap 레코드(Row) 맵
	 */

	public LinkedHashMap<String, Object> getRecord(String sql, Object... parameters) {
		LinkedHashMap<String, Object> record = null;
		Connection conn = ConnectionPool.getConnection(true);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = getPSTMT(conn, sql, parameters);
			rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				if (record == null)
					record = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					record.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(conn);
		}
		return record;
	}

	/**
	 * 여러 개의 레코드(Row)에 해당하는 데이터를 LinkedHashMap의 리스트로 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param parameters
	 *            ?에 해당하는 파라미터
	 * @return LinkedHashMap의 리스트
	 */

	public List<LinkedHashMap<String, Object>> getRecords(String sql, Object... parameters) {
		List<LinkedHashMap<String, Object>> result = null;
		Connection conn = ConnectionPool.getConnection(true);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = getPSTMT(conn, sql, parameters);
			rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				if (result == null)
					result = new ArrayList<LinkedHashMap<String, Object>>();
				LinkedHashMap<String, Object> columns = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					columns.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
				result.add(columns);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(conn);
		}
		return result;
	}

	/**
	 * SQL에 해당하는 레코드를 Object로 파싱하여 레코드(Row)에 해당하는 Object를 리턴합니다. 레코드의 어노테이션을 통해
	 * 칼럼명을 지정할 수 있습니다. 기본 칼럼명은 [클래스명]_[필드명]으로 지정됩니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param <T>
	 *            클래스 타입
	 * @param cLass
	 *            클래스 타입
	 * @param parameters
	 *            SQL 파라미터
	 * @return Object
	 */

	public <T> T getObject(String sql, Class<T> cLass, Object... parameters) {
		LinkedHashMap<String, Object> record = getRecord(sql, parameters);
		T result = Parser.getObject(cLass, record);
		return result;
	}

	/**
	 * 클래스에 해당하는 SQL을 생성하여 Object를 찾습니다. 해당 클래스의 키, 칼럼등 어노테이션 지정이 필요합니다.
	 * <p>
	 *
	 *
	 * @param <T>
	 *            클래스 타입
	 * @param cLass
	 *            클래스 타입
	 * @param parameters
	 *            Key에 해당하는 파라미터
	 * @return Object
	 */
	public <T> T getObject(Class<T> cLass, Object... parameters) {
		KeyParams sp = Static.getSqlSupports().getKeyParams(cLass);
		LinkedHashMap<String, Object> record = getRecord(String.format("SELECT * FROM %s WHERE %s", sp.getTableName(), sp.getKeyFieldNames(EQ, and)),
				parameters);
		T result = Parser.getObject(cLass, record);
		return result;
	}

	/**
	 * SQL에 해당하는 Object를 리스트로 만들어 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            sql문
	 * @param <T>
	 *            클래스 타입
	 * @param cLass
	 *            클래스 타입
	 * @param parameters
	 *            ?에 파싱할 파라미터
	 * @return ObjectList
	 */
	public <T> List<T> getObjects(String sql, Class<T> cLass, Object... parameters) {
		List<LinkedHashMap<String, Object>> records = getRecords(sql, parameters);
		List<T> result = new ArrayList<T>();
		if (records == null)
			return null;
		records.forEach(record -> {
			result.add(Parser.getObject(cLass, record));
		});
		return result;
	}

	/**
	 * SQL에 해당하는 Object를 리스트로 만들어 리턴합니다.
	 * <p>
	 *
	 * @param <T>
	 *            클래스 타입
	 * @param cLass
	 *            클래스 타입
	 * @return List
	 */
	public <T> List<T> getObjects(Class<T> cLass) {
		return getObjects(String.format("SELECT * FROM %s", Static.getSqlSupports().getKeyParams(cLass).getTableName()), cLass);
	}

	/**
	 * SQL문을 실행합니다.
	 * <p>
	 *
	 * @param sql
	 *            실행할 sql문
	 * @param parameters
	 *            파라미터
	 * @return boolean 실행결과
	 */
	public Boolean execute(String sql, Object... parameters) {
		PreparedStatement pstmt = null;
		Connection conn = ConnectionPool.getConnection(true);
		try {
			pstmt = getPSTMT(conn, sql, parameters);
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(pstmt);
			close(conn);
		}
	}

	private void close(Connection conn) {
		if (conn == null)
			return;
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected static void close(ResultSet rs) {
		if (rs == null)
			return;
		try {
			rs.close();
		} catch (SQLException sqle) {
		}
	}

	protected static void close(PreparedStatement pstmt) {
		if (pstmt == null)
			return;
		try {
			pstmt.close();
		} catch (SQLException sqle) {
		}
	}

	public static final String EQ = "=?";
	public static final String and = " and ";
	public static final String comma = ", ";

	/**
	 * Object를 DB의 데이터로 채웁니다. Object의 키 필드가 정의되어 있어야 합니다.
	 * <p>
	 *
	 * @param object
	 *            채울 object
	 * @return boolean 실행결과
	 */
	public Object fill(Object object) {
		KeyParams kp = new NullableParams(Static.getSqlSupports(), object);
		Map<String, Object> recordMap = getRecord(String.format("SELECT * FROM %s WHERE %s", kp.getTableName(), kp.getKeyFieldNames(EQ, and)), kp
				.getKeyParams().toArray());
		return Parser.setObject(object, recordMap);
	}

	private final static String INSERT = "INSERT %s SET %s";

	/**
	 * Object를 DB에 삽입합니다.
	 * <p>
	 *
	 * @param object
	 *            삽입할 object
	 * @return boolean 실행결과
	 */
	public boolean insert(Object object) {
		KeyParams sap = Static.getSqlSupports().getKeyParams(object);
		if (sap.isEmpty())
			return false;
		String fieldsNames = sap.getIntegratedFieldNames(EQ, comma);
		String sql = String.format(INSERT, sap.getTableName(), fieldsNames);
		return execute(sql, sap.getIntegratedParams());
	}

	private final static String INSERT_IFEXISTS_UPDATE = "INSERT INTO %s (%s) VALUES (%s) ON DUPLICATE KEY UPDATE %s";

	/**
	 * Object를 DB에 삽입합니다. 같은 키를 가진 레코드가 있으면, 업데이트합니다.
	 * <p>
	 *
	 * @param object
	 *            삽입 / 업데이트할 object
	 * @return boolean 실행결과
	 */
	public boolean insertIfExistUpdate(Object object) {

		KeyParams sap = Static.getSqlSupports().getKeyParams(object);
		if (sap.isEmpty())
			return false;
		String fieldsNames = sap.getFieldNames("", comma) + comma + sap.getKeyFieldNames("", comma);
		int size = sap.getParams().size() + sap.getKeyParams().size();
		String questions = "";
		for (int i = 0; i < size; i++) {
			questions += "?,";
		}
		questions = questions.substring(0, questions.length() - 1);

		String sql = String.format(INSERT_IFEXISTS_UPDATE, sap.getTableName(), fieldsNames, questions, sap.getFieldNames(EQ, comma));
		List<Object> params = new ArrayList<Object>();
		params.addAll(sap.getParams());
		params.addAll(sap.getKeyParams());
		params.addAll(sap.getParams());
		return execute(sql, params.toArray());
	}

	private final static String UPDATE = "UPDATE %s SET %s WHERE %s";

	/**
	 * Object를 업데이트합니다. 같은 키를 가진 레코드를 업데이트합니다.
	 * <p>
	 *
	 * @param object
	 *            업데이트할 object
	 * @return boolean 실행결과
	 */
	public boolean update(Object object) {
		KeyParams sap = Static.getSqlSupports().getKeyParams(object);
		if (!sap.hasKeyParams())
			return false;
		if (!sap.hasParams())
			return false;
		String sql = String.format(UPDATE, sap.getTableName(), sap.getFieldNames(EQ, comma), sap.getKeyFieldNames(EQ, and));
		List<Object> params = new ArrayList<Object>();
		params.addAll(sap.getParams());
		params.addAll(sap.getKeyParams());
		return execute(sql, params.toArray());
	}

	private final static String DELETE = "DELETE FROM %s WHERE %s";

	/**
	 * Object를 삭제합니다. 같은 키를 가진 레코드를 삭제합니다.
	 * <p>
	 *
	 * @param object
	 *            삭제할 object
	 * @return boolean 실행결과
	 */
	public boolean delete(Object object) {
		KeyParams sap = Static.getSqlSupports().getKeyParams(object);
		if (!sap.hasKeyParams())
			return false;
		return execute(String.format(DELETE, sap.getTableName(), sap.getKeyFieldNames(EQ, and)), sap.getKeyParams().toArray());
	}

}
