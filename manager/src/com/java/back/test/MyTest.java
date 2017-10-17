package com.java.back.test;

import java.io.Serializable;
//import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.java.back.redis.RedisSpringProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class MyTest extends RedisSpringProxy {

	@Test
	public void redis() {
		// flushDB();
		Set<Serializable> allKeys = getAllKeys();
		for (Serializable serializable : allKeys) {
			System.out.println(serializable);
			if (!serializable.toString().contains("~")) {
//				Object read = read(serializable.toString());
//				System.out.println(read.toString());
			}

		}
		// Long delete = delete("findMenu~keys");
		// System.out.println(delete);
	}

}
