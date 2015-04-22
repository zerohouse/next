package next.database.sql;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.regex.Pattern;

import next.database.annotation.Column;
import next.database.annotation.Key;
import next.database.annotation.RequiredRegex;
import next.setting.CreateOption;
import next.setting.Setting;
import next.setting.TableOptions;

public class SqlFieldNormal implements SqlField {

	SqlFieldNormal(String tableName, Field field) {
		this.tableName = tableName;
		this.field = field;
		setCondition();
		setFieldString();
		if (!field.isAnnotationPresent(RequiredRegex.class))
			return;
		String regex = field.getAnnotation(RequiredRegex.class).value();
		pattern = Pattern.compile(regex);
	}

	public boolean check(Object param) {
		if (pattern == null)
			return true;
		if (pattern.matcher(param.toString()).matches()) {
			return true;
		}
		return false;
	}

	private Pattern pattern;
	private String tableName;
	private Field field;
	private String columnName;

	private String fieldString;

	@Override
	public String getColumnName() {
		return columnName;
	}

	public String getWrappedColumnName() {
		return "`" + columnName + "`";
	}

	public String getFieldString() {
		return fieldString;
	}

	private final static String SPACE = " ";

	private void setCondition() {
		Class<?> t = field.getType();
		CreateOption options = Setting.get().getDatabase().getCreateOption();
		if (t.equals(Integer.class) || t.equals(int.class)) {
			setSettings(options.getIntegerOptions());
		} else if (t.equals(String.class)) {
			TableOptions to = options.getStringOptions();
			if (to.getDefaultValue().equals(""))
				to.setDefaultValue("''");
			setSettings(to);
		} else if (t.equals(Date.class)) {
			setSettings(options.getDateOptions());
		} else if (t.equals(long.class) || t.equals(Long.class)) {
			setSettings(options.getLongOptions());
		} else if (t.equals(float.class) || t.equals(Float.class)) {
			setSettings(options.getFloatOptions());
		} else if (t.equals(boolean.class) || t.equals(Boolean.class)) {
			setSettings(options.getBooleanOptions());
		}
	}

	private String defaultValue;
	private String nullType;
	private String type;

	private void setSettings(TableOptions options) {
		defaultValue = "";
		nullType = "NULL";
		this.type = options.getDataType();
		if (!options.getNotNull())
			return;
		nullType = "NOT " + nullType;
		if (!options.getHasDefaultValue())
			return;
		String defaultvalue = options.getDefaultValue().toString();
		defaultValue += "DEFAULT " + defaultvalue;
	}

	private void setFieldString() {
		fieldString = "";
		columnName = tableName + "_" + field.getName();
		if (!field.isAnnotationPresent(Column.class)) {
			fieldString += getWrappedColumnName() + SPACE + type + SPACE;
			if (field.isAnnotationPresent(Key.class) && field.getAnnotation(Key.class).AUTO_INCREMENT()) {
				fieldString += "AUTO_INCREMENT" + SPACE + "NOT NULL";
				return;
			}
			fieldString += nullType;
			if (field.isAnnotationPresent(Key.class))
				return;
			fieldString += SPACE + defaultValue;
			return;
		}
		Column column = field.getAnnotation(Column.class);
		if (!column.value().equals(""))
			columnName = column.value();
		fieldString += getWrappedColumnName() + SPACE;

		if (column.DATA_TYPE().equals(""))
			fieldString += type + SPACE;
		else
			fieldString += column.DATA_TYPE() + SPACE;

		if (field.isAnnotationPresent(Key.class) && field.getAnnotation(Key.class).AUTO_INCREMENT()) {
			fieldString += "AUTO_INCREMENT" + SPACE;
			return;
		}

		if (column.NULL())
			fieldString += "NULL" + SPACE;
		else
			fieldString += "NOT NULL" + SPACE;

		if (field.isAnnotationPresent(Key.class))
			return;
		if (!column.hasDefaultValue())
			return;
		if (!column.DEFAULT().equals(""))
			fieldString += "DEFAULT" + SPACE + column.DEFAULT();
		else
			fieldString += defaultValue;

	}

}