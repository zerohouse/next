package next.database;

import java.sql.PreparedStatement;

/**
 * 커넥션을 관리합니다. <br>
 * Autocommit.class, Transaction.class를 사용가능합니다.<br>
 * 오토커밋은 매작업마다 커넥션풀에서 다른 커넥션을 사용하며<br>
 * 트랜젝션은 모든 작업 수행후 닫아줘야 합니다. 작업 실패시 롤백합니다.<br>
 *
 */
public interface ConnectionManager {

	public PreparedStatement getPSTMT(String sql, Object... parameters);

	public void close();

	public void closeConnection();

}
