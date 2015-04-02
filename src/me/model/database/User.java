package me.model.database;

import next.database.annotation.Column;
import next.database.annotation.Exclude;
import next.database.annotation.Key;
import next.database.annotation.OtherTable;

public class User {

	@Key
	private String id;
	private String password;
	private String email;
	@Column(DATA_TYPE = "TINYINT")
	private Integer gender;
	@Column(DATA_TYPE = "TINYINT")
	private Integer age;
	private String profileUrl;

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", email=" + email + ", gender=" + gender + ", age=" + age + ", profileUrl="
				+ profileUrl + ", mbti=" + mbti + ", point=" + point + "]";
	}

	@OtherTable(COLUMN_NAME = "TestResult_result")
	private String mbti;

	public void setId(String id) {
		this.id = id;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getMbti() {
		return mbti;
	}

	public void setMbti(String mbti) {
		this.mbti = mbti;
	}

	@Exclude
	private Integer point;

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getId() {
		return id;
	}

	public Integer getAge() {
		return age;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public Integer getGender() {
		return gender;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

}
