package com.jeferson.springcloud.msvc.items;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
// import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@Configuration
public class WebClientConfig {


@Bean
    WebClient webCliente(WebClient.Builder webClientBuilder,
            @Value("${config.baseurl.endpoint.msvc-products}") String url,
            ReactorLoadBalancerExchangeFilterFunction lbFunction){
        return webClientBuilder.baseUrl(url).filter(lbFunction).build();
    }

    /*
    @Bean
    @LoadBalanced
    WebClient.Builder webCliente(){
        return WebClient.builder().baseUrl(url);
    }
    */
}