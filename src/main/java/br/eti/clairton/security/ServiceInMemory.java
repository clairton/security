package br.eti.clairton.security;

import static br.eti.clairton.security.Repository.getRepository;
import static java.lang.Boolean.FALSE;
import static org.apache.logging.log4j.LogManager.getLogger;

import javax.enterprise.inject.Vetoed;

import org.apache.logging.log4j.Logger;

@Vetoed
public class ServiceInMemory implements Service {
	private static final Logger logger = getLogger(ServiceInMemory.class);
	private final Lock lock;

	public ServiceInMemory(final Lock lock) {
		super();
		this.lock = lock;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean found(String user) {
		return getRepository().containsKey(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean update(final String user, final String currentPassword, final String newPassword) {
		if (lock.isValid(user, currentPassword)) {
			logger.debug("Atualizando senha usuário {}", user);
			getRepository().put(user, newPassword);
			return lock.isValid(user, newPassword);
		}
		return FALSE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean create(final String user, final String password) {
		getRepository().put(user, password);
		logger.debug("Criado usuário {}", user);
		return lock.isValid(user, password);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean reset(final String user, final String password) {
		getRepository().put(user, password);
		return lock.isValid(user, password);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disable(final String user) {
		getRepository().remove(user);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void enable(final String user) {
		getRepository().put(user, null);
	}
}
