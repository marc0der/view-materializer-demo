package org.example.materializer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.ByteArraySerializer
import org.apache.kafka.common.serialization.StringSerializer

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG
import static org.apache.kafka.clients.producer.ProducerConfig.METADATA_FETCH_TIMEOUT_CONFIG

class Kafka {

    final static TIMEOUT_MILLIS = 500

    static createProducer(String host, int port) {
        new KafkaProducer(
                kafkaProducerConfig(host, port, TIMEOUT_MILLIS),
                new StringSerializer(),
                new ByteArraySerializer())
    }

    private static kafkaProducerConfig(String host, int port, int timeout) {
        [
                (BOOTSTRAP_SERVERS_CONFIG)     : host + ":" + port,
                (METADATA_FETCH_TIMEOUT_CONFIG): String.valueOf(timeout)
        ]
    }

}
