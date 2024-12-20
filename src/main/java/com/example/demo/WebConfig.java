/*Assignment-3
 *  <----Team Members---->
 * Sai Vamshi Dasari-G01464718
 * Aryan Sudhagoni-G01454180
 * Lahari ummadisetty-G01454186
 */
package com.example.demo; // Replace with your package name
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow all origins for testing purposes (use * for all)
        registry.addMapping("/**").allowedOrigins("*");
        // Or, for more specific configuration, only allow a specific frontend origin:
        // registry.addMapping("/**").allowedOrigins("http://localhost:3000");
    }
}
