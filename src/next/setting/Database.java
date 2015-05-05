package next.setting;

import com.jolbox.bonecp.BoneCPConfig;

public class Database {

	private BoneCPConfig connectionSetting;
	private TableCreate createOption = new TableCreate();

	public BoneCPConfig getConnectionSetting() {
		return connectionSetting;
	}

	public void setConnectionSetting(BoneCPConfig connectionSetting) {
		this.connectionSetting = connectionSetting;
	}

	public TableCreate getCreateOption() {
		return createOption;
	}

	public void setCreateOption(TableCreate createOption) {
		this.createOption = createOption;
	}

}
