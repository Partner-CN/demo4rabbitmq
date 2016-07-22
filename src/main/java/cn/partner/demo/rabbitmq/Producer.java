package cn.partner.demo.rabbitmq;

import java.io.IOException;

import cn.partner.demo.rabbitmq.RabbitmqUtils.RabbitConstants;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

/**
 * @author qiao.yongxin
 * @date 2016年7月18日
 */
public class Producer {

    public static void send(String message) {
        Connection conn = RabbitmqUtils.getConn();
        Channel channel = null;
        try {
            if (!conn.isOpen()) {
                conn = RabbitmqUtils.getConn();
            }
            channel = conn.createChannel();
            // Transaction begin
            channel.txSelect();
            channel.basicPublish(RabbitConstants.EXCHANGE_NAME, RabbitConstants.ROUTING_KEY, false,
                    MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(String.format("+++send message : %s, channel : %s", message, channel));
            // when test transaction
            // throw new RuntimeException();

            // Transaction commit
            channel.txCommit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                // Transaction rollback
                channel.txRollback();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
