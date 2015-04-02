package me.model.database;

import java.util.Date;

import next.database.annotation.Column;
import next.database.annotation.Key;

public class Matching {
	@Key
	private String man;
	@Key
	private String woman;
	private Date matchDay;

	public Date getMatchDay() {
		return matchDay;
	}

	public void setMatchDay(Date matchDay) {
		this.matchDay = matchDay;
	}

	public String getMan() {
		return man;
	}

	public void setMan(String man) {
		this.man = man;
	}

	public String getWoman() {
		return woman;
	}

	public void setWoman(String woman) {
		this.woman = woman;
	}

}
