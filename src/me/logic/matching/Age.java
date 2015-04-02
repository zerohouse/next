package me.logic.matching;

public class Age {
	private Integer age;

	public Age(Integer age) {
		this.age = age;
	}

	public int getPoint(Integer age) {
		if (age == null)
			return 0;
		if (this.age == null)
			return -1;
		return -Math.abs(this.age - age);
	}

}
