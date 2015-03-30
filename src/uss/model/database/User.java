package uss.model.database;

import next.database.annotation.Column;
import next.database.annotation.Key;
import next.database.annotation.OtherTable;
import next.database.annotation.RequiredRegex;

public class User {

	@Key(AUTO_INCREMENT = true)
	private Integer id;
	@RequiredRegex("^\\w+$")// 영문 숫자만 가능
	@Column(function = "unique", hasDefaultValue = false)
	private String stringId;
	private String nickName;
	private String password;
	private String profile;
	@Column(DEFAULT = "0", DATA_TYPE = "TINYINT")
	private Integer gender;

	@OtherTable
	private Integer UserPhoto_id;

	public Integer getUserPhoto_id() {
		return UserPhoto_id;
	}

	public void setUserPhoto_id(Integer userPhoto_id) {
		UserPhoto_id = userPhoto_id;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", stringId=" + stringId + ", nickName=" + nickName + ", password=" + password + ", profile=" + profile
				+ ", gender=" + gender + "]";
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStringId() {
		return stringId;
	}

	public void setStringId(String stringId) {
		this.stringId = stringId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

}
