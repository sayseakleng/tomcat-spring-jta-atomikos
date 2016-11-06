package com.mcnc.mbanking.auth.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;


/**
 * Spring Application initializer 
 * <br/>
 * It will hold ApplicationContext which allow to access it via static method
 * @author sayseakleng
 *
 */
@Configurable
public class SpringContextHolder implements ApplicationContextAware, EnvironmentAware {
	protected static ApplicationContext applicationContext;
	protected static PropertyResolver propertyResolver;
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}
	
	/**
	 * Get Spring ApplicationContext
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.context.EnvironmentAware#setEnvironment(org.springframework.core.env.Environment)
	 */
	@Override
	public void setEnvironment(Environment environment) {
		propertyResolver = environment;
	}
	
	/**
	 * Get property resolver
	 * @return
	 */
	public static PropertyResolver getPropertyResolver() {
		return propertyResolver;
	}
	
	
	/**
	 * Get property value from String context configuration by key
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		String value = null;
		
		if(propertyResolver != null) {
			value = propertyResolver.getProperty(key);
		}
		
		return value;
	}
	
	public static <T> T getProperty(String key, Class<T> type) {
		T result = null;
		
		if(propertyResolver != null) {
			String value = propertyResolver.getProperty(key);
			toObject(value, type);
		}
		return result;
	}
	
	
	private static Object toObject(String value, Class<?> clazz) {
	    if( Boolean.class == clazz ) return Boolean.parseBoolean( value );
	    if( Byte.class == clazz ) return Byte.parseByte( value );
	    if( Short.class == clazz ) return Short.parseShort( value );
	    if( Integer.class == clazz ) return Integer.parseInt( value );
	    if( Long.class == clazz ) return Long.parseLong( value );
	    if( Float.class == clazz ) return Float.parseFloat( value );
	    if( Double.class == clazz ) return Double.parseDouble( value );
	    return null;
	}
}
