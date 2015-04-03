package me.model.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.matching.factor.Factor;
import next.database.DAO;
import next.database.annotation.Column;
import next.database.annotation.Exclude;
import next.database.annotation.Key;
import next.database.annotation.RequiredRegex;

public class User {

	@Exclude
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@RequiredRegex(EMAIL_PATTERN)
	@Key
	private String email;
	private Boolean authEmail;
	private String password;
	@Column(DATA_TYPE = "TINYINT")
	private Integer gender;
	@Column(DATA_TYPE = "TINYINT")
	private Integer age;
	private String profileUrl;

	@Exclude
	private Map<String, Factor> factors;
	@Exclude
	private Integer point;

	public Map<String, Factor> getFactors() {
		return factors;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public Boolean getAuthEmail() {
		return authEmail;
	}

	public void setAuthEmail(Boolean authEmail) {
		this.authEmail = authEmail;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public void removePassword() {
		password = null;
	}

	public void defineFactors(DAO dao) {
		factors = new HashMap<String, Factor>();
		List<Map<String, Object>> map = dao.getRecordsMap("SELECT * FROM TestResult WHERE TestResult_userEmail=?", email);
		if (age != null && age != 0)
			factors.put("AGE", Factor.get("AGE", age));
		map.forEach(each -> {
			factors.put(each.get("TestResult_name").toString(), Factor.get(each.get("TestResult_name").toString(), each.get("TestResult_result")));
		});
	}

}
