package com.itsp.basicspring;

import com.feiniaojin.gracefulresponse.EnableGracefulResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableGracefulResponse
@Slf4j
public class BasicSpringBootServerApplication {
    public static void main(String[] args) {
        log.info("Starting BasicSpringBootServerApplication...");
        SpringApplication.run(BasicSpringBootServerApplication.class, args);
    }
}
