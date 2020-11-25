package com.genertech.phm.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.genertech.phm.activemq.ActiveMQSender;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class ActiveMQSenderImpl implements ActiveMQSender {

	@Autowired
	private JmsTemplate jmsTemplate;

	private static final Logger logger = LoggerFactory.getLogger(ActiveMQSenderImpl.class);

	public void sendMessage(Destination destination, final String msg) {
		/*System.out.println("Send " + msg + " to Destination " + destination.toString());*/
		logger.info("Send Message to Destination " + destination.toString());

		MessageCreator messageCreator = new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {

				return session.createTextMessage(msg);
			}

		};

		jmsTemplate.send(destination, messageCreator);
	}

	public void sendMessage(final String msg) {
//		jmsTemplate.getDefaultDestination();
//		String destination = jmsTemplate.getDefaultDestinationName().toString();
//		System.out.println("Send " + msg + " to Destination " + destination);
		MessageCreator messageCreator = new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				return session.createTextMessage(msg);
			}

		};

		jmsTemplate.send(messageCreator);
	}
	
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}
