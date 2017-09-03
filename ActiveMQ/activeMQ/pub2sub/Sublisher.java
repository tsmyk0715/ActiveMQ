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
 * 消息的订阅者
 *
 */
public class Sublisher {
	
	/**
	 * 连接
	 */
	private Connection connection;
	
	/**
	 * 接收消息的线程
	 */
	private Session session;
	
	/**
	 * 消息的消费者
	 */
	private MessageConsumer consumer;
	
	/**
	 * 初始化
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
	 * 订阅者收到的消息
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
					System.out.println("订阅者收到的消息：" + msg);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * 测试
	 * @throws JMSException 
	 */
	public static void main(String[] args) throws JMSException {
		Sublisher sub = new Sublisher();
		sub.init();
		sub.receiveMessage();
		//Util.close(sub.consumer, sub.session, sub.connection);
	}
}
