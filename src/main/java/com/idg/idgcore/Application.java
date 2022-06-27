package com.idg.idgcore;

import org.modelmapper.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;
import com.idg.idgcore.dto.context.SessionContext;

@SpringBootApplication
public class Application {
    public static void main (String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper () {
        return new ModelMapper();
    }

    @Bean
    public SessionContext sessionContext () {
        return new SessionContext();
    }

}
