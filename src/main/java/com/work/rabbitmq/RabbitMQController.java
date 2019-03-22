package com.work.rabbitmq;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class RabbitMQController {
    @Autowired
    private Sender1 sender;
    @RequestMapping(value = "/helloRabbit",method = RequestMethod.GET)
    public String sendMQ(){
        System.out.println("准备发送消息。。。");
        sender.send();
        return "success";
    }

}