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
public class CommonTest {

	@Autowired
	Sender1 Sender1;
	
	@Test
	public void test() throws InterruptedException{
		int num = 10;
		for (int i=0;i<num;i++){
			Sender1.sendDirectExchanage();
		}
		System.out.println(Thread.currentThread().getName());
		Thread.sleep(100000);
	}


	@Autowired
	com.work.service.ProductService ProductService;
	@Autowired
	com.work.service.ProductService2 ProductService2;

	@Test
	public void test2(){
		try {
			ProductService.save();
			ProductService.save();
			ProductService2.save();
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
