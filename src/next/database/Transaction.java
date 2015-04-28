package next.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import next.util.LoggerUtil;

import org.slf4j.Logger;

/**
 * close 명령이 실행될때 작업을 실행합니다.<br>
 * 트랜젝션을 사용합니다.
 *
 */
public class Transaction implements ConnectionManager {

	private static final Logger logger = LoggerUtil.getLogger(Transaction.class);

	private Connection conn;

	@Override
	public PreparedStatement getPSTMT(String sql, Object... parameters) {
		if (conn == null)
			conn = ConnectionPool.getConnection(false);
		PreparedStatement pstmt = null;
		logger.debug(sql, parameters);
		try {
			pstmt = conn.prepareStatement(sql);
			if (parameters != null)
				for (int j = 0; j < parameters.length; j++) {
					pstmt.setObject(j + 1, parameters[j]);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
