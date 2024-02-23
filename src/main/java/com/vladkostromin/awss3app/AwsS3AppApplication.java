package com.vladkostromin.awss3app;

import com.vladkostromin.awss3app.entity.UserEntity;
import com.vladkostromin.awss3app.repository.UserRepository;
import com.vladkostromin.awss3app.security.PBFDK2Encoder;
import com.vladkostromin.awss3app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import reactor.core.publisher.Mono;


@SpringBootApplication
public class AwsS3AppApplication {


    public static void main(String[] args) {
        SpringApplication.run(AwsS3AppApplication.class, args);
    }
}
