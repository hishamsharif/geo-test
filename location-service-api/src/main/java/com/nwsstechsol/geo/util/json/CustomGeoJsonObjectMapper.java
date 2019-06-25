package com.nwsstechsol.geo.util.json;

import org.springframework.beans.factory.InitializingBean;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

/**
 *
 * @author hisham
 */
//TODO delete this
//@Configuration
public class CustomGeoJsonObjectMapper extends ObjectMapper implements InitializingBean {

	private static final long serialVersionUID = -1825134841298551191L;

	public CustomGeoJsonObjectMapper() {
	}

	public CustomGeoJsonObjectMapper(JsonFactory jf) {
		super(jf);
	}

	public CustomGeoJsonObjectMapper(ObjectMapper src) {
		super(src);
	}

	public CustomGeoJsonObjectMapper(JsonFactory jf, DefaultSerializerProvider sp, DefaultDeserializationContext dc) {
		super(jf, sp, dc);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.registerModule(new JtsModule());
	}

}