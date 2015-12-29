package br.eti.clairton.security;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Vetoed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * In memory sample of implementation {@link Lock}.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
@Vetoed
public class LockInMemory implements Lock {
	private Logger logger = LogManager.getLogger(LockInMemory.class);	

	private static final Map<String, String> REPOSITORY = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("admin", "123456");
			put("maria", "123456");
		}
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isValid(final String user, final String password) {
		logger.debug("Validando senha usuário {}", user);
		return REPOSITORY.containsKey(user) && REPOSITORY.get(user).equals(password);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean update(final String user, final String currentPassword, final String newPassword) {
		if(isValid(user, currentPassword)){
			logger.debug("Atualizando senha usuário {}", user);
			REPOSITORY.put(user, newPassword);
			return isValid(user, newPassword);
		}
		return Boolean.FALSE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean reset(final String user, final String password) {
		return update(user, null, password);
	}
}
