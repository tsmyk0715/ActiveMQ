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
 * acitveMQ�� ��Ե� ģʽ
 * ������
 */
public class Producer {
	
	/*
	 * ���裺
	 *  1.���JMS connection factory.
	 *	2.����factory����JMS connection
	 *	3.����connection
	 *	4.ͨ��connection����JMS session.
	 *	5.ָ��JMS destination.
	 *	6.����JMS producer���ߴ���JMS message���ṩdestination.
	 *	7.����JMS consumer��ע��JMS message listener.
	 *	8.���ͺͽ���JMS message.
	 *	9.�ر�����JMS��Դ������connection, session, producer, consumer�ȡ�
	 */
	
	/**
	 * һ������
	 */
	private Connection connection ;
	
	/**
	 * ��Ϣ������
	 */
	private MessageProducer producer;
	
	/**
	 * ������Ϣ���߳�
	 */
	private Session session;
	
	/**
	 * ��ʼ��
	 * @throws Exception
	 */
	public void init() throws Exception{
		//���ӹ�����JMS��������
		ConnectionFactory factory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER, 
				ActiveMQConnection.DEFAULT_PASSWORD,
				ActiveMQConnection.DEFAULT_BROKER_URL);
		
		//Connection��JMS�ͻ��˵�JMS Provider�����ӣ��ӹ��칤���еõ����Ӷ���
		connection = factory.createConnection();
		// ����
		connection.start();
		//��ȡ���Ӳ���
		//��һ��boolean���͵Ĳ���������ʾ�Ƿ����������Ϣ��
		//�����������Ϣ�����ڵĲ�������Ϊtrue����ʱ��Ϣ���ύ�Զ���comit����
		//��Ϣ�Ļع����Զ���rollback����
		//�ڶ���int���͵Ĳ�����ʾ��Ϣ��ȷ�Ϸ�ʽ��
		//��Session.AUTO_ACKNOWLEDGE��ʾSession���Զ�ȷ�������յ�����Ϣ��
		//Session.CLIENT_ACKNOWLEDGE��ʾ�ɿͻ��˳���ͨ��������Ϣ��ȷ�Ϸ�����ȷ�������յ�����Ϣ��
		//Session.DUPS_OK_ACKNOWLEDGEʹ��Session�������衱��ȷ����Ϣ������������ȷ����Ϣ�������п��ܵ�����Ϣ�ظ�Ͷ�ݡ�
		session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		Queue queue =  session.createQueue("RequestQueue");
		//������Ϣ�ķ�����
		producer = session.createProducer(queue);
		//������Ϣ�Ĳ��־û�
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	}
	
	/**
	 * ������Ϣ
	 * @param requestMap
	 * @throws Exception
	 */
	public void submit(HashMap<Serializable, Serializable> requestMap) throws Exception{
		// ����Ҫ���͵���Ϣ
		ObjectMessage message = session.createObjectMessage(requestMap);
		// ������Ϣ
		producer.send(message);
		session.commit();
	}
	
	// ����
	public static void main(String[] args) throws Exception{
		Producer p = new Producer();
		//��ʼ��
		p.init();
		HashMap<Serializable, Serializable> paramMap = new HashMap<>();
		paramMap.put("zs", "����");
		paramMap.put("ls", "����");
		paramMap.put("ww", "����");
		//������Ϣ
		p.submit(paramMap);
		//�ر�����
		Util.close(p.producer, p.session, p.connection);
	}
}
