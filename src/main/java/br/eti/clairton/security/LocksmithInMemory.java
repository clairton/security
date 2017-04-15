package br.eti.clairton.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Vetoed;
import javax.security.auth.login.CredentialNotFoundException;

/**
 * In memory implementation of {@link Locksmith}.
 * 
 * @author Clairton Rodrigo Heinzen clairton.rodrigo@gmail.com
 */
@Vetoed
public class LocksmithInMemory implements Locksmith {
	private final Lock lock;

	private static final Map<Object, String> REPOSITORY = new HashMap<Object, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("admin", "123");
		}
	};

	/**
	 * Defaul Constructor
	 * 
	 * @param lock
	 *            implementation
	 */
	public LocksmithInMemory(final Lock lock) {
		super();
		this.lock = lock;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T create(final String user, final String password) throws CredentialNotFoundException {
		if (lock.isValid(user, password)) {
			@SuppressWarnings("unchecked")
			final T hash = (T) (new Date().getTime() + "abc");
			REPOSITORY.put(hash, user);
			return hash;
		} else {
			throw new CredentialNotFoundException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidate(final String key) {
		REPOSITORY.remove(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isValid(final String token) {
		return REPOSITORY.containsKey(token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUserByToken(final String token) {
		return REPOSITORY.get(token);
	}
}
