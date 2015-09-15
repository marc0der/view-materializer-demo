package org.example.materializer

import com.softwaremill.react.kafka.{ConsumerProperties, ReactiveKafka}
import kafka.serializer.DefaultDecoder

trait KafkaCapability extends AkkaCapability {

  val reactiveKafka = new ReactiveKafka

  lazy val consumer = reactiveKafka.consume(
    ConsumerProperties(
      brokerList = "localhost:9092",
      zooKeeperHost = "localhost:2181",
      topic = "song_listened",
      groupId = "materializer",
      decoder = new DefaultDecoder()
    )
  )

}
