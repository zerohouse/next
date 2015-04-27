package next.instance;

import java.util.HashMap;
import java.util.Map;

import next.instance.wrapper.ClassWrapper;

public class InstancePool {

	private Map<Class<?>, ClassWrapper> instanceMap;
	private Map<String, ClassWrapper> keyMap;

	public InstancePool() {
		instanceMap = new HashMap<Class<?>, ClassWrapper>();
		keyMap = new HashMap<String, ClassWrapper>();
	}

	@Override
	public String toString() {
		return "InstanceMap [instanceMap=" + instanceMap + "]";
	}

	public Object getInstance(Class<?> type) {
		ClassWrapper cw = instanceMap.get(type);
		if (cw != null)
			return cw.getInstance();
		cw = new ClassWrapper(type, this);
		instanceMap.put(type, cw);
		return cw.getInstance();
	}

	public Object getInstance(Class<?> fieldType, String value) {
		String key = getKey(fieldType, value);
		ClassWrapper cw = keyMap.get(key);
		if (cw != null)
			return cw.getInstance();
		cw = new ClassWrapper(fieldType, value, this);
		keyMap.put(key, cw);
		return cw.getInstance();
	}
	
	private String getKey(Class<?> fieldType, String value){
		return fieldType.getName() + value;
	}

}
