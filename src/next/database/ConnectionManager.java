package next.database;

import java.sql.PreparedStatement;

public interface ConnectionManager {

	public PreparedStatement getPSTMT(String sql, Object... parameters);

	public void close();

	public void closeConnection();

}
