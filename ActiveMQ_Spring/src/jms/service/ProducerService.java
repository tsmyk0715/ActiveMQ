package jms.service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	/**
	 * 发送消息到指定的目的地
	 * @param destination
	 * @param message
	 */
	public void sendMessage(Destination destination, final String message){
		System.out.println(Thread.currentThread().getName() 
				+ " 向队列 " + destination.toString() + "发送消息：" + message);
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
	
	/**
	 * 发送消息到默认的目的地
	 * @param message
	 */
	public void sendMessage(final String message){
		jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
	}
}
