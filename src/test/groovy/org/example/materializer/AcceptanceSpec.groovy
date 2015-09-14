package org.example.materializer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.elasticsearch.action.search.SearchResponse
import spock.lang.Specification

import static java.util.UUID.randomUUID
import static org.example.materializer.ElasticsearchRepository.createIndex
import static org.example.materializer.ElasticsearchRepository.dropIndex

class AcceptanceSpec extends Specification {

    KafkaProducer producer

    void setup() {
        Application.run()
        producer = Kafka.producer()
        createIndex().ready()
    }

    void "should materialise a song that was stored"() {
        given:
        def song = new Song(
                randomUUID(),
                "Ride the Lightning",
                "And justice for all",
                "Metallica",
                302)

        when:
        def message = new ProducerRecord("song_listened", song.toBinary())
        producer.send(message)

        and:
        Thread.sleep(5000)
        SearchResponse response = ElasticsearchRepository.query("Metallica").result()

        then:
        response.hits.hits.size() == 1

    }

    void cleanup() {
        dropIndex().ready()
        producer.close()
    }

}
