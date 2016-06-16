package br.eti.clairton.security;

import javax.validation.constraints.NotNull;

public interface Service {

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

	/**
	 * Reset the password.
	 * 
	 * 
	 * @param user
	 *            login to reset password
	 * @param password
	 *            string desired to password
	 */
	Boolean reset(@NotNull final String user, final @NotNull String password);

	/**
	 * Create user.
	 * 
	 * 
	 * @param user
	 *            login to create
	 * @param password
	 *            string desired to password
	 */
	Boolean create(@NotNull final String user, final @NotNull String password);

}
