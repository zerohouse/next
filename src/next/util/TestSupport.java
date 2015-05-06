package next.util;

import next.instance.InstancePool;
import next.instance.wrapper.ClassWrapper;

public class TestSupport {

	private static InstancePool instancePool;

	public static Object getInstance(Class<?> cLass) {
		if (instancePool == null)
			instancePool = new InstancePool();
		ClassWrapper cip = new ClassWrapper(cLass, instancePool);
		return cip.getInstance();
	}
}
