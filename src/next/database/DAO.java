package next.database;

import java.util.LinkedHashMap;
import java.util.List;

public interface DAO {

	/**
	 * 하나의 레코드(Row)에 해당하는 데이터를 LinkedHashMap으로 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param parameters
	 *            ?에 해당하는 파라미터
	 * @return LinkedHashMap 레코드(Row) 맵
	 */

	LinkedHashMap<String, Object> getRecord(String sql, Object... parameters);

	/**
	 * 여러 개의 레코드(Row)에 해당하는 데이터를 LinkedHashMap의 리스트로 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param parameters
	 *            ?에 해당하는 파라미터
	 * @return LinkedHashMap의 리스트
	 */
	List<LinkedHashMap<String, Object>> getRecords(String sql, Object... parameters);

	/**
	 * SQL에 해당하는 레코드를 Object로 파싱하여 레코드(Row)에 해당하는 Object를 리턴합니다. 레코드의 어노테이션을 통해
	 * 칼럼명을 지정할 수 있습니다. 기본 칼럼명은 [클래스명]_[필드명]으로 지정됩니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param <T>
	 *            클래스 타입
	 * @param cLass
	 *            클래스 타입
	 * @param parameters
	 *            SQL 파라미터
	 * @return Object
	 */
	<T> T getObject(String sql, Class<T> cLass, Object... parameters);

	/**
	 * 클래스에 해당하는 SQL을 생성하여 Object를 찾습니다. 해당 클래스의 키, 칼럼등 어노테이션 지정이 필요합니다.
	 * <p>
	 *
	 *
	 * @param <T>
	 *            클래스 타입
	 * @param cLass
	 *            클래스 타입
	 * @param parameters
	 *            Key에 해당하는 파라미터
	 * @return Object
	 */
	<T> T getObject(Class<T> cLass, Object... parameters);

	/**
	 * SQL에 해당하는 Object를 리스트로 만들어 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            sql문
	 * @param <T>
	 *            클래스 타입
	 * @param cLass
	 *            클래스 타입
	 * @param parameters
	 *            ?에 파싱할 파라미터
	 * @return ObjectList
	 */
	<T> List<T> getObjects(String sql, Class<T> cLass, Object... parameters);

	/**
	 * SQL에 해당하는 Object를 리스트로 만들어 리턴합니다.
	 * <p>
	 *
	 * @param <T>
	 *            클래스 타입
	 * @param cLass
	 *            클래스 타입
	 * @return List
	 */
	<T> List<T> getObjects(Class<T> cLass);

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
	Boolean execute(String sql, Object... parameters);

	/**
	 * DAO의 connection을 커넥션풀에 리턴합니다. 모든 쿼리 실행 후 반드시 실행해야 합니다.
	 */
	void close();

	/**
	 * Object를 DB의 데이터로 채웁니다. Object의 키 필드가 정의되어 있어야 합니다.
	 * <p>
	 *
	 * @param object
	 *            채울 object
	 * @return boolean 실행결과
	 */
	boolean fill(Object object);

	/**
	 * Object를 DB에 삽입합니다.
	 * <p>
	 *
	 * @param object
	 *            삽입할 object
	 * @return boolean 실행결과
	 */

	boolean insert(Object object);

	/**
	 * Object를 DB에 삽입합니다. 같은 키를 가진 레코드가 있으면, 업데이트합니다.
	 * <p>
	 *
	 * @param object
	 *            삽입 / 업데이트할 object
	 * @return boolean 실행결과
	 */
	boolean insertIfExistUpdate(Object object);

	/**
	 * Object를 업데이트합니다. 같은 키를 가진 레코드를 업데이트합니다.
	 * <p>
	 *
	 * @param object
	 *            업데이트할 object
	 * @return boolean 실행결과
	 */
	boolean update(Object object);

	/**
	 * Object를 삭제합니다. 같은 키를 가진 레코드를 삭제합니다.
	 * <p>
	 *
	 * @param object
	 *            삭제할 object
	 * @return boolean 실행결과
	 */
	boolean delete(Object object);

}
