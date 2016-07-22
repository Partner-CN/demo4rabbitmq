package cn.partner.demo.rabbitmq;

import org.springframework.amqp.rabbit.connection.ConnectionFactoryUtils;
import org.springframework.amqp.rabbit.connection.RabbitResourceHolder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.partner.demo.rabbitmq.RabbitmqUtils.RabbitConstants;

import com.rabbitmq.client.Channel;

/**
 * @author qiao.yongxin
 * @date 2016年7月18日
 */
@Component
public class Producer {

    @Autowired
    private RabbitTemplate amqpTemplate;

    public void send(String message) {

        amqpTemplate.convertAndSend(RabbitConstants.EXCHANGE_NAME, RabbitConstants.ROUTING_KEY, message);

        RabbitResourceHolder resourceHolder =
                ConnectionFactoryUtils.getTransactionalResourceHolder(amqpTemplate.getConnectionFactory(), true);
        Channel channel = resourceHolder.getChannel();
        System.out.println(String.format("+++send message : %s, channel : %s", message, channel));
    }

    @Transactional
    public void poolingSend() {
        for (int i = 0; i < 5; i++) {
            send(String.format("hello, rabbitmq(%s)!", i));
        }
        System.out.println(1 / 0);
    }
}
