package next.setting;

import com.jolbox.bonecp.BoneCPConfig;

public class DatabaseSetting {

	private BoneCPConfig connectionSetting;
	private CreateOption createOption = new CreateOption();

	public BoneCPConfig getConnectionSetting() {
		return connectionSetting;
	}

	public void setConnectionSetting(BoneCPConfig connectionSetting) {
		this.connectionSetting = connectionSetting;
	}

	public CreateOption getCreateOption() {
		return createOption;
	}

	public void setCreateOption(CreateOption createOption) {
		this.createOption = createOption;
	}

}
