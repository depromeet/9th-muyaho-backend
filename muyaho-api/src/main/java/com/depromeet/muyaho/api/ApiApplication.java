package com.depromeet.muyaho.api;

import com.depromeet.muyaho.domain.MuyahoDomainRoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {
    ApiApplication.class,
    MuyahoDomainRoot.class
})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
