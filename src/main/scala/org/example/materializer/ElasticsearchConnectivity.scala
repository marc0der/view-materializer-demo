package org.example.materializer

import com.sksamuel.elastic4s.ElasticClient

trait ElasticsearchConnectivity {
  def elasticsearchClient = ElasticClient.local
}
