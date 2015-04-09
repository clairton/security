package br.eti.clairton.security;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.enterprise.context.ApplicationScoped;

/**
 * Extrai os metadados da classe para validar a permissão.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 *
 */
@ApplicationScoped
public class Extractor {

	/**
	 * Retorna o nome da operação para o metodo.
	 * 
	 * @param method
	 *            metodo a ser avaliado
	 * @return nome da operation que valida a permissão para o metodo
	 */
	public String getOperation(final Method method) {
		if (method.isAnnotationPresent(Operation.class)) {
			final Operation annotation = method.getAnnotation(Operation.class);
			return annotation.value();
		}
		return method.getName();
	}

	/**
	 * Retorna o nome do recurso ao qual o tipo se refere.
	 * 
	 * @param target
	 *            instancia da classe
	 * @return nome do recurso
	 */
	public String getResource(Object target) {
		final Class<?> type = target.getClass();
		if (type.isAnnotationPresent(Resource.class)) {
			final Resource annotation = type.getAnnotation(Resource.class);
			if (annotation.value().isEmpty()) {
				throw new IllegalStateException(Resource.class
						+ " annotation must be filled in type " + type);
			}
			return annotation.value();
		} else {
			for (final Method method : type.getDeclaredMethods()) {
				if (method.isAnnotationPresent(Resource.class)) {
					try {
						return (String) method.invoke(target);
					} catch (final Exception e) {
						throw new IllegalStateException(e);
					}
				}
			}
		}
		throw new IllegalArgumentException("The type " + type
				+ " must be annoted with " + Resource.class + " to user "
				+ Protected.class);
	}

	public String getResource(final Class<?> type) {
		Constructor<?> constructor;
		try {
			constructor = type.getDeclaredConstructor();
		} catch (final NoSuchMethodException e) {
			throw new RuntimeException("Deve haver um construtor padrão", e);
		}
		constructor.setAccessible(true);
		try {
			final Object target = constructor.newInstance();
			return getResource(target);
		} catch (final Exception e) {
			throw new RuntimeException(
					"Erro ao recuperar o nome do recurso pelo tipo do controller",
					e);
		}
	}
}
