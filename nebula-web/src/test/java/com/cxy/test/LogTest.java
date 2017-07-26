package com.cxy.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {

	private Logger logger = LoggerFactory.getLogger(LogTest.class);
	
	@Test
	public void test(){

		logger.trace("trace");
		logger.debug("debug");
		logger.warn("warn");
		logger.info("info");
		logger.error("error");
		
	}
}
