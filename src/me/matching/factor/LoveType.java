package me.matching.factor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LoveType implements Factor {

	private static Comparator<TypePercent> tComparator = new Comparator<TypePercent>() {
		@Override
		public int compare(TypePercent o1, TypePercent o2) {
			Integer i1 = o1.percent;
			Integer i2 = o2.percent;
			return (i1 > i2 ? -1 : (i1 == i2 ? 0 : 1));
		}
	};

	private String type;
	private List<TypePercent> types = new ArrayList<LoveType.TypePercent>();

	public LoveType(String result) {
		String[] split = result.split(",");
		for (int i = 0; i < split.length; i++) {
			types.add(new TypePercent(split[i]));
		}
		List<TypePercent> forSort = new ArrayList<LoveType.TypePercent>();
		forSort.addAll(this.types);
		Collections.sort(forSort, tComparator);
		switch (forSort.get(0).type) {
		case 'R':
			type = "정열적인 사랑(Passionate Love)";
			break;
		case 'B':
			type = "친구같은 사랑(Friendship Love)";
			break;
		case 'F':
			type = "소유적 사랑(Possessive Love)";
			break;
		case 'A':
			type = "헌신적 사랑(Selfless Love)";
			break;
		case 'L':
			type = "논리적 사랑(Logical Love)";
			break;
		case 'P':
			type = "유희적 사랑(Game-Playing Love)";
			break;
		}
	}

	@Override
	public int getPoint(Factor f) {
		LoveType l = (LoveType) f;
		int result = 0;
		for (int i = 0; i < types.size(); i++) {
			result += Math.abs(types.get(i).percent - l.types.get(i).percent) / 100 * 6;
		}
		return 60 - result;
	}

	@Override
	public String getType() {
		return type;
	}

	private class TypePercent {
		public TypePercent(String string) {
			type = string.charAt(0);
			percent = Integer.parseInt(string.substring(1, string.length()));
		}

		private char type;
		private int percent;
	}

	public static String getRand() {
		char[] t = { 'R', 'B', 'F', 'A', 'L', 'P' };
		String result = "";
		for (int i = 0; i < t.length; i++) {
			Double a = Math.random() * 100;
			result += String.valueOf(t[i]) + a.intValue() + ",";
		}
		return result;
	}
}
