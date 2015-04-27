package next.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import next.util.LoggerUtil;

import org.slf4j.Logger;

public class Transaction implements ConnectionManager {

	private static final Logger logger = LoggerUtil.getLogger(Autocommit.class);

	private Connection conn;

	@Override
	public PreparedStatement getPSTMT(String sql, Object... parameters) {
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

	@Override
	public void closeConnection() {
	}
}