package next.database.sql;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import next.database.annotation.Exclude;
import next.database.annotation.OtherTable;
import next.database.annotation.Table;
import next.setting.Setting;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

public class SqlSupports {

	private Map<Class<?>, KeyParams> keyParamsMap;
	private Map<Field, SqlField> sqlFieldMap;
	private Map<Class<?>, String> tableNameMap;

	private static SqlSupports sqlSupports = new SqlSupports();

	public static SqlSupports getInstance() {
		return sqlSupports;
	}

	private SqlSupports() {
		keyParamsMap = new HashMap<Class<?>, KeyParams>();
		sqlFieldMap = new HashMap<Field, SqlField>();
		tableNameMap = new HashMap<Class<?>, String>();

		Reflections ref = new Reflections(Setting.get().getDatabase().getModelPackage(), new SubTypesScanner(), new TypeAnnotationsScanner());
		ref.getTypesAnnotatedWith(Table.class).forEach(cLass -> {

			String tableName = cLass.getSimpleName();
			if (cLass.isAnnotationPresent(Table.class)) {
				Table table = cLass.getAnnotation(Table.class);
				if (!table.value().equals(""))
					tableName = table.value();
			}
			tableNameMap.put(cLass, tableName);

			// 필드 먼저 생성하고 넘겨줘야댐. KeyParams 생성할때 생성된 Sql필드 사용함.
				Field[] fields = cLass.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					if (fields[i].isAnnotationPresent(OtherTable.class)) {
						sqlFieldMap.put(fields[i], new SqlFieldOtherTable(fields[i]));
					} else {
						sqlFieldMap.put(fields[i], new SqlFieldNormal(tableName, fields[i]));
					}
				}
				keyParamsMap.put(cLass, new KeyParams(cLass, this, tableName));
			});

	}

	public KeyParams getKeyParams(Class<?> cLass) {
		return keyParamsMap.get(cLass);
	}

	public KeyParams getKeyParams(Object record) {
		return new KeyParams(record, this, tableNameMap.get(record.getClass()));
	}

	public SqlField getSqlField(Field field) {
		return sqlFieldMap.get(field);
	}

	public String getTableName(Class<?> cLass) {
		return keyParamsMap.get(cLass).getTableName();
	}

	public <T> T getObject(Class<T> cLass, Map<String, Object> record) {
		if (record == null)
			return null;
		T result = null;
		result = newInstance(cLass);
		setObject(result, record);
		return result;
	}

	@SuppressWarnings("unchecked")
	private <T> T newInstance(Class<T> cLass) {
		List<Object> params = new ArrayList<Object>();
		Class<?>[] paramTypes = cLass.getConstructors()[0].getParameterTypes();
		if (paramTypes.length == 0)
			try {
				return cLass.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e1) {
				return null;
			}
		Object obj = null;
		for (int i = 0; i < paramTypes.length; i++) {
			// obj = check(paramTypes[i]);
			params.add(obj);
		}
		try {
			return (T) cLass.getConstructors()[0].newInstance(params.toArray());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			return null;
		}
	}

	Object check(Class<?> paramTypes) {
		if (paramTypes.equals(byte.class)) {
			return 0;
		}
		if (paramTypes.equals(byte.class)) {
			return 0;
		}
		if (paramTypes.equals(short.class)) {
			return 0;
		}
		if (paramTypes.equals(int.class)) {
			return 0;
		}
		if (paramTypes.equals(long.class)) {
			return 0L;
		}
		if (paramTypes.equals(float.class)) {
			return 0.0f;
		}
		if (paramTypes.equals(double.class)) {
			return 0.0d;
		}
		if (paramTypes.equals(char.class)) {
			return '\u0000';
		}
		if (paramTypes.equals(boolean.class)) {
			return false;
		}
		return null;
	}

	public boolean setObject(Object record, Map<String, Object> recordMap) {
		if (recordMap == null)
			return false;
		Class<?> cLass = record.getClass();
		Field[] fields = cLass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			Object obj = recordMap.get(getSqlField(fields[i]).getColumnName());
			if (obj == null)
				continue;
			if (obj.getClass().equals(Timestamp.class)) {
				obj = new Date(((Timestamp) obj).getTime());
			}
			fields[i].setAccessible(true);
			try {
				fields[i].set(record, obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}
