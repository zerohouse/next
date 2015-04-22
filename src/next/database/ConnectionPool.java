package next.database;

import java.sql.Connection;
import java.sql.SQLException;

import next.setting.Setting;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class ConnectionPool {

	private static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";

	private BoneCP pool;

	public Connection getConnection(boolean autocommit) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			if (!autocommit)
				connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void shutdown() {
		pool.shutdown();
	}

	public ConnectionPool() {
		try {
			Class.forName(COM_MYSQL_JDBC_DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			BoneCPConfig config = Setting.get().getDatabase().getConnectionSetting();
			pool = new BoneCP(config);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
