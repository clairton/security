package br.eti.clairton.security;

import static br.eti.clairton.security.Repository.getRepository;
import static org.apache.logging.log4j.LogManager.getLogger;

import javax.enterprise.inject.Vetoed;

import org.apache.logging.log4j.Logger;

/**
 * In memory sample of implementation {@link Lock}.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
@Vetoed
public class LockInMemory implements Lock {
	private Logger logger = getLogger(LockInMemory.class);
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isValid(final String user, final String password) {
		logger.debug("Validando senha usuário {}", user);
		return getRepository().containsKey(user) && getRepository().get(user).equals(password);
	}
}
