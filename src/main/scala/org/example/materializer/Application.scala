package org.example.materializer

import akka.stream.scaladsl.{Sink, Source}
import com.typesafe.scalalogging.LazyLogging


object Application extends App with KafkaConnectivity with ElasticsearchCapability with LazyLogging {
  def run(args: Array[String]) = {

    Source(reactiveConsumer)
      .map(Song.fromBinary)
      .to(Sink.foreach(store(_)))
      .run()

    logger.info("Application is running...")

  }
}
