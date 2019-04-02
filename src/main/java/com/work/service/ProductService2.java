package com.work.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * 测试多个rabbittemplate实例  实现不同的回调函数  这种写的好处是 发送信息回调的归类
 * 说明: 继承RabbitTemplate.ConfirmCallback接口，而ConfirmCallback接口是用来回调消息发送成功后的方法，
 * 当一个消息被成功写入到RabbitMQ服务端时，会自动的回调RabbitTemplate.ConfirmCallback接口内的confirm方法完成通知
 */
@Service
public class ProductService2 implements  RabbitTemplate.ConfirmCallback {

    private RabbitTemplate rabbitTemplate;


    /**
     * 自动注入 prototype类型的 rabbittemplate
     * @param rabbitTemplate
     */
    public ProductService2(RabbitTemplate rabbitTemplate){
        System.out.println("调用了");
        this.rabbitTemplate=rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
    }

    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(" 消息id:" + correlationData);
        if (ack) {
            System.out.println("消息发送确认成功2");
        } else {
            System.out.println("消息发送确认失败2:" + cause);
        }
    }


    public void save( ) {
        //执行保存
        Object msg = "hello rabbitMQ:" + new Date() + " 你好，高级消息队列使用ing~";
        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        this.rabbitTemplate.convertAndSend("hello", msg, correlationId);
    }
}
