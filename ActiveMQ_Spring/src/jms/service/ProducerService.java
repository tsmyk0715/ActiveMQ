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
	 * ������Ϣ��ָ����Ŀ�ĵ�
	 * @param destination
	 * @param message
	 */
	public void sendMessage(Destination destination, final String message){
		System.out.println(Thread.currentThread().getName() 
				+ " ����� " + destination.toString() + "������Ϣ��" + message);
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
	
	/**
	 * ������Ϣ��Ĭ�ϵ�Ŀ�ĵ�
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
