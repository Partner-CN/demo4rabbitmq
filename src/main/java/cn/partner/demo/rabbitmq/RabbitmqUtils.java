package cn.partner.demo.rabbitmq;

import java.util.concurrent.Executors;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author qiao.yongxin
 * @date 2016年7月18日
 */
public class RabbitmqUtils {

    public interface RabbitConstants {
        String EXCHANGE_NAME = "Test11";
        String ROUTING_KEY = "Test11";
        String QUEUE_NAME = "Test11";
    }

    public static Connection getConn() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection conn = null;
        try {
            conn =
                    factory.newConnection(Executors.newFixedThreadPool(5),
                            Address.parseAddresses("10.113.166.67:5672,10.113.166.174:5672"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
