//package com.idg.idgcore;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import static com.idg.idgcore.coe.common.Constants.REACT_URL;
//
//@SpringBootApplication
//public class Application {
//    public static void main (String[] args) {
//        SpringApplication.run(Application.class, args);
//    }
//
//    @Bean
//    public ModelMapper modelMapper () {
//        return new ModelMapper();
//    }
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(final CorsRegistry registry) {
//                registry.addMapping("/graphql/**")
//                        .allowedOrigins(CorsConfiguration.ALL)
//                        .allowedHeaders(CorsConfiguration.ALL)
//                        .allowedMethods(CorsConfiguration.ALL);
//            }
//        };
//    }
//
//
//    public class LocalCorsConfiguration {
//
//        @Bean
//        public CorsFilter corsFilter() {
//            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            final CorsConfiguration config = new CorsConfiguration();
//            config.setAllowCredentials(true);
//            config.addAllowedOrigin(REACT_URL);
//            config.addAllowedHeader("*");
//            config.addAllowedMethod("*");
//            source.registerCorsConfiguration("/graphql/**", config);
//            return new CorsFilter(source);
//        }
//    }
//}



package com.idg.idgcore;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import static com.idg.idgcore.coe.common.Constants.REACT_URL;
@SpringBootApplication
public class Application {
    public static void main (String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Bean
    public ModelMapper modelMapper () {
        return new ModelMapper();
    }
    public class LocalCorsConfiguration {
        @Bean
        public CorsFilter corsFilter() {
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            final CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.addAllowedOrigin(REACT_URL);
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            source.registerCorsConfiguration("/graphql/**", config);
            return new CorsFilter(source);
        }
    }
    @Bean
    @CrossOrigin(origins = "http://localhost:3000")
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/graphql/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedOrigins(CorsConfiguration.ALL)
                        .allowedHeaders(CorsConfiguration.ALL)
                        .allowedMethods(CorsConfiguration.ALL)
                ;
            }
        };
    }
}