package fi.ouka.evakaoulu.vtjclient

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.ws.transport.WebServiceMessageSender
import org.springframework.ws.transport.http.HttpComponentsMessageSender

@Configuration
@Profile("integration-test")
class TestConfig {

    @Bean
    fun webServiceMessageSender(): WebServiceMessageSender =
        HttpComponentsMessageSender()
}