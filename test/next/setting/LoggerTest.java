package next.setting;

import java.io.IOException;

import next.util.LoggerUtil;

import org.junit.Test;
import org.slf4j.Logger;

public class LoggerTest {

	@Test
	public void test() throws NoSuchMethodException, SecurityException, IOException {
		Logger foo = (Logger) LoggerUtil.getLogger(LoggerTest.class);
		foo.info("test");
		foo.info("test");
		foo.info("test");
	}
}
