package com.aluracursos.sentimentapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//fuerza el escaneo de configuracion
@ComponentScan(basePackages = "com.aluracursos.sentimentapi")
public class SentimentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SentimentApiApplication.class, args);
	}
}
