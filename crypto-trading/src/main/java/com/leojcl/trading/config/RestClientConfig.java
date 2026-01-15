package com.leojcl.trading.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${price.api.connection.timeout:5000}") // 5 seconds default
    private int connectionTimeout;

    @Value("${price.api.read.timeout:10000}") // 10 seconds default
    private int readTimeout;

    @Bean
    public RestClient restClient() {

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectionTimeout);
        requestFactory.setReadTimeout(readTimeout);

        return RestClient.builder()
                .requestFactory(requestFactory)
                .defaultHeader("User-Agent", "crypto-trading-service/1.0")
                .build();
    }
}
