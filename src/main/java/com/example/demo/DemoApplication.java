/*Assignment-3
 *  <----Team Members---->
 * Sai Vamshi Dasari-G01464718
 * Aryan Sudhagoni-G01454180
 * Lahari ummadisetty-G01454186
 */
package com.example.demo; // Specifies the package for the application

import org.springframework.boot.SpringApplication; // Import for running the Spring Boot application
import org.springframework.boot.autoconfigure.SpringBootApplication; // Import for enabling Spring Boot auto-configuration

// Marks this class as the starting point of a Spring Boot application
@SpringBootApplication
public class DemoApplication { // Main class for the Spring Boot application

	// Main method: Entry point of the application
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args); // Runs the Spring Boot application
	}

}
