package de.angr.springrestclients.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Bean
    public RestClient restClient() {
        RestClient.Builder restClientBuilder = RestClient.builder();
        return restClientBuilder.baseUrl("https://jsonplaceholder.typicode.com/").build();
    }
}
