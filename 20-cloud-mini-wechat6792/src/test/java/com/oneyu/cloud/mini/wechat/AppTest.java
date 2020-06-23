package com.oneyu.cloud.mini.wechat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

/**
 * Unit test for simple App.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AppTest 
    extends TestCase
{
	
	@Value("${wechat.seckey}")
	String seckey;
	
	@Test
	public void printNacosValue() {
		log.info(seckey);
	}

}
