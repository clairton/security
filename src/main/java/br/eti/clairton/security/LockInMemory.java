package br.eti.clairton.security;

import static br.eti.clairton.security.Repository.getRepository;
import static java.util.logging.Level.FINE;
import static java.util.logging.Logger.getLogger;

import java.util.logging.Logger;

import javax.enterprise.inject.Vetoed;

/**
 * In memory sample of implementation {@link Lock}.
 * 
 * @author Clairton Rodrigo Heinzen clairton.rodrigo@gmail.com
 */
@Vetoed
public class LockInMemory implements Lock {
	private Logger logger = getLogger(LockInMemory.class.getSimpleName());
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isValid(final String user, final String password) {
		logger.log(FINE, "Validando senha usuário {}", user);
		return getRepository().containsKey(user) && getRepository().get(user).equals(password);
	}
}
