package br.eti.clairton.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;
import javax.security.auth.login.CredentialNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.eti.clairton.cdi.test.CdiJUnit4Runner;

@RunWith(CdiJUnit4Runner.class)
public class LocksmithInMemoryTest {

	private @Inject Locksmith lockSmith;
	private String token;

	@Before
	public void setUp() throws Exception {
		token = lockSmith.create("admin", "123456");
	}

	@Test
	public void testDestroyByToken() throws CredentialNotFoundException {
		lockSmith.invalidate(token);
		assertFalse(lockSmith.isValid(token));
	}

	@Test
	public void testDestroyByUser() throws CredentialNotFoundException {
		lockSmith.invalidate(token);
		assertFalse(lockSmith.isValid(token));
	}

	@Test
	public void testIsValid() throws CredentialNotFoundException {
		assertTrue(lockSmith.isValid(token));
	}

	@Test
	public void testGetUserByToken() throws CredentialNotFoundException {
		assertEquals("admin", lockSmith.getUserByToken(token));
	}

}
