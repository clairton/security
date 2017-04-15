package br.eti.clairton.security;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * Qualifier para devolver a string que representa o usuario.
 * 
 * @author Clairton Rodrigo Heinzen clairton.rodrigo@gmail.com
 *
 */
@Qualifier
@Target({ FIELD, PARAMETER, METHOD, CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface User {

}
