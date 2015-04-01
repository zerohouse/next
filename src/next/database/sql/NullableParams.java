package next.database.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class NullableParams extends KeyParams {

	public NullableParams(Object record) {
		Class<?> cLass = record.getClass();
		SqlTable table = SqlTable.getInstance(cLass);
		tableName = table.getTableName();
		Field[] fields = cLass.getDeclaredFields();
		params = new ArrayList<FieldObject>();
		keyParams = new ArrayList<FieldObject>();
		for (int i = 0; i < fields.length; i++) {
			try {
				fields[i].setAccessible(true);
				Object param = fields[i].get(record);
				if (param == null) {
					params.add(new FieldObject(param, fields[i]));
					continue;
				}
				keyParams.add(new FieldObject(param, fields[i]));
				continue;
			} catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}

}
