package me.matching.factor;

public interface Factor {

	int getPoint(Factor f);

	public static Factor get(String test, Object result) {
		switch (test) {
		case "MBTI":
			return new Mbti(result.toString());
		case "ENNEA":
			return new Enneagram(result.toString());
		}
		return null;
	};

}
