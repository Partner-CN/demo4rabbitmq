package cn.partner.demo.rabbitmq;

import java.io.IOException;

import cn.partner.demo.rabbitmq.RabbitmqUtils.RabbitConstants;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * @author qiao.yongxin
 * @date 2016年7月18日
 */
public class Consumer {

    private static Connection conn = RabbitmqUtils.getConn();

    public static void listen() {

        read();
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!conn.isOpen()) {
                conn = RabbitmqUtils.getConn();
                read();
            }
        }

    }

    private static void read() {
        try {
            Channel channel = conn.createChannel();
            channel.exchangeDeclare(RabbitConstants.EXCHANGE_NAME, "direct", true);
            channel.queueDeclare(RabbitConstants.QUEUE_NAME, true, false, false, null);
            channel.queueBind(RabbitConstants.QUEUE_NAME, RabbitConstants.EXCHANGE_NAME, RabbitConstants.ROUTING_KEY);

            channel.basicConsume(RabbitConstants.QUEUE_NAME, false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                        byte[] body) throws IOException {

                    System.out.println(String
                            .format("---recevie message : %s, channel : %s", new String(body), channel));
                    long deliveryTag = envelope.getDeliveryTag();
                    channel.basicAck(deliveryTag, false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
