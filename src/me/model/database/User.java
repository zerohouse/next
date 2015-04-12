package me.model.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.matching.factor.Age;
import me.matching.factor.Factor;
import me.matching.factor.Location;
import next.database.DAO;
import next.database.annotation.Column;
import next.database.annotation.Exclude;
import next.database.annotation.Key;
import next.database.annotation.RequiredRegex;

public class User {

	@Exclude
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Key
	@RequiredRegex(EMAIL_PATTERN)
	private String email;
	private Boolean authEmail;
	@RequiredRegex("^[\\w\\W]{4,}$")
	private String password;
	@Column(DATA_TYPE = "TINYINT")
	private Integer gender;
	@Column(DATA_TYPE = "TINYINT")
	private Integer age;
	private String nickName;
	private String profileUrl;
	private String location;
	private String address;

	@Exclude
	private Map<String, Factor> factors;
	@Exclude
	List<UserLike> likes;
	@Exclude
	private Integer point;

	public Map<String, Factor> getFactors() {
		return factors;
	}

	public String getLocation() {
		return location;
	}

	public String getAddress() {
		return address;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public void setFactors(Map<String, Factor> factors) {
		this.factors = factors;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	public void defineFactors(DAO dao) {
		if (factors == null)
			factors = new HashMap<String, Factor>();
		List<Map<String, Object>> map = dao.getRecordsMap("SELECT * FROM TestResult WHERE TestResult_userEmail=?", email);
		if (map == null)
			return;
		map.forEach(each -> {
			factors.put(each.get("TestResult_name").toString(), Factor.get(each.get("TestResult_name").toString(), each.get("TestResult_result")));
		});
	}
	
	public void defineLikes(DAO dao) {
		likes = dao.getRecords(UserLike.class, "SELECT * FROM UserLike WHERE UserLike_email=?", email);
	}

	public void defineUserFactors() {
		if (factors == null)
			factors = new HashMap<String, Factor>();
		if (age == null || age == 0)
			return;
		factors.put("Age", new Age(age));
		if (location == null || location.equals(""))
			return;
		factors.put("Location", new Location(location));
	}

	@Override
	public String toString() {
		return email;
	}

	public void putFactor(String key, Factor factor) {
		if (factors == null)
			factors = new HashMap<String, Factor>();
		factors.put(key, factor);
	}

}
