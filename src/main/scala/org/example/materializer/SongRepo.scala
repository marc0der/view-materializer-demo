package org.example.materializer

import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.mappings.FieldType.{IntegerType, StringType}
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.action.search.SearchResponse

import scala.concurrent.Future

trait SongPersistence {

  val client: ElasticClient

  def query(q: String): Future[SearchResponse] = client.execute {
    search in "music" query q
  }

  def store(s: Song): Future[IndexResponse] = client.execute {
    index into "music" / "songs" fields(
      "title" -> s.title,
      "album" -> s.album,
      "artist" -> s.artist,
      "duration" -> s.duration)
  }

  def dropIndex(): Future[DeleteIndexResponse] = client.execute {
    delete index "music"
  }

  def createIndex(): Future[CreateIndexResponse] = client.execute {
    create index "music" mappings (
      "songs" as(
        "title" typed StringType,
        "album" typed StringType,
        "artist" typed StringType,
        "duration" typed IntegerType))
  }
}

object SongRepo extends SongPersistence {
  override val client = ElasticClient.remote("localhost", 9300)
}
