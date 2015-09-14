package org.example.materializer

import spock.lang.Specification

import static java.util.UUID.randomUUID

class SerializationSpec extends Specification {
    void "test pickling"() {
        given:
        def song = new Song(
                randomUUID(),
                "Ride the Lightning",
                "And justice for all",
                "Metallica",
                302)

        def json = song.toBinary()

        when:
        def deserialized = Song.fromBinary(json)

        then:
        song == deserialized
    }
}
