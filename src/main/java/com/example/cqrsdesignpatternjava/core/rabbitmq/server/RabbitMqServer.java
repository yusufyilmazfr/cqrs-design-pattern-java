package com.example.cqrsdesignpatternjava.core.rabbitmq.server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface RabbitMqServer {
    Connection getConnection() throws IOException, TimeoutException;
    Channel getChannel(Connection connection) throws IOException;
}
