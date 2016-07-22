package cn.partner.demo.rabbitmq;

import java.io.IOException;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import cn.partner.demo.rabbitmq.RabbitmqUtils.RabbitConstants;

import com.rabbitmq.client.Channel;

/**
 * @author qiao.yongxin
 * @date 2016年7月18日
 */
@Component
@RabbitListener(bindings = @QueueBinding(key = RabbitConstants.ROUTING_KEY, exchange = @Exchange(
        value = RabbitConstants.EXCHANGE_NAME, autoDelete = "false", durable = "true", type = ExchangeTypes.DIRECT),
        value = @Queue(value = RabbitConstants.QUEUE_NAME, durable = "true")))
public class Consumer {

    @RabbitHandler
    public void listen(String message, @Header(AmqpHeaders.DELIVERY_TAG) int deliveryTag, Channel channel) {
        System.out.println(String.format("---recevie message : %s, channel : %s", message, channel));
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
