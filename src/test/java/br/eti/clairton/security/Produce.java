package br.eti.clairton.security;

import javax.enterprise.context.ApplicationScoped;
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
}
