package com.depromeet.muyaho.batch;

import com.depromeet.muyaho.domain.MuyahoDomainRoot;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication(scanBasePackageClasses = {
    MuyahoDomainRoot.class,
    MuyahoBatchApplication.class
})
public class MuyahoBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuyahoBatchApplication.class, args);
    }

}
