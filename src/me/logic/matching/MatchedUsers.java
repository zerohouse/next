package me.logic.matching;

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
}
