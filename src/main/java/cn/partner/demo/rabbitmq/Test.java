package cn.partner.demo.rabbitmq;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author qiao.yongxin
 * @date 2016年7月22日
 */
@Component
public class Test {

    private @Autowired Producer producer;

    @PostConstruct
    public void init() {

        new Thread(() -> {
            try {
                producer.poolingSend();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
