package activeMQ.p2p;

import java.io.Serializable;
import java.util.HashMap;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * acitveMQ的 点对点 模式
 * 生产者
 */
public class Producer {
	
	/*
	 * 步骤：
	 *  1.获得JMS connection factory.
	 *	2.利用factory构造JMS connection
	 *	3.启动connection
	 *	4.通过connection创建JMS session.
	 *	5.指定JMS destination.
	 *	6.创建JMS producer或者创建JMS message并提供destination.
	 *	7.创建JMS consumer或注册JMS message listener.
	 *	8.发送和接收JMS message.
	 *	9.关闭所有JMS资源，包括connection, session, producer, consumer等。
	 */
	
	/**
	 * 一个连接
	 */
	private Connection connection ;
	
	/**
	 * 消息发送者
	 */
	private MessageProducer producer;
	
	/**
	 * 发送消息的线程
	 */
	private Session session;
	
	/**
	 * 初始化
	 * @throws Exception
	 */
	public void init() throws Exception{
		//连接工厂，JMS用它创建
		ConnectionFactory factory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER, 
				ActiveMQConnection.DEFAULT_PASSWORD,
				ActiveMQConnection.DEFAULT_BROKER_URL);
		
		//Connection：JMS客户端到JMS Provider的连接，从构造工厂中得到连接对象
		connection = factory.createConnection();
		// 启动
		connection.start();
		//获取连接操作
		//第一个boolean类型的参数用来表示是否采用事务消息。
		//如果是事务消息，对于的参数设置为true，此时消息的提交自动有comit处理，
		//消息的回滚则自动由rollback处理
		//第二个int类型的参数表示消息的确认方式：
		//有Session.AUTO_ACKNOWLEDGE表示Session会自动确认所接收到的消息。
		//Session.CLIENT_ACKNOWLEDGE表示由客户端程序通过调用消息的确认方法来确认所接收到的消息。
		//Session.DUPS_OK_ACKNOWLEDGE使得Session将“懒惰”地确认消息，即不会立即确认消息，这样有可能导致消息重复投递。
		session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		Queue queue =  session.createQueue("RequestQueue");
		//创建消息的发送者
		producer = session.createProducer(queue);
		//设置消息的不持久化
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	}
	
	/**
	 * 发送消息
	 * @param requestMap
	 * @throws Exception
	 */
	public void submit(HashMap<Serializable, Serializable> requestMap) throws Exception{
		// 创建要发送的消息
		ObjectMessage message = session.createObjectMessage(requestMap);
		// 发送消息
		producer.send(message);
		session.commit();
	}
	
	// 测试
	public static void main(String[] args) throws Exception{
		Producer p = new Producer();
		//初始化
		p.init();
		HashMap<Serializable, Serializable> paramMap = new HashMap<>();
		paramMap.put("zs", "张三");
		paramMap.put("ls", "李四");
		paramMap.put("ww", "王五");
		//发送消息
		p.submit(paramMap);
		//关闭连接
		Util.close(p.producer, p.session, p.connection);
	}
}
