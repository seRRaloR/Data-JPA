package com.jrla.springboot.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		// Se pone, como parámetro del 1er método, lo que en la plantilla
		registry.addResourceHandler("/uploads/**").addResourceLocations("file:/C:/Temp/Uploads/");
	}
	

}
