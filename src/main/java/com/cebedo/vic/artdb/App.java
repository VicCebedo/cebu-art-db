package com.cebedo.vic.artdb;

import com.cloudinary.Cloudinary;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@SpringBootApplication
public class App {

//    @Value("${spring.datasource.url}")
//    private String dbUrl;
//
//    @Value("${cloudinary.cloud_name}")
//    private String cloud;
//
//    @Value("${cloudinary.api_key}")
//    private String apiKey;
//
//    @Value("${cloudinary.api_secret}")
//    private String apiSecret;
    private String dbUrl = "jdbc:postgresql://localhost:5432/heroku_local?user=postgres&password=postgres";
    private String cloud = "hqx5vpvj4";
    private String apiKey = "224457221896293";
    private String apiSecret = "e-Sz1hP7ojcd4QY5XZWtdTFQpQI";

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public Validator validator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        if (dbUrl == null || dbUrl.isEmpty()) {
            return new HikariDataSource();
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            return new HikariDataSource(config);
        }
    }

    @Bean
    public Cloudinary cloudinary() {
        Map config = new HashMap();
        config.put("cloud_name", cloud);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}
