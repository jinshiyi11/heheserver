package com.shuai.hehe.server;

import org.apache.ibatis.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class HeheserverApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(HeheserverApplication.class);
	}

	public static void main(String[] args) {
		LogFactory.useStdOutLogging();
		SpringApplication.run(HeheserverApplication.class, args);
	}
}
