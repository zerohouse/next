package next.setting;

public class CreateOption {

	private Boolean createTablesOnServerStart;
	private Boolean resetTablesOnServerStart;
	private Boolean insertDataOnServerStart;
	private String table_suffix;
	private TableOptions stringOptions;
	private TableOptions integerOptions;
	private TableOptions booleanOptions;
	private TableOptions dateOptions;
	private TableOptions floatOptions;
	private TableOptions longOptions;

	public CreateOption() {
		this.createTablesOnServerStart = true;
		this.resetTablesOnServerStart = false;
		this.insertDataOnServerStart = false;
		this.table_suffix = "ENGINE = InnoDB DEFAULT CHARACTER SET utf8";
		this.stringOptions = new TableOptions("VARCHAR(255)", true, true, "");
		this.integerOptions = new TableOptions("INTEGER", true, true, 0);
		this.booleanOptions = new TableOptions("TINYINT(1)", true, true, 0);
		this.dateOptions = new TableOptions("DATETIME", true, true, "CURRENT_TIMESTAMP");
		this.floatOptions = new TableOptions("FLOAT", true, true, 0);
		this.longOptions = new TableOptions("BIGINT", true, true, 0);
	}

	public Boolean getCreateTablesOnServerStart() {
		return createTablesOnServerStart;
	}

	public void setCreateTablesOnServerStart(Boolean createTablesOnServerStart) {
		this.createTablesOnServerStart = createTablesOnServerStart;
	}

	public Boolean getResetTablesOnServerStart() {
		return resetTablesOnServerStart;
	}

	public void setResetTablesOnServerStart(Boolean resetTablesOnServerStart) {
		this.resetTablesOnServerStart = resetTablesOnServerStart;
	}

	public Boolean getInsertDataOnServerStart() {
		return insertDataOnServerStart;
	}

	public void setInsertDataOnServerStart(Boolean insertDataOnServerStart) {
		this.insertDataOnServerStart = insertDataOnServerStart;
	}

	public String getTable_suffix() {
		return table_suffix;
	}

	public void setTable_suffix(String table_suffix) {
		this.table_suffix = table_suffix;
	}

	public TableOptions getStringOptions() {
		return stringOptions;
	}

	public void setStringOptions(TableOptions stringOptions) {
		this.stringOptions = stringOptions;
	}

	public TableOptions getIntegerOptions() {
		return integerOptions;
	}

	public void setIntegerOptions(TableOptions integerOptions) {
		this.integerOptions = integerOptions;
	}

	public TableOptions getBooleanOptions() {
		return booleanOptions;
	}

	public void setBooleanOptions(TableOptions booleanOptions) {
		this.booleanOptions = booleanOptions;
	}

	public TableOptions getDateOptions() {
		return dateOptions;
	}

	public void setDateOptions(TableOptions dateOptions) {
		this.dateOptions = dateOptions;
	}

	public TableOptions getFloatOptions() {
		return floatOptions;
	}

	public void setFloatOptions(TableOptions floatOptions) {
		this.floatOptions = floatOptions;
	}

	public TableOptions getLongOptions() {
		return longOptions;
	}

	public void setLongOptions(TableOptions longOptions) {
		this.longOptions = longOptions;
	}

}
