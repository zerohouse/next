package me.matching.factor;

public class Age implements Factor {

	private Integer age;

	public Age(Integer age) {
		this.age = age;
	}

	@Override
	public int getPoint(Factor f) {
		Age age = (Age) f;
		return 10 - Math.abs(this.age - age.age);
	}

	@Override
	public String getType() {
		return null;
	}

}
