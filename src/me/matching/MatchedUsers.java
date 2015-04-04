package me.matching;

import me.model.database.Matching;
import me.model.database.User;

public class MatchedUsers {

	public MatchedUsers(User man, User woman) {
		this.man = man;
		this.woman = woman;
	}

	private User man;
	private User woman;

	@Override
	public String toString() {
		return "MatchedUsers [man=" + man + ", woman=" + woman + "]";
	}

	public Matching getMatching() {
		Matching m = new Matching();
		m.setMan(man.getEmail());
		m.setWoman(woman.getEmail());
		return m;
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

}
