package me.matching.factor;

public class Enneagram implements Factor {

	private Integer result;

	public Enneagram(String type) {
		this.result = Integer.parseInt(type);
	}

	@Override
	public int getPoint(Factor f) {
		Enneagram en = (Enneagram) f;
		int gap = Math.abs(result - en.getType());
		if (result > 4)
			gap = 9 - result;
		return 20 - gap * 4;
	}

	private Integer getType() {
		return result;
	}

}
