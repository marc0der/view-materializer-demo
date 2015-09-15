package org.example.materializer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait AkkaCapability {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
}
