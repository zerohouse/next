package next.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Queue;

import next.setting.Setting;

public class ConnectionPool {

	Queue<Connection> conns;

	private static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = Setting.get("database", "url");
	private static final String ID = Setting.get("database", "id");
	private static final String PASSWORD = Setting.get("database", "password");


	public synchronized Connection getConnection() {
		Connection con = conns.poll();
		if (con != null) {
			return con;
		}
		try {
			Class.forName(COM_MYSQL_JDBC_DRIVER);
			con = DriverManager.getConnection(URL, ID, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public synchronized void returnConnection(Connection con) {
		conns.add(con);
	}
}
