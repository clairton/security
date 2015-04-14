package br.eti.clairton.security;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.Matcher;

/**
 * Extrai os metadados da classe para validar a permissão.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 *
 */
@ApplicationScoped
public class Extractor {
	private final Mirror mirror = new Mirror();
	private final Matcher<Method> matcher = new Matcher<Method>() {

		@Override
		public boolean accepts(final Method method) {
			return method.isAnnotationPresent(Resource.class);
		}
	};

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
			final String typeName = withoutProxy(type.getName()); 
			final List<Method> methods = mirror.on(typeName).reflectAll().methods()
					.matching(matcher);
			if (methods.size() > 1) {
				throw new IllegalArgumentException("The type " + type
						+ " must be annoted twice with " + Resource.class);
			} else if (methods.size() == 1) {
				try {
					Method method = methods.get(0);
					return (String) method.invoke(target);
				} catch (final Exception e) {
					throw new IllegalStateException(e);
				}
			} else {
				final String controller = withoutProxy(target.getClass().getSimpleName());
				final String model = controller.split("Controller*.")[0];
				return model.substring(0, 1).toLowerCase() + model.substring(1);
			}
		}
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
	
	private String withoutProxy(String className){
		return className.split("\\$Proxy\\$")[0];
	}
}
