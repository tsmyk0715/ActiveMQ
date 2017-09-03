package jms.controller;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jms.service.ConsumerService;
import jms.service.ProducerService;

@Controller
public class MessageController {
	
	/**
	 * 消息目的地
	 */
	 @Resource(name = "queueDestination")
	 private Destination destination;
	
	/**
	 * 消息生产者
	 */
	@Autowired
	private ProducerService producerService;
	
	/**
	 * 消息的消费者
	 */
	@Autowired
	private ConsumerService consumerService;
	
	@RequestMapping(value="/sendMessage/{msg}", method = RequestMethod.POST)
	@ResponseBody
	public void sendMessage(@PathVariable(value="msg") String message){
		System.out.println(Thread.currentThread().getName() + "开始发送消息...");
		producerService.sendMessage(message);
		System.out.println(Thread.currentThread().getName() + "发送消息结束...");
	}
	
	
	@RequestMapping(value="/receiveMessage", method = RequestMethod.GET)
	@ResponseBody
	public Object receiveMessage(){
		System.out.println(Thread.currentThread().getName() + "开始接收消息...");
		TextMessage textMsg = consumerService.receive(destination);
		System.out.println(Thread.currentThread().getName() + "结束接收消息...");
		return textMsg;
	}
}
