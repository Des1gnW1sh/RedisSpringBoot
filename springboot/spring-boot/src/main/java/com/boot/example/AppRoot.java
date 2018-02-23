package com.boot.example;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * 运行工厂类
 * @author Administrator
 *
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class AppRoot {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AppRoot.class).run(args);
	}

}
