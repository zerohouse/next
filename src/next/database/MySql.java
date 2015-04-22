package next.database;

import java.math.BigInteger;
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
import next.database.sql.SqlSupports;
import next.util.LoggerUtil;

import org.slf4j.Logger;

public class MySql implements DAO{

	private static final Logger logger = LoggerUtil.getLogger(DAO.class);

	private Connection conn;

	private SqlSupports sqlSupports = SqlSupports.getInstance();

	public MySql(Connection conn) {
		this.conn = conn;
	}

	private PreparedStatement getPSTMT(String sql, Object... parameters) {
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

	@Override
	public List<Object> getRecord(String sql, int resultSize, Object... parameters) {
		List<Object> record = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = getPSTMT(sql, parameters);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (record == null)
					record = new ArrayList<Object>();
				for (int i = 0; i < resultSize; i++) {
					record.add(rs.getObject(i + 1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
		}
		return record;
	}

	@Override
	public Map<String, Object> getRecordMap(String sql, Object... parameters) {
		Map<String, Object> record = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = getPSTMT(sql, parameters);
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
		}
		return record;
	}

	@Override
	public List<List<Object>> getRecords(String sql, int resultSize, Object... parameters) {
		List<Object> record;
		List<List<Object>> result = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = getPSTMT(sql, parameters);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (result == null)
					result = new ArrayList<List<Object>>();
				record = new ArrayList<Object>();
				for (int i = 0; i < resultSize; i++) {
					record.add(rs.getObject(i + 1));
				}
				result.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
		}

		return result;
	}

	@Override
	public List<Map<String, Object>> getRecordMaps(String sql, Object... parameters) {
		List<Map<String, Object>> result = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = getPSTMT(sql, parameters);
			rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				if (result == null)
					result = new ArrayList<Map<String, Object>>();
				Map<String, Object> columns = new LinkedHashMap<String, Object>();
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
		}
		return result;
	}

	@Override
	public <T> T getObject(String sql, Class<T> cLass, Object... parameters) {
		Map<String, Object> record = getRecordMap(sql, parameters);
		T result = sqlSupports.getObject(cLass, record);
		return result;
	}

	@Override
	public <T> T getObject(Class<T> cLass, Object... parameters) {
		KeyParams sp = sqlSupports.getKeyParams(cLass);
		Map<String, Object> record = getRecordMap(String.format("SELECT * FROM %s WHERE %s", sp.getTableName(), sp.getKeyFieldNames(EQ, and)),
				parameters);
		T result = sqlSupports.getObject(cLass, record);
		return result;
	}

	@Override
	public <T> List<T> getObjects(String sql, Class<T> cLass, Object... parameters) {
		List<Map<String, Object>> records = getRecordMaps(sql, parameters);
		List<T> result = new ArrayList<T>();
		if (records == null)
			return null;
		records.forEach(record -> {
			result.add(sqlSupports.getObject(cLass, record));
		});
		return result;
	}

	@Override
	public <T> List<T> getObjects(Class<T> cLass) {
		return getObjects(String.format("SELECT * FROM %s", sqlSupports.getKeyParams(cLass).getTableName()), cLass);
	}

	@Override
	public Boolean execute(String sql, Object... parameters) {
		PreparedStatement pstmt = null;
		try {
			pstmt = getPSTMT(sql, parameters);
			logger.debug(pstmt.toString());
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(pstmt);
		}
	}

	private static void close(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException sqle) {
			}
	}

	private static void close(PreparedStatement pstmt) {
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (SQLException sqle) {
			}
	}

	@Override
	public void close() {
		try {
			if (!conn.getAutoCommit())
				conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static final String EQ = "=?";
	public static final String and = " and ";
	public static final String comma = ", ";

	@Override
	public boolean fill(Object record) {
		KeyParams kp = new NullableParams(sqlSupports, record);
		Map<String, Object> recordMap = getRecordMap(String.format("SELECT * FROM %s WHERE %s", kp.getTableName(), kp.getKeyFieldNames(EQ, and)), kp
				.getKeyParams().toArray());
		return sqlSupports.setObject(record, recordMap);
	}

	private final static String INSERT = "INSERT %s SET %s";

	@Override
	public boolean insert(Object record) {
		KeyParams sap = sqlSupports.getKeyParams(record);
		if (sap.isEmpty())
			return false;
		String fieldsNames = sap.getIntegratedFieldNames(EQ, comma);
		String sql = String.format(INSERT, sap.getTableName(), fieldsNames);
		return execute(sql, sap.getIntegratedParams());
	}

	private final static String INSERT_IFEXISTS_UPDATE = "INSERT INTO %s (%s) VALUES (%s) ON DUPLICATE KEY UPDATE %s";

	@Override
	public boolean insertIfExistUpdate(Object record) {
		KeyParams sap = sqlSupports.getKeyParams(record);
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

	@Override
	public boolean update(Object record) {
		KeyParams sap = sqlSupports.getKeyParams(record);
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

	@Override
	public boolean delete(Object record) {
		KeyParams sap = sqlSupports.getKeyParams(record);
		if (!sap.hasKeyParams())
			return false;
		return execute(String.format(DELETE, sap.getTableName(), sap.getKeyFieldNames(EQ, and)), sap.getKeyParams().toArray());
	}

	private final static String LAST = "SELECT LAST_INSERT_ID();";

	@Override
	public BigInteger getLastKey() {
		return (BigInteger) getRecordMap(LAST, 1).get(0);
	}

}
