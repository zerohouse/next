package me.matching.factor;

import java.util.ArrayList;
import java.util.List;

public class Mbti implements Factor {

	public static String getRand() {
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
	private List<String> bestTypes = new ArrayList<String>();
	private List<String> goodTypes = new ArrayList<String>();

	public Mbti(String type) {
		this.type = type;
		switch (this.type) {
		case ENFJ:
			String[] best = { ISFJ, ENFJ, ENTJ, INFJ, ENFP, INFP };
			addAll(bestTypes, best);
			String[] good = { ESFJ, ESFP, ISFP, INTP, ISTJ, ENTP };
			addAll(goodTypes, good);
			break;
		case ISTJ:
			String[] best2 = { ESTJ, ISTJ, INTJ, ISTP, ESTP };
			addAll(bestTypes, best2);
			String[] good2 = { ENTJ, INTP, ENFJ, INFJ, ISFJ, ISFP, ENTP };
			addAll(goodTypes, good2);
			break;
		case ENTP:
			String[] best3 = { ENTP, INTP, INFJ };
			addAll(bestTypes, best3);
			String[] good3 = { ESTJ, ISTJ, ESTP, ESFP, ENTJ, ENFP, INFP, ENFJ };
			addAll(goodTypes, good3);
			break;
		case ISFP:
			String[] best4 = { ESFP, ISFP };
			addAll(bestTypes, best4);
			String[] good4 = { ESTP, ESTJ, ESFJ, ISTP, ENFJ, INFJ, INFP, ISFJ, ISTJ, ENFP };
			addAll(goodTypes, good4);
			break;
		case ESFJ:
			String[] best5 = { ESTJ, ENFP };
			addAll(bestTypes, best5);
			String[] good5 = { ISFJ, ESFJ, ENFJ, INFP, ISFP, ISTP, ESFP };
			addAll(goodTypes, good5);
			break;
		case INTJ:
			String[] best6 = { ESTJ, INTJ, ISTP, ENTJ };
			addAll(bestTypes, best6);
			String[] good6 = { INTP, INFJ, INFP, ENFP };
			addAll(goodTypes, good6);
			break;
		case ESTP:
			String[] best7 = { ISTJ, ESTP, ISTP, ESFP };
			addAll(bestTypes, best7);
			String[] good7 = { ESTJ, ISFP, ENTJ, ENTP, INTP, ISFJ };
			addAll(goodTypes, good7);
			break;
		case INFP:
			String[] best8 = { ENFP, INFP, ENFJ, INFJ };
			addAll(bestTypes, best8);
			String[] good8 = { ISFJ, ESFJ, ESFP, ISFP, ENTP, INTP };
			addAll(goodTypes, good8);
			break;
		case ESFP:
			String[] best9 = { ESTP, ISFP };
			addAll(bestTypes, best9);
			String[] good9 = { ESTJ, ESFJ, ISFJ, ESFP, ENTP, ENFJ, INFJ, ENFP, INFP };
			addAll(goodTypes, good9);
			break;
		case INTP:
			String[] best10 = { ENTP, INTP, INTJ };
			addAll(bestTypes, best10);
			String[] good10 = { ESTJ, ISTJ, ESTP, ENTJ, ENFJ, INFJ, ENFP, INFP };
			addAll(goodTypes, good10);
			break;
		case ENTJ:
			String[] best11 = { ESTJ, ISTP, ENTJ, ENFJ, INTJ };
			addAll(bestTypes, best11);
			String[] good11 = { ISTJ, ESTP, ENTP, INTP, INFJ, ENFP };
			addAll(goodTypes, good11);
			break;
		case ISFJ:
			String[] best12 = { ISFJ, ENFJ, ESTJ };
			addAll(bestTypes, best12);
			String[] good12 = { ESFJ, ESTP, ISFP, INFJ, INFP, ESFP, ISTJ, ISFP };
			addAll(goodTypes, good12);
			break;
		case ESTJ:
			String[] best13 = { ISTJ, ESFJ, ISFJ, ENTJ, INTJ, ISTP };
			addAll(bestTypes, best13);
			String[] good13 = { ENTP, INTP, ESTP, ESFP, ISFP };
			addAll(goodTypes, good13);
			break;
		case INFJ:
			String[] best14 = { ENTP, ENFP, INFJ, INFP, ENFJ };
			addAll(bestTypes, best14);
			String[] good14 = { ISFJ, ESFP, ISFP, ENTJ, INTJ, INTP, ISTJ };
			addAll(goodTypes, good14);
			break;
		case ENFP:
			String[] best15 = { INFJ, INFP, ENFJ, ENFP, ESFJ };
			addAll(bestTypes, best15);
			String[] good15 = { ENTJ, ENTP, INTJ, INTP, ESFP, ISFP };
			addAll(goodTypes, good15);
			break;
		case ISTP:
			String[] best16 = { ESTJ, ISTJ, ENTJ, ESTP };
			addAll(bestTypes, best16);
			String[] good16 = { ESFJ, ISFP, INTJ, ISFJ };
			addAll(goodTypes, good16);
			break;
		}

	}

	// http://www.massmatch.com/MBTI-2.php
	@Override
	public int getPoint(Factor factor) {
		if (this.type == null)
			return -10;
		if (this.bestTypes.contains(factor.getType()))
			return 20;
		if (this.goodTypes.contains(factor.getType()))
			return 10;
		return 0;
	}

	@Override
	public String getType() {
		return type;
	}

	private static void addAll(List<String> added, String[] add) {
		for (int i = 0; i < add.length; i++) {
			added.add(add[i]);
		}
	}

}
