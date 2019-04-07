package com.waldi.queues.controller;

import java.util.List;

class QueueMessage {
    private String queueName;

    private List<String> messages;

    public QueueMessage(String queueName, List<String> messages) {
        this.queueName = queueName;
        this.messages = messages;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}