package cn.partner.demo.rabbitmq;

/**
 * @author qiao.yongxin
 * @date 2016年7月18日
 */
public class Test {

    public static void main(String[] args) {

        // produce
        new Thread(() -> {
            try {
                boolean flag = true;
                int i = 0;
                while (flag) {
                    Producer.send(String.format("Hello, rabbitmq!(%s)", ++i));
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


        // consume
        new Thread(() -> {
            try {
                Consumer.listen();
                while (true) {
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


        System.out.println("main --end.");
    }
}
