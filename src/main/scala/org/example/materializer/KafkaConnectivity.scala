package org.example.materializer

import com.softwaremill.react.kafka.{ConsumerProperties, ReactiveKafka}
import kafka.serializer.DefaultDecoder

trait KafkaConnectivity extends AkkaCapability {

  import KafkaConnectivity._

  private val reactiveKafka = new ReactiveKafka()

  lazy val reactiveConsumer = reactiveKafka.consume(
    ConsumerProperties(
      brokerList = "localhost:9092",
      zooKeeperHost = "localhost:2181",
      topic = KafkaSongListenedTopic,
      groupId = KafkaConsumerGroupId,
      decoder = new DefaultDecoder()
    )
  )
}

object KafkaConnectivity {
  val KafkaSongListenedTopic = "song_listened"

  val KafkaConsumerGroupId = "song_listened_group"
}
