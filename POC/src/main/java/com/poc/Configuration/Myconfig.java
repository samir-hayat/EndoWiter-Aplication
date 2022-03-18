package com.poc.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class Myconfig implements  WebMvcConfigurer {
 
 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path uploadDir = Paths.get("./user-photos");
        Path uploadVideoDir = Paths.get("./user-video");
        String uploadPath = uploadDir.toFile().getAbsolutePath();
        String uploadVdPath = uploadVideoDir.toFile().getAbsolutePath();
         
        registry.addResourceHandler("/user-photos/**").addResourceLocations("file:/"+ uploadPath + "/");
        registry.addResourceHandler("/user-video/**").addResourceLocations("file:/"+ uploadVdPath + "/");
    }}