package com.depromeet.muyaho.batch;

import com.depromeet.muyaho.domain.MuyahoDomainRoot;
import com.depromeet.muyaho.external.MuyahoExternalRoot;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication(scanBasePackageClasses = {MuyahoDomainRoot.class, MuyahoExternalRoot.class, MuyahoBatchApplication.class})
public class MuyahoBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuyahoBatchApplication.class, args);
    }

}
