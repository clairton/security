package br.eti.clairton.security;

import static br.eti.clairton.security.Repository.getRepository;
import static java.lang.Boolean.FALSE;
import static java.util.logging.Level.FINE;
import static java.util.logging.Logger.getLogger;

import java.util.logging.Logger;

import javax.enterprise.inject.Vetoed;

@Vetoed
public class ServiceInMemory implements Service {
	private static final Logger logger = getLogger(ServiceInMemory.class.getSimpleName());
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
			logger.log(FINE, "Atualizando senha usuário {}", user);
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
		logger.log(FINE, "Criado usuário {}", user);
		return lock.isValid(user, password);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean remove(final String user) {
		final Boolean result = getRepository().remove(user) != null;
		if (result) {			
			logger.log(FINE, "Removido usuário {}", user);
		} else {			
			logger.log(FINE, "Usuário {} não encontrado", user);
		}
		return result ;
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
