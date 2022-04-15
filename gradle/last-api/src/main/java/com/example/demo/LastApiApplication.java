package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.config.ServiceTest;

@SpringBootApplication
public class LastApiApplication implements CommandLineRunner{
	
	@Autowired
	ServiceTest serviceTest;

	public static void main(String[] args) {
		SpringApplication.run(LastApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		serviceTest.test();
	}

}
