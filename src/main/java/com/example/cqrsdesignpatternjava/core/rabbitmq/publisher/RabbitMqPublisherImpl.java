package com.example.cqrsdesignpatternjava.core.rabbitmq.publisher;

import com.example.cqrsdesignpatternjava.core.rabbitmq.server.RabbitMqServer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class RabbitMqPublisherImpl implements RabbitMqPublisher {

    private final RabbitMqServer rabbitMqServer;

    @Autowired
    public RabbitMqPublisherImpl(RabbitMqServer rabbitMqServer) {
        this.rabbitMqServer = rabbitMqServer;
    }

    @Override
    public void publish(String queueName, String data) throws IOException, TimeoutException {
        try (Connection connection = rabbitMqServer.getConnection()) {
            try (Channel channel = rabbitMqServer.getChannel(connection)) {
                channel.queueDeclare(queueName, true, false, false, null);
                channel.basicPublish("", queueName, null, data.getBytes());
            }
        }
    }
}

