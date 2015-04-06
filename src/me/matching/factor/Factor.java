package me.matching.factor;

import java.util.HashMap;
import java.util.Map;

public interface Factor {

	int getPoint(Factor f);

	static Map<String, Mbti> mbtiMap = new HashMap<String, Mbti>();
	static Map<String, Enneagram> enMap = new HashMap<String, Enneagram>();

	public static Factor get(String test, Object result) {
		switch (test) {
		case "MBTI":
			Mbti mbti = mbtiMap.get(result);
			if (mbti == null) {
				mbti = new Mbti(result.toString());
				mbtiMap.put(result.toString(), mbti);
			}
			return mbti;
		case "EnneaGram":
			Enneagram en = enMap.get(result);
			if (en == null) {
				en = new Enneagram(result.toString());
				enMap.put(result.toString(), en);
			}
			return en;
		case "LoveType":
			return new LoveType(result.toString());
		}
		return null;
	}

	String getType();;

}
