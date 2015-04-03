package next.database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import next.database.annotation.Exclude;
import next.database.sql.SqlField;

public class Parser {

	public static <T> T getObject(Class<T> cLass, Map<String, Object> record) {
		if (record == null)
			return null;
		T result = null;
		try {
			result = cLass.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		setObject(result, record);
		return result;
	}

	public static boolean setObject(Object record, Map<String, Object> recordMap) {
		if (recordMap == null)
			return false;
		Class<?> cLass = record.getClass();
		Field[] fields = cLass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			Object obj = recordMap.get(SqlField.getInstance(fields[i]).getColumnName());
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
