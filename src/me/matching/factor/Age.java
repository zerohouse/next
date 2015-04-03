package me.matching.factor;

public class Age implements Factor {
	private Integer age;

	public Age(Integer age) {
		this.age = age;
	}

	public int getPoint(Factor f) {
		Age age = (Age) f;
		if (age == null)
			return 0;
		if (this.age == null)
			return 0;
		return 10 - Math.abs(this.age - age.getAge());
	}

	public Integer getAge() {
		return age;
	}

}
