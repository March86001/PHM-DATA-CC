package com.genertech.phm.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-activemq.xml"})
public class ActiveMqTest {

	@Test
    public void mqTest() {
		try {
			while (true) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
