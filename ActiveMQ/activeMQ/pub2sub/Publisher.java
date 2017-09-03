package activeMQ.pub2sub;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import activeMQ.p2p.Util;

/**
 * ����/����ģʽ
 * 
 * ��Ϣ�ķ�����
 */
public class Publisher {
	
	/**
	 * ��Ϣ�ķ�����
	 */
	private MessageProducer producer;
	
	/**
	 * ������Ϣ���߳�
	 */
	private Session session;
	
	/**
	 * JMS�ͻ��˵�JMS Provider������
	 */
	private Connection connection;
	
	/**
	 * ��ʼ��
	 * @throws JMSException
	 */
	public void init() throws JMSException{
		ConnectionFactory factory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER, 
				ActiveMQConnection.DEFAULT_PASSWORD, 
				ActiveMQConnection.DEFAULT_BROKER_URL);
		connection = factory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("MessageTopic");
		producer = session.createProducer(topic);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	}
	
	/**
	 * ������Ϣ
	 * @throws JMSException 
	 */
	public void submit(String message) throws JMSException{
		TextMessage msg = session.createTextMessage();
		msg.setText(message);
		producer.send(msg);
	}
	
	/**
	 * ����
	 * @throws JMSException 
	 */
	public static void main(String[] args) throws JMSException {
		Publisher publisher = new Publisher();
		publisher.init();
		String message = "i'm a student";
		publisher.submit(message);
		Util.close(publisher.producer, publisher.session, publisher.connection);
	}
}
