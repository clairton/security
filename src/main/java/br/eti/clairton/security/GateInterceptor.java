package br.eti.clairton.security;

import static java.util.logging.Level.FINE;
import static java.util.logging.Logger.getLogger;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Interceptor para metodos anotados com {@link Protected}.
 * 
 * @author Clairton Rodrigo Heinzen clairton.rodrigo@gmail.com
 */
@Interceptor
@Protected
public class GateInterceptor {
	private final Logger logger = getLogger(GateInterceptor.class.getSimpleName());
	private final String user;
	private final String app;
	private final Gate gate;
	private final Extractor extractor;

	@Inject
	public GateInterceptor(@App final String app, @User final String user, final Gate gate, final Extractor extractor) {
		super();
		this.app = app;
		this.user = user;
		this.gate = gate;
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
		final String operation = extractor.getOperation(context.getMethod());
		logger.log(FINE ,"Interceptando {}#{}", new Object[]{target.getClass().getSimpleName(), operation});
		if (gate.isOpen(user, app, resource, operation)) {
			try {
				return context.proceed();
			} catch (final InvocationTargetException e) {
				throw e.getTargetException();
			}
		} else {
			throw new UnauthorizedException(user, app, resource, operation);
		}
	}
}
