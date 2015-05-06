package next.util;

import next.instance.InstancePool;
import next.instance.wrapper.ClassWrapper;

public class TestSupport {

	private static InstancePool instancePool;

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> cLass) {
		if (instancePool == null)
			instancePool = new InstancePool();
		ClassWrapper cip = new ClassWrapper(cLass, instancePool);
		return (T) cip.getInstance();
	}
}
