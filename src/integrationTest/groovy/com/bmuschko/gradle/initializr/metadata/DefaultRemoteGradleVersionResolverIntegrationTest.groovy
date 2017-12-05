package com.bmuschko.gradle.initializr.metadata

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.client.MockRestServiceServer

import static DefaultRemoteGradleVersionResolver.GRADLE_ALL_VERSIONS_URL
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
    void "can resolve all versions"() {
        String json = getNoFinalVersionJson()
        server.expect(requestTo(GRADLE_ALL_VERSIONS_URL))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess(json, MediaType.APPLICATION_JSON))
        String allVersions = defaultRemoteGradleVersionResolver.getAllVersions()
        assert allVersions == json
    }

    static String getNoFinalVersionJson() {
        """
            [
               {
                  version:"4.5",
                  buildTime:"20171125235933+0000",
                  current:false,
                  snapshot:false,
                  nightly:false,
                  activeRc:false,
                  rcFor:"",
                  milestoneFor:"",
                  broken:false,
                  downloadUrl:"https://services.gradle.org/distributions-snapshots/gradle-4.5-bin.zip",
                  checksumUrl:"https://services.gradle.org/distributions-snapshots/gradle-4.5-bin.zip.sha256"
               },
               {
                  version:"4.4-rc-2",
                  buildTime:"20171124103528+0000",
                  current:false,
                  snapshot:false,
                  nightly:false,
                  activeRc:true,
                  rcFor:"4.4",
                  milestoneFor:"",
                  broken:false,
                  downloadUrl:"https://services.gradle.org/distributions/gradle-4.4-rc-2-bin.zip",
                  checksumUrl:"https://services.gradle.org/distributions/gradle-4.4-rc-2-bin.zip.sha256"
               }
            ]
        """
    }
}
