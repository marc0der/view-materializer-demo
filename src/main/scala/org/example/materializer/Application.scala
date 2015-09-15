package org.example.materializer

import akka.stream.scaladsl.{Sink, Source}
import com.typesafe.scalalogging.LazyLogging

object Application extends App with LazyLogging with KafkaCapability {
  logger.info("I am sentient aware, Master!")

  Source(consumer)
    .map(Song.fromBinary)
    .to(Sink.foreach(SongRepo.store(_)))
    .run()

}
