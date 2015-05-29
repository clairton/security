package br.eti.clairton.security;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * Especifica o nome do recurso a ser acessado.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
@Inherited
@Target({ FIELD, PARAMETER, METHOD, CONSTRUCTOR, TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface Resource {
	String value() default "";
}
