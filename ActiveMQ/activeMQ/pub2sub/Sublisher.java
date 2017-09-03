package activeMQ.pub2sub;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import activeMQ.p2p.Util;

/**
 * ��Ϣ�Ķ�����
 *
 */
public class Sublisher {
	
	/**
	 * ����
	 */
	private Connection connection;
	
	/**
	 * ������Ϣ���߳�
	 */
	private Session session;
	
	/**
	 * ��Ϣ��������
	 */
	private MessageConsumer consumer;
	
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
		consumer = session.createConsumer(topic);
	}
	
	/**
	 * �������յ�����Ϣ
	 * @return
	 * @throws JMSException 
	 */
	public void receiveMessage() throws JMSException{
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				TextMessage textMsg = (TextMessage) message;
				try {
					String msg = textMsg.getText();
					System.out.println("�������յ�����Ϣ��" + msg);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * ����
	 * @throws JMSException 
	 */
	public static void main(String[] args) throws JMSException {
		Sublisher sub = new Sublisher();
		sub.init();
		sub.receiveMessage();
		//Util.close(sub.consumer, sub.session, sub.connection);
	}
}
