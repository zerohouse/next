package next.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import next.util.LoggerUtil;

import org.slf4j.Logger;

public class Transaction extends DAO {

	private static final Logger logger = LoggerUtil.getLogger(DAO.class);

	private Connection conn;

	@Override
	public LinkedHashMap<String, Object> getRecord(String sql, Object... parameters) {
		LinkedHashMap<String, Object> record = null;
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
	public List<LinkedHashMap<String, Object>> getRecords(String sql, Object... parameters) {
		List<LinkedHashMap<String, Object>> result = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = getPSTMT(sql, parameters);
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
		}
		return result;
	}

	private PreparedStatement getPSTMT(String sql, Object... parameters) {
		if (conn == null)
			conn = ConnectionPool.getConnection(false);
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
	public Boolean execute(String sql, Object... parameters) {
		PreparedStatement pstmt = null;
		try {
			pstmt = getPSTMT(sql, parameters);
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(pstmt);
		}
	}

	public void close() {
		if (conn == null)
			return;
		try {
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

}
