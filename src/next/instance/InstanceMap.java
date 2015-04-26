package next.instance;

import java.util.HashMap;
import java.util.Map;

import next.instance.classwrapper.ClassWrapper;

public class InstanceMap {

	private Map<Class<?>, ClassWrapper> instanceMap;

	public InstanceMap() {
		instanceMap = new HashMap<Class<?>, ClassWrapper>();
	}

	public void put(Class<?> type, ClassWrapper classWrapper) {
		ClassWrapper cw = instanceMap.get(type);
		if (cw != null)
			return;
		instanceMap.put(type, classWrapper);
	}

	public ClassWrapper get(Class<?> type) {
		ClassWrapper cw = instanceMap.get(type);
		if (cw != null)
			return cw;
		cw = new ClassWrapper(type);
		instanceMap.put(type, cw);
		cw.injectAutoWired(this);
		return cw;
	}

	@Override
	public String toString() {
		return "InstanceMap [instanceMap=" + instanceMap + "]";
	}
	

}
