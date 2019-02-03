package com.lorescianatico.driftcoin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriftCoinConfiguration {

    @Value("${driftcoin.mining.difficulty}")
    private Integer difficulty;

    @Bean
    public DriftcoinSettings driftcoinSettings(){
        return DriftcoinSettings.builder().difficulty(difficulty).build();
    }

}
