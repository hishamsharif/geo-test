package com.nwsstechsol.geo.json;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.ObjectMapper;



/* This did not work
@EnableWebMvc
@Configuration
*/
public class JacksonConfig extends WebMvcConfigurationSupport {

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	  ObjectMapper webObjectMapper = objectMapper.copy();
	  webObjectMapper.registerModule(new JtsModule());
	  System.out.println("<Hisham> JtsModule configured with jason http message convertion ... ");
	  converters.add(new MappingJackson2HttpMessageConverter(webObjectMapper));
	  
  }

}


/*
 * @Configuration public class JacksonConfig extends WebMvcConfigurationSupport
 * {
 * 
 * @Bean public JtsModule jtsModule() { return new JtsModule(); } }
 * 
 * 
 * @Configuration
 * 
 * @EnableSwagger2 public class SwaggerConfig extends WebMvcConfigurationSupport
 * {
 * 
 * @Autowired private ObjectMapper serializingObjectMapper;
 * 
 * 
 * @Override protected void
 * configureMessageConverters(List<HttpMessageConverter<?>> converters) {
 * MappingJackson2HttpMessageConverter messageConverter = new
 * MappingJackson2HttpMessageConverter();
 * messageConverter.setObjectMapper(serializingObjectMapper);
 * converters.add(messageConverter);
 * super.configureMessageConverters(converters); }
 * 
 * // another settings for swagger }
 */


