# queues-amqp
create queue in rabbit, fill queue, send own messages, pop from queue

a) create queue: (post method) http://localhost:8080/?queueName=waldzik1 

b) send own messages (post method): http://localhost:8080/send with json body:
{
    "queueName": "waldzik1",
    
        "messages" : ["wiadomosc1", "wiadomosc1123", "superPartia"]
}

c) fill queue (post method): http://localhost:8080/fill?queueName=waldzik1 
no json body necessary
d) pop from queue (get methods): http://localhost:8080/?queueName=waldzik1
