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
	 * ��ϢĿ�ĵ�
	 */
	 @Resource(name = "queueDestination")
	 private Destination destination;
	
	/**
	 * ��Ϣ������
	 */
	@Autowired
	private ProducerService producerService;
	
	/**
	 * ��Ϣ��������
	 */
	@Autowired
	private ConsumerService consumerService;
	
	@RequestMapping(value="/sendMessage/{msg}", method = RequestMethod.POST)
	@ResponseBody
	public void sendMessage(@PathVariable(value="msg") String message){
		System.out.println(Thread.currentThread().getName() + "��ʼ������Ϣ...");
		producerService.sendMessage(message);
		System.out.println(Thread.currentThread().getName() + "������Ϣ����...");
	}
	
	
	@RequestMapping(value="/receiveMessage", method = RequestMethod.GET)
	@ResponseBody
	public Object receiveMessage(){
		System.out.println(Thread.currentThread().getName() + "��ʼ������Ϣ...");
		TextMessage textMsg = consumerService.receive(destination);
		System.out.println(Thread.currentThread().getName() + "����������Ϣ...");
		return textMsg;
	}
}
