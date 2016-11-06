package com.mcnc.mbanking.auth.secure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark on POJO field when String Data needed to be encrypted or decrypted automatically
 * @author sayseakleng
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
public @interface CipherData {
}
