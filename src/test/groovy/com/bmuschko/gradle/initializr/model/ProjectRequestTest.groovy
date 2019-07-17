package com.bmuschko.gradle.initializr.model

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class ProjectRequestTest extends Specification {

    @Subject
    def projectRequest = new ProjectRequest()

    @Unroll
    def "can indicate Java type for '#type'"() {
        given:
        projectRequest.type = type

        when:
        boolean javaType = projectRequest.isJavaType()

        then:
        projectRequest.type == type
        javaType == result

        where:
        type                 | result
        null                 | false
        ''                   | false
        'basic'              | false
        'java-application'   | true
        'java-library'       | true
        'groovy-application' | false
        'groovy-library'     | false
        'scala-library'      | false
    }
}
