package me.matching.factor;


public class Mbti implements Factor {

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

	public Mbti(String type) {
		this.type = type;
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

	@Override
	public int getPoint(Factor factor) {
		Mbti mbti = (Mbti) factor;
		if (this.type == null)
			return -10;
		if (this.goodMatch.equals(mbti.getType()))
			return 20;
		if (this.type.equals(mbti.getType()))
			return 10;
		return 0;
	}

	public String getType() {
		return type;
	}

}
