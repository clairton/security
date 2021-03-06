package br.eti.clairton.security;


/**
 * Exceção para lançar quando tentar acessar um recurso sem estar autenticado.
 * 
 * @author Clairton Rodrigo Heinzen clairton.rodrigo@gmail.com
 *
 */
public class UnauthenticatedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor Padrão.
	 */
	public UnauthenticatedException() {
		this("Unauthenticated");
	}

	/**
	 * Construtor com Parametros.
	 * 
	 * @param message
	 *            mensagem a ser mostrada
	 */
	public UnauthenticatedException(final String message) {
		super(message);
	}
}
