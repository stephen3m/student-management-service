package com.student.management

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification
import jakarta.inject.Inject
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.annotation.Client
import spock.lang.Specification

@MicronautTest
class StudentSpec extends Specification {

//    @Inject
//    RxHttpClient client
//
//    def "should return student data"() {
//        when:
//        def response = client.toBlocking().exchange("/students", Map)
//
//        then:
//        response.status == HttpStatus.OK
//        response.body() != null
//        response.body() instanceof List
//    }
}
