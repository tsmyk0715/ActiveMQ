package jms.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 在实际项目中，我们很少会自己手动去获取消息，如果需要手动去获取消息，那就没有必要使用到ActiveMq了，
 * 不能手动去获取消息，那么我们就可以选择使用一个监听器来监听是否有消息到达，
 * 这样子可以很快的完成对消息的处理。
 *
 */
public class QueueMessageListener implements MessageListener{

	@Override
	public void onMessage(Message arg0) {
		TextMessage textMsg = (TextMessage) arg0;
		try {
			System.out.println("监听到的信息：" + textMsg.getText());
			// TODO 
			//将需要的业务操作在里面解决，这样子，就完成了我们生产者-中间件-消费者，这样一个解耦的操作了。　
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
