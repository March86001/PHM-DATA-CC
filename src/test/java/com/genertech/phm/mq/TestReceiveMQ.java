package com.genertech.phm.mq;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQTopicSession;
import org.apache.activemq.ActiveMQTopicSubscriber;
import org.apache.activemq.command.ActiveMQTopic;

import com.alibaba.fastjson.JSON;

public class TestReceiveMQ {
    private static String url = "tcp://192.168.3.151:61616";
    // CLI_KLBE1    CLI_KLBE2
    private static String clientID =UUID.randomUUID().toString();
    // 1  Q_TENLY_REMEDY       2  Q_TENLY_WARNING
    private static String topicName = "PHM_REPAIR_ADVICE_TOPIC";
   private static int i=0;
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        TopicConnection connection = null;
        ActiveMQTopicSession session = null;
        ActiveMQTopic topic = null;
        ActiveMQTopicSubscriber subscriber = null;
        try {
            connection = connectionFactory.createTopicConnection();
            // 设置客户端ID
            connection.setClientID(clientID);
            connection.start();
            session = (ActiveMQTopicSession) connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            // 设置队列名称     
            topic = (ActiveMQTopic) session.createTopic(topicName);
            // 创建持久订阅
            subscriber = (ActiveMQTopicSubscriber) session.createDurableSubscriber(topic, clientID);
            // 设置监听
            subscriber.setMessageListener(new CustomMessageListener());
           
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // ConnectionUtil.closeSession(session);
            // ConnectionUtil.closeConnection(connection);
            // ConnectionUtil.closeTopicSubscriber(subscriber);
        }
    }

    static class CustomMessageListener implements MessageListener {
        public CustomMessageListener() {

        }

        /**
         * 编写收到消息后的处理逻辑****此处需要根据业务需求编写相应代码
         */
        @Override
        public void onMessage(Message message) {
            try {
                if (message != null) {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        Map<String,String> map=JSON.parseObject(textMessage.getText(), Map.class);
                     // if(map.get("trainNo").indexOf("长客标动")!=-1){
                    	  System.out.println(map.get("trainNo"));
                     //}
                        saveStr(textMessage.getText(), "tenly_" + topicName.toLowerCase());
                    } else if (message instanceof StreamMessage) {
                        System.out.println("收到stream消息");
                    }
                } else {
                    System.out.println("没有收到消息");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public void saveStr(String str, String fileName) {
            try {
                FileWriter wirter = new FileWriter("D:/data/"+fileName+".txt", true);
                wirter.write(str);
                wirter.write("\n");
                wirter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
