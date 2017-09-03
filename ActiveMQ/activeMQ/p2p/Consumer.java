package activeMQ.p2p;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消费者
 *
 */
public class Consumer {
	
	
	/**
	 * 连接
	 */
	private Connection connection;
	
	/**
	 * 消息的接收者
	 */
	private MessageConsumer consumer;
	
	/**
	 * 接收消息的线程
	 */
	private Session session;
	
	/**
	 * 初始化
	 * @throws Exception
	 */
	public void init() throws Exception{
		ConnectionFactory factory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER, 
				ActiveMQConnection.DEFAULT_PASSWORD, 
				ActiveMQConnection.DEFAULT_BROKER_URL);
		connection = factory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("RequestQueue");
		//消费者
		consumer = session.createConsumer(destination);
	}
	
	/**
	 * 处理请求
	 * @param requestMap
	 */
	public void reqeustHandler(HashMap<Serializable, Serializable> requestMap){
		System.out.println("开始处理请求...");
		for(Entry<Serializable, Serializable> entry : requestMap.entrySet()){
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		System.out.println("处理请求结束...");
	}
	
	/**
	 * 测试
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Consumer c = new Consumer();
		c.init();
		while(true){
			ObjectMessage message = (ObjectMessage) c.consumer.receive(1000);
			if (null != message) {
				System.out.println(message);
				@SuppressWarnings("unchecked")
				HashMap<Serializable, Serializable> requestMap 
					= (HashMap<Serializable, Serializable>) message.getObject();
				c.reqeustHandler(requestMap);
			}else{
				break;
			}
		}
		Util.close(c.consumer, c.session, c.connection);
	}
}
