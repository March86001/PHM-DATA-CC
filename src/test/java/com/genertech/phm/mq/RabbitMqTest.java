package com.genertech.phm.mq;


import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqTest {
    public static final String QUEUE_NAME = "TS_TO_PHM_MEAS_QUEUE_V1_TZ";
    public static void main(String[] args) throws IOException, TimeoutException {
        Long t = System.currentTimeMillis();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);
        factory.setUsername("gener");
        factory.setPassword("111111");
        factory.setHost("192.168.31.115");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                //body就是从队列中获取的数据
                String msg = new String(body);
                if (msg.contains("长客标动-5145") && msg.contains("rpaxle1biggearavg")) {
                    System.out.println("接收京张-1轴齿端轴箱轴承预警："+msg);
                } else {
                    System.out.println("接收："+msg);
                }
            }
        };

        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
