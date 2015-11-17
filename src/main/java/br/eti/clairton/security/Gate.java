package br.eti.clairton.security;

import javax.validation.constraints.NotNull;

/**
 * Serviço que verifica se o usuario está autorizado.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
public interface Gate {

	/**
	 * Verifica se o usuario esta habilitado a acessar o recurso especificao.
	 * 
	 * @param user
	 *            aplicacão
	 * @param app
	 *            usuario
	 * @param resource
	 *            recurso
	 * @param operation
	 *            operation
	 * @return true/false
	 */
	public Boolean isOpen(@NotNull final String user, @NotNull final String app, @NotNull final String resource, @NotNull final String operation);
}
