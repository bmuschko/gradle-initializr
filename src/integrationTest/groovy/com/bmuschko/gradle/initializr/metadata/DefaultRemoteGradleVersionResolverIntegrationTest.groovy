package com.bmuschko.gradle.initializr.metadata

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.client.MockRestServiceServer

import static DefaultRemoteGradleVersionResolver.GRADLE_CURRENT_VERSION_URL
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@RunWith(SpringRunner)
@RestClientTest(DefaultRemoteGradleVersionResolver)
class DefaultRemoteGradleVersionResolverIntegrationTest {

    @Autowired
    DefaultRemoteGradleVersionResolver defaultRemoteGradleVersionResolver

    @Autowired
    MockRestServiceServer server

    @Test
    void "can resolve latest final version"() {
        String json = getLatestVersionJson()
        server.expect(requestTo(GRADLE_CURRENT_VERSION_URL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON))
        String currentVersion = defaultRemoteGradleVersionResolver.getLatestFinalVersion()
        assert currentVersion == json
    }

    static String getLatestVersionJson() {
        """
            {
              "version" : "4.5",
              "buildTime" : "20180124170452+0000",
              "current" : true,
              "snapshot" : false,
              "nightly" : false,
              "activeRc" : false,
              "rcFor" : "",
              "milestoneFor" : "",
              "broken" : false,
              "downloadUrl" : "https://services.gradle.org/distributions/gradle-4.5-bin.zip",
              "checksumUrl" : "https://services.gradle.org/distributions/gradle-4.5-bin.zip.sha256"
            }
        """
    }
}
