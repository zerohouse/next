package me.model.database;

import java.util.Date;

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

	public Matching() {

	}

	public Matching(String man, String woman, Date matchDay) {
		this.man = man;
		this.woman = woman;
		this.matchDay = matchDay;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((man == null) ? 0 : man.hashCode());
		result = prime * result + ((matchDay == null) ? 0 : matchDay.hashCode());
		result = prime * result + ((woman == null) ? 0 : woman.hashCode());
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
		Matching other = (Matching) obj;
		if (man == null) {
			if (other.man != null)
				return false;
		} else if (!man.equals(other.man))
			return false;
		if (matchDay == null) {
			if (other.matchDay != null)
				return false;
		} else if (!matchDay.equals(other.matchDay))
			return false;
		if (woman == null) {
			if (other.woman != null)
				return false;
		} else if (!woman.equals(other.woman))
			return false;
		return true;
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
