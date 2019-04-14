package com.shuai.hehe.server;

import org.apache.ibatis.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class HeheserverApplication extends SpringBootServletInitializer {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(HeheserverApplication.class);
	}

	public static void main(String[] args) {
		LogFactory.useStdOutLogging();
		SpringApplication.run(HeheserverApplication.class, args);
	}
}
