package com.youlai.common.rabbitmq.demo.manager;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huawei
 * @desc 创建 Connection 连接工厂对象，创建连接
 * @email huawei_code@163.com
 * @date 2021/2/2
 */
public class ConnectionManager {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("192.168.56.10");
                factory.setPort(5672);
                factory.setVirtualHost("/");
                connection = factory.newConnection();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
