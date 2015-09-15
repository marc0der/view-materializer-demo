package org.example.materializer

import scala.pickling.Defaults._
import scala.pickling.binary._

case class Song(title: String, album: String, artist: String, duration: Int) {
  def toBinary: Array[Byte] = this.pickle.value
}

object Song {
  def fromBinary(bytes: Array[Byte]): Song = BinaryPickle(bytes).unpickle[Song]
}
