package org.example.materializer

import groovy.json.JsonBuilder
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.transport.InetSocketTransportAddress
import spock.lang.Specification

import static java.util.concurrent.TimeUnit.SECONDS

class ElasticsearchIntegrationSpec extends Specification {

    Client client

    void setup() {
        client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("localhost", 9300))
        createIndex()
    }

    void "should index a song to specified index and type in elasticsearch"() {
        given:
        def id = UUID.randomUUID()
        def song = new Song(id, "Ride the Lightning", "And Justice for All...", "Metallica", 100)

        when:
        def indexResponse = ElasticsearchRepository.store(song).result()

        then:
        indexResponse.created
        indexResponse.index == "music"
        indexResponse.type == "songs"
    }

    void cleanup() {
        deleteIndex()
        client.close()
    }

    private deleteIndex() {
        client.admin()
                .indices()
                .delete(new DeleteIndexRequest("music"))
                .actionGet(10, SECONDS)
    }

    private createIndex() {
        client.admin()
                .indices()
                .create(new CreateIndexRequest("music").mapping("songs", mapping()))
                .actionGet(10, SECONDS)
    }

    private static mapping() {
        def json = new JsonBuilder()
        json.properties {
            title {
                type 'string'
            }
            album {
                type 'string'
            }
            artist {
                type 'string'
            }
            duration {
                type 'long'
            }
        }
        json.toString()
    }

}