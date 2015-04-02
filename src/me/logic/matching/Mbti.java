package me.logic.matching;

import me.model.database.User;

public class Mbti {

	public static String getRandMbti() {
		Double ran = Math.random() * 16;
		int val = ran.intValue();
		switch (val) {
		case 0:
			return ENFJ;
		case 1:
			return ENFP;
		case 2:
			return ENTJ;
		case 3:
			return ENTP;
		case 4:
			return ESFJ;
		case 5:
			return ESFP;
		case 6:
			return ESTJ;
		case 7:
			return ESTP;
		case 8:
			return INFJ;
		case 9:
			return INFP;
		case 10:
			return INTJ;
		case 11:
			return INTP;
		case 12:
			return ISFJ;
		case 13:
			return ISFP;
		case 14:
			return ISTJ;
		}
		return ISTP;
	}

	public static final String ENTP = "ENTP";
	public static final String ISFP = "ISFP";
	public static final String ESFJ = "ESFJ";
	public static final String INTJ = "INTJ";

	public static final String ENFJ = "ENFJ";
	public static final String ISTJ = "ISTJ";
	public static final String ESTP = "ESTP";
	public static final String INFP = "INFP";

	public static final String ESFP = "ESFP";
	public static final String INTP = "INTP";
	public static final String ENTJ = "ENTJ";
	public static final String ISFJ = "ISFJ";

	public static final String ESTJ = "ESTJ";
	public static final String INFJ = "INFJ";
	public static final String ENFP = "ENFP";
	public static final String ISTP = "ISTP";

	private String type;
	private String goodMatch;

	public Mbti(User user) {
		this.type = user.getMbti();
		switch (this.type) {
		case ENFJ:
			goodMatch = ISTJ;
			break;
		case ISTJ:
			goodMatch = ENFJ;
			break;
		case ENTP:
			goodMatch = ISFP;
			break;
		case ISFP:
			goodMatch = ENTP;
			break;
		case ESFJ:
			goodMatch = INTJ;
			break;
		case INTJ:
			goodMatch = ESFJ;
			break;
		case ESTP:
			goodMatch = INFP;
			break;
		case INFP:
			goodMatch = ESTP;
			break;
		case ESFP:
			goodMatch = INTP;
			break;
		case INTP:
			goodMatch = ESFP;
			break;
		case ENTJ:
			goodMatch = ISFJ;
			break;
		case ISFJ:
			goodMatch = ENTJ;
			break;
		case ESTJ:
			goodMatch = INFJ;
			break;
		case INFJ:
			goodMatch = ESTJ;
			break;
		case ENFP:
			goodMatch = ISTP;
			break;
		case ISTP:
			goodMatch = ENFP;
			break;

		}

	}

	public int getPoint(User user) {
		if (this.type == null)
			return -1;
		if (type == null)
			return 0;
		if (this.goodMatch.equals(user.getMbti()))
			return 2;
		if (this.type.equals(user.getMbti()))
			return 1;
		return 0;
	}

}
