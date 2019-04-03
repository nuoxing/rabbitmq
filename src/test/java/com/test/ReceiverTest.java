package com.test;

import com.work.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.work.Application;
import com.work.rabbitmq.Sender1;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class ReceiverTest {

	
	@Test
	public void test() throws InterruptedException{
		Thread.sleep(100000);
	}




}
