package org.le.uid.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Starter
 *
 * @author xiaole
 */

@SpringBootApplication(scanBasePackages = {"org.le"})
public class UidGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(UidGeneratorApplication.class, args);
    }
}
