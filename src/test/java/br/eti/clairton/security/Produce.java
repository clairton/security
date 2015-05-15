package br.eti.clairton.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ApplicationScoped
public class Produce {

	@Produces
	public Logger produceLogger(final InjectionPoint injectionPoint) {
		final Class<?> type = injectionPoint.getMember().getDeclaringClass();
		final String klass = type.getName();
		return LogManager.getLogger(klass);
	}

	@Produces
	@User
	public String getUser() {
		return "admin";
	}

	@Produces
	@App
	public String getApp() {
		return "Pass";
	}

	@Produces
	@Token
	public String getToken() {
		return "123";
	}

	@Produces
	public Lock getLock() {
		return new LockInMemory();
	}

	@Produces
	public Locksmith getLocksmith(@Default Lock lock) {
		return new LocksmithInMemory(lock);
	}

	@Produces
	public Gate getGate() {
		final Map<String, Map<String, List<String>>> roles = new HashMap<String, Map<String, List<String>>>() {
			private static final long serialVersionUID = 1L;

			{
				put("Pass", new HashMap<String, List<String>>() {
					private static final long serialVersionUID = 1L;
					{
						put("aplicacao", Arrays.asList("create", "update"));
					}
				});
			}
		};
		Map<String, Map<String, Map<String, List<String>>>> authorizations = new HashMap<String, Map<String, Map<String, List<String>>>>();
		authorizations.put("admin", roles);
		return new GateInMemory();
	}
}
