package com.example.cqrsdesignpatternjava.core.rabbitmq.receiver;

import com.example.cqrsdesignpatternjava.core.rabbitmq.server.RabbitMqServer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class RabbitMqReceiverImpl implements RabbitMqReceiver {

    private final RabbitMqServer rabbitMqServer;

    @Autowired
    public RabbitMqReceiverImpl(RabbitMqServer rabbitMqServer) {
        this.rabbitMqServer = rabbitMqServer;
    }

    @Override
    public void receive(String queueName, DeliverCallback deliverCallback) throws IOException, TimeoutException {
        Connection connection = rabbitMqServer.getConnection();
        Channel channel = rabbitMqServer.getChannel(connection);

        channel.queueDeclare(queueName, true, false, false, null);

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
