package me.matching.factor;

public class Location implements Factor {

	private String location;

	public Location(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Location [location=" + location + "]";
	}

	@Override
	public int getPoint(Factor f) {
		String l = f.getType();
		String[] larray = l.split(",");
		String[] location = this.location.split(",");
		Float a = Math.abs(Float.parseFloat(larray[0]) - Float.parseFloat(location[0]));
		Float b = Math.abs(Float.parseFloat(larray[1]) - Float.parseFloat(location[1]));

		return 30 - (a.intValue() + b.intValue());
	}

	@Override
	public String getType() {
		return location;
	}

}
