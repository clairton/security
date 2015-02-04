package br.eti.clairton.security;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.logging.log4j.Logger;

/**
 * Interceptor para metodos anotados com {@link Protected}.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 *
 */
@Interceptor
@Protected
public class GateInterceptor {
	private final String user;
	private final String app;
	private final Gate gate;
	private final Extractor extractor;
	private final Logger logger;

	@Inject
	public GateInterceptor(@App final String app, @User final String user,
			final Gate gate, Extractor extractor, final Logger logger) {
		super();
		this.app = app;
		this.user = user;
		this.gate = gate;
		this.logger = logger;
		this.extractor = extractor;
	}

	/**
	 * Intercepta cada chamada de método anotado com {@link Protected}.
	 * 
	 * @param context
	 *            contexto da invocação
	 * @return retorno do método que foi invocado
	 * @throws Throwable
	 *             caso ocorra algum problema na chamada do metodo do contexto,
	 *             ou o usuário não esteja autorizado
	 */
	@AroundInvoke
	public Object invoke(final InvocationContext context) throws Throwable {
		final Object target = context.getTarget();
		final String resource = extractor.getResource(target);
		final String operation;
		final Method method = context.getMethod();
		if (method.isAnnotationPresent(Operation.class)) {
			final Operation annotation = method.getAnnotation(Operation.class);
			operation = annotation.value();
		} else {
			operation = method.getName();
		}
		logger.debug("Interceptando {}#{}", new Object[] {
				target.getClass().getSimpleName(), operation });
		if (gate.isOpen(user, app, resource, operation)) {
			try {
				return context.proceed();
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		} else {
			throw new UnauthorizedException(user, app, resource, operation);
		}
	}
}
