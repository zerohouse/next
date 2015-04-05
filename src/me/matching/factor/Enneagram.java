package me.matching.factor;

import java.util.ArrayList;
import java.util.List;

public class Enneagram implements Factor {

	private String type;
	private List<String> bestTypes = new ArrayList<String>();
	private List<String> goodTypes = new ArrayList<String>();
	private List<String> badTypes = new ArrayList<String>();

	public Enneagram(String type) {
		this.type = type;
		switch (type) {
		case "M1":
			bestTypes.add("F5");
			goodTypes.add("F3");
			goodTypes.add("F7");
			badTypes.add("F1");
			break;
		case "M2":
			goodTypes.add("F1");
			goodTypes.add("F4");
			badTypes.add("F2");
			break;
		case "M3":
			goodTypes.add("F6");
			goodTypes.add("F9");
			badTypes.add("F5");
			break;
		case "M4":
			bestTypes.add("F8");
			goodTypes.add("F2");
			badTypes.add("F5");
			badTypes.add("F9");
			break;
		case "M5":
			goodTypes.add("F1");
			badTypes.add("F4");
			badTypes.add("F8");
			break;
		case "M6":
			goodTypes.add("F2");
			goodTypes.add("F9");
			badTypes.add("F4");
			badTypes.add("F6");
			break;
		case "M7":
			goodTypes.add("F1");
			badTypes.add("F6");
			badTypes.add("F7");
			break;
		case "M8":
			goodTypes.add("F2");
			goodTypes.add("F6");
			badTypes.add("F1");
			badTypes.add("F3");
			badTypes.add("F5");
			break;
		case "M9":
			bestTypes.add("F8");
			goodTypes.add("F4");
			badTypes.add("F2");
			badTypes.add("F9");
			break;
			
		case "F1":
			goodTypes.add("M2");
			goodTypes.add("M5");
			goodTypes.add("M7");
			badTypes.add("M1");
			badTypes.add("M8");
			break;
		case "F2":
			goodTypes.add("M4");
			goodTypes.add("M6");
			goodTypes.add("M8");
			badTypes.add("M2");
			badTypes.add("M9");
			break;
		case "F3":
			goodTypes.add("M1");
			badTypes.add("M8");
			break;
		case "F4":
			goodTypes.add("M2");
			goodTypes.add("M9");
			badTypes.add("M5");
			badTypes.add("M6");
			break;
		case "F5":
			bestTypes.add("M1");
			badTypes.add("M3");
			badTypes.add("M4");
			badTypes.add("M8");
			break;
		case "F6":
			goodTypes.add("M3");
			goodTypes.add("M8");
			badTypes.add("M6");
			badTypes.add("M7");
			break;
		case "F7":
			goodTypes.add("M1");
			badTypes.add("M7");
			break;
		case "F8":
			bestTypes.add("M4");
			bestTypes.add("M9");
			badTypes.add("M5");
			break;
		case "F9":
			goodTypes.add("M3");
			goodTypes.add("M6");
			badTypes.add("M4");
			badTypes.add("M9");
			break;
		}
	}

	// http://www.9types.com/writeup/enneagram_relationships.php
	@Override
	public int getPoint(Factor f) {
		if (this.type == null)
			return -10;
		if (this.bestTypes.contains(f.getType()))
			return 25;
		if (this.goodTypes.contains(f.getType()))
			return 15;
		if (this.badTypes.contains(f.getType()))
			return 0;
		return 8;
	}

	@Override
	public String getType() {
		return type;
	}

	public static String getRand() {
		Double a = Math.random() * 9 + 1;
		return "" + a.intValue();
	}

}
