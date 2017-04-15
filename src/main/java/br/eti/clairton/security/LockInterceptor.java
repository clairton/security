package br.eti.clairton.security;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.constraints.NotNull;

/**
 * Interceptor para metodos anotados com {@link Protected}.
 * 
 * @author Clairton Rodrigo Heinzen clairton.rodrigo@gmail.com
 *
 */
@Interceptor
@Authenticated
public class LockInterceptor {
	private final Locksmith locksmith;
	private final String token;

	@Inject
	public LockInterceptor(@NotNull final Locksmith locksmith, @Token final String token) {
		this.locksmith = locksmith;
		this.token = token;
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
		try {
			if (locksmith.isValid(token)) {
				return context.proceed();
			} else {
				throw new UnauthenticatedException("Token " + token + " is invalid");
			}
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}
}
