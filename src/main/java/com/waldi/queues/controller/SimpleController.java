package com.waldi.queues.controller;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/")
public class SimpleController {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @PostMapping
    public ResponseEntity<ReturnMessage> createQueue(@RequestParam String queueName) {

        if (queueName == null) {
            return new ResponseEntity<ReturnMessage>(new ReturnMessage("lack of quque name in" +
                    "httpparam"), NOT_ACCEPTABLE);
        }

        amqpAdmin.declareQueue(new Queue(queueName));


        //   for (long p = 0; p < 4000; p++)
        //     amqpTemplate.convertAndSend(queueName, "hahahaha");
        //String foo = (String) amqpTemplate.receiveAndConvert(queueName);
        return new ResponseEntity<>(new ReturnMessage("created queue: " + queueName), OK);
    }

    @PostMapping("/fill")
    public ResponseEntity<String> fillQueue(@RequestParam String queueName) {
        for (long p = 0; p < 4000; p++)
            amqpTemplate.convertAndSend(queueName, "simpleMessage" + p);
        return new ResponseEntity<>("Filled", OK);
    }

    @PostMapping("/send")
    public ResponseEntity<ReturnMessage> sendMessageToQueue(@RequestBody QueueMessage queueMessage) {

        queueMessage.getMessages()
                .forEach((x) -> amqpTemplate.convertAndSend(queueMessage.getQueueName(), x));
        return new ResponseEntity<>(new ReturnMessage("sent to queue"), OK);
    }

    class ReturnMessage {
        private String message;


        public ReturnMessage(String message) {
            this.message = message;

        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @GetMapping
    public List<String> getMessages(@RequestParam String queueName) {

        List<String> messages = new ArrayList<>();
        String foo = "";
        while (true) {
            foo = (String) amqpTemplate.receiveAndConvert(queueName);
            if (foo == null) break;
            messages.add(foo);
        }
        return messages;
    }
}
