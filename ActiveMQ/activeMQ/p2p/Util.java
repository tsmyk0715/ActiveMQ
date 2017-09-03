package activeMQ.p2p;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

public final class Util {
	
	public static void close(MessageProducer producer, Session session, Connection connection){
		if(null != producer){
			try {
				producer.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		if(null != session){
			try {
				session.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		if(null != connection){
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(MessageConsumer consumer, Session session, Connection connection){
		if(null != consumer){
			try {
				consumer.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		if(null != session){
			try {
				session.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		if(null != connection){
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
