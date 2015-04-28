package next.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import next.util.LoggerUtil;

import org.slf4j.Logger;


/**
 * 매 작업마다 다른 커넥션을 사용합니다.<br>
 * 트랜젝션을 사용하지 않습니다.
 *
 */
public class Autocommit implements ConnectionManager {

	private static final Logger logger = LoggerUtil.getLogger(Autocommit.class);

	private Connection conn;

	@Override
	public PreparedStatement getPSTMT(String sql, Object... parameters) {
		conn = ConnectionPool.getConnection(true);
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
	}

	@Override
	public void closeConnection() {
		if (conn == null)
			return;
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
