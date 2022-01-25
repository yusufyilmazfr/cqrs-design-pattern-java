package com.example.cqrsdesignpatternjava.core.rabbitmq.receiver;

import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface RabbitMqReceiver {
    void receive(String queueName, DeliverCallback deliverCallback) throws IOException, TimeoutException;
}
