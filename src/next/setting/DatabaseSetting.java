package next.setting;

import com.jolbox.bonecp.BoneCPConfig;

public class DatabaseSetting {

	private String modelPackage;
	private String testDataPackage;
	private BoneCPConfig connectionSetting;
	private CreateOption createOption = new CreateOption();

	public String getModelPackage() {
		return modelPackage;
	}

	public void setModelPackage(String modelPackage) {
		this.modelPackage = modelPackage;
	}

	public String getTestDataPackage() {
		return testDataPackage;
	}

	public void setTestDataPackage(String testDataPackage) {
		this.testDataPackage = testDataPackage;
	}

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
