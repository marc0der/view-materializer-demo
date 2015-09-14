package org.example.materializer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig._
import org.apache.kafka.common.serialization.{ByteArraySerializer, StringSerializer}

import scala.collection.JavaConversions._

object Kafka {
  val producer = new KafkaProducer(
    Map(
      BOOTSTRAP_SERVERS_CONFIG -> "localhost:9092",
      METADATA_FETCH_TIMEOUT_CONFIG -> "500"),
    new StringSerializer,
    new ByteArraySerializer)
}
