package me.matching;

import java.util.List;

import me.model.database.Matching;
import me.model.database.User;

public class MatchedUsers {

	public MatchedUsers(User man, User woman) {
		if (man == null)
			return;
		if (man.getGender() == 1) {
			this.man = man;
			this.woman = woman;
		}
		if (man.getGender() == 2) {
			this.man = woman;
			this.woman = man;
		}
	}

	private User man;
	private User woman;

	@Override
	public String toString() {
		return "MatchedUsers [man=" + man + ", woman=" + woman + "]\n";
	}

	public Matching getMatching() {
		Matching m = new Matching();
		m.setMan(man.getEmail());
		m.setWoman(woman.getEmail());
		return m;
	}

	public User getMan() {
		return man;
	}

	public User getWoman() {
		return woman;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((man == null) ? 0 : man.hashCode());
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
		MatchedUsers other = (MatchedUsers) obj;
		if (man == null) {
			if (other.man != null)
				return false;
		} else if (!man.equals(other.man))
			return false;
		if (woman == null) {
			if (other.woman != null)
				return false;
		} else if (!woman.equals(other.woman))
			return false;
		return true;
	}

	public int isExist(List<MatchedUsers> result) {
		boolean m = false, f = false;
		for (int i = 0; i < result.size(); i++) {
			if (this.man.equals(result.get(i).getMan()))
				m = true;
			if (this.woman.equals(result.get(i).getWoman()))
				f = true;
		}
		if (m && f)
			return 3;
		if (m)
			return 1;
		if (f)
			return 2;
		return 0;

	}

}
