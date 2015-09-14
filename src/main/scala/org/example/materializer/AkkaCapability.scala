package org.example.materializer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait AkkaCapability {
  implicit val actorSystem = ActorSystem("songs")
  implicit val materializer = ActorMaterializer()
}
