package com.test.EveryDayHelp.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //Simply it means doing thing explicitly other then implicitly
public class FolerPathConfig implements WebMvcConfigurer { //We need to implement the WebMvc by adding Config

//	 @Value("${file.upload-dir}")
//	    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)  //It Handles the resources which we upload
    {
        registry.addResourceHandler("/employee/**")
                .addResourceLocations("file:employee/");

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
        
//        registry.addResourceHandler("/uploadFiles/**")
//        .addResourceLocations("file:"+uploadDir+"/");


       
    }
}