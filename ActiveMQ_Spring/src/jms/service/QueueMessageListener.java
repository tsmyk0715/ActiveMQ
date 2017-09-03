package jms.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * ��ʵ����Ŀ�У����Ǻ��ٻ��Լ��ֶ�ȥ��ȡ��Ϣ�������Ҫ�ֶ�ȥ��ȡ��Ϣ���Ǿ�û�б�Ҫʹ�õ�ActiveMq�ˣ�
 * �����ֶ�ȥ��ȡ��Ϣ����ô���ǾͿ���ѡ��ʹ��һ���������������Ƿ�����Ϣ���
 * �����ӿ��Ժܿ����ɶ���Ϣ�Ĵ���
 *
 */
public class QueueMessageListener implements MessageListener{

	@Override
	public void onMessage(Message arg0) {
		TextMessage textMsg = (TextMessage) arg0;
		try {
			System.out.println("����������Ϣ��" + textMsg.getText());
			// TODO 
			//����Ҫ��ҵ��������������������ӣ������������������-�м��-�����ߣ�����һ������Ĳ����ˡ���
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
