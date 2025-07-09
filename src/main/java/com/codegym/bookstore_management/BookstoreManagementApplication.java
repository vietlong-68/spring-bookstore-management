package com.codegym.bookstore_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
@SpringBootApplication
public class BookstoreManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreManagementApplication.class, args);
	}

}
