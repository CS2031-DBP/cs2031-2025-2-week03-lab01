package org.lab.week03lab01;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    @Scope("singleton")
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
