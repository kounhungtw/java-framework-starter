package com.system;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;

import org.glassfish.jersey.server.ResourceConfig;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.system.filter.RestRequestFilter;
import com.system.filter.RestResponseFilter;

@ApplicationPath("/rest")
@Component
public class RestApplication extends ResourceConfig {
	
    private static final Logger log = LoggerFactory.getLogger(RestApplication.class);

    @Autowired
    ApplicationContext appCtx;
	
	public RestApplication() {
		register(RestRequestFilter.class);
		register(RestResponseFilter.class);
		scan(
				"com.example.demo.rest",
				"com.system.rest",
				"com.system.exception"
		);
	}
	
	public void scan(String... packages) {
	    for (String pack : packages) {
	        Reflections reflections = new Reflections(pack);
	        reflections.getTypesAnnotatedWith(Path.class)
	                .parallelStream()
	                .forEach((clazz) -> {
	                    log.debug("New resource registered: " + clazz.getName());
	                    register(clazz);
	                });
	    }
	}
}
