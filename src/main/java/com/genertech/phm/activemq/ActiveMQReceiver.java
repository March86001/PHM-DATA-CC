package com.genertech.phm.activemq;

import com.genertech.phm.dzs.action.DzsWarnAction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.IOException;

@Component("mqReceiver")
public class ActiveMQReceiver implements SessionAwareMessageListener<Message> {

    private static final Log logger = LogFactory.getLog(ActiveMQReceiver.class);

    @Autowired
    private DzsWarnAction dzsWarnAction;

    public void receive(TextMessage message) throws JmsException, JMSException, IOException {
        String info = "this is receiver, "
                + " mood is " + message.getStringProperty("mood") + ","
                + "say " + message.getText();
        System.out.println(info);

    }

    @Override
    public void onMessage(Message  message, Session session) throws JMSException {
        try {
            String message_str = "";
            Destination destination = message.getJMSDestination();
            String topic = destination.toString();
            if(message instanceof BytesMessage){
                BytesMessage bytemessage = (BytesMessage) message;
                long length = bytemessage.getBodyLength();
                byte[] b = new byte[(int) length];
                bytemessage.readBytes(b);
                message_str = new String(b);
            }else{
                TextMessage textMessage = (TextMessage) message;
                message_str = textMessage.getText();
            }
            if (topic.equals("topic://PHM_WARNING_TOPIC")) {
                dzsWarnAction.readMessageFromMQ(message_str);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
