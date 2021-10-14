package com.pichincha.tacuri;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Freddy Tacuri
 */
@Configuration
@EnableWebMvc
@Log4j2
public class WebConf implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("WebMVC configuration : addCorsMappings");
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Hibernate5Module module = new Hibernate5Module(); // or Hibernate4Module ... depends on hibernate version you are using
        module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        module.disable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);
        module.disable(Hibernate5Module.Feature.WRITE_MISSING_ENTITIES_AS_NULL);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);
        converters.add(new MappingJackson2HttpMessageConverter(mapper));
    }
}
