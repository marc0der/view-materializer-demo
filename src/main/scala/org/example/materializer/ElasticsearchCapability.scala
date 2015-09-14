package org.example.materializer

import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.mappings.FieldType.{IntegerType, StringType}
import com.typesafe.scalalogging.LazyLogging
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.action.search.SearchResponse

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ElasticsearchCapability extends LazyLogging {

  def client = ElasticClient.remote("localhost", 9300)

  private val indexName = "music"
  private val typeName = "songs"

  def store(song: Song): Future[IndexResponse] = client.execute {
    logger.info(s"About to persist: $song")
    index into indexName/typeName fields(
      "title" -> song.title,
      "album" -> song.album,
      "artist" -> song.artist,
      "duration" -> song.duration)
  }

  def query(str: String): Future[SearchResponse] = client.execute {
    logger.info(s"Searching for: $str")
    search in indexName/typeName query str
  }

  def createIndex(): Future[Boolean] = client.execute {
    logger.info(s"Creating index: $indexName/$typeName")
    create index indexName mappings {
      "songs" as(
        "title" typed StringType,
        "album" typed StringType,
        "artist" typed StringType,
        "duration" typed IntegerType)
    }
  }.map(_.isAcknowledged)
  
  def dropIndex: Future[DeleteIndexResponse] = client.execute {
    logger.warn(s"Dropping index: $indexName")
    delete index indexName
  }

}

object ElasticsearchRepository extends ElasticsearchCapability
