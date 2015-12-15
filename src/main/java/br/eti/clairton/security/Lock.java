package br.eti.clairton.security;

import javax.validation.constraints.NotNull;

/**
 * Lock for users.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
public interface Lock {
	/**
	 * Verify if user is who speak be.
	 * 
	 * @param user
	 *            the user
	 * @param password
	 *            the password
	 * @return true/false
	 */
	Boolean isValid(@NotNull final String user, @NotNull final String password);

	/**
	 * Update user password.
	 * 
	 * @param user
	 *            user to be password update
	 * @param currentPassword
	 *            current password
	 * @param newPassword
	 *            new password
	 * 
	 * @return true/false sucesso ou falha ao atualizar senha
	 */
	Boolean update(@NotNull final String user, @NotNull final String currentPassword, @NotNull final String newPassword);
}
