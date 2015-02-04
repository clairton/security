package br.eti.clairton.security;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

/**
 * In memory sample of implementation {@link Lock}.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 *
 */
@ApplicationScoped
public class LockInMemory implements Lock {

	private static final Map<String, String> REPOSITORY = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("admin", "123456");
		}
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isValid(String user, String password) {
		return REPOSITORY.containsKey(user)
				&& REPOSITORY.get(user).equals(password);
	}

}
