package cn.partner.demo.rabbitmq;

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
        try {
            if (!conn.isOpen()) {
                conn = RabbitmqUtils.getConn();
            }
            Channel channel = conn.createChannel();
            channel.basicPublish(RabbitConstants.EXCHANGE_NAME, RabbitConstants.ROUTING_KEY, false,
                    MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(String.format("+++send message : %s, channel : %s", message, channel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
