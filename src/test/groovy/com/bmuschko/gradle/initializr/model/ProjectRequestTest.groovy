package com.bmuschko.gradle.initializr.model

import spock.lang.Specification

class ProjectRequestTest extends Specification {

    def "can indicate Java type"() {
        given:
        def projectRequest = new ProjectRequest()
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
