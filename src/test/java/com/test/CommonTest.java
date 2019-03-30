package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.work.Application;
import com.work.rabbitmq.Sender1;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class CommonTest {

	@Autowired
	Sender1 Sender1;
	
	@Test
	public void test() throws InterruptedException{
		//Sender1.sendDirectExchanage();
		//System.out.println("返回");
		  System.out.println(Thread.currentThread().getName());
		Thread.sleep(50000);
	}
	
	
}
