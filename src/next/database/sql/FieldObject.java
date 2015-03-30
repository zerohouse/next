package next.database.sql;

import java.lang.reflect.Field;

import next.database.exception.RegexNotMatchedException;

public class FieldObject {

	Object param;
	SqlField field;

	public FieldObject(Object param, Field field) {
		this.param = param;
		this.field = SqlField.getInstance(field);
		if (param == null)
			return;
		if (!RegexCheck.check(param, field))
			throw new RegexNotMatchedException();
	}

	public Object getParam() {
		return param;
	}

	public String getColumnName() {
		return field.getColumnName();
	}

}
