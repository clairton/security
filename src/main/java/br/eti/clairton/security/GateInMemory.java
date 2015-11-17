package br.eti.clairton.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Vetoed;
import javax.validation.constraints.NotNull;

@Vetoed
public class GateInMemory implements Gate {
	private final Map<String, Map<String, Map<String, List<String>>>> authorizations;

	public GateInMemory() {
		this(new HashMap<String, Map<String, Map<String, List<String>>>>());
	}

	public GateInMemory(final Map<String, Map<String, Map<String, List<String>>>> authorizations) {
		super();
		this.authorizations = authorizations;
	}

	@Override
	public Boolean isOpen(@NotNull final String user, @NotNull final String app, @NotNull final String resource, @NotNull final String operation) {
		if (user != null) {
			return authorizations.get(user).get(app).get(resource)
					.contains(operation);
		} else {
			return Boolean.FALSE;
		}
	}

}
