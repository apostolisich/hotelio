package com.apostolisich.api.hotelio;

import com.apostolisich.api.hotelio.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties(RsaKeyProperties.class)
public class HotelioApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelioApplication.class, args);
	}

}
