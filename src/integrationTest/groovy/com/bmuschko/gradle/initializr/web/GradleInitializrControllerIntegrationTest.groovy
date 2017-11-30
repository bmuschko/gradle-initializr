package com.bmuschko.gradle.initializr.web

import com.bmuschko.gradle.initializr.metadata.GradleVersion
import com.bmuschko.gradle.initializr.model.ProjectRequest
import com.bmuschko.gradle.initializr.service.GradleInitializrService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

import static org.mockito.BDDMockito.given
import static org.mockito.Mockito.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner)
@WebMvcTest(GradleInitializrController)
class GradleInitializrControllerIntegrationTest {

    @Autowired
    MockMvc mvc

    @MockBean
    GradleInitializrService gradleInitializrService

    @Test
    void "can forward to home page"() {
        def gradleVersions = [new GradleVersion(1, 2), new GradleVersion(1, 3)]
        given(gradleInitializrService.getGradleVersions()).willReturn(gradleVersions)
        mvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("gradleVersions", gradleVersions))
            .andExpect(view().name("home"))
        verify(gradleInitializrService, times(1)).getGradleVersions()
    }

    @Test
    void "can forward to starter ZIP archive URL"() {
        mvc.perform(get("/starter").param("archive", "zip"))
            .andExpect(status().isOk())
            .andExpect(forwardedUrl("/starter.zip"))
        verifyNoMoreInteractions(gradleInitializrService)
    }

    @Test
    void "can forward to starter TAR archive URL"() {
        mvc.perform(get("/starter").param("archive", "tgz"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/starter.tgz"))
        verifyNoMoreInteractions(gradleInitializrService)
    }

    @Test
    void "redirect to error page if archive type is other than zip or tgz"() {
        mvc.perform(get("/starter").param("archive", "unknown"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"))
        verifyNoMoreInteractions(gradleInitializrService)
    }

    @Test
    void "can generate ZIP file and send in response"() {
        def projectRequest = new ProjectRequest()
        projectRequest.archive = 'zip'
        mvc.perform(get("/starter.zip").param("archive", "zip"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(GradleInitializrController.ZIP_CONTENT_TYPE))
                .andExpect(header().string("Content-Disposition", 'attachment; filename="starter.zip"'))
        verify(gradleInitializrService, times(1)).createZip(projectRequest)
    }

    @Test
    void "can generate TAR file and send in response"() {
        def projectRequest = new ProjectRequest()
        projectRequest.archive = 'tgz'
        mvc.perform(get("/starter.tgz").param("archive", "tgz"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(GradleInitializrController.LZW_CONTENT_TYPE))
                .andExpect(header().string("Content-Disposition", 'attachment; filename="starter.tar.gz"'))
        verify(gradleInitializrService, times(1)).createTgz(projectRequest)
    }
}
