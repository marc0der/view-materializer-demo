package org.example.materializer

import java.util.UUID

case class Song(id: UUID, title: String, album: String, artist: String, duration: Int)