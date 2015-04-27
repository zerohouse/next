package next.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import next.annotation.Build;
import next.annotation.ImplementedBy;

public class DAORaw {

	@Build
	@ImplementedBy(Autocommit.class)
	private ConnectionManager cm;

	public DAORaw(ConnectionManager cm) {
		this.cm = cm;
	}

	public DAORaw() {
		this.cm = new Autocommit();
	}

	/**
	 * 하나의 레코드(Row)에 해당하는 데이터를 Map으로 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param parameters
	 *            ?에 해당하는 파라미터
	 * @return Map 레코드(Row) 맵
	 */

	public Map<String, Object> getRecord(String sql, Object... parameters) {
		Map<String, Object> record = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = cm.getPSTMT(sql, parameters);
			rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				if (record == null)
					record = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					record.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			cm.closeConnection();
		}
		return record;
	}

	/**
	 * 여러 개의 레코드(Row)에 해당하는 데이터를 Map리스트로 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param parameters
	 *            ?에 해당하는 파라미터
	 * @return Map의 리스트
	 */

	public List<Map<String, Object>> getRecords(String sql, Object... parameters) {
		List<Map<String, Object>> result = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = cm.getPSTMT(sql, parameters);
			rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				if (result == null)
					result = new ArrayList<Map<String, Object>>();
				HashMap<String, Object> columns = new HashMap<String, Object>();
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
			cm.closeConnection();
		}
		return result;
	}

	/**
	 * 하나의 레코드(Row)에 해당하는 데이터를 List로 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param parameters
	 *            ?에 해당하는 파라미터
	 * @return List 레코드(Row) 맵
	 */

	public List<Object> getRecordAsList(String sql, Object... parameters) {
		List<Object> record = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = cm.getPSTMT(sql, parameters);
			rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			if (rs.next()) {
				if (record == null)
					record = new ArrayList<Object>();
				for (int i = 1; i <= columnCount; i++) {
					record.add(rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			cm.closeConnection();
		}
		return record;
	}

	/**
	 * 여러 개의 레코드(Row)에 해당하는 데이터를 리스트로 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param parameters
	 *            ?에 해당하는 파라미터
	 * @return List records
	 */

	public List<List<Object>> getRecordsAsList(String sql, Object... parameters) {
		List<List<Object>> result = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = cm.getPSTMT(sql, parameters);
			rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				if (result == null)
					result = new ArrayList<List<Object>>();
				List<Object> columns = new ArrayList<Object>();
				for (int i = 1; i <= columnCount; i++) {
					columns.add(rs.getObject(i));
				}
				result.add(columns);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			cm.closeConnection();
		}
		return result;
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
		try {
			pstmt = cm.getPSTMT(sql, parameters);
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(pstmt);
			cm.closeConnection();
		}
	}

	public void close(ResultSet rs) {
		if (rs == null)
			return;
		try {
			rs.close();
		} catch (SQLException sqle) {
		}
	}

	public void close(PreparedStatement pstmt) {
		if (pstmt == null)
			return;
		try {
			pstmt.close();
		} catch (SQLException sqle) {
		}
	}

	public void close() {
		cm.close();
	}

}
