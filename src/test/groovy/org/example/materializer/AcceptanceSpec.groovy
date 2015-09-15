package org.example.materializer

import org.apache.kafka.clients.producer.ProducerRecord
import org.elasticsearch.action.search.SearchResponse
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import static java.util.UUID.randomUUID

class AcceptanceSpec extends Specification {

    def conditions = new PollingConditions(timeout: 10, delay: 1)

    def producer = Kafka.createProducer("localhost", 9092)

    def setup() {
        SongRepo.createIndex()
        Application.main()
    }

    void "should consume events and materialize in datastore"() {
        given:
        def song = new Song(
                "One",
                "And Justice for All...",
                "Metallica",
                700)

        when:
        producer.send(
                new ProducerRecord("song_listened",
                        randomUUID().toString(),
                        song.toBinary()))

        then:
        conditions.eventually {
            SearchResponse response = SongRepo.query("Metallica").result()
            response.hits.hits.size()
        }
    }

    def cleanup() {
        SongRepo.dropIndex()
    }

}
