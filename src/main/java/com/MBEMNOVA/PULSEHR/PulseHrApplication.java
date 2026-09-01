package com.MBEMNOVA.PULSEHR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class PulseHrApplication {
	public static void main(String[] args) {
		SpringApplication.run(PulseHrApplication.class, args);
	}
}