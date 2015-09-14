package org.example.materializer

import java.util.UUID

import scala.pickling.Defaults._
import scala.pickling.binary._

case class Song(id: UUID, title: String, album: String, artist: String, duration: Int) {
  def toBinary: Array[Byte] = this.pickle.value
}

object Song {
  def fromBinary(binary: Array[Byte]): Song = BinaryPickle(binary).unpickle[Song]
}
