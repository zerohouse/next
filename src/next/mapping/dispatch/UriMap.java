package next.mapping.dispatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import next.mapping.http.Http;

public class UriMap {

	private Map<UriKey, List<MethodHolder>> match = new HashMap<UriKey, List<MethodHolder>>();
	private Map<String, List<Pattern>> regexMap = new HashMap<String, List<Pattern>>();
	private Map<Pattern, List<MethodHolder>> PatternMap = new HashMap<Pattern, List<MethodHolder>>();

	public void put(UriKey key, List<MethodHolder> methodList) {
		if (!key.contains("{}")) {
			match.put(key, methodList);
			return;
		}
		String regex = key.getUri().replace("{}", "(.*)");
		Pattern pattern = Pattern.compile(regex);
		List<Pattern> regexList = regexMap.get(key.getMethod());
		if (regexList == null) {
			regexList = new LinkedList<Pattern>();
			regexMap.put(key.getMethod(), regexList);
		}
		regexList.add(pattern);
		PatternMap.put(pattern, methodList);
	}

	public List<MethodHolder> get(UriKey key, Http http) {
		List<MethodHolder> methodArray = match.get(key);
		if (methodArray != null)
			return methodArray;
		List<Pattern> regexList = regexMap.get(key.getMethod());
		if (regexList == null)
			return null;
		for (int i = 0; i < regexList.size(); i++) {
			Matcher matcher = regexList.get(i).matcher(key.getUri());
			if (matcher.matches()) {
				methodArray = PatternMap.get(regexList.get(i));
				for (int j = 1; j < matcher.groupCount() + 1; j++) {
					http.addUriVariable(matcher.group(j));
				}
				return methodArray;
			}
		}
		return null;
	}
	
	
}
