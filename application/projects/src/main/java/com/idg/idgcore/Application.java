package com.idg.idgcore;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
@EnableAutoConfiguration
public class Application {
    public static void main (String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper () {
        return new ModelMapper();
    }
}
