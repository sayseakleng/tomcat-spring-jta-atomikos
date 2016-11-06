package com.mcnc.mbanking.auth.secure.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.mcnc.mbanking.auth.secure.annotation.CipherData;

public class CipherDataObjectMapperConfigurationAdaptor {

	@Autowired
	public void configureObjectMapper(ObjectMapper mapper) {
		AnnotationIntrospector sis = mapper.getSerializationConfig().getAnnotationIntrospector();
        AnnotationIntrospector dis = mapper.getDeserializationConfig().getAnnotationIntrospector();

        AnnotationIntrospector serializerAI = AnnotationIntrospectorPair.pair(sis, 
        		new CipherDataAnnotationIntrospector());
        AnnotationIntrospector deserializerAI = AnnotationIntrospectorPair.pair(dis, 
        		new CipherDataAnnotationIntrospector());

        mapper.setAnnotationIntrospectors(serializerAI, deserializerAI);

	}
	
	
	private class CipherDataAnnotationIntrospector extends NopAnnotationIntrospector {
		private static final long serialVersionUID = 5627484870788944601L;

		@Override
        public Object findSerializer(Annotated am) {
			CipherData annotation = am.getAnnotation(CipherData.class);
            if (annotation != null) {
                return CipherDataSerializer.class;
            }

            return null;
        }

        @Override
        public Object findDeserializer(Annotated am) {
        	CipherData annotation = am.getAnnotation(CipherData.class);
            if (annotation != null) {
                return CipherDataDeserializer.class;
            }

            return null;
        }
	}
	
}
