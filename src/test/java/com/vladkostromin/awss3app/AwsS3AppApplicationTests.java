package com.vladkostromin.awss3app;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.vladkostromin.awss3app.config.AwsConfig;
import com.vladkostromin.awss3app.dto.UserDto;
import com.vladkostromin.awss3app.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AwsS3AppApplicationTests {

    @Test
    void contextLoads() {

    }

}
