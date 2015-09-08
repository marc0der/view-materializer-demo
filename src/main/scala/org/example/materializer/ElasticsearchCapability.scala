package org.example.materializer

import com.sksamuel.elastic4s.ElasticDsl._
import org.elasticsearch.action.index.IndexResponse

import scala.concurrent.Future

trait ElasticsearchCapability extends ElasticsearchConnectivity {
  def store(song: Song): Future[IndexResponse] = elasticsearchClient.execute {
    index into "music/songs" fields {
      "title" -> song.title
      "album" -> song.album
      "artist" -> song.artist
      "duration" -> song.duration
    }
  }
}

object ElasticsearchRepository extends ElasticsearchCapability
