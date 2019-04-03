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
public class ProductServiceTest {




	@Autowired
	com.work.service.ProductService ProductService;
	

	@Test
	public void test2() throws Exception{
		try {
			ProductService.sendMessageByVo();
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void test3() throws Exception{
		try {
			ProductService.sendUser();
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
